import java.util.ArrayList;
import java.util.Collections;

/**
 * Aceasta clasa reprezinta casa cu tot sistemul sau propriu. 
 * @author SERBOI FLOREA-DAN 325CB
 */
public class House {
	/**
	 * Numarul de camere.
	 */
	int noRooms;
	/**
	 * Temperatura globala dorita.
	 */
	double globalTemp;
	/**
	 * Umiditatea globala dorita.
	 */
	double globalHum;
	/**
	 * Momentul de timp de referinta.
	 */
	long timestampRef;
	/**
	 * Lista camerelor.
	 */
	ArrayList <Room> rooms;
	/**
	 * Constructor pentru casa fara senzori de umiditate
	 * @param noRooms numarul de camere
	 * @param globalTemp temperatura globala dorita
	 * @param timestampRef momentul de timp de referinta
	 */
	public House(int noRooms, double globalTemp, long timestampRef) {
		super();
		this.noRooms = noRooms;
		this.globalTemp = globalTemp;
		this.timestampRef = timestampRef;
		this.rooms = new ArrayList<Room>();
	}
	
	/**
	 * Constructor pentru casa cu senzori de umiditate
	 * @param noRooms numarul de camere
	 * @param globalTemp temperatura globala dorita
	 * @param globalHum umiditatea globala dorita
	 * @param timestampRef momentul de timp de referinta
	 */
	public House(int noRooms, double globalTemp, double globalHum, long timestampRef) {
		super();
		this.noRooms = noRooms;
		this.globalTemp = globalTemp;
		this.globalHum = globalHum;
		this.timestampRef = timestampRef;
		this.rooms = new ArrayList<Room>();
	}

