
package de.gameoflife.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        /*Button btn = new Button();
         btn.setText("Dieses Spiel ist mist!");
         btn.setOnAction(new EventHandler<ActionEvent>() {
            
         @Override
         public void handle(ActionEvent event) {
         System.out.println("Hello World!");
         }
         });
        
         StackPane root = new StackPane();
         root.getChildren().add(btn);
        
         Scene scene = new Scene(root, 300, 250);*/

        //Parent root = FXMLLoader.load(getClass().getResource("GoF.fxml"));
        /*Parent root = FXMLLoader.load(getClass().getResource("LoginMask.fxml"));
       
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(column1); 

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2); 
        
        
        
        
        grid.add( root, 0, 0 ); */
        
        AnchorPane gameScene = FXMLLoader.load(getClass().getResource("GoF.fxml"));
        StackPane stackPane = new StackPane();    
        GridPane grid = new GridPane();
        Button login  = new Button("Login");
        Label error = new Label("");
        Label username = new Label("User name:");
        Label password = new Label("Password:");
        TextField usernameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();

        //HBox hbButtons = new HBox();
        //hbButtons.setSpacing(10.0);
        
        error.setStyle("-fx-text-fill: red;");

        usernameInput.setPrefWidth( 190.0 );
        usernameInput.setMaxWidth( 190.0 );
        passwordInput.setPrefWidth( 190.0 );
        passwordInput.setMaxWidth( 190.0 );
        
        login.setStyle("-fx-font-size: 15pt;");
        login.setOnAction( new EventHandler() {

            @Override
            public void handle(Event event) {
                
                if( usernameInput.getText().equals("") || passwordInput.getText().equals("") ) {
                
                    error.setText("User name or password empty!");
                    
                }
                else {
                    //error.setText("");
                    ObservableList<Node> children = stackPane.getChildren();
                    children.get(1).toFront();
                    children.get(1).setVisible(true);
                    children.get(0).setVisible( false );
                }
                
            }
            
        });

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(12);
        grid.add(error, 1, 0);
        grid.add(username, 0, 1);
        grid.add(usernameInput, 1, 1);
        grid.add(password, 0, 2);
        grid.add(passwordInput, 1, 2);
        grid.add(login, 1, 3);


        column1.setHalignment(HPos.RIGHT);
        grid.getColumnConstraints().add(column1);

        column2.setHalignment(HPos.LEFT);
        grid.getColumnConstraints().add(column2);
        
        gameScene.setVisible( false );
        gameScene.maxHeight( Control.USE_COMPUTED_SIZE );
        gameScene.maxWidth( Control.USE_COMPUTED_SIZE );
        gameScene.minHeight( Control.USE_COMPUTED_SIZE );
        gameScene.minWidth( Control.USE_COMPUTED_SIZE );
        gameScene.prefHeight( 400 );
        gameScene.prefWidth( 600 );
        
        gameScene.prefWidthProperty().bind(stackPane.widthProperty());
        gameScene.prefHeightProperty().bind(stackPane.heightProperty());
        stackPane.maxWidth( Control.USE_PREF_SIZE );
        stackPane.maxHeight(Control.USE_PREF_SIZE );
        stackPane.minHeight(Control.USE_PREF_SIZE );
        stackPane.minWidth(Control.USE_PREF_SIZE );
        stackPane.prefWidth( 600 );
        stackPane.prefHeight( 400 );
        stackPane.setMinSize(0, 0);
        stackPane.setNodeOrientation( NodeOrientation.INHERIT );
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll( grid, gameScene );
        
        //StackPane.setAlignment( gameScene, Pos.CENTER);
        
        Scene scene = new Scene( stackPane, 600, 400 );
        /*AnchorPane anchor = new AnchorPane();
        Pane root = new Pane();
        root.setStyle("-fx-background-color:red;");
        
        URL url = this.getClass().getResource("gameoflife.css");
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        
        Scene scene = new Scene(anchor, 320, 320);
        scene.getStylesheets().add(css);

        // Create a board with dead cells
        for (int x = 0; x < 320; x += 30) {
            for (int y = 0; y < 320; y += 30) {
                StackPane cell = StackPaneBuilder.create().layoutX(x).layoutY(y).prefHeight(30).prefWidth(30).styleClass("cell").build();
                StackPane cell = new StackPane();
                cell.setLayoutX(x);
                cell.setLayoutY(y);
                cell.prefHeight(10);
                cell.prefWidth(10);
                cell.setStyle("-fx-background-color: red;");
                
                root.getChildren().add(cell);

                //Store the cell in a HashMap for fast access 
                //in the iterateBoard method.
                boardMap.put(x + " " + y, cell);
            }
        }
        
        anchor.getChildren().add(root);
        
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        
        */
        primaryStage.setTitle("Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Stop");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
