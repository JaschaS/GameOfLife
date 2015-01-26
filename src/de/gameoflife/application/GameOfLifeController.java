package de.gameoflife.application;

import de.gameoflife.connection.rmi.GameHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import rmi.data.GameUI;
import rmi.data.rules.NumericRule;
import rmi.data.rules.RulePattern;

/**
 * FXML Controller class
 *
 * @author JScholz
 *
 * @version 2014-12-11-1
 *
 */
public final class GameOfLifeController {

    @FXML
    private TabPane tabPane;
    @FXML
    private Label username;
    private GameOfLife application;

    private final HashMap<Integer, Tab> gameOpen = new HashMap<>();

    @FXML
    public void logout(ActionEvent event) throws IOException {

        tabPane.getTabs().clear();
        gameOpen.clear();

        application.showLoginScreen();

    }

    @FXML
    public void newGame(ActionEvent event) throws IOException {
        application.newGame();
    }

    @FXML
    public void loadGame(ActionEvent event) throws IOException {
        application.loadGame();
    }

    @FXML
    public void deleteGame(ActionEvent event) throws IOException {
        application.deleteGame();
    }

    public void setRootApplication(GameOfLife rootApp) {
        application = rootApp;
    }

    public void setUsername(String name) {
        username.setText(name);
    }

    public void createTab(int gameId) throws IOException {

        createTab(GameHandler.getInstance().getGame(gameId));

    }

    public void createTab(String gamename) throws IOException {

        GameUI game = GameHandler.getInstance().getGame(gamename);

        createTab(game);

    }

    public void closeTab(int gameId) {

        tabPane.getTabs().remove(gameOpen.get(gameId));

    }

    public boolean gameIsOpen(int gameId) {

        Iterator<Integer> it = gameOpen.keySet().iterator();

        while (it.hasNext()) {

            if (it.next() == gameId) {
                return true;
            }

        }

        return false;

    }

    private void createTab(GameUI game) throws IOException {
       
        //System.out.println( game.getBirthRules() );
        //System.out.println( game.getDeathRules() );
        
        //GameHandler.getInstance().saveGame(game.getGameId());

        FXMLLoader tabContentLoader = new FXMLLoader(getClass().getResource("FXML/Tab.fxml"));

        Parent tabContent = (Parent) tabContentLoader.load();

        GameTab tabController = tabContentLoader.getController();
        tabController.initCanvas(game);
        tabController.parentController(this);

        //Parent tabContent = FXMLLoader.load( getClass().getResource("FXML/NewGame.fxml") );
        Tab newTab = new Tab();
        newTab.setText(game.getGameName());

        newTab.setOnCloseRequest((Event event) -> {
            gameOpen.remove(game.getGameId());
        });

        AnchorPane pane = new AnchorPane();
        pane.minHeight(0.0);
        pane.minHeight(0.0);
        pane.prefHeight(180.0);
        pane.prefWidth(200.0);

        pane.getChildren().add(tabContent);

        AnchorPane.setLeftAnchor(tabContent, 0.0);
        AnchorPane.setRightAnchor(tabContent, 0.0);
        AnchorPane.setBottomAnchor(tabContent, 0.0);
        AnchorPane.setTopAnchor(tabContent, 0.0);

        newTab.setContent(pane);

        tabPane.getTabs().add(newTab);

        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(newTab);

        gameOpen.put(game.getGameId(), newTab);

    }

}
