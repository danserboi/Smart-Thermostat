import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Aceasta este clasa main. Se citeste input-ul din fisier, se efectueaza operatiile si se scrie in fisierul de output.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class Tema2 {
	/**
	 * In metoda main se efectuaza citirea din fisier, efectuarea operatiilor si scrierea in noul fisier.
	 * @param args argumentul functiei main
	 * @throws Exception arunc exceptiile.
	 */
	public static void main(String[] args) throws Exception{
		/*deschidem fisierul si citim line cu line*/
		File input = new File("therm.in");
		BufferedReader buffer = new BufferedReader(new FileReader(input));
		File output = new File("therm.out");
	    PrintWriter printWriter = new PrintWriter(output);   
		/*citim prima line si extragem numarul de camere, temperatura globala si timestamp-ul de referinta*/
	    /*tratam separat daca avem si umiditate*/
		String line = buffer.readLine();
		String[] fields = line.split(" ");
		int noRooms =  Integer.parseInt(fields[0]);
		double globalTemp = Double.parseDouble(fields[1]);
		long timestampRef;
		House house;
		/*verificam daca avem sau nu senzor de umiditate si apelam constructorul corespunzator*/
		if(fields.length == 4)
		{
			double globalHum = Double.parseDouble(fields[2]);
			timestampRef = Long.parseLong(fields[3]);
			house = new House(noRooms, globalTemp, globalHum, timestampRef);
		}
		else
		{
			timestampRef = Long.parseLong(fields[2]);
			house = new House(noRooms, globalTemp, timestampRef);
		}
		for(int i = 0; i < noRooms; i++){
			line = buffer.readLine();
			/**extragem campurile camerei**/
			fields = line.split(" ");
			Sensor sensor = new Sensor(fields[1]);
			Room room = new Room(fields[0], Integer.parseInt(fields[2]), sensor);
			house.rooms.add(room);
		}
		/** citim line cu line operatiile**/
		while ((line = buffer.readLine()) != null) {
			/**extragem campurile unei operatii dupa care efectuam operatia**/
			fields = line.split(" ");
			if(fields[0].equals("OBSERVE"))
				house.observe(fields[1], Long.parseLong(fields[2]), Double.parseDouble(fields[3]));
			if(fields[0].equals("OBSERVEH"))
				house.observeH(fields[1], Long.parseLong(fields[2]), Double.parseDouble(fields[3]));
			if(fields[0].equals("TRIGGER"))
				printWriter.print(house.triggerHeat());
			if(fields[0].equals("TEMPERATURE"))
				house.globalTemp = Double.parseDouble(fields[1]);
			if(fields[0].equals("LIST"))
			{
				printWriter.print(house.list(Integer.parseInt(fields[1].substring(4)), Long.parseLong(fields[2]), Long.parseLong(fields[3])));
			}
		}
		printWriter.close();
		buffer.close();
	}
}
