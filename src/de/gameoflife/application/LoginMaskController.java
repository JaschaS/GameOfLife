
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
public final class LoginMaskController implements Initializable {

    @FXML private Label error;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void loginOnActionEvent( EventHandler<ActionEvent> event ) {
        login.setOnAction( event );
    }
    
    public void setErrorText( String text ) {
        error.setText( text );
    }
    
    public String getUserName() {
        return username.getText();
    }
    
    public String getPassword() {
        return password.getText();
    }
    
    public void clear() {
        error.setText("");
        password.setText("");
        username.setText("");
    }
    
}
