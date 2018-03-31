package de.gameoflife.connection.tasks;

import com.goebl.david.Webb;
import de.gameoflife.application.TableViewWindow;
import de.gameoflife.application.User;
import de.gameoflife.connection.rmi.GameHandler;
import de.gameoflife.connection.rmi.IConnectionRuleEditor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import org.json.JSONArray;
import org.json.JSONObject;
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 */
public class LoadingScreenTask extends DefaultTask<Void> {

    private final TableViewWindow view;
    private final Parent parent;
    private final Parent loadingScreen;

    public LoadingScreenTask ( final TableViewWindow view, final Parent parent, final Parent loadingScreen ) {
        
        super("http://localhost:8080");
        
        this.view = view;
        this.parent = parent;
        this.loadingScreen = loadingScreen;
    }

    @Override
    protected Void call () throws Exception {

        Webb webb = Webb.create ();
        webb.setBaseUri ( getUrl () );

        JSONObject result = webb.get ( "/GameList" )
                .param ( "userid", User.getInstance ().getId () )
                .ensureSuccess ()
                .asJsonObject ()
                .getBody ();

        ObservableList<GameUI> data = convert(result);

        Platform.runLater ( () -> {
            view.setItems ( data );
            view.clearSelection ();

            loadingScreen.toBack ();
            loadingScreen.setVisible ( false );

            parent.toFront ();
            parent.setVisible ( true );
            parent.requestFocus ();
        } );

        return null;

    }

    private ObservableList<GameUI> convert(final JSONObject result) {
        
        //TODO validate JSON
        JSONArray games = result.getJSONArray ( "games" );
        ObservableList<GameUI> list = FXCollections.observableArrayList ();
        
        for(int i=0; i < games.length (); ++i) {
        
            final JSONObject object = games.getJSONObject ( i );
            final int userId = object.getInt ("userId");
            final int gameId = object.getInt ("gameId");
            final String gameName = object.getString ( "gameName");
            
            final GameUI newGame = new GameUI (userId, gameId, gameName);
            list.add ( newGame );
        }
        
        return list;
    }
    
}
