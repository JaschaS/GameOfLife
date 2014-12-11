
package de.gameoflife.application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Parent root = FXMLLoader.load(getClass().getResource("GoF.fxml"));
        Scene scene = new Scene( root, 1280, 720 );
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
