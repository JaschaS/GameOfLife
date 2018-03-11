/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.collections.ObservableList;
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 */
class RuleEditorHandler implements IConnectionRuleEditor {

    public RuleEditorHandler () {
    }

    @Override
    public boolean establishConnectionRuleEditor () throws NotBoundException, MalformedURLException, RemoteException {
        return true;
    }

    @Override
    public boolean closeConnectionRuleEditor () {
        return true;
    }

    @Override
    public boolean generateNewGame ( int userId, String name ) {
        return true;
    }

    @Override
    public int copyGame ( int gameId ) {
        return 0;
    }

    @Override
    public boolean saveGame ( int gameId ) {
        return true;
    }

    @Override
    public boolean loadGame ( int userId, int gameId ) {
        return true;
    }

    @Override
    public ObservableList<GameUI> getGameList ( int userId ) {
        return null;
    }
    
}
