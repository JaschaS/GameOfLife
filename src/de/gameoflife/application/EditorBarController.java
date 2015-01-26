/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class EditorBarController implements Initializable {

    @FXML
    private ToolBar editorToolBar;
    @FXML
    private Button done;
    @FXML
    private CheckBox borderOverflow;
    @FXML
    private ColorPicker colorPicker;

    private GameTab parent;
    private NumberTextField cellWidth;
    private NumberTextField cellHeight;
    private NumberTextField cellSize;
    private boolean userDraws = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cellWidth = new NumberTextField(100, 3, 100, "3");
        cellHeight = new NumberTextField(100, 3, 100, "3");
        cellSize = new NumberTextField(100, 3, 100, "20");

        cellWidth.setListener((int value) -> {
            if (parent != null) {

                parent.setCanvasWidth(value);

            }
        });

        cellHeight.setListener((int value) -> {
            if (parent != null) {

                parent.setCanvasHeight(value);

            }
        });

        cellSize.setListener((int value) -> {
            if (parent != null) {

                parent.setCanvasCellSize(value);

            }
        });

        editorToolBar.getItems().add(4, cellWidth);
        editorToolBar.getItems().add(6, cellHeight);
        editorToolBar.getItems().add(8, cellSize);

        borderOverflow.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {
            parent.getGame().setBorderOverflow(newVal);

            GameHandler.getInstance().saveGame(parent.getGameId());
        });

        colorPicker.setOnAction((ActionEvent event) -> {

            parent.setCellColor(colorPicker.getValue());

        });

        colorPicker.setValue(Color.RED);

        ObservableList<Color> list = FXCollections.observableArrayList();

        list.addAll(
                Color.web("D93E30"),
                Color.web("FFB429"),
                Color.web("51A64E"),
                Color.web("0D6BA6"),
                Color.web("FF4444"),
                Color.web("FFBB33"),
                Color.web("99CC00"),
                Color.web("33B5E5"),
                Color.web("AA66CC"),
                Color.web("CC0000"),
                Color.web("FF8800"),
                Color.web("669900"),
                Color.web("9933CC"),
                Color.web("0099CC")
        );

        colorPicker.getCustomColors().addAll(list);

        colorPicker.setValue(colorPicker.getCustomColors().get(2));

        //colorPicker.setValue(new Color(81, 166, 78, 1));
        //System.out.println(colorPicker.getCustomColors());
        /*colorPicker.getCustomColors().addAll(
         new Color(81, 166, 78, 1),
         new Color(13, 107, 166, 1),
         new Color(242, 171, 39, 1),
         new Color(217, 62, 48, 1)
         );
         */
    }

    @FXML
    public void deathRules(ActionEvent event) throws IOException {

        parent.showDeathRules();
        
    }

    @FXML
    public void save(ActionEvent event) throws IOException {

        final RulePattern oneBirthrule = new RulePattern(new boolean[]{true, true, true, false, false, true, true, true});
        final NumericRule oneDeathrule = new NumericRule();
        oneDeathrule.setTriggerAtNumberOfNeighbours(5, true); //Death at 5 alive neigbours
        oneDeathrule.setTriggerAtNumberOfNeighbours(4, true); //Death at 4 alive neigbours

        parent.getGame().addBirthRule(oneBirthrule);
        parent.getGame().addDeathRule(oneDeathrule);

        boolean successful = GameHandler.getInstance().saveGame(parent.getGameId());

        System.out.println("Save successful: " + successful);
        
    }

    @FXML
    public void draw(ActionEvent event) throws IOException {

        if (!userDraws) {
            parent.getCanvas().addListener();
            userDraws = true;
        } else {
            parent.getCanvas().removeListener();
            userDraws = false;
        }

    }

    @FXML
    public void clear(ActionEvent event) throws IOException {

        GameCanvas canvas = parent.getCanvas();

        canvas.clear(true);
        canvas.drawGrid();

    }

    public void doneActionEvent(EventHandler<ActionEvent> event) {
        done.setOnAction(event);
    }

    public void setParent(GameTab newParent) {

        parent = newParent;

    }

    public void setBorderOverflow(boolean overflow) {
        borderOverflow.setSelected(overflow);
    }

    public void setCellWidth(int width) {
        cellWidth.setText(width + "");
    }

    public void setCellHeight(int height) {
        cellHeight.setText(height + "");
    }

    private void setCanvasWidth(int width) {

        if (parent != null) {

            parent.setCanvasWidth(width);

        }

    }

    private void setCanvasHeight(int height) {

        if (parent != null) {

            parent.setCanvasWidth(height);

        }

    }

    private void setCanvasCellSize(int size) {

        if (parent != null) {

            parent.setCanvasWidth(size);

        }

    }

}
