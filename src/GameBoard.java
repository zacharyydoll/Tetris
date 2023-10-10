import Tetrominoes.Tetromino;

/**
 * @author : Zachary Doll
 */

public class GameBoard {
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private int[][] board = new int[HEIGHT][WIDTH]; //0 for empty, 1 for filled

    public GameBoard() {
        //board is initially empty
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = 0;
            }
        }
    }

    public boolean isCollision(Tetromino tetromino) {
        int[][] shape = tetromino.getShape();
        int x = tetromino.getX();
        int y = tetromino.getY();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    if (y + i >= HEIGHT || x + j >= WIDTH || x + j < 0 || board[y + i][x + j] == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void placeTetromino(Tetromino tetromino) {
        int[][] shape = tetromino.getShape();
        int x = tetromino.getX();
        int y = tetromino.getY();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    board[y + i][x + j] = 1;
                }
            }
        }
    }

    public int clearFullRows() {
        var nbRowsCleared = 0;
        for(int i = 0; i < HEIGHT; i++) {
            boolean isFull = true;
            for(int j = 0; j < WIDTH; j++) {
                if(board[i][j] == 0) {
                    isFull = false;
                    break;
                }
            }
            if(isFull) {
                nbRowsCleared++; //keep count of how many rows are cleared
                for(int k = i; k > 0; k--) {
                    board[k] = board[k-1].clone();
                }
                board[0] = new int[WIDTH];
            }
        }
        return nbRowsCleared;
    }

    public int[][] getBoard() {
    	return board;
    }
}
