/**
 * @author : Alexei Thornber (363088)
 * @author : Zachary Doll (356458)
 */

package Tetrominoes;

import javafx.scene.paint.Color;

public class TetrominoZ extends Tetromino {
    private int rotationState = 0;
    
    public TetrominoZ() {
        super(new int[][]{
                {1, 1, 0},
                {0, 1, 1}
        });
    }

    @Override
    public void rotate() {
        if (rotationState == 0) {
            this.shape = new int[][] {
                    {0, 1},
                    {1, 1},
                    {1, 0}
            };
            rotationState = 1;
        } else {
            this.shape = new int[][] {
                    {1, 1, 0},
                    {0, 1, 1}
            };
            rotationState = 0;
        }
    }

    @Override
    public void reverseRotate() {
        rotate(); // Since there are only two states, reverse is the same as a regular rotate for this piece
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
}
