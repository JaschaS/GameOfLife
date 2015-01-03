package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class NewGameController implements Initializable {

    @FXML private AnchorPane pane;
    @FXML private TextField gameName;
    @FXML private Button create;
    @FXML private Button cancel;
    @FXML private Label error;
    
    /*
    private Stage stage;
    private Scene scene;
    */
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        scene = new Scene(pane);
        
        stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.centerOnScreen();        
     */
    }
    /*
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
    */
    
    public void clearText() {
        
        gameName.setText("");
        error.setText("");
        
    }
    
    public void setFocus() {
    
        gameName.requestFocus();
        
    }
    
    public void setErrorText( String errorText ) {
    
        error.setText(errorText);
        
    }
    
    public void createEvent( EventHandler<ActionEvent> event ) {
        
        create.setOnAction(event);
        
    }
    
    public void cancelEvent( EventHandler<ActionEvent> event ) {
    
        cancel.setOnAction(event);
        
    }
    
    public String getGameName() {
        
        return gameName.getText();
        
    }
    
}
