
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

        gamescene = FXMLLoader.load(getClass().getResource("FXML/GoF.fxml"));
        gamescene.setVisible( false );
        gamescene.prefWidthProperty().bind(stackpane.widthProperty());
        gamescene.prefHeightProperty().bind(stackpane.heightProperty());
        
        loginMaskLoader = new FXMLLoader( getClass().getResource("FXML/LoginMask.fxml") );
        
        loginMask = (Parent) loginMaskLoader.load();
        LoginMaskController controller = loginMaskLoader.getController();
        controller.loginOnActionEvent((ActionEvent event) -> {
            if( controller.getUserName().equals("") || controller.getPassword().equals("") ) {
                
                controller.setErrorText("User name or password empty!");
                
            }
            else {            
                //error.setText("");
                ObservableList<Node> children = stackpane.getChildren();
                children.get(1).toFront();
                children.get(1).setVisible(true);
                children.get(0).setVisible( false );
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
