package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import rmi.data.GameUI;

/**
 * FXML Controller class
 * <p>
 * @author JScholz
 */
public final class LoadGameController extends TableViewWindow implements Initializable {

    @FXML
    private TableColumn<GameUI, Boolean> history;
    @FXML
    private TableColumn<GameUI, Boolean> analysis;

    @Override
    public void initialize ( URL url, ResourceBundle rb ) {

        name.setCellValueFactory ( ( TableColumn.CellDataFeatures<GameUI, String> param ) -> new SimpleStringProperty ( param.getValue ().getGameName () ) );
        date.setCellValueFactory ( ( TableColumn.CellDataFeatures<GameUI, String> param ) -> new SimpleStringProperty ( param.getValue ().getCreationDate ().toString () ) );

        history.setCellFactory ( celldata -> new CheckBoxTableCell<> () );
        history.setCellValueFactory ( ( TableColumn.CellDataFeatures<GameUI, Boolean> param ) -> new SimpleBooleanProperty ( param.getValue ().isHistoryAvailable () ) );

        analysis.setCellFactory ( celldata -> new CheckBoxTableCell<> () );
        analysis.setCellValueFactory ( ( TableColumn.CellDataFeatures<GameUI, Boolean> param ) -> new SimpleBooleanProperty ( param.getValue ().isAnalysisAvailable () ) );

        okButtonEvent ( ( ActionEvent deleteEvent ) -> {
            try {

                int gameId = getSelectedGameID ();

                if ( gameId != TableViewWindow.ERROR_VALUE ) {

                    final GameOfLifeController controller = getGamesceneController ();

                    assert controller != null;

                    if ( !controller.gameIsOpen ( gameId ) ) {

                        controller.createTab ( gameId );

                    }

                    closeScreen ();
                }

            }
            catch ( IOException ex ) {
                Logger.getLogger ( GameOfLife.class.getName () ).log ( Level.SEVERE, null, ex );
            }

        } );

        cancelEvent ( ( ActionEvent cancelEvent ) -> {

            closeScreen ();

        } );
    }

}
