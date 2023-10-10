import Tetrominoes.Tetromino;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * @author : Alexei Thornber (363088)
 * @author : Zachary Doll (356458)
 */

public class NextTetrominoDisplay extends Pane {
    private final static int CELL_SIZE = 10;

    public NextTetrominoDisplay() {
        super();
        this.setPrefSize(CELL_SIZE * 4, CELL_SIZE * 4);
    }

    public void displayTetronimo(Tetromino tetromino) {
        this.getChildren().clear(); // Clear the current display
        int[][] shape = tetromino.getShape();

        for (int y = 0; y < shape.length; y++) {
            for (int x = 0; x < shape[y].length; x++) {
                if (shape[y][x] == 1) {
                    Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE, tetromino.getColor());
                    rect.setX(x * CELL_SIZE);
                    rect.setY(y * CELL_SIZE);
                    this.getChildren().add(rect);
                }
            }
        }
    }
}
