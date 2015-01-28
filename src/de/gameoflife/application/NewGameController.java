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

    public String getGameName() {

        return gameName.getText();

    }

}
