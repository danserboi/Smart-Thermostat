import java.util.ArrayList;
/**
 * Clasa reprezinta totalitea temperaturilor observate intr-un interval de o ora.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class TempsInterval {
	/**
	 * Avem o lista cu temperaturile observate.
	 */
	ArrayList <TempObs> tempsIntervalList;
	/**
	 * Constructor fara parametru care initializeaza lista.
	 */
	public TempsInterval() {
		tempsIntervalList = new ArrayList<TempObs>();
	}
}
