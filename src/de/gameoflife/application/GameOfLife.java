package de.gameoflife.application;

import com.goebl.david.Webb;
import de.gameoflife.connection.rabbitmq.RabbitMQConnection;
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
import org.json.JSONObject;
import rmi.data.GameUI;

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
    private Parent loadGame;
    private Parent deleteGame;
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

        queue = new RabbitMQConnection();

        GameHandler.init();

        this.primaryStage = primaryStage;

        initLoginScreen();
        initGameScreen();
        initLoadGame();
        initDeleteGame();
        initNewGameScreen();

        ObservableList<Node> children = stackpane.getChildren();
        children.addAll(loginMask, gamescene, newGame, loadGame, deleteGame);

        currentNodeInFront = loginMask;

        Scene scene = new Scene(stackpane, 600, 400);

        primaryStage.centerOnScreen();
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        queue.closeConnection();
        GameHandler.getInstance().closeConnection();

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

        loadGameController.setItems();
        loadGameController.clearSelection();

        loadGame.toFront();
        loadGame.setVisible(true);

        gamescene.setDisable(true);

        loadGame.requestFocus();

    }

    void deleteGame() {

        deleteGameController.setItems();

        deleteGame.toFront();
        deleteGame.setVisible(true);

        gamescene.setDisable(true);

        deleteGame.requestFocus();

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

                //TODO Check if name is Valid??? -nicht null, "", bereits vorhanden
                boolean successful = GameHandler.getInstance().generateNewGame(User.getInstance().getId(), newGameController.getGameName());

                //System.out.println( successful );
                if (successful) {

                    gamesceneController.createTab(newGameController.getGameName());

                    closeNewGame();

                } else {

                    newGameController.setErrorText("An error occurred.");

                }

                //gamesceneController.createTab( "asdfasdf" );
                //closeNewGame();
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

                GameUI g = loadGameController.getSelectedGame();

                if (g != null) {

                    boolean successful = GameHandler.getInstance().loadGame(User.getInstance().getId(), g.getGameId());

                    //System.out.println( successful );
                    if (successful && !gamesceneController.gameIsOpen(g.getGameId())) {

                        gamesceneController.createTab(g.getGameId());

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

            GameUI g = deleteGameController.getSelectedGame();

            if (g != null) {

                if (gamesceneController.gameIsOpen(g.getGameId())) gamesceneController.closeTab(g.getGameId());

                    //deleteGameController.setItems();
                queue.deleteGame(User.getInstance().getId(), g.getGameId());

                //TODO Spiel Tab offen und loeschen soll das Tab schließen.
                closeDeleteScreen();

            }

        });

        deleteGameController.cancelEvent((ActionEvent cancelEvent) -> {

            closeDeleteScreen();

        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
