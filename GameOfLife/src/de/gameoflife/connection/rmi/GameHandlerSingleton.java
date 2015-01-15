/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rmi;

import de.gameoflife.application.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author JScholz
 */
public final class GameHandlerSingleton {
    
    private GameHandler connection = null;
    
    private static GameHandlerSingleton instance;
    
    private GameHandlerSingleton() {
    
        connection = new GameHandler();
        connection.establishConnectionRuleEditor();
        
    }
    
    public static void init( String url ) {
        if( instance == null ) instance = new GameHandlerSingleton();
    }
    
    public static GameHandlerSingleton getInstance() {
        
        return instance;
        
    }
    
    public static void closeConnection() {
        
        if( instance != null ) {
            
            instance.connection.closeConnection();
            
            instance = null;
            
        }
        
    }
    
    public ObservableList<Game> loadGame() {
        
        ObservableList<Game> data = FXCollections.observableArrayList();
        
        data.add( new Game("MyGame", "2014-11-2", true, true ) );
        data.add( new Game("MyGame2", "2014-11-3", true, false) );
        data.add( new Game("MyGame3", "2014-11-4", false, true) );
        
        return data;
        
    }
    
}
