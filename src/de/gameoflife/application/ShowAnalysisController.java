/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import javafx.fxml.FXML;;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Daniel
 */
public class ShowAnalysisController{

    @FXML
    private Label analyseDataLabel;
    
    @FXML
    private TableView tableView;
    
    @FXML
    private TableColumn pattern;
    
    @FXML
    private TableColumn count;
    
    @FXML
    private TableColumn generation;
    
    @FXML
    private GridPane gridPane;
    
    @FXML
    public void setLabel(String data){
        analyseDataLabel.setText(data);
    }
}
