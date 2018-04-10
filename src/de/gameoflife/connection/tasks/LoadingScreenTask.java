package de.gameoflife.connection.tasks;

import com.goebl.david.Webb;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import de.gameoflife.application.TableViewWindow;
import de.gameoflife.application.User;
import de.gameoflife.payloads.GoFGame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import org.json.JSONObject;
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 */
public class LoadingScreenTask extends DefaultTask<ObservableList<GameUI>> {

    private final TableViewWindow view;
    private final Parent parent;
    private final Parent loadingScreen;

    public LoadingScreenTask ( final TableViewWindow view, final Parent parent, final Parent loadingScreen ) {

        super ( "http://localhost:8080" );

        this.view = view;
        this.parent = parent;
        this.loadingScreen = loadingScreen;
    }

    @Override
    protected ObservableList<GameUI> call () throws Exception {

        Webb webb = Webb.create ();
        webb.setBaseUri ( getUrl () );

        JSONObject result = webb.get ( "/GameList" )
                .param ( "userid", User.getInstance ().getId () )
                .ensureSuccess ()
                .asJsonObject ()
                .getBody ();

        error = result.getBoolean ( "error" );
        if ( error ) {
            errorMassage = result.getString ( "errorMessage" );
        }
        else {
            ObservableList<GameUI> data = convert ( result );

            Platform.runLater ( () -> {
                view.setItems ( data );
                view.clearSelection ();

                loadingScreen.toBack ();
                loadingScreen.setVisible ( false );

                parent.toFront ();
                parent.setVisible ( true );
                parent.requestFocus ();
            } );
            
            return data;
        }

        return null;

    }

    private ObservableList<GameUI> convert ( final JSONObject result ) {

        //TODO validate JSON
        final String rawData = result.getString ( "message" );
        ObservableList<GameUI> list = FXCollections.observableArrayList ();

        try {
            GoFGame.Game game = GoFGame.Game.parseFrom ( ByteString.copyFromUtf8 ( rawData ) );

            GameUI g = new GameUI ( game.getUserID (), game.getGameID (), game.getGameName () );
            list.add ( g );
        }
        catch ( InvalidProtocolBufferException ex ) {
            Logger.getLogger ( LoadingScreenTask.class.getName () ).log ( Level.SEVERE, null, ex );
        }

        return list;
    }

}
