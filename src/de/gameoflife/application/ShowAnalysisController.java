/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Daniel
 */


public class ShowAnalysisController {

    @FXML
    private Label analyseDataLabel;

    @FXML
    private TableView<AnalysisPattern> tableView;

    @FXML
    private TableColumn<AnalysisPattern, String> pattern;

    @FXML
    private TableColumn<AnalysisPattern, String> count;


    @FXML
    public void setLabel(String data) {
        analyseDataLabel.setText(data);
    }    
    
    public void setItemsToAdd(List<AnalysisPattern> list){
        tableView.getItems().setAll(list);
        
    }

}
