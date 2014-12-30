package de.gameoflife.connection.rmi.rules;


/**
 * Repräsentiert eine Regel, die sich an der Anzahl lebender Nachbarn orientiert.
 * Setzen von mehreren Ranges möglich.
 * 
 * Bsp.:
 *  
 * AnzahlNachbar | auslösen
 * --------------+----------
 *       0       |    0
 *       1       |    0
 *       2       |    1
 *       3       |    1
 *       4       |    0
 *       5       |    0
 *       6       |    1
 *       7       |    1
 *       8       |    0
 * => Regel löst aus bei Anzahl lebender Nachbarn 2 ODER 3 ODER 6 ODER 7
 * 
 * @author tknapp
 * @author tknapp
 * 
 * @version 2014-12-19-1
 *
 */
public class NumericRule implements Evaluable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7973529047689049592L;

	public NumericRule() {
		fields = new boolean[9]; //Cell has eight neighbours + "zero"
	}
	
	/**
	 * Setzt das Verhalten bei num Nachbarn
	 * @param num Anzahl Nachbarn
	 * @param behavior Bei num Nachbarn löst Regel aus ja/nein
	 * @return true = Verhalten gesetzt / false = num ist ungültig
	 */
	public boolean setTriggerAtNumberOfNeighbours(int num, boolean behavior){
		if(num < 0 || num >= fields.length)
			return false;
		fields[num] = behavior;
		return true;
	}
	
	@Override
	public boolean evaluate(int[] neigbours) {
		if(neigbours == null || neigbours.length != 8){
			return false;
		}
		
		int numNeighbours = 0;
		for (int b : neigbours) {
			if(b!=0){
				numNeighbours++;
			}
		}
		
		return fields[numNeighbours];
	}
	
	private boolean[] fields;
}
