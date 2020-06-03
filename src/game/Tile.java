package game;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class that makes the background mesh
 */

public class Tile extends StackPane {
    private Rectangle border = new Rectangle(Tetris.SIZE -2, Tetris.SIZE -2);
    private int x, y;
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(0.5);
        border.setFill(Color.valueOf("#003366"));
        getChildren().add(border);

        setTranslateX(x * Tetris.SIZE);
        setTranslateY(y * Tetris.SIZE);
    }

}
