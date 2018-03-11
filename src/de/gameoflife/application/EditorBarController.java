package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import de.gameoflife.connection.rmi.IConnectionRuleEditor;
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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public final class EditorBarController implements Initializable {

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
    private boolean userErase = false;
    private ToggleGroup drawGroup;
    private ToggleButton draw;
    private ToggleButton erase;
    private final GameHandler gameHandler = GameHandler.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        drawGroup = new ToggleGroup();

        draw = new ToggleButton("Draw");
        draw.setToggleGroup(drawGroup);
        draw.setOnAction((ActionEvent event) -> {

            if (!userDraws) {

                if (userErase) {
                    parent.getCanvas().removeListener();
                    userErase = false;
                }

                parent.getCanvas().addDrawListener();
                userDraws = true;

            } else {
                parent.getCanvas().removeListener();
                userDraws = false;
            }

        });

        erase = new ToggleButton("Erase");
        erase.setToggleGroup(drawGroup);
        erase.setOnAction((ActionEvent event) -> {

            if (!userErase) {

                if (userDraws) {
                    parent.getCanvas().removeListener();
                    userDraws = false;
                }

                parent.getCanvas().addEraserListener();
                userErase = true;
            } else {
                parent.getCanvas().removeListener();
                userErase = false;
            }

        });

        editorToolBar.getItems().add(12, draw);
        editorToolBar.getItems().add(13, erase);

        cellWidth = new NumberTextField(100, 3, 100, "3");
        cellHeight = new NumberTextField(100, 3, 100, "3");
        cellSize = new NumberTextField(100, 3, 100, "20");

        cellWidth.setListener((int value) -> {
            if (parent != null) {

                //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
                if (!isDrawing()) {
                    isErasing();
                }

                parent.setCanvasWidth(value);

            }
        });

        cellHeight.setListener((int value) -> {
            if (parent != null) {

                //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
                if (!isDrawing()) {
                    isErasing();
                }

                parent.setCanvasHeight(value);

            }
        });

        cellSize.setListener((int value) -> {
            if (parent != null) {

                //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
                if (!isDrawing()) {
                    isErasing();
                }

                parent.setCanvasCellSize(value);

            }
        });

        editorToolBar.getItems().add(5, cellWidth);
        editorToolBar.getItems().add(7, cellHeight);
        editorToolBar.getItems().add(10, cellSize);

        borderOverflow.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean newVal) -> {

            //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
            if (!isDrawing()) {
                isErasing();
            }

            gameHandler.setBorderOverflow(parent.getGameId(), newVal);

        });

        colorPicker.setOnAction((ActionEvent event) -> {

            //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
            if (!isDrawing()) {
                isErasing();
            }

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

    }

    @FXML
    public void deathRules(ActionEvent event) throws IOException {

        //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
        if (!isDrawing()) {
            isErasing();
        }

        parent.showDeathRules();

    }

    @FXML
    public void birthRules(ActionEvent event) throws IOException {

        //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
        if (!isDrawing()) {
            isErasing();
        }

        parent.showBirthRules();

    }

    @FXML
    public void save(ActionEvent event) throws IOException {

        //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
        if (!isDrawing()) {
            isErasing();
        }

        IConnectionRuleEditor ruleEditor = gameHandler.getRuleEditor ();
        boolean successful = ruleEditor.saveGame(parent.getGameId());

        System.out.println("Save successful: " + successful);
    }

    @FXML
    public void rename(ActionEvent event) throws IOException {

        //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
        if (!isDrawing()) {
            isErasing();
        }

        parent.renameGame();
        
    }

    @FXML
    public void clear(ActionEvent event) throws IOException {

        //Wenn nicht gezeichnet worden ist, schauen ob geloescht wird!
        if (!isDrawing()) {
            isErasing();
        }

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

    public boolean isDrawing() {

        if (userDraws) {

            parent.getCanvas().removeListener();
            userDraws = false;

            draw.setSelected(false);

            return true;

        }

        return false;
    }

    public boolean isErasing() {

        if (userErase) {

            parent.getCanvas().removeListener();
            userErase = false;

            erase.setSelected(false);

            return true;
        }

        return false;

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
