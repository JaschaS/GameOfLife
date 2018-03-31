package de.gameoflife.application.login;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import de.gameoflife.application.User;
import de.gameoflife.connection.tasks.LoginTask;
import de.gameoflife.payloads.Login;
import de.gameoflife.payloads.Login.UserLogin;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.json.JSONObject;

/**
 * FXML Controller class
 * <p>
 * @author JScholz
 * <p>
 * @version 2014-12-11-1
 * <p>
 */
public final class LoginMaskController implements Initializable {

    @FXML
    private Label error;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    private LoginTask task;
    private final ArrayList<SuccededListener> succedListener;

    public LoginMaskController () {
        succedListener = new ArrayList<> ();
    }

    @Override
    public void initialize ( URL location, ResourceBundle resources ) {
        login.defaultButtonProperty ().bind ( login.focusedProperty () );

        login.setOnAction ( ( ActionEvent event ) -> {
            if ( getUserName ().equals ( "" ) || getPassword ().equals ( "" ) ) {

                setErrorText ( "Username or password empty!" );

            }
            else if ( task != null && task.isRunning () ) {
                setErrorText ( "Please wait while we try to login ..." );
            }
            else {

                task = new LoginTask ( null, null );
                task.addEventFilter (
                        WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                        ( WorkerStateEvent stateEvent ) -> {
                            try {
                                taskSucceded ();
                            }
                            catch ( InterruptedException | ExecutionException ex ) {
                                Logger.getLogger ( LoginMaskController.class.getName () ).log ( Level.SEVERE, null, ex );
                            }
                        }
                );

                Thread t = new Thread ( task );
                t.start ();
            }
        } );

    }

    public void loginOnActionEvent ( SuccededListener listener ) {
        succedListener.add ( listener );
    }

    public void setErrorText ( String text ) {
        error.setText ( text );
    }

    public String getUserName () {
        return username.getText ();
    }

    public String getPassword () {
        return password.getText ();
    }

    public void clear () {
        error.setText ( "" );
        password.setText ( "" );
        username.setText ( "" );
    }

    private void taskSucceded () throws InterruptedException, ExecutionException {

        assert task != null && task.isDone ();

        final Login.UserLogin result = task.get ();

        //TODO Error inside Protobuf
        final boolean error = task.hasError ();//result.getBoolean ( "error" );

        if ( error ) {
            setErrorText ( task.getErrorMessage () );//result.getString ( "errorMessage" ) );
        }
        else {
            final String username = result.getUsername ();
            final int id = result.getUserID ();

            User.create ( username, id );

            for ( SuccededListener listener : succedListener ) {

                listener.succeded ();

            }
        }
    }

    public interface SuccededListener {

        public void succeded ();
    }

}
