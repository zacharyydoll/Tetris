import Tetrominoes.Tetromino;
import Tetrominoes.TetrominoFactory;

import java.util.Map;

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
    private int ACCELERATION = 100; //0 seconds
    private int currentDelay = BASE_DELAY;
    private Tetromino nextTetromino;
    private Difficulty difficulty = Difficulty.MEDIUM; //default difficulty
    public final String GAME_NAME = "Tetris";
    private static final Map<Difficulty, Integer> DELAYS = Map.of(
            Difficulty.EASY, 1200,
            Difficulty.MEDIUM, 600,
            Difficulty.HARD, 300
    );

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

    //TODO : fix delay according to difficulty level
    private void adjustDelay(int linesCleared) {
        int baseDelay = DELAYS.get(difficulty);
        currentDelay = Math.max(baseDelay - (linesCleared * ACCELERATION), 20); // Clamp with 20ms delay
        System.out.println("Current delay : " + currentDelay);
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
        //100pts per row cleared and 50 bonus per row after the first
        score += nbRowsCleared > 0 ? (nbRowsCleared * POINTS_PER_ROW) + (50 * (nbRowsCleared -1)) : 0;
    }

    public void resetGame() {
        currentDelay = BASE_DELAY;
        this.gameBoard = new GameBoard();
        this.isGameOver = false;
        this.score = 0;
        this.spawnNewTetromino();
    }

    public int getCurrentDelay() {
        return this.currentDelay;
    }

    public void setDifficulty(Difficulty difficulty) {
        System.out.println("Setting difficulty to : " + difficulty);
        this.difficulty = difficulty;
        this.currentDelay = DELAYS.get(difficulty);
    }

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}
