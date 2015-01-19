/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rmi.data.GameUI;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class DeleteGameController extends ListViewWindow implements Initializable {

    @FXML private TableView<GameUI> gameList;
    @FXML private TableColumn<GameUI, String> name;
    @FXML private TableColumn<GameUI, String> date;
    @FXML private Button okButton;
    @FXML private Button cancel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        name.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, String> param) -> new SimpleStringProperty( param.getValue().getGameName() ) );
        date.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, String> param) -> new SimpleStringProperty( param.getValue().getCreationDate().toString() ) );
        
    }    
    
}
