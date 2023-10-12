import Tetrominoes.Tetromino;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * @author : Zachary Doll
 */

public class Main extends Application {
    private final int TILE_SIZE = 30;
    private final int WIDTH = 10;
    private final int HEIGHT = 20;
    private final int SCORE_TEXT_X = 10;
    private final int SCORE_TEXT_Y = 20;
    private final Color TILE_SEPARATOR_COLOR = Color.rgb(50, 50, 50);
    private final int TILE_SEPARATOR_WIDTH = 1;
    private TetrisGame game;
    private Pane root;
    private Menu menu;
    private AnimationTimer gameLoop;
    private NextTetrominoDisplay nextTetrominoDisplay;
    private GameState gameState = GameState.MENU;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        game = new TetrisGame();
        menu = new Menu(this::onGameStart, game);
        root = new Pane();
        nextTetrominoDisplay = new NextTetrominoDisplay();
        nextTetrominoDisplay.setLayoutX(250);
        nextTetrominoDisplay.setLayoutY(10);

        root.getChildren().add(nextTetrominoDisplay);
        root.getChildren().addAll(menu.getMenuBox());

        VBox menuBox = menu.getMenuBox();
        menuBox.layoutXProperty().bind(root.widthProperty().subtract(menuBox.widthProperty()).divide(2));
        menuBox.layoutYProperty().bind(root.heightProperty().subtract(menuBox.heightProperty()).divide(2));
        menuBox.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        menuBox.setPadding(new Insets(0, 0, 0, 0));
        menuBox.getChildren().forEach(child -> {
            if (child instanceof Region) {
                VBox.setMargin(child, new Insets(0, 0, 0, 0));
            }
        });
        menuBox.setMinWidth(TILE_SIZE * WIDTH);
        menuBox.setMinHeight(TILE_SIZE * HEIGHT);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(20, 20, 20),
                CornerRadii.EMPTY,
                Insets.EMPTY)));

        Scene scene = new Scene(root, TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        scene.setOnKeyPressed(event -> {
            if(gameState == GameState.PLAYING) {
                switch (event.getCode()) {
                    case LEFT -> game.moveLeft();
                    case RIGHT -> game.moveRight();
                    case DOWN -> game.moveDown();
                    case UP -> game.rotate();
                    default -> {}
                }
                drawGame();
            }
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
        score.setFill(Color.WHITE);
        score.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        nextTetrominoDisplay.displayTetronimo(game.getNextTetromino());

        root.getChildren().clear();
        root.getChildren().removeIf(child -> child instanceof Rectangle ||
                (child instanceof Text && ((Text) child).getText().startsWith("Score: ")));
        root.getChildren().add(score);
        root.getChildren().add(nextTetrominoDisplay);

        int[][] board = game.getBoard();

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (board[y][x] == 1) {
                    Rectangle tile = new Rectangle(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    tile.setFill(Color.LIGHTGRAY);
                    tile.setStroke(TILE_SEPARATOR_COLOR); // light black color for the border
                    tile.setStrokeWidth(TILE_SEPARATOR_WIDTH);  // set the border width
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
                        tile.setStroke(TILE_SEPARATOR_COLOR); // light black color for the border
                        tile.setStrokeWidth(TILE_SEPARATOR_WIDTH);  // set the border width
                        root.getChildren().add(tile);
                    }
                }
            }
        }
        int currentScore = game.getScore();
        if(currentScore > menu.getHighScore()) menu.setHighScoreText(currentScore);
    }

    private void onGameStart() {
        gameState = GameState.PLAYING;
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
        gameState = GameState.GAME_OVER;
        root.getChildren().clear();
        menu.getMenuBox().setVisible(true);
        root.getChildren().addAll(menu.getMenuBox());
        menu.updateScore(game.getScore());
        menu.showGameOver();
        menu.getMenuBox().setVisible(true);
    }

    public enum GameState{
        MENU, PLAYING, GAME_OVER
    }
}
