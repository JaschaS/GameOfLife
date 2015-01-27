/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 *
 */
public class DeathRulesController extends RulesController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
      
        numericPatternController.setFirstText("Death at ");
        
        rules.setItems(list);

        rules.setOnMouseClicked((MouseEvent event) -> {
            
            MultipleSelectionModel selection = rules.getSelectionModel();
            
            int index = selection.getSelectedIndex();

            if (index >= 0 && index < list.size()) {
                
                showPattern(list.get(index).getRule());

            }

        });

    }

    @Override
    public void numericRule(ActionEvent event) throws IOException {

        parent.getGame().addDeathRule(new NumericRule());
        addItems(parent.getGame().getDeathRules());

    }

    @Override
    public void patternRule(ActionEvent event) throws IOException {

        parent.getGame().addDeathRule(new RulePattern(new boolean[8]));
        addItems(parent.getGame().getDeathRules());

    }

}