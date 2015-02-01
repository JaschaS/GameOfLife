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
    private final SimpleStringProperty direction = new SimpleStringProperty("");
    private final SimpleStringProperty avg = new SimpleStringProperty("");
  
    private final SimpleStringProperty countOfPattern = new SimpleStringProperty("");
    private final SimpleStringProperty periode = new SimpleStringProperty("");
    private final SimpleStringProperty startGenerationNo = new SimpleStringProperty("");
    private final SimpleStringProperty startXCoordinate = new SimpleStringProperty("");
    private final SimpleStringProperty startYCoordinate = new SimpleStringProperty("");
    private final SimpleStringProperty generationCount = new SimpleStringProperty("");
    
    
    
    public AnalysisPattern(){
        this("","","","","","","","","");
    }
     
    public AnalysisPattern(String name, String direction, String avg, String count, String periode, String x, String y, String genNo, String genCount){
        //Raise readability
        if (direction == null){
            direction = "";
        }
        if (avg.equals("-1")){
            avg = "";
        }
        if (periode.equals("-1")){
            periode = "";
        }
        if (x.equals("-1")){
            x = "";
        }
        if (y.equals("-1")){
            y = "";
        }
        if (genNo.equals("-1")){
            genNo = "";
        }
        if (genCount.equals("-1")){
            genCount = "";
        }
        
        this.patternName.set(name);
        this.direction.set(direction);
        this.avg.set(avg);
        
        this.countOfPattern.set(count);
        this.periode.set(periode);
        this.startXCoordinate.set(x);
        this.startYCoordinate.set(y);
        this.startGenerationNo.set(genNo);
        this.generationCount.set(genCount);
    }

    public String getPatternName() {
        return patternName.getValue();
    }

    public String getCountOfPattern() {
        return countOfPattern.getValue();
    }
    
    public void setPatternName(String name){
        patternName.set(name);
    }
    
    public void setCountOfPattern(String count) {
        countOfPattern.set(count);
    }

    public String getDirection() {
        return direction.getValue();
    }

    public String getAvg() {
        return avg.getValue();
    }

    public String getPeriode() {
        return periode.getValue();
    }

    public String getStartGenerationNo() {
        return startGenerationNo.getValue();
    }

    public String getStartXCoordinate() {
        return startXCoordinate.getValue();
    }

    public String getStartYCoordinate() {
        return startYCoordinate.getValue();
    }

    public String getGenerationCount() {
        return generationCount.getValue();
    }
    
    
    
}
