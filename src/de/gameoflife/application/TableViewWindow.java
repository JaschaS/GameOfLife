package de.gameoflife.application;

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
public class TableViewWindow {

    public static final int ERROR_VALUE = -1;
    
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

    public void setItems(ObservableList<GameUI> data) {

        gameList.setItems(data);

    }

    public void clearSelection() {

        gameList.getSelectionModel().clearSelection();

    }

    public int getSelectedGameID() {
        
        GameUI g = gameList.getSelectionModel().getSelectedItem();
        
        return g != null ? g.getGameId() : -1;

    }

}
