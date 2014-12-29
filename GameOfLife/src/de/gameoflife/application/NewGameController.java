package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class NewGameController implements Initializable {

    @FXML private AnchorPane pane;
    @FXML private TextField gameName;
    @FXML private Button create;
  
    private Stage stage;
    private Scene scene;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        scene = new Scene(pane);
        
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.centerOnScreen();        
     
    }
    
    public void setOwner(Window owner) {
        
        stage.initOwner(owner);
        
    }
    
    public void hide() {
        
        gameName.setText("");
        stage.hide();
        
    }
    
    public void showAndWait() {
        
        gameName.requestFocus();
        stage.showAndWait();
        
    }
    
    public void onActionEvent( EventHandler<ActionEvent> event ) {
        
        create.setOnAction(event);
        
    }
    
    public String getGameName() {
        
        return gameName.getText();
        
    }
    
}
