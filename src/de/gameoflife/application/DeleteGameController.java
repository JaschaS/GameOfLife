package de.gameoflife.application;

import de.gameoflife.connection.tasks.DeleteGameTask;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import rmi.data.GameUI;

/**
 * FXML Controller class
 * <p>
 * @author JScholz
 */
public class DeleteGameController extends TableViewWindow implements Initializable {

    @Override
    public void initialize ( URL url, ResourceBundle rb ) {

        name.setCellValueFactory ( ( TableColumn.CellDataFeatures<GameUI, String> param ) -> new SimpleStringProperty ( param.getValue ().getGameName () ) );
        date.setCellValueFactory ( ( TableColumn.CellDataFeatures<GameUI, String> param ) -> new SimpleStringProperty ( param.getValue ().getCreationDate ().toString () ) );

        okButtonEvent ( ( ActionEvent deleteEvent ) -> {

            int gameId = getSelectedGameID ();
            System.out.println ( gameId );

            if ( gameId != TableViewWindow.ERROR_VALUE ) {

                final GameOfLifeController controller = getGamesceneController ();

                assert controller != null;

                if ( controller.gameIsOpen ( gameId ) ) {
                    controller.closeTab ( gameId );
                }

                final DeleteGameTask task = new DeleteGameTask ( User.getInstance ().getId (), gameId );
                final Thread t = new Thread ( task );
                t.start ();

                //TODO Spiel Tab offen und loeschen soll das Tab schließen.
                closeScreen ();

            }

        } );

        cancelEvent ( ( ActionEvent cancelEvent ) -> {

            closeScreen ();

        } );
    }

}
