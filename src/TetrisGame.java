import Tetrominoes.Tetromino;
import Tetrominoes.TetrominoFactory;

/**
 * @author : Zachary Doll
 */

public class TetrisGame {
    private GameBoard gameBoard;
    private boolean isGameOver = false;
    private Tetromino currentTetromino;
    private int score = 0;
    private final int POINTS_PER_ROW = 100;

    public TetrisGame() {
        this.gameBoard = new GameBoard();
        this.spawnNewTetromino();
    }

    public void update() {
        if (isGameOver) return;
        currentTetromino.moveDown();
        if(isCollision()) {
            currentTetromino.moveUp();
            gameBoard.placeTetromino(currentTetromino);
            incrementScore(gameBoard.clearFullRows());
            spawnNewTetromino();
        }
    }

    public void moveLeft() {
        if (isGameOver) return;

        currentTetromino.moveLeft();
        if(isCollision()) {
            currentTetromino.moveRight();
        }
    }

    public void moveRight() {
        if (isGameOver) return;

        currentTetromino.moveRight();
        if(isCollision()) {
            currentTetromino.moveLeft();
        }
    }

    public void moveDown() {
        if (isGameOver) return;

        currentTetromino.moveDown();
        if(isCollision()) {
            currentTetromino.moveUp();
            gameBoard.placeTetromino(currentTetromino);
            incrementScore(gameBoard.clearFullRows());
            spawnNewTetromino();
        }
    }

    public void rotate() {
        if (isGameOver) return;

        currentTetromino.rotate();
        if(isCollision()) {
            currentTetromino.reverseRotate();
        }
    }

    private void spawnNewTetromino() {
        this.currentTetromino = TetrominoFactory.getRandomTetromino();
        if(isCollision()) {
            isGameOver = true;
        }
    }

    public int[][] getBoard() {
        return gameBoard.getBoard();
    }

    public Tetromino getCurrentTetromino() {
        return this.currentTetromino;
    }

    private boolean isCollision() {
        return gameBoard.isCollision(currentTetromino);
    }

    public boolean isGameOver() {
    	return isGameOver;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int nbRowsCleared) {
        //100pts per row cleared
        score += nbRowsCleared * POINTS_PER_ROW;
    }

    public void resetGame() {
        this.gameBoard = new GameBoard();
        this.isGameOver = false;
        this.score = 0;
        this.spawnNewTetromino();
    }
}
