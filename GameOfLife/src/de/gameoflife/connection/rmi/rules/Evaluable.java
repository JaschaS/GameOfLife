package de.gameoflife.connection.rmi.rules;

import java.io.Serializable;


/**
 * Gemeinsame Schnittstelle aller Regeln
 * @author tknapp
 *
 * @version 2014-11-26-1
 *
 */
public interface Evaluable extends Serializable {
	
	/**
	 * Wertet eine Regel für eine Zelle anhand der Nachbarn aus.
	 * @param neigbours 8 Nachbarfelder um Zelle C</br>
	 * <pre>
	 * [0][1][2]
	 * [3] C [4]
	 * [5][6][7]
	 * </pre>
	 * 
	 * @return Ergebnis der Auswertung der Regel (true = Regel ausgelöst)
	 */
	public boolean evaluate(int[] neighbours);

}
