
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
public final class RulePatternController {

    @FXML
    private CheckBox checkBox00;
    @FXML
    private CheckBox checkBox01;
    @FXML
    private CheckBox checkBox02;
    @FXML
    private CheckBox checkBox10;
    @FXML
    private CheckBox checkBox12;
    @FXML
    private CheckBox checkBox20;
    @FXML
    private CheckBox checkBox21;
    @FXML
    private CheckBox checkBox22;
    @FXML
    private CheckBox checkBox11;

    private boolean[] values = new boolean[8];

    void setGrid(boolean[] grid) {

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
    private void gridPos00(ActionEvent event) throws IOException {
        values[0] = checkBox00.isSelected();
    }

    @FXML
    private void gridPos01(ActionEvent event) throws IOException {
        values[1] = checkBox01.isSelected();
    }

    @FXML
    private void gridPos02(ActionEvent event) throws IOException {
        values[2] = checkBox02.isSelected();
    }

    @FXML
    private void gridPos10(ActionEvent event) throws IOException {
        values[3] = checkBox10.isSelected();
    }

    @FXML
    private void gridPos12(ActionEvent event) throws IOException {
        values[4] = checkBox12.isSelected();
    }

    @FXML
    private void gridPos20(ActionEvent event) throws IOException {
        values[5] = checkBox20.isSelected();
    }

    @FXML
    private void gridPos21(ActionEvent event) throws IOException {
        values[6] = checkBox21.isSelected();
    }

    @FXML
    private void gridPos22(ActionEvent event) throws IOException {
        values[7] = checkBox22.isSelected();
    }

    public void selectCheckBox11(boolean value) {
        checkBox11.setSelected(value);
    }
    
}
