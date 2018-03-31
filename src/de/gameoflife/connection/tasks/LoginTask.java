package de.gameoflife.connection.tasks;

import com.goebl.david.Webb;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import org.json.JSONObject;

/**
 *
 * @author JScholz
 */
public class LoginTask extends Task<JSONObject> {

    //TODO load url from database
    private final String url;
    private final String user;
    private final String passwordHash;

    public LoginTask ( final String user, final String passwordHash ) {

        assert user != null && !user.isEmpty ();
        assert passwordHash != null && !passwordHash.isEmpty ();

        this.url = "http://localhost:8080";
        this.user = user;
        this.passwordHash = passwordHash;
    }

    public String getUrl () {
        return url;
    }

    public String getUser () {
        return user;
    }

    public String getPasswordHash () {
        return passwordHash;
    }

    @Override
    protected JSONObject call () throws Exception {
        Webb webb = Webb.create ();
        webb.setBaseUri ( url );

        JSONObject result = webb.get ( "/login" )
                .param ( "loginname", getUser () )
                .param ( "password", getPasswordHash () )
                .ensureSuccess ()
                .asJsonObject ()
                .getBody ();
        
        return result;
    }

}
