/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

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
            
            server.sendAnalyseData(3, 3, "{\"beacon\":[],\"blinker\":[],\"glider\":[{\"direction\":\"left/bottom\",\"start-x-coordinate\":4,\"start-y-coordinate\":5,\"start-gerneration-no.\":102,\"periode\":8},{\"direction\":\"right/top\",\"start-x-coordinate\":25,\"start-y-coordinate\":96,\"start-gerneration-no.\":523,\"periode\":4}],\"heavyweight-spaceship\":[],\"lightweight-spaceship\":[],\"middleweight-spaceship\":[],\"beehive\":[],\"block\":[]}");
            
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
