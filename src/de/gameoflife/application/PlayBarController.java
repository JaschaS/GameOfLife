package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import queue.data.Generation;

/**
 * FXML Controller class
 *
 * @author JScholz
 *
 * TODO Bei Start Threshold hinzufügen
 *
 * TODO Prev und next sprung groesse hinzufuegen
 *
 * TODO Analyse anzeigen
 *
 * TODO Farbe setzen
 *
 * TODO Prev und Next deaktivieren, wenn Play laeuft
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

    private GameTab parent;
    private GameHandler connection;
    private Thread updateThread;
    private NumberTextField cellSize;
    private final GameHandler gameHandler = GameHandler.getInstance();
    private int userId;

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
        connection.stopEngine(User.getInstance().getId(), parent.getGameId());
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

            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), ++currentGeneration);

            if (gen == null) {
                return;
            }

            if (prev.isDisabled()) {
                prev.setDisable(false);
            }

            parent.getCanvas().setCurrentGeneration(currentGeneration);
            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    private void previous(ActionEvent event) throws IOException {

        int currentGeneration = parent.getCanvas().getCurrentGeneration();
        boolean isRunning = connection.gameRunning();

        if (!isRunning && currentGeneration > 1) {

            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), --currentGeneration);

            if (gen == null) {
                return;
            }

            if (currentGeneration <= 1) {
                prev.setDisable(true);
            }

            parent.getCanvas().setCurrentGeneration(currentGeneration);
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

    /*
     public class UpdateTask extends Task<int[][]> {

     private final GameHandler handler;
     private final int userId;
     private final int gameId;
     private final GameCanvas canvas;

     public UpdateTask(int gameId, GameCanvas canvas) {
     handler = GameHandler.getInstance();
     userId = User.getInstance().getId();
     gameId = parent.getGameId();
     canvas = parent.getCanvas();
     }

     @Override
     protected int[][] call() throws Exception {

     Generation gen;
     long time;
     int currentGen = 0;

     while (!isCancelled()) {

     try {
     time = 60 * 1000 / ((long) speedSlider.getValue());
     Thread.sleep(time);
     } catch (InterruptedException interrupted) {
     }

     gen = handler.getNextGeneration(userId, gameId);

     if (gen != null) {

     ++currentGen;

     final CountDownLatch doneLatch = new CountDownLatch(1);

     final int tmp = currentGen;

     Platform.runLater(new Runnable() {

     final int value = tmp;

     @Override
     public void run() {
                            
     Generation g = handler.getGeneration(userId, gameId, value);
                            
     if (g != null) {
     System.out.println(value);
     canvas.drawCells(g.getConfig());
     canvas.setCurrentGeneration(value);
     doneLatch.countDown();
     }
     }
     });

     doneLatch.await();
                    
     } else {
     System.out.println("gen ist null");
     }

     }

     return null;

     }

     }
     */
}
