
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
        
    }
    
    public int getGameId() {
        return game.getGameId();
    }
    
    public GameCanvas getCanvas() {
        return canvas;
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
        
        //3, 3, 20
        canvas = new GameCanvas(
                game.getStartGen()[0].length,
                game.getStartGen().length,
                20
        );
        
        content.setCenter( canvas );
    
    }
    
    
}
