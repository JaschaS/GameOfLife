
package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
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
 * @author JScholz
 * 
 * @version 2014-12-11-1 
 * 
 */
public class GameOfLife extends Application {

    public static ReadOnlyDoubleProperty stageWidthProperty;
    public static ReadOnlyDoubleProperty stageHeightProperty;
       
    private final StackPane stackpane = new StackPane();
    private Stage primaryStage;
    private AnchorPane gamescene;
    private Parent newGame;
    private Parent loginMask;
    private FXMLLoader loginMaskLoader;
    private FXMLLoader gamesceneLoader;
    private FXMLLoader newGameLoader;
    private LoginMaskController loginMaskController;
    private GameOfLifeController gamesceneController;
    private NewGameController newGameController;
    private GameHandler connection;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        stageWidthProperty = primaryStage.widthProperty();
        stageHeightProperty = primaryStage.heightProperty();
        
        connection = new GameHandler();
        
        this.primaryStage = primaryStage;
        
        newGameLoader = new FXMLLoader( getClass().getResource("FXML/NewGame.fxml") ); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );
        
        newGame = (Parent) newGameLoader.load();
        newGame.setVisible(false);
        
        newGameController = newGameLoader.getController();
        
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
                
                showGameScreen();
                
            }
            
        });
        
        stackpane.setStyle("-fx-background-color: rgba(71, 71, 71, 0.5);");
        stackpane.getChildren().addAll( loginMask, gamescene, newGame );
        
        Scene scene = new Scene( stackpane, 600, 400 );
        
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        
        connection.closeConnection();
        
        System.exit(0);
    }
    
    void showLoginScreen() {
        
        ObservableList<Node> children = stackpane.getChildren();
        
        int gamePosition = children.indexOf( gamescene );
        int loginPosition = children.indexOf( loginMask );
        int newGamePosition = children.indexOf( newGame );
         
        setChildrenVisible(false, true, false);
        
        children.get(gamePosition).toBack();
        children.get(loginPosition).toFront();
        children.get(newGamePosition).toBack();  
        
    }
    
    void showGameScreen() {
        
         ObservableList<Node> children = stackpane.getChildren();
        
        int gamePosition = children.indexOf( gamescene );
        int loginPosition = children.indexOf( loginMask );
        int newGamePosition = children.indexOf( newGame );
        
        setChildrenVisible(true, false, false);
        
        children.get(gamePosition).toFront();
        children.get(loginPosition).toBack();
        children.get(newGamePosition).toBack();  
        
    }

    void newGame() {
        
        ObservableList<Node> children = stackpane.getChildren();
        
        int gamePosition = children.indexOf( gamescene );
        int loginPosition = children.indexOf( loginMask );
        int newGamePosition = children.indexOf( newGame );
        
        setChildrenVisible(true, false, true);
        
        children.get(gamePosition).toBack();
        children.get(loginPosition).toBack();
        children.get(newGamePosition).toFront();
        
        gamescene.setDisable(true);
        
        newGameController.setFocus();
        
        newGameController.createEvent((ActionEvent event1) -> {
            
            try {
                
                //TODO Check if name is Valid??? -nicht null, "", bereits vorhanden


                    //boolean successful = connection.generateNewGame( 0, newGameController.getGameName() );
                    
                    //if( successful ) {
                        
                        gamesceneController.createTab( newGameController.getGameName() );
                        
                        closeNewGame();
                    
                    /*}
                    else {
                    
                        newGameController.setErrorText("Es ist ein Fehler beim Erstellen aufgetretten.");
                        
                    }
                */
            } catch (IOException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        });
        
        newGameController.cancelEvent( (ActionEvent event2) -> {
        
            closeNewGame();
            
        });
        
        
    }
    
    private void closeNewGame() {
            newGameController.clearText();
            newGame.setVisible(false);
            newGame.toBack();
            gamescene.setDisable(false);   
    }
    
    private void setChildrenVisible( boolean gamesceneVisible, boolean loginVisible, boolean newGameVisible ) {
        gamescene.setVisible(gamesceneVisible);
        loginMask.setVisible(loginVisible);
        newGame.setVisible(newGameVisible);      
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
