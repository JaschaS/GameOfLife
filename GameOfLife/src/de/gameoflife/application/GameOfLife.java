
package de.gameoflife.application;

import com.goebl.david.Webb;
import de.gameoflife.connection.rmi.GameHandlerSingleton;
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
import org.json.JSONObject;

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
    private Parent loadingScreen;
    private LoginMaskController loginMaskController;
    private GameOfLifeController gamesceneController;
    private NewGameController newGameController;
    private LoadGameController loadingScreenController;
    private Node currentNodeInFront;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        stageWidthProperty = primaryStage.widthProperty();
        stageHeightProperty = primaryStage.heightProperty();
        
        GameHandlerSingleton.init( "rmi://143.93.91.72/RuleEditor" );
        
        this.primaryStage = primaryStage;
        
        initLoginScreen();
        initGameScreen();
        initLoadingsScreen();
        initNewGameScreen();
        
        ObservableList<Node> children = stackpane.getChildren();
        children.addAll( loginMask, gamescene, newGame, loadingScreen );
        
        currentNodeInFront = loginMask;
        
        Scene scene = new Scene( stackpane, 600, 400 );
        
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        
        GameHandlerSingleton.closeConnection();
        
        System.exit(0);
    }
    
    void showLoginScreen() {
        
        currentNodeToBack();
        
        currentNodeInFront = loginMask;
        loginMask.setVisible(true);
        loginMask.toFront();
        
        User.removeInstance();
        
    }
    
    void showGameScreen() {
        
        currentNodeToBack();
        
        currentNodeInFront = gamescene;
        gamescene.toFront();
        gamescene.setVisible(true);
        
    }

    void newGame() {
        
        newGame.toFront();
        newGame.setVisible(true);
        
        gamescene.setDisable(true);
        
        newGameController.setFocus();
        
    }
    
    void loadGame() {
    
        loadingScreen.toFront();
        loadingScreen.setVisible(true);
        
        gamescene.setDisable(true);
        
        loadingScreen.requestFocus();
        
    }
    
    private void closeLoadScreen() {
    
        loadingScreen.setVisible(false);
        loadingScreen.toBack();
        gamescene.setDisable(false);
            
    }
    
    private void closeNewGame() {
            newGameController.clearText();
            newGame.setVisible(false);
            newGame.toBack();
            gamescene.setDisable(false);   
    }
    
    private void currentNodeToBack() {
        currentNodeInFront.setVisible(false);
        currentNodeInFront.toBack();       
    }
    
    private void initNewGameScreen() throws IOException {
        FXMLLoader newGameLoader = new FXMLLoader( getClass().getResource("FXML/NewGame.fxml") ); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );
        
        newGame = (Parent) newGameLoader.load();
        newGame.setVisible(false);
        newGameController = newGameLoader.getController();
        newGameController.createEvent((ActionEvent event1) -> {
            
            try {
                
                //TODO Check if name is Valid??? -nicht null, "", bereits vorhanden


                //boolean successful = connection.generateNewGame( User.getInstance().getId(), newGameController.getGameName() );
                    
                //System.out.println( successful );
                
                //if( successful ) {
                        
                        gamesceneController.createTab( newGameController.getGameName() );
                        
                        closeNewGame();
                    
                //}
                //else {
                    
                //    newGameController.setErrorText("Es ist ein Fehler beim Erstellen aufgetretten.");
                    
               //}
                
            } catch (IOException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        });
        
        newGameController.cancelEvent( (ActionEvent event2) -> {
        
            closeNewGame();
            
        });
    
    }
    
    private void initLoginScreen() throws IOException {
        FXMLLoader loginMaskLoader = new FXMLLoader( getClass().getResource("FXML/LoginMask.fxml") );
        
        loginMask = (Parent) loginMaskLoader.load();
        LoginMaskController controller = loginMaskLoader.getController();
        controller.loginOnActionEvent((ActionEvent event) -> {
            
            if( controller.getUserName().equals("") || controller.getPassword().equals("") ) {
                
                controller.setErrorText("Benutzername und Passwort leer!");
                
            }
            else {
                
                Webb webb = Webb.create();        
                webb.setBaseUri("http://143.93.91.71/Ajax");

                JSONObject result = webb.get("/login")
                    .param("loginname", controller.getUserName() )
                    .param("password", controller.getPassword() )
                    .ensureSuccess()
                    .asJsonObject()
                    .getBody();

                boolean error = result.getBoolean("error");

                if( error ) {

                    controller.setErrorText( result.getString("message") );

                }
                else {
                
                    JSONObject user = result.getJSONObject("callback");
                    
                    String username = user.getString("vorname");
                    int id = Integer.parseInt( user.getString("id") );
                    
                    User.create( username, id );
                    
                    gamesceneController.setUsername( "Willkommen, " + username );
                    
                    controller.clear();
                    
                    showGameScreen();
                    
                }
                
            }
            
        });
        
        stackpane.setStyle("-fx-background-color: rgba(71, 71, 71, 0.5);");
    }
    
    private void initGameScreen() throws IOException {
        FXMLLoader gamesceneLoader = new FXMLLoader( getClass().getResource("FXML/GoF.fxml") );
        
        gamescene = (AnchorPane) gamesceneLoader.load();
        gamescene.setVisible( false );
        gamescene.prefWidthProperty().bind(stackpane.widthProperty());
        gamescene.prefHeightProperty().bind(stackpane.heightProperty());
        
        gamesceneController = gamesceneLoader.getController();
        gamesceneController.setRootApplication(this);
    }
    
    private void initLoadingsScreen() throws IOException {
        FXMLLoader loadingScreenLoader = new FXMLLoader( getClass().getResource("FXML/LoadGame.fxml") );
        
        loadingScreen = (Parent) loadingScreenLoader.load();
        loadingScreen.setVisible( false );
        
        loadingScreenController = loadingScreenLoader.getController();
        
        loadingScreenController.loadEvent( (ActionEvent event) -> {
        
            closeLoadScreen();
        
        });
        
        loadingScreenController.cancelEvent( (ActionEvent event) -> {
        
            closeLoadScreen();
        
        });
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}