/**
 * Aceasta clasa reprezinta umiditatea pe care senzorul o observa la un anumit moment.
 * @author SERBOI FLOREA-DAN 325CB
 */
public class HumObs implements Comparable<HumObs>{
	/**
	 * Valoarea umiditatii. Are modificatorul de acces private pentru a nu putea fi modificata din exterior.
	 */
	private double humidity;
	/**
	 * Momentul de timp. Are modificatorul de acces private pentru a nu putea fi modificat din exterior.
	 */
	private long timestamp;
	
	/**
	 * Getter pentru umiditate.
	 * @return double - umiditate
	 */
	public double getHumidity() {
		return humidity;
	}
	/**
	 * Getter pentru timestamp.
	 * @return long - timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}
	/**
	 * constructor cu doi parametri: umiditate si timestamp
	 * @param humidity umiditatea observata
	 * @param timestamp momentul de timp
	 */
	public HumObs(double humidity, long timestamp) {
		this.humidity = humidity;
		this.timestamp = timestamp;
	}
	
	/**
	 * Suprascriem metoda compareTo deoarece sortarea se face in functie de aceasta.
	 * @param comp se compara cu alte valori observate
	 * @return returneaza un intreg: 1 pentru ordinea dorita, 0 pentru egalitate, -1 altfel
	 */
    @Override
    public int compareTo(HumObs comp) {
        /*ordonam intai descrescator dupa umiditate si, in caz de egalitate, crescator, dupa timestamp*/
        if(humidity < ((HumObs)comp).getHumidity())
        	return 1;
        else
        {
        	if(humidity == ((HumObs)comp).getHumidity() && timestamp > ((HumObs)comp).getTimestamp())
        		return 1;
    		if(humidity == ((HumObs)comp).getHumidity() && timestamp == ((HumObs)comp).getTimestamp())
    			return 0;
        }
    	return -1;
    }
}
