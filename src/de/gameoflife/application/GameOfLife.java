package de.gameoflife.application;

import com.goebl.david.Webb;
import de.gameoflife.connection.rabbitmq.RabbitMQConnection;
import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.JSONObject;
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 *
 * @version 2014-12-11-1
 *
 */
public final class GameOfLife extends Application {

    public static ReadOnlyDoubleProperty stageWidthProperty;
    public static ReadOnlyDoubleProperty stageHeightProperty;

    private final StackPane stackpane = new StackPane();
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
    public void start(Stage primaryStage) throws IOException {

        stageWidthProperty = primaryStage.widthProperty();
        stageHeightProperty = primaryStage.heightProperty();

        this.primaryStage = primaryStage;

        FXMLLoader connectionErrorLoader = new FXMLLoader(getClass().getResource("FXML/ConnectionError.fxml")); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );

        Parent connectionError = (Parent) connectionErrorLoader.load();
        connectionError.setVisible(false);
        ConnectionErrorController connectionErrorController = connectionErrorLoader.getController();
        connectionErrorController.addActionEvent((ActionEvent event) -> {
            try {

                GameHandler.getInstance().establishConnectionAnalysis();
                GameHandler.getInstance().establishConnectionGameEngine();
                GameHandler.getInstance().establishConnectionRuleEditor();

                //GameHandler.getInstance().establishConnection();
                connectionError.setVisible(false);
                connectionError.toBack();

                loginMask.setVisible(true);
                loginMask.toFront();

            } catch (NotBoundException | MalformedURLException | RemoteException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        loadingScreen = FXMLLoader.load(getClass().getResource("FXML/LoadingScreen.fxml"));
        loadingScreen.setVisible(false);
        loadingScreen.toBack();

        initLoginScreen();
        initGameScreen();
        initLoadGame();
        initDeleteGame();
        initNewGameScreen();

        ObservableList<Node> children = stackpane.getChildren();
        children.addAll(loginMask, gamescene, newGame, loadGame, deleteGame, loadingScreen, connectionError);

        currentNodeInFront = loginMask;

        Scene scene = new Scene(stackpane, 1280, 720);

        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();

        try {

            queue = new RabbitMQConnection();

            GameHandler.init();

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {

            loginMask.setVisible(false);
            loginMask.toBack();

            connectionError.setVisible(true);
            connectionError.toFront();

        } catch (IOException ex) {

            loginMask.setVisible(false);
            loginMask.toBack();

            connectionError.setVisible(true);
            connectionError.toFront();

        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        queue.closeConnection();

        GameHandler connection = GameHandler.getInstance();
        connection.stopCurrentRunningGame();
        connection.closeConnection();

        System.exit(0);
    }

    public void showLoginScreen() {

        currentNodeToBack();

        currentNodeInFront = loginMask;
        loginMask.setVisible(true);
        loginMask.toFront();

        User.removeInstance();

    }

    public void showGameScreen() {

        currentNodeToBack();

        currentNodeInFront = gamescene;
        gamescene.toFront();
        gamescene.setVisible(true);

    }

    public void newGame() {

        newGame.toFront();
        newGame.setVisible(true);

        gamescene.setDisable(true);

        newGameController.setFocus();

    }

    public void loadGame() {

        gamescene.setDisable(true);

        loadingScreen.setVisible(true);
        loadingScreen.toFront();

        LoadingScreenTask task = new LoadingScreenTask(loadGameController, loadGame);
        Thread t = new Thread(task);
        t.start();

    }

    public void deleteGame() {

        gamescene.setDisable(true);

        loadingScreen.setVisible(true);
        loadingScreen.toFront();

        LoadingScreenTask task = new LoadingScreenTask(deleteGameController, deleteGame);
        Thread t = new Thread(task);
        t.start();

    }

    private void closeLoadScreen() {

        loadGame.setVisible(false);
        loadGame.toBack();
        gamescene.setDisable(false);

    }

    private void closeDeleteScreen() {

        deleteGame.setVisible(false);
        deleteGame.toBack();
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
        FXMLLoader newGameLoader = new FXMLLoader(getClass().getResource("FXML/NewGame.fxml")); //FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );

        newGame = (Parent) newGameLoader.load();
        newGame.setVisible(false);
        newGameController = newGameLoader.getController();
        newGameController.createEvent((ActionEvent event1) -> {

            try {

                boolean successful = GameHandler.getInstance().generateNewGame(User.getInstance().getId(), newGameController.getGameName());

                if (successful) {

                    gamesceneController.createTab(newGameController.getGameName());

                    closeNewGame();

                } else {

                    newGameController.setErrorText("An error occurred.");

                }

            } catch (IOException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        newGameController.cancelEvent((ActionEvent event2) -> {

            closeNewGame();

        });

    }

    private void initLoginScreen() throws IOException {
        FXMLLoader loginMaskLoader = new FXMLLoader(getClass().getResource("FXML/LoginMask.fxml"));

        loginMask = (Parent) loginMaskLoader.load();
        LoginMaskController controller = loginMaskLoader.getController();
        controller.loginOnActionEvent((ActionEvent event) -> {

            if (controller.getUserName().equals("") || controller.getPassword().equals("")) {

                controller.setErrorText("Username or password empty!");

            } else {

                Webb webb = Webb.create();
                webb.setBaseUri("http://143.93.91.71/Ajax");

                JSONObject result = webb.get("/login")
                        .param("loginname", controller.getUserName())
                        .param("password", controller.getPassword())
                        .ensureSuccess()
                        .asJsonObject()
                        .getBody();

                boolean error = result.getBoolean("error");

                if (error) {

                    controller.setErrorText(result.getString("message"));

                } else {

                    JSONObject user = result.getJSONObject("callback");

                    String username = user.getString("vorname");
                    int id = Integer.parseInt(user.getString("id"));

                    User.create(username, id);

                    gamesceneController.setUsername("Welcome, " + username);

                    controller.clear();

                    showGameScreen();

                }

            }

        });

        stackpane.setStyle("-fx-background-color: rgba(71, 71, 71, 0.5);");
    }

    private void initGameScreen() throws IOException {

        FXMLLoader gamesceneLoader = new FXMLLoader(getClass().getResource("FXML/GoF.fxml"));

        gamescene = (AnchorPane) gamesceneLoader.load();
        gamescene.setVisible(false);
        gamescene.prefWidthProperty().bind(stackpane.widthProperty());
        gamescene.prefHeightProperty().bind(stackpane.heightProperty());

        gamesceneController = gamesceneLoader.getController();
        gamesceneController.setRootApplication(this);
    }

    private void initLoadGame() throws IOException {

        FXMLLoader loadingScreenLoader = new FXMLLoader(getClass().getResource("FXML/LoadGame.fxml"));

        loadGame = (Parent) loadingScreenLoader.load();
        loadGame.setVisible(false);

        loadGameController = loadingScreenLoader.getController();

        loadGameController.okButtonEvent((ActionEvent event) -> {

            try {

                int gameId = loadGameController.getSelectedGameID();

                if (gameId != TableViewWindow.ERROR_VALUE) {

                    if (!gamesceneController.gameIsOpen(gameId)) {

                        gamesceneController.createTab(gameId);

                        closeLoadScreen();

                    } else {
                        closeLoadScreen();
                    }

                }

            } catch (IOException ex) {
                Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        loadGameController.cancelEvent((ActionEvent event) -> {

            closeLoadScreen();

        });

    }

    private void initDeleteGame() throws IOException {

        FXMLLoader deleteGameLoader = new FXMLLoader(getClass().getResource("FXML/DeleteGame.fxml"));

        deleteGame = (Parent) deleteGameLoader.load();
        deleteGame.setVisible(false);

        deleteGameController = deleteGameLoader.getController();

        deleteGameController.okButtonEvent((ActionEvent deleteEvent) -> {

            int gameId = deleteGameController.getSelectedGameID();
            System.out.println(gameId);
            if (gameId != TableViewWindow.ERROR_VALUE) {

                if (gamesceneController.gameIsOpen(gameId)) {
                    gamesceneController.closeTab(gameId);
                }

                queue.deleteGame(User.getInstance().getId(), gameId);

                //TODO Spiel Tab offen und loeschen soll das Tab schließen.
                closeDeleteScreen();

            }

        });

        deleteGameController.cancelEvent((ActionEvent cancelEvent) -> {

            closeDeleteScreen();

        });

    }

    private class LoadingScreenTask extends Task<Void> {

        private final TableViewWindow view;
        private final Parent parent;
        
        public LoadingScreenTask(TableViewWindow view, Parent parent) {
            this.view = view;
            this.parent = parent;
        }

        @Override
        protected Void call() throws Exception {

            ObservableList<GameUI> data = GameHandler.getInstance().getGameList(User.getInstance().getId());

            Platform.runLater(() -> {
                view.setItems(data);
                view.clearSelection();
                
                loadingScreen.toBack();
                loadingScreen.setVisible(false);
                
                parent.toFront();
                parent.setVisible(true);
                parent.requestFocus();
            });

            return null;

        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
