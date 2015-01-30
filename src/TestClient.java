/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.interfaces.IRemoteAnalyseData;

/**
 *
 * @author Daniel
 */
public class TestClient implements Serializable{
        
    public static void main(String args[]){
        try {
            IRemoteAnalyseData server = (IRemoteAnalyseData) Naming.lookup(IRemoteAnalyseData.FULLSERVICEIDENTIFIER);
            
            server.sendAnalyseData(3, 3, "Test Analyse Daten2");
            
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
