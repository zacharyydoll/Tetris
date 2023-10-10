/**
 * @author : Zachary Doll (356458)
 */

package Tetrominoes;

import java.util.Random;

public class TetrominoFactory {
    public static final Random RANDOM = new Random();

    public static Tetromino getRandomTetromino() {
        int choice = RANDOM.nextInt(4);
        return switch (choice) {
            case 0 -> new Tetromino0();
            case 1 -> new TetrominoI();
            case 2 -> new TetrominoZ();
            case 3 -> new TetrominoL();
            //TODO : add other Tetrominoes (eg L, Z, TT, S, J...)
            default -> throw new IllegalArgumentException("Invalid choice");
        };
    }
}
