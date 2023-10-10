import Tetrominoes.Tetromino;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
/**
 * @author : Zachary Doll
 */

public class Main extends Application {
    private final int TILE_SIZE = 30;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private final int SCORE_TEXT_X = 10;
    private final int SCORE_TEXT_Y = 20;
    private TetrisGame game;
    private Pane root;
    private Menu menu;
    private AnimationTimer gameLoop;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        menu = new Menu(this::onGameStart);
        root = new Pane();
        game = new TetrisGame();

        root.getChildren().addAll(menu.getMenuBox());

        VBox menuBox = menu.getMenuBox();
        menuBox.layoutXProperty().bind(root.widthProperty().subtract(menuBox.widthProperty()).divide(2));
        menuBox.layoutYProperty().bind(root.heightProperty().subtract(menuBox.heightProperty()).divide(2));
        menuBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        Scene scene = new Scene(root, TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> game.moveLeft();
                case RIGHT -> game.moveRight();
                case DOWN -> game.moveDown();
                case UP -> game.rotate();
            }
            drawGame();
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.show();
    }

    private void drawGame() {
        Text score = new Text("Score: " + game.getScore());
        score.setX(SCORE_TEXT_X);
        score.setY(SCORE_TEXT_Y);
        score.setVisible(true);

        root.getChildren().clear();
        root.getChildren().removeIf(child -> child instanceof Rectangle ||
                (child instanceof Text && ((Text) child).getText().startsWith("Score: ")));
        root.getChildren().add(score);
        int[][] board = game.getBoard();

        // Draw the fixed tiles on the game board
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (board[y][x] == 1) {
                    Rectangle tile = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    tile.setFill(Color.BLUE);
                    root.getChildren().add(tile);
                }
            }
        }

        // Draw the current tetromino (falling one)
        Tetromino currentTetromino = game.getCurrentTetromino();
        if (currentTetromino != null) {
            int tetrominoX = currentTetromino.getX();
            int tetrominoY = currentTetromino.getY();
            for (int i = 0; i < currentTetromino.getShape().length; i++) {
                for (int j = 0; j < currentTetromino.getShape()[i].length; j++) {
                    if (currentTetromino.getShape()[i][j] == 1) {
                        Rectangle tile = new Rectangle((tetrominoX + j) * TILE_SIZE,
                                (tetrominoY + i) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                        tile.setFill(currentTetromino.getColor());
                        root.getChildren().add(tile);
                    }
                }
            }
        }
        int currentScore = game.getScore();
        if(currentScore > menu.getHighScore()) menu.setHighScoreText(currentScore);
    }

    private void onGameStart() {
        game.resetGame();
        gameLoop = new AnimationTimer() {
            long lastTick = 0;

            @Override
            public void handle(long now) {
                if (!game.isGameOver()) {
                    if (lastTick == 0) {
                        lastTick = now;
                        drawGame();
                        return;
                    }

                    if (now - lastTick > 1000000000 / 4) { // update 4 times per second
                        lastTick = now;
                        game.update();
                        drawGame();
                    }
                }
                else {
                    gameLoop.stop();
                    displayGameOverMenu();
                }
            }
        };
        gameLoop.start();
    }

    private void displayGameOverMenu() {
        root.getChildren().clear(); // Clear the game board
        menu.getMenuBox().setVisible(true);
        root.getChildren().addAll(menu.getMenuBox()); // Add the menu
        menu.updateScore(game.getScore()); // Update the displayed score
        menu.showGameOver(); // Show the game over text
        menu.getMenuBox().setVisible(true); // Ensure the game over menu is visible
    }

}
