/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author JScholz
 */
public class Game {
    
        private final StringProperty gameName;
        private final StringProperty date;
        private final BooleanProperty hasHistory;
        private final BooleanProperty hasAnalysis;
        
        public Game( String gameName, String date, boolean hasHistory, boolean hasAnalysis ) {
            
            this.gameName = new SimpleStringProperty( gameName );
            this.date = new SimpleStringProperty( date );
            
            this.hasHistory = new SimpleBooleanProperty( hasHistory );
            
            this.hasAnalysis = new SimpleBooleanProperty( hasAnalysis );
            
        }
        
        public StringProperty getGameName() {
            return gameName;
        }
        
        public StringProperty getDate() {
            return date;
        }
        
        public BooleanProperty hasHistory() {
            return hasHistory;
        }
        
        public BooleanProperty hasAnalysis() {
            return hasAnalysis;
        }  
        
}
