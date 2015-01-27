/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class RulePatternController {

    @FXML
    protected CheckBox checkBox00;
    @FXML
    protected CheckBox checkBox01;
    @FXML
    protected CheckBox checkBox02;
    @FXML
    protected CheckBox checkBox10;
    @FXML
    protected CheckBox checkBox12;
    @FXML
    protected CheckBox checkBox20;
    @FXML
    protected CheckBox checkBox21;
    @FXML
    protected CheckBox checkBox22;
    @FXML
    protected CheckBox checkBox11;

    protected boolean[] values = new boolean[8];

    public boolean[] getGrid() {
        boolean[] copy = new boolean[values.length];

        for (int i = 0; i < values.length; ++i) {
            copy[i] = values[i];
        }

        return copy;
    }

    public void setGrid(boolean[] grid) {

        values = grid;

        checkBox00.setSelected(grid[0]);
        checkBox01.setSelected(grid[1]);
        checkBox02.setSelected(grid[2]);
        checkBox10.setSelected(grid[3]);
        checkBox12.setSelected(grid[4]);
        checkBox20.setSelected(grid[5]);
        checkBox21.setSelected(grid[6]);
        checkBox22.setSelected(grid[7]);

    }

    @FXML
    protected void gridPos00(ActionEvent event) throws IOException {
        values[0] = checkBox00.isSelected();
    }

    @FXML
    protected void gridPos01(ActionEvent event) throws IOException {
        values[1] = checkBox01.isSelected();
    }

    @FXML
    protected void gridPos02(ActionEvent event) throws IOException {
        values[2] = checkBox02.isSelected();
    }

    @FXML
    protected void gridPos10(ActionEvent event) throws IOException {
        values[3] = checkBox10.isSelected();
    }

    @FXML
    protected void gridPos12(ActionEvent event) throws IOException {
        values[4] = checkBox12.isSelected();
    }

    @FXML
    protected void gridPos20(ActionEvent event) throws IOException {
        values[5] = checkBox20.isSelected();
    }

    @FXML
    protected void gridPos21(ActionEvent event) throws IOException {
        values[6] = checkBox21.isSelected();
    }

    @FXML
    protected void gridPos22(ActionEvent event) throws IOException {
        values[7] = checkBox22.isSelected();
    }

    public void selectCheckBox11(boolean value) {
        checkBox11.setSelected(value);
    }
    
}
