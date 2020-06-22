import java.util.ArrayList;

/**
 * Aceasta clasa reprezinta senzorul impreuna cu valorile temperaturii, respectiv umiditatii, inregistrate in ultimele 24 de ore.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class Sensor {
	/**
	 * Denumirea senzorului.
	 */
	String id;
	/**
	 * Lista de temperaturi din intervalele ultimelor 24 de ore. Indicele 0 reprezinta cea mai recenta ora, iar 23 - ultima.
	 */
	ArrayList <TempsInterval> tempList;
	/**
	 * Lista cu valorile umiditatii din intervalele ultimelor 24 de ore. Indicele 0 reprezinta cea mai recenta ora, iar 23 - ultima.
	 */
	ArrayList <HumsInterval> humList;
	/**
	 * Constructor care are ca parametru denumirea senzorului si care initializeaza lista de temperaturi, respectiv cu valorile umiditatii, din ultimele 24 de ore.
	 * @param id denumirea senzorului
	 */
	public Sensor(String id) {
		this.id = id;
		tempList = new ArrayList<TempsInterval>();
		for(int i = 0; i < 24; i++)
			tempList.add(new TempsInterval());
		humList = new ArrayList<HumsInterval>();
		for(int i = 0; i < 24; i++)
			humList.add(new HumsInterval());		
	}
}
