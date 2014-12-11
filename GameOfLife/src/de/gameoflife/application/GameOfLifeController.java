
package de.gameoflife.application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author JScholz
 * 
 * @version 2014-12-11-1
 * 
 */
public class GameOfLifeController {

    @FXML private TabPane tabPane;
    @FXML private StackPane stackPane;
    @FXML private Label username;

    @FXML
    protected void login(ActionEvent event) throws IOException {
        //System.out.println("Login");
               
        assert( stackPane == null );
        
        //stackPane.getChildren().get(1).toBack();
        
        final Scene loginMask;
        final Stage stage = new Stage( StageStyle.UTILITY );
        
        FXMLLoader loader = new FXMLLoader( getClass().getResource("LoginMask.fxml") );
        
        Parent root = (Parent) loader.load();
        LoginMaskController controller = loader.getController();
        
        controller.setOnActionEvent( new EventHandler() {

            @Override
            public void handle(Event event) {
                
                System.out.println("name: " + controller.getUserName() + " pw: " + controller.getPassword());
               
                if( controller.isUserAndPasswordEmpty() ) {
                    
                    controller.setError( "Benutzer oder Passwort falsch!");
                    
                }
                else {
                
                    stage.close();
                
                    username.setText( controller.getUserName() );
                    stackPane.getChildren().get(1).toBack();
                    
                }
            }
            
        });
     
        
        loginMask = new Scene(root);
        
        stage.setScene( loginMask );
        stage.setResizable( false );
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
    }
    
    @FXML
    protected void logout(ActionEvent event) throws IOException {
        //System.out.println("logout");
               
        assert( stackPane == null );
                
        stackPane.getChildren().get(0).toFront();
        tabPane.getTabs().clear();
        
    }

    @FXML
    protected void quit(ActionEvent event) throws IOException {
        //System.out.println("quit");
        System.exit(0);

    }

    @FXML
    protected void newGame(ActionEvent event) throws IOException {
        //System.out.println("newGame");

        assert( tabPane == null );
        
        Parent root = FXMLLoader.load(getClass().getResource("Tab.fxml"));

        //Damit der Inhalt des Tabs das komplette Fenster einnimmt, muss
        //Root in ein Anchorpane gewrappt werden. 
        
        AnchorPane pane = new AnchorPane();
        pane.getChildren().add( root );
        //pane.setStyle("-fx-background-color: red;");

        AnchorPane.setLeftAnchor( root, 0.0 );
        AnchorPane.setRightAnchor( root, 0.0 );
        AnchorPane.setTopAnchor( root, 0.0 );
        AnchorPane.setBottomAnchor( root, 0.0 );

        Tab t = new Tab("Game");
        t.setContent( pane );

        tabPane.getTabs().add( t );

        /*if (tabPane != null) {

            Tab t = new Tab("Game Bla Bla");

            
            
            Canvas c = new Canvas( tabPane.getWidth(), tabPane.getHeight());
            
            GraphicsContext gc = c.getGraphicsContext2D();
            
            //Pane root = new Pane();

            int startX = (int) tabPane.getWidth() / 4;
            int startY = (int) tabPane.getHeight() / 2 - 30 * 4;
            
            for (int x = startX; x < tabPane.getWidth() / 4 + 320; x += 30) {
                for (int y = 0; y < 320; y += 30) {
                    
                    gc.setFill( Color.WHITE );
                    gc.setStroke( Color.BLACK );
                    gc.strokeRect(x, y, 30, 30);
                    
                    Rectangle cell = new Rectangle( 30, 30);
                    cell.setFill( Color.WHITE );
                    cell.setStroke(Color.BLACK);
                    cell.setLayoutX(x);
                    cell.setLayoutY(y);*/
                    //StackPane cell = StackPaneBuilder.create().layoutX(x).layoutY(y).prefHeight(30).prefWidth(30).styleClass("cell").build();
                    /*StackPane cell = new StackPane();
                    cell.setLayoutX(x);
                    cell.setLayoutY(y);
                    cell.prefHeight(10);
                    cell.prefWidth(10);
                    cell.setStyle("-fx-background-color: red;");

                    //root.getChildren().add(cell);

                //Store the cell in a HashMap for fast access 
                    //in the iterateBoard method.
                    //boardMap.put(x + " " + y, cell);
                }
            }

            //Rectangle r = new Rectangle(10, 10);
            //r.setFill(Color.RED);

            t.setContent( c );

            tabPane.getTabs().add(t);

        }*/
        /*
         ToolBar toolBar = new ToolBar(
         new Button("New"),
         new Button("Open"),
         new Button("Save"),
         new Separator(),
         new Button("Clean"),
         new Button("Compile"),
         new Button("Run"),
         new Separator(),
         new Button("Debug"),
         new Button("Profile")
         );

         Tab tab = new Tab();
         tab.setText("Tab");
         HBox hbox = new HBox();
         hbox.getChildren().add(toolBar);
         hbox.setAlignment(Pos.CENTER);
         tab.setContent(hbox);
         tabPane.getTabs().add(tab);
         */
    }

}
