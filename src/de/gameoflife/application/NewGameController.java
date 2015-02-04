package de.gameoflife.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public final class NewGameController {

    @FXML
    private AnchorPane pane;
    @FXML
    private TextField gameName;
    @FXML
    private Button create;
    @FXML
    private Button cancel;
    @FXML
    private Label error;

    private boolean createGame = true;
    private int gameId = 0;

    public void clearText() {

        gameName.setText("");
        error.setText("");

    }

    public void setFocus() {

        gameName.requestFocus();

    }

    public void setErrorText(String errorText) {

        error.setText(errorText);

    }

    public void createEvent(EventHandler<ActionEvent> event) {

        create.setOnAction(event);

    }

    public void cancelEvent(EventHandler<ActionEvent> event) {

        cancel.setOnAction(event);

    }

    public void createsAGame(boolean value) {
        createGame = value;
    }
    
    public void setGameId(int id) {
        gameId = id;
    }

    public String getGameName() {

        return gameName.getText();

    }

    public boolean createsAGame() {
        return createGame;
    }
    
    public int getGameId() {
        return gameId;
    }

}
