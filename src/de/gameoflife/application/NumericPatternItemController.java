
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public final class NumericPatternItemController implements Initializable {

    @FXML
    private HBox numericPatternField;
    @FXML
    private Label firstText;
    @FXML
    private Label number;
    
    private ChoiceBox<String> choice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //number = new Label();
        ObservableList<String> list = FXCollections.observableArrayList();

        list.add("alive");
        list.add("dead");

        choice = new ChoiceBox<>(list);
        choice.getSelectionModel().select(0);

        //numericPatternField.getChildren().add(1, number);
        numericPatternField.getChildren().add(3, choice);

    }

    public void setNumver(int number) {
        this.number.setText(number + "");
    }

    public void setChoice(boolean alive) {

        SingleSelectionModel model = choice.getSelectionModel();

        if (alive) {
            model.select(0);
        } else {
            model.select(1);
        }

    }

    public int getNumber() {
        return Integer.parseInt(number.getText());
    }

    public boolean getValue() {

        return choice.getSelectionModel().getSelectedIndex() == 0;

    }

    public void addChoiceBoxListener(ChangeListener<Number> listener) {
        choice.getSelectionModel().selectedIndexProperty().addListener(listener);
    }

    public void setFirstText(String text) {
        firstText.setText(text);
    }

}
