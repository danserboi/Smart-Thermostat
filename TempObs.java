/**
 * Aceasta clasa reprezinta temperatura pe care senzorul o observa la un anumit moment.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class TempObs implements Comparable<TempObs> {
	/**
	 * Valoarea temperaturii. Are modificatorul de acces private pentru a nu putea fi modificata din exterior.
	 */
	private double temperature;
	/**
	 * Momentul de timp. Are modificatorul de acces private pentru a nu putea fi modificat din exterior.
	 */
	private long timestamp;

	/**
	 * Getter pentru temperatura.
	 * @return double - temperatura
	 */
	public double getTemperature() {
		return temperature;
	}
	/**
	 * Getter pentru timestamp.
	 * @return long - timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * constructor cu doi parametri: temperatura si timestamp
	 * @param temperature temperatura observata
	 * @param timestamp momentul de timp
	 */
	public TempObs(double temperature, long timestamp) {
		this.temperature = temperature;
		this.timestamp = timestamp;
	}

	
	/**
	 * Suprascriem metoda compareTo deoarece sortarea se face in functie de aceasta.
	 * @param comp se compara cu alte valori observate
	 * @return returneaza un intreg: 1 pentru ordinea dorita, 0 pentru egalitate, -1 altfel
	 */
	@Override
	public int compareTo(TempObs comp) {
		/* ordonam intai crescator dupa temperatura si, in caz de egalitate, crescator, dupa timestamp
		 */
		if (temperature > ((TempObs) comp).getTemperature())
			return 1;
		else {
			if (temperature == ((TempObs) comp).getTemperature() && timestamp > ((TempObs) comp).getTimestamp())
				return 1;
			if (temperature == ((TempObs) comp).getTemperature() && timestamp == ((TempObs) comp).getTimestamp())
				return 0;
		}
		return -1;
	}
}
