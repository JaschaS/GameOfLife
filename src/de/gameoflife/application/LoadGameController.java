/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import rmi.data.GameUI;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class LoadGameController extends ListViewWindow implements Initializable {

    @FXML
    private TableColumn<GameUI, Boolean> history;
    @FXML
    private TableColumn<GameUI, Boolean> analysis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, String> param) -> new SimpleStringProperty(param.getValue().getGameName()));
        date.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, String> param) -> new SimpleStringProperty(param.getValue().getCreationDate().toString()));

        history.setCellFactory(celldata -> new CheckBoxTableCell<>());
        history.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, Boolean> param) -> new SimpleBooleanProperty(param.getValue().isHistoryAvailable()));

        analysis.setCellFactory(celldata -> new CheckBoxTableCell<>());
        analysis.setCellValueFactory((TableColumn.CellDataFeatures<GameUI, Boolean> param) -> new SimpleBooleanProperty(param.getValue().isAnalysisAvailable()));

    }


}
