
package de.gameoflife.application;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author JScholz
 * 
 * @version 2014-12-11-1 
 * 
 */
class GameCanvas extends Parent {
    
    protected final Canvas grid;
    protected final Canvas elements;
    protected final int cellWidth;
    protected final int cellHeight;
    protected int width;
    protected int height;
    protected ArrayList<Cell> cells = new ArrayList<>();
   
    GameCanvas(int width, int height, int cellSize) {
        this(width, height, cellSize, cellSize);
    }
    
    GameCanvas(int width, int height, int cellWidth, int cellHeight) {
        super();
        
        this.width = width;
        this.height = height;
        
        grid = new Canvas( width, height );
        elements = new Canvas( width, height );
        
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        
        drawGrid();
        
        getChildren().addAll( grid, elements );
        
    }
    
    void addListener() {
        setOnMouseClicked( new GameCanvasListener() );
    }
    
    void removeListener() {
        setOnMouseClicked(null);
    }
    
    private void drawGrid() {
    
        GraphicsContext gc = grid.getGraphicsContext2D();
        
        for( int y=0; y < grid.getHeight(); y += 30 ) {
        
            for( int x=0; x < grid.getWidth(); x += 30 ) {
                
                gc.setFill(Color.WHITE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, cellWidth, cellHeight);
                
                cells.add( new Cell( x, y ) );
                
            } 
        
        }     
    
    }
    
    int getWidth() {
        return width;
    }
    
    int getHeight() {
        return height;
    }
    
    class GameCanvasListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {
            
            Iterator<Cell> it = cells.iterator();
            boolean found = false;
            Cell c = null;
            
            while( it.hasNext() && !found ) {
            
                c = it.next();
                
                if( c.hit( (int)event.getX(), (int)event.getY() ) ) {
                    
                    //Wenn bereits ein Cell schon befüllt werden ist,
                    //soll nichts passieren, daher c = null setzen.
                    if( !c.hasDrawing() ) c.setDrawing( true );
                    else c = null;
                    
                    found = true;
                    
                }
                
            }
            
            if( found && c != null ) {
                
                GraphicsContext gc = elements.getGraphicsContext2D();
                
                gc.setFill(Color.RED);
                gc.setStroke(Color.RED);
                gc.fillRect(c.getX(), c.getY(), cellWidth, cellHeight);
                
                //System.out.println( c.getX() + " " + c.getY() );
            }
            
        }


    }
    
    private class Cell {
        
        private final int x;
        private final int y;
        private boolean drawing = false;
        
        Cell( int x, int y ) {
            this.x = x;
            this.y = y;
        }
        
        int getX() {
            return x;
        }
        
        int getY() {
            return y;
        }
        
        boolean hit( int x, int y ) {
        
            return this.x <= x && x < (this.x + cellWidth) && this.y <= y && y < ( this.y + cellHeight );
            
        }
        
        void setDrawing( boolean b ) {
            this.drawing = b;
        }
    
        boolean hasDrawing() {
            return drawing;
        }
        
    }
    
}
