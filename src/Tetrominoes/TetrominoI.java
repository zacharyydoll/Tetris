/**
 * @author : Zachary Doll (356458)
 */

package Tetrominoes;

import javafx.scene.paint.Color;

public class TetrominoI extends Tetromino {
    private int rotationState = 0;

    public TetrominoI() {
        super(new int[][]{
            {1, 1, 1, 1}
        });
    }

    @Override
    public void rotate() {
        if (rotationState == 0) {
            this.shape = new int[][] {
                    {1},
                    {1},
                    {1},
                    {1}
            };
            rotationState = 1;
        } else {
            this.shape = new int[][] {
                    {1, 1, 1, 1}
            };
            rotationState = 0;
        }
    }

    @Override
    public void reverseRotate() {
        if (rotationState == 0) {
            this.shape = new int[][] {
                    {1},
                    {1},
                    {1},
                    {1}
            };
            rotationState = 1;
        } else {
            this.shape = new int[][] {
                    {1, 1, 1, 1}
            };
            rotationState = 0;
        }
    }

    @Override
    public Color getColor() {
        return Color.HOTPINK;
    }

}
