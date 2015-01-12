package rmi.interfaces;

/**
 *
 * @author Daniel
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteAnalyseData extends Remote{
    /*
     * This method can be called to tranfer the analyse data to the UI.
     * @param data A data JSON-object represented as a String. The String
     * contains key/value pairs. The keys are the founded patterns and the 
     * values are the count of the single patterns.
     */
    void sendAnalyseData(String data)throws RemoteException;
}
