
package de.gameoflife.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 * 
 * @version 2014-12-11-1 
 * 
 */
public class GameOfLife extends Application {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    
    private final StackPane stackpane = new StackPane();
    private AnchorPane gamescene;
    private Parent loginMask;
    private FXMLLoader loginMaskLoader;
    private FXMLLoader gamesceneLoader;
    private LoginMaskController loginMaskController;
    private GameOfLifeController gamesceneController;
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        gamesceneLoader = new FXMLLoader( getClass().getResource("FXML/GoF.fxml") );
        
        gamescene = (AnchorPane) gamesceneLoader.load();//= FXMLLoader.load(getClass().getResource("FXML/GoF.fxml"));
        gamescene.setVisible( false );
        gamescene.prefWidthProperty().bind(stackpane.widthProperty());
        gamescene.prefHeightProperty().bind(stackpane.heightProperty());
        
        gamesceneController = gamesceneLoader.getController();
        gamesceneController.setRootApplication(this);
        
        loginMaskLoader = new FXMLLoader( getClass().getResource("FXML/LoginMask.fxml") );
        
        loginMask = (Parent) loginMaskLoader.load();
        LoginMaskController controller = loginMaskLoader.getController();
        controller.loginOnActionEvent((ActionEvent event) -> {
            if( controller.getUserName().equals("") || controller.getPassword().equals("") ) {
                
                controller.setErrorText("User name or password empty!");
                
            }
            else {
                
                System.out.println("Password check");
                
                //TODO schauen ob username und passwort richtig sind!
                
                User.create( controller.getUserName(), controller.getPassword() );
                
                controller.clear();
                
                ObservableList<Node> children = stackpane.getChildren();
                children.get(0).setVisible( false );
                children.get(1).setVisible(true);
                children.get(1).toFront();
                children.get(0).toBack();
                
            }
        });
        
        stackpane.getChildren().addAll( loginMask, gamescene );

        Scene scene = new Scene( stackpane, 600, 400 );

        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
    
    void logout() {
    
        assert stackpane != null ;
        assert stackpane.getChildren().size() == 2;
        
        ObservableList<Node> children = stackpane.getChildren();
        children.get(1).setVisible(false);
        children.get(0).setVisible(true);
        children.get(0).toFront();
        children.get(1).toBack();
    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

/**
 * - Einige probleme mit der Stackpane der main app behoben
 * - Zusätzliche funktionalität im GoFController hinzugefügt
 * - User Klasse hinzugefügt
 */
