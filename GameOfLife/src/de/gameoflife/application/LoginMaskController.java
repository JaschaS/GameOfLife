
package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author JScholz
 * 
 * @version 2014-12-11-1
 * 
 */
public class LoginMaskController implements Initializable {

    @FXML private Button login;
    @FXML private TextField user;
    @FXML private PasswordField password;
    @FXML private Label error;
    
    public void setOnActionEvent(EventHandler event) throws IOException {
    
        login.setOnAction(event);
    
    }
    
    public void setError( String errorText ) {
        error.setText( errorText );
    }
    
    public String getUserName() {
        return user.getText();
    }
    
    public String getPassword() {
        return password.getText();
    }
    
    public boolean isUserAndPasswordEmpty() {
        return user.getText().equals("") || password.getText().equals("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        assert( login == null );
        
        login.setDefaultButton(true);
        
    }
    
}
