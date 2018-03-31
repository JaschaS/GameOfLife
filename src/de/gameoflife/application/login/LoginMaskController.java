package de.gameoflife.application.login;

import com.goebl.david.Webb;
import de.gameoflife.application.User;
import de.gameoflife.connection.tasks.LoginTask;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.T;
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
    private ArrayList<SuccededListener> succedListener;

    public LoginMaskController() {
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
                    catch ( InterruptedException ex ) {
                        Logger.getLogger ( LoginMaskController.class.getName() ).log ( Level.SEVERE, null, ex );
                    }
                    catch ( ExecutionException ex ) {
                        Logger.getLogger ( LoginMaskController.class.getName() ).log ( Level.SEVERE, null, ex );
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

        final JSONObject result = task.get ();
        final boolean error = result.getBoolean ( "error" );

        if ( error ) {
            setErrorText ( result.getString ( "message" ) );
        }
        else {
            JSONObject user = result.getJSONObject ( "callback" );

            //TODO vorname in user aendern?
            final String username = user.getString ( "vorname" );
            final int id = user.getInt ( "id" );

            User.create ( username, id );

            for(SuccededListener listener : succedListener) {
            
                listener.succeded ();
            
            }
        }
    }
    
    public interface SuccededListener {
        public void succeded();
    }

}
