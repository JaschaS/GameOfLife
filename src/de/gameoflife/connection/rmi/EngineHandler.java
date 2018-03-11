/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import queue.data.Generation;

/**
 *
 * @author JScholz
 */
class EngineHandler implements IConnectionGameEngine {

    EngineHandler() {
    }
    
    @Override
    public boolean establishConnectionGameEngine () throws NotBoundException, MalformedURLException, RemoteException {
        return false;
    }

    @Override
    public boolean closeConnectionGameEngine () {
        return false;
    }

    @Override
    public boolean startEngine ( int userID, int gameID ) {
        return false;
    }

    @Override
    public boolean stopEngine ( int userID, int gameID ) {
        return false;
    }

    @Override
    public Generation getGeneration ( int userID, int gameID, int genID ) {
        return null;
    }
    
}
