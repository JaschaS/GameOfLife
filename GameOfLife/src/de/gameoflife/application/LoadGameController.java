/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
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
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class LoadGameController implements Initializable {

    @FXML private TableView<Game> gameList;
    @FXML private TableColumn<Game, String> name;
    @FXML private TableColumn<Game, String> date;
    @FXML private TableColumn<Game, Boolean> history;
    @FXML private TableColumn<Game, Boolean> analysis;
    @FXML private Button load;
    @FXML private Button cancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setCellValueFactory((TableColumn.CellDataFeatures<Game, String> param) -> param.getValue().getGameName());
        date.setCellValueFactory((TableColumn.CellDataFeatures<Game, String> param) -> param.getValue().getDate());
            
        history.setCellFactory( celldata -> new CheckBoxTableCell<>() );
        history.setCellValueFactory( (TableColumn.CellDataFeatures<Game, Boolean> param) -> param.getValue().hasHistory() );
        
        analysis.setCellFactory( celldata -> new CheckBoxTableCell<>() );
        analysis.setCellValueFactory( (TableColumn.CellDataFeatures<Game, Boolean> param) -> param.getValue().hasAnalysis() );
        
        ObservableList<Game> data = GameHandler.getInstance().getGameList( User.getInstance().getId() );
    
        gameList.setItems( data );
        
    }    
    
    public void loadEvent( EventHandler<ActionEvent> event ) {
        
        load.setOnAction(event);
        
    }
    
    public void cancelEvent( EventHandler<ActionEvent> event ) {
    
        cancel.setOnAction(event);
        
    }
    
    public Game getSelectedGame() {
    
        return gameList.getSelectionModel().getSelectedItem();
        
    }
    
}
