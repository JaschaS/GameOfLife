/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Daniel
 */
public class ShowAnalysisController implements Initializable{

    @FXML
    private TableView tableView;
    
    @FXML
    private TableColumn pattern;
    
    @FXML
    private TableColumn count;
    
    @FXML
    private TableColumn generation;
    
    @FXML
    private Pane parentPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void setPattern(String data){
        pattern.setText(data);
    }
}
