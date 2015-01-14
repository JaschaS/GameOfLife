/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandlerSingleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class DeleteGameController implements Initializable {

    @FXML private TableView<Game> gameList;
    @FXML private TableColumn<Game, String> name;
    @FXML private TableColumn<Game, String> date;
    @FXML private Button delete;
    @FXML private Button cancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        name.setCellValueFactory((TableColumn.CellDataFeatures<Game, String> param) -> param.getValue().getGameName());
        date.setCellValueFactory((TableColumn.CellDataFeatures<Game, String> param) -> param.getValue().getDate());
        
        ObservableList<Game> data = GameHandlerSingleton.getInstance().loadGame();
    
        gameList.setItems( data );
        
    }    
    
    public void deleteEvent( EventHandler<ActionEvent> event ) {
        
        delete.setOnAction(event);
        
    }
    
    public void cancelEvent( EventHandler<ActionEvent> event ) {
    
        cancel.setOnAction(event);
        
    }
    
}
