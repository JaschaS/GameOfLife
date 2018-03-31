package de.gameoflife.connection.tasks;

import com.goebl.david.Webb;
import com.google.protobuf.ByteString;
import de.gameoflife.payloads.Login;
import org.json.JSONObject;

/**
 *
 * @author JScholz
 */
public class LoginTask extends DefaultTask<Login.UserLogin> {

    //TODO load url from database
    private final String user;
    private final String passwordHash;

    public LoginTask ( final String user, final String passwordHash ) {

        super ( "http://localhost:8080" );

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
    protected Login.UserLogin call () throws Exception {
        Webb webb = Webb.create ();
        webb.setBaseUri ( getUrl () );

        JSONObject result = webb.get ( "/login" )
                .param ( "loginname", getUser () )
                .param ( "password", getPasswordHash () )
                .ensureSuccess ()
                .asJsonObject ()
                .getBody ();

        error = result.getBoolean ( "error" );
        if ( error ) {
            errorMassage = result.getString ( "errorMessage" );
        }
        else {

            final String rawData = result.getString ( "message" );
            Login.UserLogin user = Login.UserLogin.parseFrom ( ByteString.copyFromUtf8 ( rawData ) );

            return user;
        }

        return null;
    }

}
