
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author JScholz
 * 
 * @version 2014-12-11-1 
 * 
 */
public class Tab implements Initializable {

    @FXML private Slider speedSlider;
    @FXML private Label currentSpeed;
    @FXML private AnchorPane pane;
    @FXML private Button draw;
    @FXML private ToolBar toolBar;

    private GameCanvas canvas;

    @FXML
    protected void play() {
    }

    @FXML
    protected void next() {
    }

    @FXML
    protected void gridOptions() {
    }

    @FXML
    protected void draw() {
    }

    @FXML
    protected void rules() {
    }

    @FXML
    protected void wraps() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        assert( speedSlider == null );
        assert( draw == null);
        assert( currentSpeed == null );
        assert( pane == null );

        draw.setOnAction( new EventHandler<ActionEvent>() {
            
            private boolean clicked = false;
            
            @Override public void handle(ActionEvent e) {
                //System.out.println("Klicked");
                if( !clicked ) canvas.addListener();
                else canvas.removeListener();
                
                clicked ^= true;
            }
        });
        
        speedSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            currentSpeed.setText(newValue.intValue() + "");
        });
        
        canvas = new GameCanvas( 300, 300, 30 );
        canvas.setLayoutX( GameOfLife.SCREEN_WIDTH / 2 - canvas.getWidth() / 2 );
        canvas.setLayoutY( GameOfLife.SCREEN_HEIGHT / 2 - 15 - canvas.getHeight() / 2 );
        
        //canvas.addListener();
        //canvas.removeListener();
        
        pane.getChildren().add( canvas );
        /*
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {

                
                        System.out.println( " Klick " + (int)( event.getX() ) + " y " + (int)(event.getY()) );

            }

        });

        //GameOfLife.SCREEN_WIDTH
        canvas.setWidth(300);

        //Screen.Height - Main Toolbar - TabPane_Tabbar - Tab_ToolBar
        //GameOfLife.SCREEN_HEIGHT - 109 
        canvas.setHeight(300);

        canvasGraphicContext = canvas.getGraphicsContext2D();

        
         int startPosY = (int) canvas.getHeight() / 2 - 15 * 10;
         int endPosY = (int) canvas.getHeight() / 2 + 15 * 10;
        
         int startPosX = (int) canvas.getWidth() / 2 - 15 * 10;
         int endPosX = (int) canvas.getWidth() / 2 + 15 * 10;
         
        int rectWidth = 30;
        int rectHeight = 30;

        for (int y = 0; y < 300; y += rectHeight) {
            for (int x = 0; x < 300; x += rectWidth) {

                canvasGraphicContext.setFill(Color.WHITE);
                canvasGraphicContext.setStroke(Color.BLACK);
                canvasGraphicContext.strokeRect(x, y, rectWidth, rectHeight);
            }
        }

        canvas.setLayoutX( GameOfLife.SCREEN_WIDTH / 2 - 300 / 2 );
        canvas.setLayoutY( GameOfLife.SCREEN_HEIGHT / 2 - 15 - 300 / 2f );
        */
    }

}
