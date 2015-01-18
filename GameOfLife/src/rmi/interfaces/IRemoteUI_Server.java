package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import queue.data.Generation;

/**
 *
 * @author Daniel
 */
public interface IRemoteUI_Server extends Remote{
    public Generation getNextGeneration(final int userId, final int gameId) throws RemoteException;
}
