package rmi.interfaces;

//import gameoflife_server.Client;
import java.rmi.Remote;
import java.rmi.RemoteException;
import queue.data.Generation;

/**
 *
 * @author Daniel
 */
public interface IRemoteUI_Server extends Remote{
    public Generation getNextGeneration(final int userId, final int gameId) throws RemoteException;
    
    public String getAnalyseData(int userId, int gameId) throws RemoteException;
    
    /*public void registerClient(final int userId,final int gameId,  Client client) throws RemoteException;
    
    public boolean isRegistered(final int userId, final int gameId) throws RemoteException;
    
    public String getJSONForClient(Client client, Integer userId) throws RemoteException;*/
}
