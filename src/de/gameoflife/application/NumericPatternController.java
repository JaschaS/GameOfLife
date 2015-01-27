/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class NumericPatternController implements Initializable {

    @FXML
    protected HBox numericPattern;
    @FXML
    protected ListView listView;

    protected final ObservableList<HBox> list = FXCollections.observableArrayList();
    protected final ArrayList<NumericPatternItemController> itemController = new ArrayList<>();
    protected boolean[] rules;
    protected String text;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setItems(list);
    }

    public void setItems(boolean[] rules) throws IOException {

        if (list.size() > 0) {
            itemController.clear();
            list.clear();
        }

        this.rules = rules;

        for (int i = 0; i < rules.length; ++i) {
            
            FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("FXML/NumericPatternItem.fxml"));

            HBox item = (HBox) itemLoader.load();
            NumericPatternItemController controller = itemLoader.getController();
            controller.setChoice(rules[i]);
            controller.setNumver(i);
            controller.setFirstText(text);
            controller.addChoiceBoxListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                this.rules[ controller.getNumber() ] = controller.getValue();
            });

            list.add(item);
            itemController.add(controller);
            
        }

    }
    
    public void setFirstText(String text) {
        this.text = text;
    }

}
