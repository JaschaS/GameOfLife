/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import rmi.data.rules.Evaluable;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class RulesController {

    @FXML
    protected Button close;
    @FXML
    protected Button up;
    @FXML
    protected Button down;
    @FXML
    protected Label firstText;
    @FXML
    protected Label secondText;
    @FXML
    protected HBox numericPatternField;
    @FXML
    protected ListView rules;
    @FXML
    protected BorderPane numericPattern;
    @FXML
    protected BorderPane rulePattern;

    protected final ObservableList<ListItems> list = FXCollections.observableArrayList();
    //protected List<Evaluable> items;
    protected GameTab parent;
    protected NumberTextField text = new NumberTextField(25, 0, 8, "");

    @FXML
    public void numericRule(ActionEvent event) throws IOException {

        list.add(new ListItems("Numeric Rule", new NumericRule()));
        
        //TODO parent Item hinzufügen

    }

    @FXML
    public void patternRule(ActionEvent event) throws IOException {

        list.add( new ListItems("RulePattern", new RulePattern(new boolean[8])));

    }

    public void showNumericPattern() {

        numericPattern.toFront();
        numericPattern.setVisible(true);

        rulePattern.toBack();
        rulePattern.setVisible(false);

    }

    public void showRulePattern() {

        numericPattern.toBack();
        numericPattern.setVisible(false);

        rulePattern.toFront();
        rulePattern.setVisible(true);

    }

    public void closeActionEvent(EventHandler<ActionEvent> event) {
        close.setOnAction(event);
    }

    public void setParent(GameTab parent) {
        this.parent = parent;
    }

    @FXML
    protected void up(ActionEvent event) throws IOException {

        int index = rules.getSelectionModel().getSelectedIndex();

        if (index > 0) {

            ListItems first = list.remove(index);
            ListItems tmp = list.remove(index - 1);

            list.add(index - 1, first);
            list.add(index, tmp);

            //Evaluable firstItem = items.remove(index);
            //Evaluable secondItem = items.remove(index - 1);

            //items.add(index - 1, firstItem);
            //items.add(index, secondItem);

            rules.getSelectionModel().select(index - 1);

        }

    }

    @FXML
    protected void down(ActionEvent event) throws IOException {

        int index = rules.getSelectionModel().getSelectedIndex();
        //System.out.println(index + " " + list.size());
        if (index < list.size() - 1) {

            ListItems first = list.remove(index);
            //Label tmp = list.remove(index + 1);

            list.add(index + 1, first);
            //list.add(index, tmp);

            //Evaluable firstItem = items.remove(index);

            //items.add(index + 1, firstItem);

            rules.getSelectionModel().select(index + 1);

        }

    }

    class ListItems {
    
        private final Evaluable rule;
        private final Label label;
        
        public ListItems(String text, Evaluable rule) {
            this.rule = rule;
            label = new Label(text);          
        }
        
        public String getText() {
        
            return label.getText();
            
        }
        
        public Evaluable getRule() {
            return rule;
        }

        @Override
        public String toString() {
            return label.getText();
        }
    
    }
    
}
