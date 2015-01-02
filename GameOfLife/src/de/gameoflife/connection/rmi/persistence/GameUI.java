package de.gameoflife.connection.rmi.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.gameoflife.connection.rmi.rules.Evaluable;

/**
 * Repräsentiert ein konfiguriertes GoL-Spiel mit
 * Regeln, Startgeneration, Randverhalten, UserId, GameId 
 * 
 * 
 * @author tknapp
 * @author sboeing
 * 
 * @version 2014-12-22-1
 *
 */

public class GameUI implements Serializable {
    
    protected int userId;
    protected int gameId;
    protected String gameName;
    protected boolean generation[][]; //Dimensions explicit?
    //protected Object generation;
    //private Set<Evaluable> availableRules;
    //ToDo: Write Methods to compare Rules regarding identity
    protected List<Evaluable> birthRules;
    protected List<Evaluable> deathRules;
    protected boolean borderOverflow;
    protected Date creationDate;
    protected Date modifiedDate;
    
    public GameUI() {
        // TODO Auto-generated constructor stub
    }
    
    public GameUI(int userId, int gameId, String gameName) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameName = gameName;
                
        birthRules = new ArrayList<Evaluable>();
        deathRules = new ArrayList<Evaluable>();
        
        generation = new boolean[][] {new boolean[] {true, false, false}, new boolean[] {false, true, false}, new boolean[] {false, false, true}};
    
        borderOverflow = false;
        
        //Defaulting dates to now
        this.creationDate = new Date();
        this.modifiedDate = this.creationDate;
    }
    
    public GameUI(
            int userId, 
            int gameId, 
            String gameName,
            boolean[][] generation,
            boolean borderOverflow,
            Date creationDate,
            Date mofifiedDate,
            List<Evaluable> birthRules,
            List<Evaluable> deathRules
            ){
        this.userId = userId;
        this.gameId = gameId;
        this.gameName = gameName;
        this.generation = generation;
        this.borderOverflow = borderOverflow;
        this.creationDate = creationDate;
        this.modifiedDate = mofifiedDate;
        this.birthRules = birthRules;
        this.deathRules = deathRules;
    }
    
    public int getUserId() {
        return userId;
    }

    public int getGameId() {
        return gameId;
    }

    public boolean[][] getGeneration() {
        return generation;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    
    public String getGameName(){
        return gameName;
    }
    
    public boolean setStartGen(boolean[][] startGen) {
        if(startGen == null)
            return false;
        if(startGen.length < 3 || startGen[0].length < 3)
            return false;
        this.generation = startGen;
        return true;
    }
    
    public boolean[][] getStartGen() {
        return generation;
    }
    
    public boolean addDeathRule(Evaluable rule){
        return deathRules.add(rule);
    }
    
    public List<Evaluable> getDeathRules() {
        return deathRules;
    }
    
    public boolean addBirthRule(Evaluable rule){
        return birthRules.add(rule);
    }
    
    public List<Evaluable> getBirthRules(){
        return birthRules;
    }
    
    public boolean getBorderOverflow() {
        return borderOverflow;
    }
    
    public void setBorderOverflow(boolean borderOverflow) {
        this.borderOverflow = borderOverflow;
    }
    
    private static final long serialVersionUID = 5237999154163723683L;

    
    @Override
    public String toString() {
        return  
                  "| gameId: " + gameId
                + "| userId: " + userId
                + "| gameName: " + gameName
                + "| generation: " + generation.length + " x " + generation[0].length
                + "| borderOverflow: " + borderOverflow
                + "| creationDate: " + creationDate
                + "| modifiedDate: " + modifiedDate
                ;
    }
    
}
