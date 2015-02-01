/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    
    public void setItemsToAdd(List<AnalysisPattern> list){
        analyseDataLabel.setText("Founded patterns:");
        tableView.getItems().setAll(list);
    }

}
