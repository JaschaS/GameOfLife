/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Daniel
 */
public class AnalysisPattern {
    private final SimpleStringProperty patternName = new SimpleStringProperty("");
    private final SimpleIntegerProperty countOfPattern = new SimpleIntegerProperty(0);
     
    public AnalysisPattern(){
        this("",0);
    }
     
    public AnalysisPattern(String name, int count){
         patternName.set(name);
         countOfPattern.set(count);
    }

    public SimpleStringProperty getPatternName() {
        return patternName;
    }

    public SimpleIntegerProperty getCountOfPattern() {
        return countOfPattern;
    }
    
    public void setPatternName(String name){
        patternName.set(name);
    }
    
    public void setCountOfPattern(int count) {
        countOfPattern.set(count);
    }
    
}
