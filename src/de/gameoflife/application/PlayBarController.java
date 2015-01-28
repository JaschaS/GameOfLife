package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
 */
public class PlayBarController implements Initializable {

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

    private UpdateTask updateTask;
    private GameTab parent;
    private GameHandler connection;
    private int currentGeneration = 1;
    private boolean isRunning = false;
    private Thread updateThread;
    private NumberTextField cellSize;

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

    }

    public void editorActionEvent(EventHandler<ActionEvent> event) {

        editor.setOnAction(event);

    }

    /**
     * Wird aufgerufen falls das Tab geschlossen wird!
     */
    public void close() {
        if (updateTask != null && updateTask.isRunning()) {
            updateTask.cancel();
        }
        //TODO Analyse Task cancel!
        connection.stopEngine(User.getInstance().getId(), parent.getGameId());
    }

    @FXML
    public void play(ActionEvent event) throws IOException, NotBoundException {

        //System.out.println(parent.getGame().getUserId() + " " + parent.getGameId());
        if (!isRunning) {

            isRunning = connection.startEngine(parent.getGame().getUserId(), parent.getGameId());

            play.setDisable(isRunning);
            stop.setDisable(!isRunning);

            if (isRunning) {

                updateTask = new UpdateTask();
                updateThread = new Thread(updateTask);
                //updateThread.setDaemon(true);
                updateThread.start();

                //Wert an canvas binden, damit dieses beim aendern geupdatet wird?
                //parent.getCanvas().setCanvasProperty(updateTask.valueProperty());
            }
        }

        /*Generation gen = connection.getNextGeneration(parent.getGame().getUserId(), parent.getGameId());

         if (gen == null) {
         System.out.println("null");
         } else {
         System.out.println("not null");
         System.out.println(gen.toString());
         }
         */
    }

    @FXML
    public void stop(ActionEvent event) throws IOException {

        if (isRunning) {
            connection.stopEngine(User.getInstance().getId(), parent.getGameId());

            if (updateTask != null && updateTask.isRunning()) {
                updateTask.cancel();
            }

            play.setDisable(!isRunning);
            stop.setDisable(isRunning);
            isRunning = false;
        }
        //if (updateTask != null && updateTask.isRunning()) {

        //  connection.stopEngine(User.getInstance().getId(), parent.getGameId());
        //  updateTask.cancel();
        //}
        //System.out.println("Stop successful: " + successful);
    }

    @FXML
    public void next(ActionEvent event) throws IOException {

        //if (!isRunning) {
        //    isRunning = connection.startEngine(parent.getGame().getUserId(), parent.getGameId());
        //}

        if (updateTask == null || !updateTask.isRunning()) {
            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), ++currentGeneration);
            //Generation gen = connection.getNextGeneration(parent.getGame().getUserId(), parent.getGameId());
            if (gen == null) {
                //System.out.println("gen null");
                return;
            }
            /*
             for (int i = 0; i < gen.getConfig().length; ++i) {

             for (int j = 0; j < gen.getConfig()[i].length; ++j) {
             System.out.print(gen.getConfig()[i][j] + " ");
             }
             System.out.println();

             }

             System.out.println();
             */
            if (prev.isDisabled()) {
                prev.setDisable(false);
            }

            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    public void previous(ActionEvent event) throws IOException {

        if (currentGeneration > 1 && (updateTask == null || !updateTask.isRunning()) ) {

            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), --currentGeneration);

            if (gen == null) {
                System.out.println("gen null");
                return;
            }
            /*
             for (int i = 0; i < gen.getConfig().length; ++i) {

             for (int j = 0; j < gen.getConfig()[i].length; ++j) {
             System.out.print(gen.getConfig()[i][j] + " ");
             }
             System.out.println();

             }

             System.out.println();
             */

            //System.out.println(currentGeneration);
            if (currentGeneration <= 1) {
                prev.setDisable(true);
                isRunning = false;
            }

            parent.getCanvas().drawCells(gen.getConfig());
        }

    }

    @FXML
    public void startAnalysis(ActionEvent event) throws IOException {

        connection.startAnalysis(parent.getGameId());

        //TODO Analysis abfangen...
        //Task schreiben
        //Task starten wie in play
        //Task sollte ein boolean wert zurueckgeben, dann kann der show button wieder aktiviert werden mit setdisable(false)
        //Oder so Etwas: bindet den wert den der task zurueck gibt an den disable des buttons
        //analysisShow.disableProperty().bind( task.valueProperty);
        //Diesen JSon string vorher im Task speichern. Also eine Variable in PlaybarController definieren und im task zuweisen?
    }

    @FXML
    public void showAnalysis(ActionEvent event) throws IOException {

        //Wenn das gedrueckt wird, sollen der string ausgegeben werden...
        //Daten holen sys out
        //System.out.println("");
        //Variante neues Fenster erstellen
        //Eventuell model setzen??
        // man erhält nur das Parent FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") ); aber kein Controller!
        /*
         FXMLLoader analyseLoader = new FXMLLoader(getClass().getResource("FXML/Analyse.fxml")); 

         Parent analyse = (Parent) analyseLoader.load();
        
         AnalyseController controller = analyseLoader.getController();
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

    public void setParent(GameTab newParent) {

        parent = newParent;

    }

    protected class UpdateTask extends Task<int[][]> {

        private final GameHandler handler;
        private final int userId;
        private final int gameId;
        private final GameCanvas canvas;

        public UpdateTask() {
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
                    
                    //final int tmp = currentGen;
                    ++currentGeneration;
                    //System.out.println( gen.getGenID());
                    final CountDownLatch doneLatch = new CountDownLatch(1);
                    
                    //final int[][] genConfig = gen.getConfig();
                    //final int id = gen.getGenID();
                    
                    /*Platform.runLater( new Runnable() {
                        
                        final int[][] config = genConfig;

                        @Override
                        public void run() {
                            //System.out.println("start drawing " + id );
                            canvas.drawCells(config);
                            //System.out.println("Done drawing");
                            doneLatch.countDown();
                        }
                    });*/
                    
                    final int tmp = currentGen;
                    
                    Platform.runLater(new Runnable() {

                        final int value = tmp;

                        @Override
                        public void run() {
                            Generation g = handler.getGeneration(userId, gameId, value);
                            if (g != null) {
                                System.out.println(value);
                                canvas.drawCells(g.getConfig());
                                doneLatch.countDown();
                            }
                            else System.out.println("null");
                        }
                    });
                    
                    
                    
                    System.out.println("wait for update");
                    doneLatch.await();
                    //System.out.println(currentGeneration);
                    System.out.println("Done");
                    //updateValue(gen.getConfig());
                } else {
                    System.out.println("gen ist null");
                }

            }

            return null;

        }

    }

}
