
package de.gameoflife.application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
    private Stage primaryStage;
    private AnchorPane gamescene;
    private AnchorPane newGame;
    private Parent loginMask;
    private FXMLLoader loginMaskLoader;
    private FXMLLoader gamesceneLoader;
    private FXMLLoader newGameLoader;
    private LoginMaskController loginMaskController;
    private GameOfLifeController gamesceneController;
    private NewGameController newGameController;
    
    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;
        
        newGameLoader = new FXMLLoader( getClass().getResource("FXML/NewGame.fxml") ); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );
        
        newGame = (AnchorPane) newGameLoader.load();
        newGame.setStyle("-fx-background-color: rgba(71, 71, 71, 0.5)");
        
        newGameController = newGameLoader.getController();
        newGameController.setOwner(primaryStage);
        
        gamesceneLoader = new FXMLLoader( getClass().getResource("FXML/GoF.fxml") );
        
        gamescene = (AnchorPane) gamesceneLoader.load();
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
                
                //TODO schauen ob username und passwort richtig sind!
                
                User.create( controller.getUserName(), controller.getPassword() );
                
                gamesceneController.setUsername( controller.getUserName() );
                
                controller.clear();
                
                //ObservableList<Node> children = stackpane.getChildren();
                /*children.get(0).setVisible( false );
                children.get(1).setVisible(true);
                children.get(2).setVisible( false );
                children.get(2).toBack();
                children.get(1).toFront();
                children.get(0).toBack();
                */
                //showLoginScreen();
                
                
                showGameScreen();
                
            }
            
        });
        
        stackpane.getChildren().addAll( loginMask, gamescene );

        StackPane.setAlignment(newGame, Pos.CENTER);
        
        
        Scene scene = new Scene( stackpane, 600, 400 );
        
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
    
    void showLoginScreen() {
        
        ObservableList<Node> children = stackpane.getChildren();
        
        int game = children.indexOf( gamescene );
        int login = children.indexOf( loginMask );
        //int newGameDialog = children.indexOf( newGame );
        
        children.get(game).setVisible(false);
        children.get(login).setVisible(true);
        //children.get(newGameDialog).setVisible(false);   
        
        children.get(game).toBack();
        children.get(login).toFront();
        //children.get(newGameDialog).toBack();  
    }
    
    void showGameScreen() {
        
        ObservableList<Node> children = stackpane.getChildren();
        
        int game = children.indexOf( gamescene );
        int login = children.indexOf( loginMask );
        //int newGameDialog = children.indexOf( newGame );
        
        children.get(game).setVisible(true);
        children.get(login).setVisible(false);
        //children.get(newGameDialog).setVisible(false);   
        
        children.get(game).toFront();
        children.get(login).toBack();
        //children.get(newGameDialog).toBack(); 
        
    }

    void newGame() {
        
        newGameController.onActionEvent((ActionEvent event1) -> {
            
            try {
                gamesceneController.createTab( newGameController.getGameName() );
            } catch (IOException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            newGameController.hide();
            
        });
        
        newGameController.showAndWait();
        
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
