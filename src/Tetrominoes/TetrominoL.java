/**
 * @author : Alexei Thornber (363088)
 * @author : Zachary Doll (356458)
 */

package Tetrominoes;

import javafx.scene.paint.Color;

public class TetrominoL extends Tetromino {
    private int rotationState = 0;

    public TetrominoL() {
        super(new int[][]{
                {1, 0},
                {1, 0},
                {1, 1}
        });
    }

    @Override
    public void rotate() {
        switch (rotationState) {
            case 0 -> {
                this.shape = new int[][]{
                        {0, 0, 1},
                        {1, 1, 1}
                };
                rotationState = 1;
            }
            case 1 -> {
                this.shape = new int[][]{
                        {1, 1},
                        {0, 1},
                        {0, 1}
                };
                rotationState = 2;
            }
            case 2 -> {
                this.shape = new int[][]{
                        {1, 1, 1},
                        {1, 0, 0}
                };
                rotationState = 3;
            }
            case 3 -> {
                this.shape = new int[][]{
                        {1, 0},
                        {1, 0},
                        {1, 1}
                };
                rotationState = 0;
            }
        }
    }

    @Override
    public void reverseRotate() {
        for (int i = 0; i < 3; i++) {
            rotate(); // To reverse from any state, we just rotate 3 times.
        }
    }

    @Override
    public Color getColor() {
        return Color.TURQUOISE;
    }
}
