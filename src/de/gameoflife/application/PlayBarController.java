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

    private Task<Void> updateTask;
    private GameTab parent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue, Number newValue) -> {

            currentSpeed.setText(newValue.intValue() + " Gen/Min");

        });

    }

    public void editorActionEvent(EventHandler<ActionEvent> event) {

        editor.setOnAction(event);

    }

    @FXML
    public void play(ActionEvent event) throws IOException {

        if (updateTask != null && !updateTask.isRunning()) {

            GameHandler.getInstance().startEngine( User.getInstance().getId(), parent.getGameId() );
            
            updateTask = new UpdateTask();

            Thread th = new Thread(updateTask);
            th.start();

        }

    }

    @FXML
    public void stop(ActionEvent event) throws IOException {

        if (updateTask != null && updateTask.isRunning()) {
            
            GameHandler.getInstance().stopEngine(User.getInstance().getId(), parent.getGameId() );
            
            updateTask.cancel();
            
        }

    }

    @FXML
    public void next(ActionEvent event) throws IOException {

        Generation gen = GameHandler.getInstance().getNextGeneration( User.getInstance().getId(), parent.getGameId() );
        
        //TODO Wie funktioniert Generation
        parent.getCanvas().setGeneration(null);
        
    }

    @FXML
    public void previous(ActionEvent event) throws IOException {

        //TODO Prev Generation????
        
    }

    @FXML
    public void analysis(ActionEvent event) throws IOException {

        GameHandler.getInstance().startAnalysis( parent.getGameId() );
     
        //TODO Analysis abfangen...
        
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
