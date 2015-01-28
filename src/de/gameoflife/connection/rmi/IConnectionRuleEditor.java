package de.gameoflife.connection.rmi;

import javafx.collections.ObservableList;
import rmi.data.GameUI;

/**
 * This interface handles the connection to the RuleEditor
 * @author Daniel
 */
public interface IConnectionRuleEditor {
    
    /*
     * Establishs a connection to the RuleEditor.
     * @return true, if the connection is established, else false.
     */
    boolean establishConnectionRuleEditor();
    
    /*
     * Closes the connection to the RuleEditor. Should be called by logout of
     * the user.
     * @return true, if the connection is closed, else false.
     */
    boolean closeConnectionRuleEditor();
    
    /**
     * Generates a new game.
     * @param userId The id of the user.
     * @param name The name which references the new game.
     * @return true, if the game is generated successfully, else false.
     */
    boolean generateNewGame(final int userId, final String name);
    
    /**
     * Copies an existing GameUI object and set the new gameId for the copied
     * game. For an copy a gameId is registered in the database.
     * @param gameId The game which is copied
     * @return The gameId of the copied game object
     */
    int copyGame(final int gameId);
    
    /**
     * Saves the given game.
     * @param gameId The game id of the game which should be saved.
     * @return true, if the game is saved successfully, else false.
     */
    boolean saveGame(final int gameId);

    /**
     * Reloads a saved game.
     * @param userId The if of the user
     * @param gameId The id of the game which should be load.
     * @return true, if the game can be load, else false.
     */
    boolean loadGame(final int userId, final int gameId);
    
    /*
     * Gets a list with all saved games for the given user.
     * @param userId The user from which the games should be shown.
     * @return A list with all ids of the appropriate games.
     */
    ObservableList<GameUI> getGameList(final int userId);
}
