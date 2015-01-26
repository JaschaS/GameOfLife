/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.MouseEvent;
import rmi.data.rules.Evaluable;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class DeathRulesController extends RulesController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        firstText.setText("Death at ");
        secondText.setText(" alive neigbours");

        numericPatternField.getChildren().add(1, text);

        /*for (int i = 0; i < 10; i++) {
         Label label = new Label("Label " + i);
            
         rules.setPrefHeight(rules.getPrefHeight() + label.getPrefHeight());
         list.add(label);
         }*/
        rules.setItems(list);

        rules.setOnMouseClicked((MouseEvent event) -> {
            //System.out.println("clicked on " + rules.getSelectionModel().getSelectedItem());
            
            MultipleSelectionModel selection = rules.getSelectionModel();
            
            int index = selection.getSelectedIndex();
            
        });

    }

    public void addItems(List<Evaluable> items) {

        if (items != null) {

            list.clear();
            //this.items = items;

            Iterator<Evaluable> it = items.iterator();

            while (it.hasNext()) {

                Evaluable e = it.next();

                if (e instanceof NumericRule) {
                    list.add(new ListItems("Numeric Rule", e));
                } else {
                    if (e instanceof RulePattern) {
                        list.add(new ListItems("Rule Pattern", e));
                    }
                }

            }
        }

    }
    
}
