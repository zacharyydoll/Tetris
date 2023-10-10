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
    private final int BASE_DELAY = 1000; //1 second delay
    private int ACCELERATION = 20; //0 seconds
    private int currentDelay = BASE_DELAY;
    private Tetromino nextTetromino;


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
            int linesCleared = gameBoard.clearFullRows();
            incrementScore(linesCleared);
            adjustDelay(linesCleared);
            spawnNewTetromino();
        }
    }

    private void adjustDelay(int linesCleared) {
        // Ensure the delay doesn't go below half of the base delay for safety.
        currentDelay = Math.max(BASE_DELAY - (linesCleared * ACCELERATION), BASE_DELAY / 2);
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
        // generate the first tetromino
        if (nextTetromino == null)
            this.currentTetromino = TetrominoFactory.getRandomTetromino();
         else
            this.currentTetromino = this.nextTetromino;

        this.nextTetromino = TetrominoFactory.getRandomTetromino();

        if(isCollision()) {
            isGameOver = true;
        }
    }

    public Tetromino getNextTetromino() {
        return this.nextTetromino;
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
        currentDelay = BASE_DELAY;
        this.gameBoard = new GameBoard();
        this.isGameOver = false;
        this.score = 0;
        this.spawnNewTetromino();
    }
}
