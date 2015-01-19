/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 */
public class ListViewWindow {

    @FXML
    protected TableView<GameUI> gameList;
    @FXML
    protected TableColumn<GameUI, String> name;
    @FXML
    protected TableColumn<GameUI, String> date;
    @FXML
    protected Button okButton;
    @FXML
    protected Button cancel;

    public void okButtonEvent(EventHandler<ActionEvent> event) {

        okButton.setOnAction(event);

    }

    public void cancelEvent(EventHandler<ActionEvent> event) {

        cancel.setOnAction(event);

    }

    public void setItems() {

        ObservableList<GameUI> data = GameHandler.getInstance().getGameList(User.getInstance().getId());

        gameList.setItems(data);

    }

    public void clearSelection() {

        gameList.getSelectionModel().clearSelection();

    }

    public GameUI getSelectedGame() {

        return gameList.getSelectionModel().getSelectedItem();

    }

}
