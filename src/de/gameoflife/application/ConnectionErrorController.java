
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
public final class ConnectionErrorController {

    @FXML private Button error;
    
    public void addActionEvent(EventHandler<ActionEvent> event) {
        if(error != null) error.setOnAction(event);
    }

}
