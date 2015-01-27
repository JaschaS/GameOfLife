package de.gameoflife.application;

import javafx.event.EventHandler;
import javafx.scene.Group;
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
 * TODO: Draw zeichnen rückgaengig machen
 */
public class GameCanvas extends Group {

    protected final Canvas grid;
    protected final Canvas elements;
    protected int cellSize;
    protected int width;
    protected int height;
    protected boolean[][] generation;
    protected Color cellColor;

    public GameCanvas(int width, int height, int cellSize) {

        super();

        cellColor = Color.web("51A64E");

        double screenWidth = GameOfLife.stageWidthProperty.get();
        double screenHeight = GameOfLife.stageHeightProperty.get();

        this.width = width * cellSize;
        this.height = height * cellSize;
        this.cellSize = cellSize;

        elements = new Canvas(this.width, this.height);
        grid = new Canvas(this.width, this.height);

        getChildren().addAll(grid, elements);

        generation = new boolean[height][width];

    }

    public void addEraserListener() {

        GameCanvasEraserClickListener listener = new GameCanvasEraserClickListener();

        setOnMouseClicked(listener);
        setOnMouseDragged(listener);

    }

    public void addDrawListener() {

        GameCanvasDrawClickListener listener = new GameCanvasDrawClickListener();

        setOnMouseClicked(listener);
        setOnMouseDragged(listener);
    }

    public void removeListener() {
        setOnMouseClicked(null);
        setOnMouseDragged(null);
    }

    public void drawCells(int[][] grid) {

        GraphicsContext gc = elements.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        for (int i = 0, y = 0; y < this.grid.getHeight(); y += cellSize) {

            for (int j = 0, x = 0; x < this.grid.getWidth(); x += cellSize) {

                if (grid[i][j] > 0) {
                    gc.setFill(cellColor);
                    gc.setStroke(cellColor);
                    gc.fillRect(x, y, cellSize, cellSize);
                }

                ++j;
            }
            ++i;
        }

    }

    private void drawCells() {

        GraphicsContext gc = elements.getGraphicsContext2D();

        for (int i = 0, y = 0; y < grid.getHeight(); y += cellSize) {

            for (int j = 0, x = 0; x < grid.getWidth(); x += cellSize) {

                if (generation[i][j] == true) {
                    gc.setFill(cellColor);
                    gc.setStroke(cellColor);
                    gc.fillRect(x, y, cellSize, cellSize);
                }

                ++j;
            }
            ++i;
        }

    }

    public void setCellColor(Color color) {

        cellColor = color;
        drawCells();

    }

    public void setGeneration(boolean[][] generation) {
        //Flache kopie um späteres zuweisen zu vereinfachen
        this.generation = generation;
        drawCells();
    }

    public void drawGrid() {

        GraphicsContext gc = grid.getGraphicsContext2D();

        for (int y = 0; y < grid.getHeight(); y += cellSize) {

            for (int x = 0; x < grid.getWidth(); x += cellSize) {

                gc.setFill(Color.WHITE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, cellSize, cellSize);

                //cells.add(new Cell(x, y));
            }

        }

    }

    public void clear(boolean deleteGeneration) {

        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        gc = elements.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        if (deleteGeneration) {

            for (int i = 0; i < generation.length; ++i) {

                for (int j = 0; j < generation[0].length; ++j) {

                    generation[i][j] = false;
                }

            }
        }

    }

    public void setGridWidth(int newWidth) {

        generation = new boolean[generation.length][newWidth];

        width = newWidth * cellSize;
        grid.setWidth(width);
        elements.setWidth(width);
        clear(true);
        drawGrid();

    }

    public void setGridHeight(int newHeight) {

        generation = new boolean[newHeight][generation[0].length];

        height = newHeight * cellSize;
        grid.setHeight(height);
        elements.setHeight(height);
        clear(true);
        drawGrid();

    }

    public void setCellSize(int size) {

        width = (width / cellSize) * size;
        height = (height / cellSize) * size;
        grid.setHeight(height);
        grid.setWidth(width);
        elements.setWidth(width);
        elements.setHeight(height);

        this.cellSize = size;
        clear(false);
        drawGrid();
        drawCells();

    }

    public int getGridWidth() {
        return width;
    }

    public int getGridHeight() {
        return height;
    }

    public Canvas getBackgroundCanvas() {
        return grid;
    }

    public Canvas getFrontendCanvas() {
        return elements;
    }

    protected class GameCanvasEraserClickListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            boolean found = false;
            int i = 0;
            int j = 0;
            int x;
            int y;

            while (i < generation.length && !found) {

                y = i * cellSize;

                while (j < generation[i].length && !found) {

                    x = j * cellSize;

                    if (x <= (int) event.getX() && (int) event.getX() < (x + cellSize)
                            && y <= (int) event.getY() && (int) event.getY() < (y + cellSize)) {

                        if (generation[i][j] == true) {

                            GraphicsContext gc = elements.getGraphicsContext2D();
                            gc.clearRect(x, y, cellSize, cellSize);

                            generation[i][j] = false;

                        }

                        found = true;

                    }

                    ++j;

                }

                j = 0;
                ++i;

            }

        }

    }

    protected class GameCanvasDrawClickListener implements EventHandler<MouseEvent> {

        @Override
        public void handle(MouseEvent event) {

            boolean found = false;
            int i = 0;
            int j = 0;
            int x;
            int y;

            while (i < generation.length && !found) {

                y = i * cellSize;

                while (j < generation[i].length && !found) {

                    x = j * cellSize;

                    if (x <= (int) event.getX() && (int) event.getX() < (x + cellSize)
                            && y <= (int) event.getY() && (int) event.getY() < (y + cellSize)) {

                        if (generation[i][j] == false) {

                            GraphicsContext gc = elements.getGraphicsContext2D();

                            gc.setFill(cellColor);
                            gc.setStroke(cellColor);
                            gc.fillRect(x, y, cellSize, cellSize);

                            generation[i][j] = true;

                        }

                        found = true;

                    }

                    ++j;

                }

                j = 0;
                ++i;

            }

        }

    }

    protected class Cell {

        private final int x;
        private final int y;
        private boolean drawing = false;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }

        boolean hit(int x, int y) {

            return this.x <= x && x < (this.x + cellSize) && this.y <= y && y < (this.y + cellSize);

        }

        void setDrawing(boolean b) {
            this.drawing = b;
        }

        boolean hasDrawing() {
            return drawing;
        }

    }

}
