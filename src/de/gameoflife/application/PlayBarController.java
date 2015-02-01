package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private Button analysisStop;
    @FXML
    private Label currentGeneration;
    @FXML
    private ColorPicker colorPicker;

    private GameTab parent;
    private NumberTextField cellSize;
    private final GameHandler gameHandler = GameHandler.getInstance();
    private int userId;
    private int step = 1;
    private NumberTextField stepSize;
    private Task<Void> analyseTask;
    private String analyseData;
    private Parent analyse;
    private ShowAnalysisController analyseController;
    private Stage analyseStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cellSize = new NumberTextField(100, 3, 100, "20");
        cellSize.setListener((int value) -> {
            if (parent != null) {

                parent.setCanvasCellSize(value);

            }
        });

        playToolBar.getItems().add(playToolBar.getItems().size() - 1, cellSize);

        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {

            currentSpeed.setText(newValue.intValue() + " Gen/Min");

        });

        stop.setDisable(true);
        prev.setDisable(true);        
        next.setDisable(true);

        userId = User.getInstance().getId();

        stepSize = new NumberTextField(40, 0, 100, "1");
        stepSize.setListener((int value) -> {
            step = value;
        });

        playToolBar.getItems().add(5, stepSize);

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

        try {
            FXMLLoader analyseLoader = new FXMLLoader(getClass().getResource("FXML/ShowAnalysis.fxml")); //Analyse

            analyse = (Parent) analyseLoader.load();
            analyseController = analyseLoader.getController();

            Scene analyseScene = new Scene(analyse);
            analyseStage = new Stage();
            analyseStage.setScene(analyseScene);
            analyseStage.centerOnScreen();
            analyseStage.setTitle("Analysis Data");

        } catch (IOException ex) {
            Logger.getLogger(PlayBarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editorActionEvent(EventHandler<ActionEvent> event) {

        editor.setOnAction(event);

    }

    /**
     * Wird aufgerufen falls das Tab geschlossen wird!
     */
    public void close() {
        gameHandler.stopCurrentRunningGame();

        if (analyseTask != null && analyseTask.isRunning()) {
            analyseTask.cancel();
        }

    }

    public void setParent(GameTab newParent) {

        parent = newParent;

    }
    
    public void activateNextButton( boolean activate) {
        next.setDisable(activate);
    }

    public void bindPropertyToCurrentGen(ObservableValue property) {
        currentGeneration.textProperty().bind(property);
    }

    @FXML
    private void play(ActionEvent event) throws IOException, NotBoundException {

        boolean isRunning = gameHandler.gameRunning();
        //System.out.println("play");
        if (!isRunning) {
            //System.out.println("play ok");
            
            gameHandler.startGame(parent.getGameId(), speedSlider.valueProperty(), parent.getCanvas());

            play.setDisable(true);
            stop.setDisable(false);
            prev.setDisable(true);
            next.setDisable(true);

        }

    }

    @FXML
    private void stop(ActionEvent event) throws IOException {

        boolean isRunning = gameHandler.gameRunning();

        if (isRunning) {

            gameHandler.stopCurrentRunningGame();
            
            play.setDisable(false);
            stop.setDisable(true);
            prev.setDisable(false);

            if (parent.getCanvas().getCurrentGeneration() > 1) {
                next.setDisable(false);
            }

        }

    }

    @FXML
    private void next(ActionEvent event) throws IOException {

        boolean isRunning = gameHandler.gameRunning();

        if (!isRunning) {

            int currentGen = parent.getCanvas().getCurrentGeneration();

            Generation gen = gameHandler.getGeneration(User.getInstance().getId(), parent.getGameId(), currentGen + step);

            if (gen == null) {
                System.out.println("Next Step: gen ist null");
                return;
            }

            if (prev.isDisabled()) {
                prev.setDisable(false);
            }

            parent.getCanvas().setCurrentGeneration(currentGen + step);
            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    private void previous(ActionEvent event) throws IOException {

        int currentGen = parent.getCanvas().getCurrentGeneration();
        boolean isRunning = gameHandler.gameRunning();

        if (!isRunning && currentGen > 1) {

            Generation gen = gameHandler.getGeneration(User.getInstance().getId(), parent.getGameId(), currentGen - step);

            if (gen == null) {
                System.out.println("Prev Step: gen ist null");
                return;
            }

            if (currentGen <= 1) {
                prev.setDisable(true);
            }

            parent.getCanvas().setCurrentGeneration(currentGen - step);
            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    private void startAnalysis(ActionEvent event) throws IOException {

        if (analyseTask == null || !analyseTask.isRunning()) {

            analyseData = null;

            analysisShow.setDisable(true);
            analysisStop.setDisable(false);
            analysisStart.setDisable(true);

            //connection.startAnalysis(parent.getGameId());
            analyseTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    while (analyseData == null) {

                        Thread.sleep(10000); //sleep 10 seconds
                        System.out.println("test");

                        analyseData = gameHandler.getAnalyseData(3, 3);
                        //analyseData=connection.getAnalyseData(User.getInstance().getId(), parent.getGameId() );
                    }

                    //Now analyseData is available
                    
                    //Die Button sind teil des UI Threads, wenn du nur setDisable machst
                    //siehst du teilweise erst das update, wenn man mit der Maus drueber
                    //geht. Daher mit Platform runlater ausfuehren, dann sieht man
                    //direkt das update.
                    //Einfach mal ohne runlater ausprobieren, dann sieht man den unterschied!
                    Platform.runLater(() -> {
                        analysisShow.setDisable(false);
                        analysisStop.setDisable(true);
                        analysisStart.setDisable(false);
                    });
                    
                    JSONObject json = new JSONObject(analyseData);
                    HashMap<String,JSONArray> patterns = new HashMap<>();
                    patterns.put("static-object",json.optJSONArray("static-object"));
                    patterns.put("population-density",json.optJSONArray("population-density"));
                    patterns.put("extermination",json.optJSONArray("extermination"));
                    patterns.put("beacon",json.optJSONArray("beacon"));
                    patterns.put("blinker",json.optJSONArray("blinker"));
                    patterns.put("glider",json.optJSONArray("glider"));
                    patterns.put("heavyweight-spaceship",json.optJSONArray("heavyweight-spaceship"));
                    patterns.put("lightweight-spaceship",json.optJSONArray("lightweight-spaceship"));
                    patterns.put("middleweight-spaceship",json.optJSONArray("middleweight-spaceship"));
                    patterns.put("beehive",json.optJSONArray("beehive"));
                    patterns.put("block",json.optJSONArray("block"));
                    
                    //enthaelt alle pattern mit ihrer anzahl
                    ArrayList<AnalysisPattern> patternWithCount= new ArrayList<>();
                    
                    Set<String> keys=patterns.keySet();
                    Iterator<String> it = keys.iterator();
                    
                    while (it.hasNext()){
                        String temp = it.next();
                        if (patterns.get(temp) != null){
                            patternWithCount.add(new AnalysisPattern(temp,patterns.get(temp).length()));
                        } else {
                            patternWithCount.add(new AnalysisPattern(temp,0));
                        }
                    }                    
        
                    //Die GUI setzen
                    analyseController.setItemsToAdd(patternWithCount);
                    
                    //Daten setzen
                    //Da es ein JSON String ist muss dieser noch zerlegt werden.
                    //TODO
                    //analyseController.setContent(analyseData);
                    return null;
                }
            };
            
            Thread t = new Thread(analyseTask);
            t.start();

        }
        //TODO Analysis abfangen...
        //Task schreiben
        //Task starten wie in play
        //Task sollte ein boolean wert zurueckgeben, dann kann der show button wieder aktiviert werden mit setdisable(false)
        //Oder so Etwas: bindet den wert den der task zurueck gibt an den disable des buttons
        //analysisShow.disableProperty().bind( task.valueProperty);  //Daniel
        //Diesen JSon string vorher im Task speichern. Also eine Variable in PlaybarController definieren und im task zuweisen?
    }

    @FXML
    private void stopAnalysis(ActionEvent event) throws IOException {

        if (analyseTask != null && analyseTask.isRunning()) {

            analyseTask.cancel();

            analysisStop.setDisable(true);
            analysisStart.setDisable(false);

            if (analyseData != null) {
                analysisShow.setDisable(false);
            }

        }

    }

    @FXML
    private void showAnalysis(ActionEvent event) throws IOException {
        /*
         TODO:
         In der Methode müssen die daten der analyse geparst werden und eine tabelle mit den
         vorhandenen mustern muss gefüllt werden. Jedes Muster hat andere Eigenschaften, deshalb
         müsste dafür noch eine passende struktur erstellt werden.
         */

        if (analyseData != null) {

            System.out.println(analyseData);

            if( analyseController != null) {
            
                analyseController.setLabel(analyseData);
            
            }
            
            if (analyseStage != null) {
                
                analyseStage.show();
            }

        }
        //Wenn das gedrueckt wird, sollen der string ausgegeben werden...
        //Daten holen sys out
        //System.out.println("");
        //Variante neues Fenster erstellen
        //Eventuell model setzen??
        // man erhält nur das Parent FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") ); aber kein Controller!

        //FXMLLoader analyseLoader = new FXMLLoader(getClass().getResource("FXML/ShowAnalysis.fxml")); //Analyse
        //Parent analyse = (Parent) analyseLoader.load();
        //ShowAnalysisController controller = analyseLoader.getController();
/*
         Scene s = new Scene(analyse);
         Stage stage = new Stage();
         stage.setScene(s);
         stage.centerOnScreen();
         stage.setTitle("Game of Life");
         stage.show();

         controller.setLabel(analyseData);
         */
        //Alternative koenntest du das Parent analyse auch der Stackpane zuweisen
    }

}
