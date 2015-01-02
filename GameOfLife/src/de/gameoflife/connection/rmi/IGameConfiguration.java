package de.gameoflife.connection.rmi;

import de.gameoflife.connection.rmi.rules.Evaluable;

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
}
