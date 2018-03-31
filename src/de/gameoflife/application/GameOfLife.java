package de.gameoflife.application;

import de.gameoflife.application.login.LoginMaskController;
import de.gameoflife.connection.rabbitmq.RabbitMQConnection;
import de.gameoflife.connection.rmi.GameHandler;
import de.gameoflife.connection.rmi.IConnectionRuleEditor;
import de.gameoflife.connection.tasks.LoadingScreenTask;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 * <p>
 * @version 2014-12-11-1
 * <p>
 */
public final class GameOfLife extends Application {

    public static ReadOnlyDoubleProperty stageWidthProperty;
    public static ReadOnlyDoubleProperty stageHeightProperty;

    private final StackPane stackpane = new StackPane ();
    private Stage primaryStage;
    private AnchorPane gamescene;
    private Parent newGame;
    private Parent loginMask;
    private Parent loadGame;
    private Parent deleteGame;
    private Parent loadingScreen;
    private LoginMaskController loginMaskController;
    private GameOfLifeController gamesceneController;
    private NewGameController newGameController;
    private LoadGameController loadGameController;
    private DeleteGameController deleteGameController;
    private Node currentNodeInFront;
    private RabbitMQConnection queue;

    @Override
    public void start ( Stage primaryStage ) throws IOException {

        stageWidthProperty = primaryStage.widthProperty ();
        stageHeightProperty = primaryStage.heightProperty ();

        this.primaryStage = primaryStage;

        FXMLLoader connectionErrorLoader = new FXMLLoader ( getClass ().getResource ( "FXML/ConnectionError.fxml" ) ); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );

        Parent connectionError = ( Parent ) connectionErrorLoader.load ();
        connectionError.setVisible ( false );
        ConnectionErrorController connectionErrorController = connectionErrorLoader.getController ();
        connectionErrorController.addActionEvent ( ( ActionEvent event ) -> {
            try {

                GameHandler.getInstance ().establishConnection ();

                connectionError.setVisible ( false );
                connectionError.toBack ();

                loginMask.setVisible ( true );
                loginMask.toFront ();

            }
            catch ( NotBoundException | MalformedURLException | RemoteException ex ) {
                Logger.getLogger ( GameOfLife.class.getName () ).log ( Level.SEVERE, null, ex );
            }
        } );

        loadingScreen = FXMLLoader.load ( getClass ().getResource ( "FXML/LoadingScreen.fxml" ) );
        loadingScreen.setVisible ( false );
        loadingScreen.toBack ();

        initLoginScreen ();
        initGameScreen ();
        initLoadGame ();
        initDeleteGame ();
        initNewGameScreen ();

        ObservableList<Node> children = stackpane.getChildren ();
        children.addAll ( loginMask, gamescene, newGame, loadGame, deleteGame, loadingScreen, connectionError );

        currentNodeInFront = loginMask;

        Scene scene = new Scene ( stackpane, 1280, 720 );

        primaryStage.centerOnScreen ();
        primaryStage.setTitle ( "Game of Life" );
        primaryStage.setScene ( scene );
        primaryStage.show ();

