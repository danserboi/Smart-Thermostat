import java.util.ArrayList;
/**
 * Clasa reprezinta valorile umiditatii observate intr-un interval de o ora.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class HumsInterval {
	/**
	 * Avem o lista cu valorile umiditatii observate.
	 */
	ArrayList <HumObs> humsIntervalList;
	/**
	 * Constructor fara parametru care initializeaza lista.
	 */
	public HumsInterval() {
		humsIntervalList = new ArrayList<HumObs>();
	}
}
