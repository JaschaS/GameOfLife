
package de.gameoflife.application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import rmi.data.GameUI;

/**
 *
 * @author JScholz
 * 
 * @version 2014-12-11-1 
 * 
 */
public class GameTab implements Initializable {

    //@FXML private Slider speedSlider;
    //@FXML private Label currentSpeed;
    @FXML private AnchorPane pane;
    //@FXML private Button draw;
    //@FXML private ToolBar toolBar;
    //@FXML private ToolBar editorToolBar;
    @FXML private BorderPane content;
    //@FXML private ScrollPane scrollpane;
    @FXML private StackPane barpane;

    private GameCanvas canvas;
    private Parent editorBar;
    private EditorBarController editorController;
    private GameUI game;
    //private ScrollPane scrollPane;

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

        //assert( speedSlider == null );
        //assert( draw == null);
        //assert( currentSpeed == null );
        assert( pane == null );
        assert( content == null );
        assert( barpane == null );
        
        try {
            
            FXMLLoader editorBarLoader = new FXMLLoader( getClass().getResource("FXML/EditorBar.fxml") );
        
            editorBar = editorBarLoader.load();
            
            editorController = editorBarLoader.getController();
            editorController.setParent( this );
            
            barpane.getChildren().add( editorBar );
            
            //canvas = new GameCanvas( 3, 3, 20 );
            
            //scrollPane = new ScrollPane( canvas );
            //scrollPane.setFitToHeight(true);
            //scrollPane.setFitToWidth(true);
            
            //BorderPane content = new BorderPane();
            
            //content.setCenter( canvas );
            
            //scrollpane.setContent( content );
        } catch( IOException ex ) {
            Logger.getLogger(GameTab.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
            /*toolBar.setVisible(false);
            toolBar.toBack();
            
            editorToolBar.getItems().add(3, new NumberTextField( 100, "3" ) );
            editorToolBar.getItems().add(5, new NumberTextField( 100, "3" ) );
            editorToolBar.toFront();*/
            /*
            draw.setOnAction( new EventHandler<ActionEvent>() {
            
            private boolean clicked = false;
            
            @Override public void handle(ActionEvent e) {
            //System.out.println("Klicked");
            if( !clicked ) canvas.addListener();
            else canvas.removeListener();
            
            clicked ^= true;
            }
            });
            */
            /*speedSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            currentSpeed.setText(newValue.intValue() + "");
            });*/
            
            //AnchorPane gameCanvasPane = new AnchorPane();
            //gameCanvasPane.setStyle("-fx-background-color: red;");
            
            //canvas = new GameCanvas( 60, 60, 20 );
            
            //content.setCenter(canvas);
            
            //canvas.setLayoutX( gameCanvasPane.getWidth() / 2 - canvas.getWidth() / 2 );
            //canvas.setLayoutY( gameCanvasPane.getHeight() / 2 );
            /*
            AnchorPane.setLeftAnchor(gameCanvasPane, 0.0);
            AnchorPane.setRightAnchor(gameCanvasPane, 0.0);
            AnchorPane.setTopAnchor(gameCanvasPane, 0.0);
            AnchorPane.setBottomAnchor(gameCanvasPane, 0.0);
            */
            //gameCanvasPane.getChildren().add(canvas);
            
            //canvas.addListener();
            //canvas.removeListener();
            
            
            //scrollpane.setContent( canvas );
            
            
            
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
        
        
            
            /*toolBar.setVisible(false);
            toolBar.toBack();
            
            editorToolBar.getItems().add(3, new NumberTextField( 100, "3" ) );
            editorToolBar.getItems().add(5, new NumberTextField( 100, "3" ) );
            editorToolBar.toFront();*/
            /*
            draw.setOnAction( new EventHandler<ActionEvent>() {
            
            private boolean clicked = false;
            
            @Override public void handle(ActionEvent e) {
            //System.out.println("Klicked");
            if( !clicked ) canvas.addListener();
            else canvas.removeListener();
            
            clicked ^= true;
            }
            });
            */
            /*speedSlider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            currentSpeed.setText(newValue.intValue() + "");
            });*/
            
            //AnchorPane gameCanvasPane = new AnchorPane();
            //gameCanvasPane.setStyle("-fx-background-color: red;");
            
            //canvas = new GameCanvas( 60, 60, 20 );
            
            //content.setCenter(canvas);
            
            //canvas.setLayoutX( gameCanvasPane.getWidth() / 2 - canvas.getWidth() / 2 );
            //canvas.setLayoutY( gameCanvasPane.getHeight() / 2 );
            /*
            AnchorPane.setLeftAnchor(gameCanvasPane, 0.0);
            AnchorPane.setRightAnchor(gameCanvasPane, 0.0);
            AnchorPane.setTopAnchor(gameCanvasPane, 0.0);
            AnchorPane.setBottomAnchor(gameCanvasPane, 0.0);
            */
            //gameCanvasPane.getChildren().add(canvas);
            
            //canvas.addListener();
            //canvas.removeListener();
            
            
            //scrollpane.setContent( canvas );
            
            
            
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
    
    public int getGameId() {
        return game.getGameId();
    }

    public void setCanvasWidth( int width ) {
    
        canvas.setGridWidth(width);
        
    }
    
    public void setCanvasHeight( int height ) {
    
        canvas.setGridHeight(height);
        
    }
        
    public void setCanvasCellSize( int size ) {
    
        canvas.setCellSize(size);
        
    }
    
    public void initCanvas( GameUI game ) {
    
        this.game = game;
        
        System.out.println( game.getStartGen()[0].length + " h: " + game.getStartGen().length );
        
        //3, 3, 20
        canvas = new GameCanvas(
                game.getStartGen()[0].length,
                game.getStartGen().length,
                20
        );
        
        content.setCenter( canvas );
    
    }
    
    
}
