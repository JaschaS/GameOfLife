package de.gameoflife.connection.rmi.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import de.gameoflife.connection.rmi.persistence.GameUI;

public interface IRemoteRuleEditor extends Remote {

	/**
	 * Erzeugt eine neues GameUI Objekt mit Default-Werten.
	 * Objekt wird von UI gefüllt.
	 * @param userId
	 * @param name of new game (given by user)
	 * @return
	 * @throws RemoteException
	 */
	GameUI generateNewGame(final int userId, final String name) throws RemoteException;
	
	/**
	 * Speichert ein GameUI Objekt in der internen DB ab. Wird synchron von der UI aufgerufen.
	 * TODO: Exception Handling und Evaluation
	 * @param game
	 * @return
	 * @throws RemoteException
	 */
	boolean saveGame(final GameUI game) throws RemoteException;
	
	/**
	 * Gibt ein gespeichertes GameUI Objekt zurück.
	 * (Verwendung durch Engine bei Spielstart)
	 * @param gameId
	 * @return
	 * @throws RemoteException
	 */
	GameUI getGameObject(final int gameId) throws RemoteException;
}
