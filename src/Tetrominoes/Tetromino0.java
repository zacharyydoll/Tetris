/**
 * @author : Zachary Doll (356458)
 */

package Tetrominoes;

import javafx.scene.paint.Color;

public class Tetromino0 extends Tetromino {
    public Tetromino0() {
        super(new int[][]{
                {1, 1},
                {1, 1}
        });
    }

    @Override
    public void rotate() {
        // do nothing
    }

    @Override
    public void reverseRotate() {
        // do nothing
    }

    @Override
    public Color getColor() {
        return Color.VIOLET;
    }
}
