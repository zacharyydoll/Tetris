/**
 * @author : Zachary Doll (356458)
 */

package Tetrominoes;

import java.util.Random;

public class TetrominoFactory {
    public static final Random RANDOM = new Random();

    public static Tetromino getRandomTetromino() {
        int choice = RANDOM.nextInt(7);
        return switch (choice) {
            case 0 -> new Tetromino0();
            case 1 -> new TetrominoI();
            case 2 -> new TetrominoZ();
            case 3 -> new TetrominoL();
            case 4 -> new TertominoMirrorL();
            case 5 -> new TetrominoMirrorZ();
            case 6 -> new TetrominoT();
            default -> throw new IllegalArgumentException("Invalid choice");
        };
    }
}
