package de.gameoflife.connection.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Daniel
 */
public interface IConnectionAnalysis {
    /*
     * Establishs a connection to the Analysis.
     * @return true, if the connection is established, else false.
     */

    boolean establishConnectionAnalysis() throws NotBoundException, MalformedURLException, RemoteException;

    /*
     * Closes the connection to the Analysis. Should be called by logout of
     * the user.
     * @return true, if the connection is closed, else false.
     */
    boolean closeConnectionAnalysis();

    /**
     * Starts the analysis for the given game
     *
     * @param gameID The id of the game
     */
    public void startAnalysis(int gameID, int genID);
}
