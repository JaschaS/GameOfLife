/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

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
    protected HBox numericPatternField;
    @FXML
    protected ListView rules;

    protected final ObservableList<Label> list = FXCollections.observableArrayList();
    protected GameTab parent;
    protected NumberTextField text = new NumberTextField(25, 0, 8, "");

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

            Label first = list.remove(index);
            Label tmp = list.remove(index - 1);

            list.add(index - 1, first);
            list.add(index, tmp);

            rules.getSelectionModel().select(index - 1);

        }

    }

    @FXML
    protected void down(ActionEvent event) throws IOException {

        int index = rules.getSelectionModel().getSelectedIndex();

        if (index < list.size()) {

            Label first = list.remove(index);
            Label tmp = list.remove(index + 1);

            list.add(index + 1, first);
            list.add(index, tmp);

            rules.getSelectionModel().select(index + 1);

        }

    }

}
