package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import queue.data.Generation;

/**
 * FXML Controller class
 *
 * @author JScholz
 *
 * TODO Bei Start Threshold hinzufügen
 *
 * TODO Analyse anzeigen
 *
 * Jar Datei schicken Schnittstellen an Marx schicken?
 */
public final class PlayBarController implements Initializable {

    @FXML
    private ToolBar playToolBar;
    @FXML
    private Button editor;
    @FXML
    private Label currentSpeed;
    @FXML
    private Slider speedSlider;
    @FXML
    private Button analysisStart;
    @FXML
    private Button analysisShow;
    @FXML
    private Button prev;
    @FXML
    private Button stop;
    @FXML
    private Button play;
    @FXML
    private Button next;
    @FXML
    private Label currentGeneration;
    @FXML
    private ColorPicker colorPicker;

    private GameTab parent;
    private GameHandler connection;
    private NumberTextField cellSize;
    private final GameHandler gameHandler = GameHandler.getInstance();
    private int userId;
    private int step = 1;
    private NumberTextField stepSize;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cellSize = new NumberTextField(100, 3, 100, "20");
        cellSize.setListener((int value) -> {
            if (parent != null) {

                parent.setCanvasCellSize(value);

            }
        });

        playToolBar.getItems().add(playToolBar.getItems().size(), cellSize);

        analysisShow.setDisable(true);

        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {

            currentSpeed.setText(newValue.intValue() + " Gen/Min");

        });

        connection = GameHandler.getInstance();

        stop.setDisable(true);
        prev.setDisable(true);

        userId = User.getInstance().getId();

        stepSize = new NumberTextField(25, 0, 100, "1");
        stepSize.setListener((int value) -> {
            step = value;
        });

        playToolBar.getItems().add(5, stepSize);

        currentGeneration.textProperty().bind(parent.getCanvas().getCurrentGame().asString());

        colorPicker.setValue(Color.RED);
        colorPicker.setOnAction((ActionEvent event) -> {

            parent.setCellColor(colorPicker.getValue());

        });

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

    public void editorActionEvent(EventHandler<ActionEvent> event) {

        editor.setOnAction(event);

    }

    /**
     * Wird aufgerufen falls das Tab geschlossen wird!
     */
    public void close() {
        connection.stopCurrentRunningGame();
        //TODO Analyse Task cancel!
    }

    public void setParent(GameTab newParent) {

        parent = newParent;

    }

    @FXML
    private void play(ActionEvent event) throws IOException, NotBoundException {

        boolean isRunning = connection.gameRunning();

        if (!isRunning) {

            connection.startGame(parent.getGameId(), speedSlider.valueProperty(), parent.getCanvas());

            play.setDisable(isRunning);
            stop.setDisable(!isRunning);
            prev.setDisable(true);
            next.setDisable(true);

        }

    }

    @FXML
    private void stop(ActionEvent event) throws IOException {

        boolean isRunning = connection.gameRunning();

        if (isRunning) {

            connection.stopCurrentRunningGame();
            play.setDisable(!isRunning);
            stop.setDisable(isRunning);

            prev.setDisable(false);

            if (parent.getCanvas().getCurrentGeneration() > 1) {
                next.setDisable(false);
            }

        }

    }

    @FXML
    private void next(ActionEvent event) throws IOException {

        boolean isRunning = connection.gameRunning();

        if (!isRunning) {

            int currentGeneration = parent.getCanvas().getCurrentGeneration();

            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), currentGeneration + step);

            if (gen == null) {
                return;
            }

            if (prev.isDisabled()) {
                prev.setDisable(false);
            }

            parent.getCanvas().setCurrentGeneration(currentGeneration + step);
            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    private void previous(ActionEvent event) throws IOException {

        int currentGeneration = parent.getCanvas().getCurrentGeneration();
        boolean isRunning = connection.gameRunning();

        if (!isRunning && currentGeneration > 1) {

            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), currentGeneration - step);

            if (gen == null) {
                return;
            }

            if (currentGeneration <= 1) {
                prev.setDisable(true);
            }

            parent.getCanvas().setCurrentGeneration(currentGeneration - step);
            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    private void startAnalysis(ActionEvent event) throws IOException {

        connection.startAnalysis(parent.getGameId());

        //TODO Analysis abfangen...
        //Task schreiben
        //Task starten wie in play
        //Task sollte ein boolean wert zurueckgeben, dann kann der show button wieder aktiviert werden mit setdisable(false)
        //Oder so Etwas: bindet den wert den der task zurueck gibt an den disable des buttons
        //analysisShow.disableProperty().bind( task.valueProperty);  //Daniel
        //Diesen JSon string vorher im Task speichern. Also eine Variable in PlaybarController definieren und im task zuweisen?
    }

    @FXML
    private void showAnalysis(ActionEvent event) throws IOException {

        //Wenn das gedrueckt wird, sollen der string ausgegeben werden...
        //Daten holen sys out
        //System.out.println("");
        //Variante neues Fenster erstellen
        //Eventuell model setzen??
        // man erhält nur das Parent FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") ); aber kein Controller!
        /*
         FXMLLoader analyseLoader = new FXMLLoader(getClass().getResource("FXML/<Dein FXML Name>.fxml")); //Analyse

         Parent analyse = (Parent) analyseLoader.load();
        
         <Dein FXML Controller> controller = analyseLoader.getController();
         controller.setDaten(String s);
        
        
         Scene s = new Scene( analyse );
         Stage stage = new Stage();
         stage.setScene(s);
         stage.centerOnScreen();
         stage.setTitle("Game of Life");
         stage.show();
         */
        //Alternative koenntest du das Parent analyse auch der Stackpane zuweisen
        analysisShow.setDisable(true);

    }

}
