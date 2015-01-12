/**
 * Diese Klasse dient der Uebermittlung eines Loeschbefehls von der Visualisierung zur Analyse.
 */
package de.gameoflife.connection.rabbitmq;

import java.io.Serializable;

/**
 * @author Jari Simon, Benjamin Pfeiffer
 *
 */
public class DeleteInfo implements Serializable{
	
	private static final long serialVersionUID = -8358591753708391884L;

	public enum DeleteType{USER, GAME};
	/**
	 * DeleteType.USER, zum Loeschen aller Spiele eines Nutzers.
	 * DeleteType.GAME, zum Loeschen eines Spiels eines Nutzers.
	 */
	private DeleteType delType;
	/**
	 * ID des Spielers
	 */
	private int userID;
	/**
	 * ID des Spiels, das geloescht werden soll
	 */
	private int gameID;
	
	public DeleteInfo(int userID){
		this.userID = userID;
		delType = DeleteType.USER;
	}
	
	public DeleteInfo(int userID, int gameID){
		this.userID = userID;
		this.gameID = gameID;
		delType = DeleteType.GAME;
	}

	public DeleteType getDelType() {
		return delType;
	}

	public int getUserID() {
		return userID;
	}
	
	public int getGameID() {
		return gameID;
	}	
}
