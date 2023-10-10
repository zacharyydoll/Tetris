package Tetrominoes;

import javafx.scene.paint.Color;

public class TetrominoT extends Tetromino {
    private int rotationState = 3; // Starting at 3 so that the first rotate sets the shape to the initial shape.

    public TetrominoT() {
        super(new int[][]{
                {1, 1, 1},
                {0, 1, 0}
        });
    }

    @Override
    public void rotate() {
        switch (rotationState) {
            case 0 -> {
                this.shape = new int[][]{
                        {1, 1, 1},
                        {0, 1, 0}
                };
                rotationState = 1;
            }
            case 1 -> {
                this.shape = new int[][]{
                        {1, 0},
                        {1, 1},
                        {1, 0}
                };
                rotationState = 2;
            }
            case 2 -> {
                this.shape = new int[][]{
                        {0, 1, 0},
                        {1, 1, 1}
                };
                rotationState = 3;
            }
            case 3 -> {
                this.shape = new int[][]{
                        {0, 1},
                        {1, 1},
                        {0, 1}
                };
                rotationState = 0;
            }
            default -> throw new IllegalStateException("Unexpected rotationState: " + rotationState);
        }
    }

    @Override
    public void reverseRotate() {
        for (int i = 0; i < 3; i++) {
            rotate(); // just rotate 3 times.
        }
    }

    @Override
    public Color getColor() {
        return Color.PLUM;
    }
}
