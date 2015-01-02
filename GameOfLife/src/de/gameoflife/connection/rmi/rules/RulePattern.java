package de.gameoflife.connection.rmi.rules;

import java.util.Arrays;


/**
 * Regel mit Pattern-Matching. Vorgegebenes Muster von Nachbarn muss erfüllt werden.
 * 
 * Auswertung wahr, wenn Nachbarn von Zelle C ... [0 tot, 1 lebendig]
 * 
 * <pre>
 * [0][1][1]
 * [1] C [0]
 * [0][1][1]
 * </pre>
 * 
 * @author tknapp
 *
 * @version 2014-11-26-1
 *
 */
public class RulePattern implements Evaluable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5272202821893329149L;

	/**
	 * Instanziieren mit zu matchendem Muster
	 * @param pattern 
	 * <pre>
	 * [f][t][t]
	 * [t] C [f]
	 * [f][t][t]
	 * </pre>
	 */
	public RulePattern(int[] pattern) {
		if(pattern != null && pattern.length == 8){
			this.pattern = pattern;
		}
	}
	
	@Override
	public boolean evaluate(int[] neigbours) {
		//If neigbours match rule pattern, return true.
		return Arrays.equals(pattern, neigbours);
	}
	
	private int[] pattern;
}
