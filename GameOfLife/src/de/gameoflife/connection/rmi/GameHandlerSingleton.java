/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.connection.rmi;

import de.gameoflife.application.Game;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author JScholz
 */
public final class GameHandlerSingleton {
    
    private final GameHandler connection;
    
    private static GameHandlerSingleton instance;
    
    private GameHandlerSingleton() {
    
        connection = new GameHandler();
        //connection.establishConnection();
        
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
    
    public boolean newGame( int gameId, String gameName ) {
    
        return connection.generateNewGame(gameId, gameName);
    
    }
    
    public boolean saveGame( int gameId ) {
    
        return connection.saveGame(gameId);
    
    }
    
    public void deleteGame() {
    
        
        
    }
    
    public void loadGame() {
    
        
        
    }
    
    public ObservableList<Game> getGameList( int userId ) {
        
        List<Integer> gameIds = connection.getGameList(userId);
        Iterator<Integer> it = gameIds.iterator();
        
        ObservableList<Game> data = FXCollections.observableArrayList();
        
        int gameId;
        Game g;
        
        while( it.hasNext() ) {
        
            gameId = it.next();
            
            g = new Game( 
                    connection.getName(gameId),
                    connection.getCreationDate(gameId).toString(),
                    connection.isHistoryAvailable(gameId),
                    connection.isAnalysisAvailable(gameId)
            );
            
            data.add( g );
        
        }
        
        return data;
        
    }
    
}
