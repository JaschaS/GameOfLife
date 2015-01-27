package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
import queue.data.Generation;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class PlayBarController implements Initializable {

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

    private Task<Void> updateTask;
    private GameTab parent;
    private GameHandler connection;
    private int currentGeneration = 0;
    private boolean isRunning = false;
    private Thread updateThread;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        analysisShow.setDisable(true);

        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {

            currentSpeed.setText(newValue.intValue() + " Gen/Min");

        });

        connection = GameHandler.getInstance();

        stop.setDisable(true);

    }

    public void editorActionEvent(EventHandler<ActionEvent> event) {

        editor.setOnAction(event);

    }

    public void close() {
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
            System.out.println(connection.stopEngine(User.getInstance().getId(), parent.getGameId()));

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

        if (!isRunning) {
            isRunning = connection.startEngine(parent.getGame().getUserId(), parent.getGameId());
        }

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

    @FXML
    public void previous(ActionEvent event) throws IOException {

        if (currentGeneration > 1) {

            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), --currentGeneration);

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
            if (currentGeneration <= 0) {
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
        //Show button disable = false;
    }

    @FXML
    public void showAnalysis(ActionEvent event) throws IOException {

        //Daten holen sys out
        System.out.println("");

    }

    public void setParent(GameTab newParent) {

        parent = newParent;

    }

    protected class UpdateTask extends Task<Void> {

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
        protected Void call() throws Exception {

            Generation gen;
            long time;
            
            while (!isCancelled()) {
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interrupted) {
                    if (isCancelled()) {
                        updateMessage("Cancelled");
                        break;
                    }
                }
                
                gen = handler.getNextGeneration(userId, gameId);
                
                if (gen != null) {
                        
                    final int[][] config = gen.getConfig();

                    Platform.runLater(() -> {
                        canvas.drawCells(config);
                    });

                } else {
                    System.out.println("gen ist null");
                }
                
            }

            return null;

        }

    }

}
