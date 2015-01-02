package de.gameoflife.connection.rmi;

import java.util.List;

/**
 * This interface handles the connection to the RuleEditor
 * @author Daniel
 */
public interface IConnectionRMI {
    
    /*
     * Establishs a connection to the RuleEditor.
     * @return true, if the connection is established, else false.
     */
    boolean establishConnection();
    
    /*
     * Closes the connection to the RuleEditor. Should be called by logout of
     * the user.
     * @return true, if the connection is closed, else false.
     */
    boolean closeConnection();
    
    /**
     * Generates a new game.
     * @param userId The id of the user.
     * @param name The name which references the new game.
     * @return true, if the game is generated successfully, else false.
     */
    boolean generateNewGame(final int userId, final String name);
    
    /*
     * Deletes the given game.
     * @param gameId The id of the game which should be deleted.
     * @return true, if the game is deleted successfully, else false.
     */
    boolean deleteExistingGame(final int gameId);
    
    /**
     * Saves the given game.
     * @param gameId The game id of the game which should be saved.
     * @return true, if the game is saved successfully, else false.
     */
    boolean saveGame(final int gameId);

    /**
     * Reloads a saved game.
     * @param gameId The id of the game which should be load.
     * @return true, if the game can be load, else false.
     */
    boolean loadGame(final int gameId);
    
    /*
     * Gets a list with all saved games for the given user.
     * @param userId The user from which the games should be shown.
     * @return A list with all appropriate games.
     * TODO: Der teil ist noch nicht vom ruleeditor behandelt. fehler oder macht das engine?
     */
    List<Integer> getGameList(final int userId);
}
