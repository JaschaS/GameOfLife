package de.gameoflife.application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
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

    private GameOfLifeController gamesceneController;
    private Parent parent;
    private AnchorPane gamescene;

    public void okButtonEvent ( EventHandler<ActionEvent> event ) {

        okButton.setOnAction ( event );

    }

    public void cancelEvent ( EventHandler<ActionEvent> event ) {

        cancel.setOnAction ( event );

    }

    public void setItems ( ObservableList<GameUI> data ) {

        gameList.setItems ( data );

    }

    public void clearSelection () {

        gameList.getSelectionModel ().clearSelection ();

    }

    public int getSelectedGameID () {

        GameUI g = gameList.getSelectionModel ().getSelectedItem ();

        return g != null ? g.getGameId () : -1;

    }

    public void closeScreen () {
        assert parent != null;
        assert gamescene != null;
        
        parent.setVisible ( false );
        parent.toBack ();
        gamescene.setDisable ( false );
    }

    public void setGamesceneController ( GameOfLifeController gamesceneController ) {
        this.gamesceneController = gamesceneController;
    }

    public void setLoadGameParent ( Parent loadGameParent ) {
        this.parent = loadGameParent;
    }

    public void setGamescene ( AnchorPane gamescene ) {
        this.gamescene = gamescene;
    }

    public GameOfLifeController getGamesceneController () {
        return gamesceneController;
    }

    public Parent getLoadGameParent () {
        return parent;
    }

    public AnchorPane getGamescene () {
        return gamescene;
    }

}
