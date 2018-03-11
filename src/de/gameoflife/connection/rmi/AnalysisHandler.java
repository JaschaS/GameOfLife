/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author JScholz
 */
class AnalysisHandler implements IConnectionAnalysis {

    public AnalysisHandler () {
    }

    @Override
    public boolean establishConnectionAnalysis () throws NotBoundException, MalformedURLException, RemoteException {
        return true;
    }

    @Override
    public boolean closeConnectionAnalysis () {
        return true;
    }

    @Override
    public void startAnalysis ( int gameID, int genID ) {
        
    }
    
}
