/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class DeathRulesController extends RulesController implements Initializable {

    @FXML
    public void numericRule(ActionEvent event) throws IOException {

        list.add(new Label("Numeric Rule"));

    }

    @FXML
    public void patternRule(ActionEvent event) throws IOException {
        
        list.add(new Label("Pattern Rule"));
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        numericPatternField.getChildren().add( 1, text );
        
        for (int i = 0; i < 10; i++) {
            Label label = new Label("Label " + i);
            
            rules.setPrefHeight(rules.getPrefHeight() + label.getPrefHeight());
            list.add(label);
        }

        rules.setItems(list);

    }

}