        try {

            queue = new RabbitMQConnection ();

            GameHandler.init ();

        }
        catch ( NotBoundException | MalformedURLException | RemoteException ex ) {

            loginMask.setVisible ( false );
            loginMask.toBack ();

            connectionError.setVisible ( true );
            connectionError.toFront ();

        }
        catch ( IOException ex ) {

            loginMask.setVisible ( false );
            loginMask.toBack ();

            connectionError.setVisible ( true );
            connectionError.toFront ();

        }
    }

    @Override
    public void stop () throws Exception {
        super.stop ();

        queue.closeConnection ();

        GameHandler connection = GameHandler.getInstance ();
        connection.stopCurrentRunningGame ();
        connection.closeConnection ();

        System.exit ( 0 );
    }

    public void showLoginScreen () {
        showScreen ( loginMask );
        GameHandler.getInstance ().clearGameList ();
        User.removeInstance ();

    }

    public void showGameScreen () {
        showScreen ( gamescene );
    }

    public void newGame () {

        newGame.toFront ();
        newGame.setVisible ( true );

        gamescene.setDisable ( true );

        newGameController.setFocus ();

    }

    public void renameGame ( int gameId ) {

        newGameController.setGameId ( gameId );
        newGameController.createsAGame ( false );

        newGame ();

    }

    public void loadGame () {
        loadOrDeleteGame ( loadGame, loadGameController );
    }

    public void deleteGame () {
        loadOrDeleteGame ( deleteGame, deleteGameController );
    }

    private void showScreen ( final Parent parent ) {
        currentNodeToBack ();

        currentNodeInFront = parent;
        gamescene.setVisible ( true );
        gamescene.toFront ();
    }

    private void loadOrDeleteGame ( final Parent parent, final TableViewWindow view ) {
        gamescene.setDisable ( true );

        loadingScreen.setVisible ( true );
        loadingScreen.toFront ();

        LoadingScreenTask task = new LoadingScreenTask ( view, parent, loadingScreen );
        Thread t = new Thread ( task );
        t.start ();
    }

    private void closeNewGame () {
        newGameController.clearText ();
        closeScreen ( newGame );
    }

    private void currentNodeToBack () {
        currentNodeInFront.setVisible ( false );
        currentNodeInFront.toBack ();
    }

    private void closeScreen ( final Parent parent ) {
        parent.setVisible ( false );
        parent.toBack ();
        gamescene.setDisable ( false );
    }

    private void initNewGameScreen () throws IOException {
        FXMLLoader newGameLoader = new FXMLLoader ( getClass ().getResource ( "FXML/NewGame.fxml" ) ); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );

        newGame = ( Parent ) newGameLoader.load ();
        newGame.setVisible ( false );
        newGameController = newGameLoader.getController ();
        newGameController.createEvent ( ( ActionEvent event1 ) -> {
            try {

                GameHandler handler = GameHandler.getInstance ();
                IConnectionRuleEditor ruleEditor = handler.getRuleEditor ();

                if ( newGameController.createsAGame () ) {

                    boolean successful = ruleEditor.generateNewGame ( User.getInstance ().getId (), newGameController.getGameName () );

                    if ( successful ) {

                        gamesceneController.createTab ( newGameController.getGameName () );

                        closeNewGame ();

                    }
                    else {

                        newGameController.setErrorText ( "An error occurred." );

                    }

                }
                else {

                    String newName = newGameController.getGameName ();

                    if ( !newName.equals ( "" ) ) {

                        GameUI game = handler.getGame ( newGameController.getGameId () );

                        if ( !newName.equals ( game.getGameName () ) ) {

                            game.setGameName ( newGameController.getGameName () );

                            ruleEditor.saveGame ( game.getGameId () );

                        }

                        gamesceneController.renameSelectedTab ( newName );
                        newGameController.createsAGame ( true );
                        closeNewGame ();

                    }
                    else {

                        newGameController.setErrorText ( "Wrong input." );

                    }

                }

            }
            catch ( IOException ex ) {
                Logger.getLogger ( GameOfLife.class.getName () ).log ( Level.SEVERE, null, ex );
            }
        } );

        newGameController.cancelEvent ( ( ActionEvent event2 ) -> {

            closeNewGame ();

        } );

    }

    private void initLoginScreen () throws IOException {
        FXMLLoader loginMaskLoader = new FXMLLoader ( getClass ().getResource ( "FXML/LoginMask.fxml" ) );

        loginMask = ( Parent ) loginMaskLoader.load ();
        LoginMaskController controller = loginMaskLoader.getController ();
        controller.loginOnActionEvent ( () -> {
            final String username = User.getInstance ().getUsername ();

            gamesceneController.setUsername ( "Welcome, " + username );
            controller.clear ();
            showGameScreen ();
        } );
        stackpane.setStyle ( "-fx-background-color: rgba(71, 71, 71, 0.5);" );
    }

    private void initGameScreen () throws IOException {

        FXMLLoader gamesceneLoader = new FXMLLoader ( getClass ().getResource ( "FXML/GoF.fxml" ) );

        gamescene = ( AnchorPane ) gamesceneLoader.load ();
        gamescene.setVisible ( false );
        gamescene.prefWidthProperty ().bind ( stackpane.widthProperty () );
        gamescene.prefHeightProperty ().bind ( stackpane.heightProperty () );

        gamesceneController = gamesceneLoader.getController ();
        gamesceneController.setRootApplication ( this );
    }

    private void initLoadGame () throws IOException {

        FXMLLoader loadingScreenLoader = new FXMLLoader ( getClass ().getResource ( "FXML/LoadGame.fxml" ) );

        loadGame = ( Parent ) loadingScreenLoader.load ();
        loadGame.setVisible ( false );

        loadGameController = loadingScreenLoader.getController ();

        loadGameController.okButtonEvent ( ( ActionEvent event ) -> {

            try {

                int gameId = loadGameController.getSelectedGameID ();

                if ( gameId != TableViewWindow.ERROR_VALUE ) {

                    if ( !gamesceneController.gameIsOpen ( gameId ) ) {

                        gamesceneController.createTab ( gameId );

                        closeScreen ( loadGame );

                    }
                    else {
                        closeScreen ( loadGame );
                    }

                }

            }
            catch ( IOException ex ) {
                Logger.getLogger ( GameOfLife.class.getName () ).log ( Level.SEVERE, null, ex );
            }

        } );

        loadGameController.cancelEvent ( ( ActionEvent event ) -> {

            closeScreen ( loadGame );

        } );

    }

    private void initDeleteGame () throws IOException {

        FXMLLoader deleteGameLoader = new FXMLLoader ( getClass ().getResource ( "FXML/DeleteGame.fxml" ) );

        deleteGame = ( Parent ) deleteGameLoader.load ();
        deleteGame.setVisible ( false );

        deleteGameController = deleteGameLoader.getController ();

        deleteGameController.okButtonEvent ( ( ActionEvent deleteEvent ) -> {

            int gameId = deleteGameController.getSelectedGameID ();
            System.out.println ( gameId );

            if ( gameId != TableViewWindow.ERROR_VALUE ) {

                if ( gamesceneController.gameIsOpen ( gameId ) ) {
                    gamesceneController.closeTab ( gameId );
                }

                queue.deleteGame ( User.getInstance ().getId (), gameId );

                //TODO Spiel Tab offen und loeschen soll das Tab schließen.
                closeScreen ( deleteGame );

            }

        } );

        deleteGameController.cancelEvent ( ( ActionEvent cancelEvent ) -> {

            closeScreen ( deleteGame );

        } );

    }

    public static void main ( String[] args ) {
        launch ( args );
    }

}
