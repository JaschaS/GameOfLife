package de.gameoflife.application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class ErrorDialogController {

    @FXML
    private Label error;
    @FXML
    private Button ok;

    public void okClickEvent(EventHandler<ActionEvent> event) {

        ok.setOnAction(event);

    }

    public void setErrorText(String text) {
        error.setText(text);
    }
    
}