	/**
	 * Functia returneaza indicele intervalului de timp (de o ora) unde trebuie incadrata valoarea observata
	 * @param currentTimestamp momentul de timp cand s-a realizat masuratoarea
	 * @param timestampRef momentul de timp de referinta
	 * @return long indicele
	 */
	public long intervalIndex(long currentTimestamp, long timestampRef)
	{
		return (timestampRef - currentTimestamp) / 3600;
	}
	/**
	 * Functia introduce valoarea temperaturii masurate intr-o camera in lista temperaturilor, mapata la intervalul corespunzator
	 * @param sensorId denumirea senzorului
	 * @param timestamp momentul de timp al masuratorii
	 * @param temp valoarea temperaturii
	 */
	void observe(String sensorId, long timestamp, double temp)
	{
		/*verific daca momentul de timp este valid*/
		if(timestamp <= timestampRef && timestamp >= timestampRef - 3600*24)
			for(Room room : rooms)
			{
				if(room.sensor.id.equals(sensorId))
				{	
					long index = intervalIndex(timestamp, timestampRef);
					TempsInterval interval = room.sensor.tempList.get((int)index);
					TempObs observedTemp = new TempObs(temp, timestamp);
					interval.tempsIntervalList.add(observedTemp);
					/*dupa adaugare, sortam valorile dupa cum ne dorim*/
					Collections.sort(interval.tempsIntervalList);
					break;
				}
			}
	}
	/**
	 * Functia introduce valoarea umiditatii masurate intr-o camera in lista cu valori ale umiditatii, mapata la intervalul corespunzator
	 * @param sensorId denumirea senzorului
	 * @param timestamp momentul de timp al masuratorii
	 * @param hum valoarea umiditatii
	 */
	void observeH(String sensorId, long timestamp, double hum)
	{
		/*verific daca momentul de timp este valid*/
		if(timestamp <= timestampRef && timestamp >= timestampRef - 3600*24)
			for(Room room : rooms)
			{
				if(room.sensor.id.equals(sensorId))
				{	
					long index = intervalIndex(timestamp, timestampRef);
					HumsInterval interval = room.sensor.humList.get((int)index);
					HumObs observedHum = new HumObs(hum, timestamp);
					interval.humsIntervalList.add(observedHum);
					/*dupa adaugare, sortam valorile dupa cum ne dorim*/
					Collections.sort(interval.humsIntervalList);
					break;
				}
			}
	}
	/**
	 * Functia stabileste daca centrala termica va trebui pornita.
	 * @return String YES sau NO
	 */
	String triggerHeat()
	{
		double weightedMean = 0, hWeightedMean = 0;
		int totalArea = 0;
		/*pentru fiecare camera, calculam in functie de ultima ora inregistrata*/
		for(Room room : rooms)
		{
			/*cautam ultima ora cu temperatura inregistrata*/
			int i = 0;
			TempsInterval interval = room.sensor.tempList.get(0);
			do
			{
			interval = room.sensor.tempList.get(i);
			i++;
			}	
			while(interval.tempsIntervalList.size() == 0 && i < 24);
			/*daca avem valori inregistrate, adaugam la medie*/
			if(interval.tempsIntervalList.size() != 0)
			{
				/*temperatura minima este pe prima pozitie, deoarece lista este sortata crescator*/
				double minTemp = interval.tempsIntervalList.get(0).getTemperature();
				double weight = room.area;
				/*ponderea este data suprafata camerei*/
				weightedMean += minTemp*weight;
				totalArea += weight;
			}
			/*cautam ultima ora cu umiditate inregistrata*/
			i = 0;
			HumsInterval hInterval = room.sensor.humList.get(0);
			do
			{
			hInterval = room.sensor.humList.get(i);
			i++;
			}	
			while(hInterval.humsIntervalList.size() == 0 && i < 24);
			/*daca avem valori inregistrate, adaugam la medie*/
			if(hInterval.humsIntervalList.size() != 0)
			{
				/*umiditatea maxima este pe prima pozitie, deoarece lista este sortata descrescator*/
				double maxHum = hInterval.humsIntervalList.get(0).getHumidity();
				double hWeight = room.area;
				hWeightedMean += maxHum * hWeight;
			}
		}
		/*obtinem media ponderata pentru temperatura, respectiv pentru umiditate*/
		weightedMean /= totalArea;
		hWeightedMean /= totalArea;
		//System.out.print(weightedMean+" ");
		//System.out.println(globalTemp);
		/*daca media umiditatii este mai mare decat umiditatea dorita, atunci centrala nu va fi niciodata pornita*/
		if(hWeightedMean > globalHum)
			return "NO\n";
		/*daca media temperaturii este mai mica decat temperatura dorita, atunci se va porni centrala*/
		if(weightedMean < globalTemp)
			return "YES\n";
		/*in celelalte situatii, nu se va porni*/
		else
			return "NO\n";
	}
	/**
	 * Functia listeaza valorile temperaturilor inregistrate intr-o camera.
	 * @param roomIndex indicele camerei
	 * @param startTimestamp momentul de timp de start
	 * @param stopTimestamp momentul de timp de stop
	 * @return creeaza un String care contine contine denumirea camerei si lista de valori cu precizie de 2 zecimale
	 */
	String list(int roomIndex, long startTimestamp, long stopTimestamp)
	{
		/*ne folosim de StringBuilder pentru a crea String-ul final*/
		StringBuilder sb = new StringBuilder();
		sb.append("ROOM");
		sb.append(roomIndex);
		/*daca momentele de timp sunt in afara valorilor posibile, ajustam cu marginile*/
		if(startTimestamp < timestampRef - 3600*24)
			startTimestamp = timestampRef - 3600*24;
		if(stopTimestamp > timestampRef)
			stopTimestamp = timestampRef;
		/*calculam index-ul intervalului de timp pentru fiecare moment de timp*/
		int startIndex = (int) intervalIndex(startTimestamp, timestampRef);
		int stopIndex = (int) intervalIndex(stopTimestamp, timestampRef);
		/*identificam camera*/
		Room room = rooms.get(roomIndex - 1);
		/*tinem minte temperatura de dinainte pentru a nu afisa duplicat*/
		double auxTemp = 0;
		/*construim un string cu valorile fiecarui interval*/
		for (int i = stopIndex; i <= startIndex; i++)
		{
			/*identificam intervalul*/
			TempsInterval tempInterval = room.sensor.tempList.get(i);
			for(TempObs temp : tempInterval.tempsIntervalList)
			{
				/*desi se afla in intervalul de timp corespunzator, trebuie sa ne asiguram ca nu se depasesc momentele de timp*/
				if(temp.getTimestamp() >= startTimestamp && temp.getTimestamp() <= stopTimestamp && auxTemp != temp.getTemperature())
				{
					sb.append(" ");
					String temp2Dec = String.format(String.format("%.2f", temp.getTemperature()));
					sb.append(temp2Dec);
					auxTemp = temp.getTemperature();
				}
			}
		}
		sb.append("\n");
		/*returnam String-ul care se doreste scris in fisier*/
		return sb.toString();
	}
}
