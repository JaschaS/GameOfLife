package de.gameoflife.connection.tasks;

import com.goebl.david.Webb;
import org.json.JSONObject;

/**
 *
 * @author JScholz
 */
public class LoginTask extends DefaultTask<JSONObject> {

    //TODO load url from database
    private final String user;
    private final String passwordHash;

    public LoginTask ( final String user, final String passwordHash ) {

        super("http://localhost:8080");
        
        assert user != null && !user.isEmpty ();
        assert passwordHash != null && !passwordHash.isEmpty ();

        this.user = user;
        this.passwordHash = passwordHash;
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
        webb.setBaseUri ( getUrl () );

        JSONObject result = webb.get ( "/login" )
                .param ( "loginname", getUser () )
                .param ( "password", getPasswordHash () )
                .ensureSuccess ()
                .asJsonObject ()
                .getBody ();
        
        return result;
    }

}
