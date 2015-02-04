package de.gameoflife.connection.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import queue.data.Generation;

/**
 *
 * @author Daniel
 */
public interface IConnectionGameEngine {

    /*
     * Establishs a connection to the RuleEditor.
     * @return true, if the connection is established, else false.
     */
    boolean establishConnectionGameEngine() throws NotBoundException, MalformedURLException, RemoteException;

    /*
     * Closes the connection to the RuleEditor. Should be called by logout of
     * the user.
     * @return true, if the connection is closed, else false.
     */
    boolean closeConnectionGameEngine();

    /*
     * Sends a signal to the engine that it can start the calcutation for the 
     * given user and game.
     * @param userID The id of the user
     * @param gameID The if of the game
     */
    public boolean startEngine(final int userID, final int gameID);

    /*
     * Sends a signal to the engine that it can stop the calcutation for the 
     * given user and game.
     * @param userID The id of the user
     * @param gameID The if of the game
     */
    public boolean stopEngine(final int userID, final int gameID);

    /*
     * Returns a specific generation to look into the past.
     * @param userID The id of the user
     * @param gameID the id of the game
     * @param genID The id of the generation which should be returned.
     * @return The specific generation
     */
    Generation getGeneration(final int userID, final int gameID, final int genID);

}
