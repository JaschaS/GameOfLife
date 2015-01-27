/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.util.ResourceBundle;
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

    private Task<Void> updateTask;
    private GameTab parent;
    private GameHandler connection;
    private int currentGeneration = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        analysisShow.setDisable(true);
        
        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {

            currentSpeed.setText(newValue.intValue() + " Gen/Min");

        });

        connection = GameHandler.getInstance();

    }

    public void editorActionEvent(EventHandler<ActionEvent> event) {

        editor.setOnAction(event);

    }

    @FXML
    public void play(ActionEvent event) throws IOException, NotBoundException {

        System.out.println( parent.getGame().getUserId() + " " + parent.getGameId());

        connection.startEngine(parent.getGame().getUserId(), parent.getGameId());
        
        Generation gen = connection.getNextGeneration(parent.getGame().getUserId(), parent.getGameId());

        if (gen == null) {
            System.out.println("null");
        } else {
            System.out.println("not null");
            System.out.println(gen.toString());
        }

    }

    @FXML
    public void stop(ActionEvent event) throws IOException {

        boolean successful = connection.stopEngine(User.getInstance().getId(), parent.getGameId());
        //if (updateTask != null && updateTask.isRunning()) {

        //  connection.stopEngine(User.getInstance().getId(), parent.getGameId());
        //  updateTask.cancel();
        //}
        System.out.println("Stop successful: " + successful);

    }

    @FXML
    public void next(ActionEvent event) throws IOException {
        
        Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), ++currentGeneration);
        //Generation gen = connection.getNextGeneration(parent.getGame().getUserId(), parent.getGameId());
        if (gen == null) {
            System.out.println("gen null");
            return;
        }

        //TODO Wie funktioniert Generation
        //parent.getCanvas().setGeneration(null);
        for (int i = 0; i < gen.getConfig().length; ++i) {

            for (int j = 0; j < gen.getConfig()[i].length; ++j) {
                System.out.print(gen.getConfig()[i][j] + " ");
            }
            System.out.println();

        }

        System.out.println();

    }

    @FXML
    public void previous(ActionEvent event) throws IOException {

        if (currentGeneration > 1) {
            
            Generation gen = connection.getGeneration(User.getInstance().getId(), parent.getGameId(), --currentGeneration);
            //TODO Prev Generation????

            if (gen == null) {
                System.out.println("gen null");
                return;
            }

            for (int i = 0; i < gen.getConfig().length; ++i) {

                for (int j = 0; j < gen.getConfig()[i].length; ++j) {
                    System.out.print(gen.getConfig()[i][j] + " ");
                }
                System.out.println();

            }

            System.out.println();

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

        public UpdateTask() {
            handler = GameHandler.getInstance();
            userId = User.getInstance().getId();
            gameId = parent.getGameId();
        }

        @Override
        protected Void call() throws Exception {

            Generation gen;

            Thread.sleep((long) speedSlider.getValue());

            gen = handler.getNextGeneration(userId, gameId);

            //TODO Was ist get Config??
            return null;

        }

    }

}
