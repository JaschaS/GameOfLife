package de.gameoflife.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class CopyGameDialogController {

    @FXML
    private Button ok;
    @FXML
    private Button cancel;
    @FXML
    private Button copyGame;

    public void okClickEvent(EventHandler<ActionEvent> event) {

        ok.setOnAction(event);

    }

    public void cancelClickEvent(EventHandler<ActionEvent> event) {

        cancel.setOnAction(event);

    }

    public void copyGameClickEvent(EventHandler<ActionEvent> event) {

        copyGame.setOnAction(event);

    }

}
