
package de.gameoflife.application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author JScholz
 * 
 * @version 2014-12-11-1
 * 
 */
public final class GameOfLifeController {

    @FXML private TabPane tabPane;
    @FXML private Label username;
    private GameOfLife application;

    @FXML
    public void logout(ActionEvent event) throws IOException {
        
        //System.out.println("Logout");
        
        application.logout();
        
    }
    
    @FXML
    public void newGame(ActionEvent event) throws IOException {
        
    }
    
    @FXML
    public void loadGame(ActionEvent event) throws IOException {
        
    }
        
    @FXML
    public void deleteGame(ActionEvent event) throws IOException {
        
    }
    
    public void setRootApplication( GameOfLife rootApp ) {
        application = rootApp;
    }
    
}
