package de.gameoflife.connection.tasks;

import com.goebl.david.Webb;
import org.json.JSONObject;

/**
 *
 * @author JScholz
 */
public class DeleteGameTask extends DefaultTask<Void> {

    private final int userId;
    private final int gameId;

    public DeleteGameTask ( final int userId, final int gameId ) {

        super ( "http://localhost:8080" );

        this.userId = userId;
        this.gameId = gameId;
    }

    public int getUserId () {
        return userId;
    }

    public int getGameId () {
        return gameId;
    }

    @Override
    protected Void call () throws Exception {
        Webb webb = Webb.create ();
        webb.setBaseUri ( getUrl () );

        JSONObject result = webb.get ( "/deleteGame" )
                .param ( "userId", getUserId ())
                .param ( "gameId", getGameId ())
                .ensureSuccess ()
                .asJsonObject ()
                .getBody ();
        
        error = result.getBoolean ( "error" );
        if ( error ) {
            errorMassage = result.getString ( "errorMessage" );
        }
        
        return null;
    }

}
