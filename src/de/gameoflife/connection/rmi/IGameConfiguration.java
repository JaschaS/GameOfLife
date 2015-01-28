package de.gameoflife.connection.rmi;

import java.util.Date;
import java.util.List;
import rmi.data.rules.Evaluable;


/**
 * This interface contains all methods to modify a specific game object
 * @author Daniel
 */
public interface IGameConfiguration {
    
    /*
     * Setter for game name.
     * @param gameId the gameId
     * @param gameName The new name of the game
     */
    void setGameName(final int gameId, String gameName);
    
    /*
     * Setter for the start configuration.
     * @param gameId the gameId
     * @param startGen An array which represent the whole game field. The values
     *  which are set to true will be represent as alive, the others will be 
     *  represented as dead.
     */
    void setStartGen(final int gameId, boolean[][] startGen);
    
    /*
     * Adds a death rule to the game. 
     * @param gameId the gameId
     * @param rule The rule which specifies when the cell has to die.
     */
    void addDeathRule(final int gameId, Evaluable rule);
    
    /*
     * Adds a birth rule to the game. 
     * @param gameId the gameId
     * @param rule The rule which specifies when a cell has to born.
     */
    void addBirthRule(final int gameId, Evaluable rule);
    
    /*
     * Configures the border overflow. 
     * @param gameId the gameId
     * @param borderOverflow Specifies if the border overflow should be 
     * activated or not.
     */
    void setBorderOverflow(final int gameId, boolean borderOverflow);
    
    /*
     * Marks the game as modified.
     * @param gameId the if of the game which is modified
     */
    void setNowModified(final int gameId);
    
    /*
     * @return Returns the id of the clients username.
     */
    int getUserId();
    
    /*
     * @return The date when the game was created
     */
    Date getCreationDate(final int gameId);
    
    /*
     * @return the date when the game was modified
     */
    Date getModifiedDate(final int gameId);
    
    /*
     * @return the name of the given game
     */
    String getName(final int gameId);
 
    /*
     * @return the start generation as an boolean array
     */
    boolean[][] getStartGen(final int gameId);
            
    List<Evaluable> getDeathRules(final int gameId);
    
    List<Evaluable> getBirthRules(final int gameId);
            
    boolean getBorderOverflow(final int gameId);
    
    boolean isHistoryAvailable(final int gameId);
    
    boolean isAnalysisAvailable(final int gameId);
    
    String toString(final int gameId);
    
    int getGameId(final String gameName);
    
    void removeDeathRule(final int gameId, final int index);
    
    void removeBirthRule(final int gameId, final int index);
}
