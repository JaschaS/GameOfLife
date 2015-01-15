/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gameoflife.application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;

/**
 * FXML Controller class
 *
 * @author JScholz
 */
public class EditorBarController implements Initializable {

    @FXML private ToolBar editorToolBar;
    
    private GameTab parent;
    private NumberTextField cellWidth;
    private NumberTextField cellHeight;
    private NumberTextField cellSize;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cellWidth = new NumberTextField( 100, 3, 100, "3" );
        cellHeight = new NumberTextField( 100, 3, 100, "3" );
        cellSize = new NumberTextField( 100, 3, 100, "20" );
        
        cellWidth.setListener((int value) -> {
            if( parent != null ) {
                
                parent.setCanvasWidth( value );
                
            }
        });
        
        cellHeight.setListener((int value) -> {
            if( parent != null ) {
                
                parent.setCanvasHeight( value );
                
            }
        });
        
        cellSize.setListener((int value) -> {
            if( parent != null ) {
                
                parent.setCanvasCellSize( value );
                
            }
        });
        
        editorToolBar.getItems().add( 4, cellWidth  );
        editorToolBar.getItems().add( 6, cellHeight  );
        editorToolBar.getItems().add( 8, cellSize  );
        
    }    
    
    public void setParent( GameTab newParent ) {
    
        parent = newParent;
        
    }
    
    
    
    private void setCanvasWidth( int width ) {
    
        if( parent != null ) {
        
            parent.setCanvasWidth(width);
            
        }
        
    }
    
    private void setCanvasHeight( int height ) {
    
        if( parent != null ) {
        
            parent.setCanvasWidth(height);
            
        }
        
    }
        
    private void setCanvasCellSize( int size ) {
    
         if( parent != null ) {
        
            parent.setCanvasWidth(size);
            
        }       
        
    }
    
}
