/**
 * Aceasta clasa este reprezentarea unei camere.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class Room {
	/**
	 * Index-ul camerei.
	 */
	String room;
	/**
	 * Suprafata camerei.
	 */
	int area;
	/**
	 * Senzorul cu valorile sale inregistrate.
	 */
	Sensor sensor;

    /**
     * Constructor care initializeaza toate campurile.
	 * @param room index camera
	 * @param area suprafata
	 * @param sensor senzorul
	 */
	public Room(String room, int area, Sensor sensor) {
		super();
		this.room = room;
		this.area = area;
		this.sensor = sensor;
	}
}
