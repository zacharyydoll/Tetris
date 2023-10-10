package Tetrominoes;

import javafx.scene.paint.Color;

/**
 * @author : Zachary Doll
 */

public abstract class Tetromino {
    protected int[][] shape;
    protected int x, y; // top left position of the Tetrominoes.Tetromino

    public Tetromino(int[][] shape) {
        this.shape = shape;
        this.x = 4;
        this.y = 0;
    }

    public void moveLeft() {
        this.x--;
    }

    public void moveRight() {
        this.x++;
    }

    public void moveDown() {
        this.y++;
    }

    public void moveUp() {
        this.y--;
    }

    public abstract void rotate();

    public abstract void reverseRotate();

    public int[][] getShape() {
        return this.shape;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract Color getColor();
}
