import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Menu {
    private VBox menuBox;
    private Text highScoreText;
    private Text currentScoreText;
    private Text gameOverText = new Text("Game Over!");
    private Runnable startGame;
    private final int MENU_SPACING = 20;
    private final int FONT_SIZE = 20;
    private HBox difficultyBox;
    private TetrisGame gameInstance;
    private Button easyButton, mediumButton, hardButton;
    private Button selectedDifficultyButton = null;

    public Menu(Runnable startGame, TetrisGame gameInstance) {
        this.startGame = startGame;
        this.gameInstance = gameInstance;

        menuBox = new VBox(MENU_SPACING);
        menuBox.setVisible(true);
        menuBox.setStyle("-fx-background-color: #FF6B61; " +  // Coral color
                "-fx-padding: 20px;" +
                "-fx-background-insets: 0;");
        menuBox.setAlignment(Pos.CENTER);

        highScoreText = new Text("High Score: " + 0);
        highScoreText.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        highScoreText.setFill(javafx.scene.paint.Color.WHITE);

        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        gameOverText.setFill(javafx.scene.paint.Color.RED);

        currentScoreText = new Text("Score: " + 0);
        currentScoreText.setFont(Font.font("Arial", FontWeight.BOLD, FONT_SIZE));
        currentScoreText.setFill(javafx.scene.paint.Color.WHITE);

        Button startButton = createButton("New Game", "#007BFF");
        Button exitButton = createButton("Exit", "#007BFF");
        Button difficultyButton = createButton("Difficulty", "#007BFF");

        difficultyBox = new HBox(10);
        easyButton = createSmallerButton("Easy", "#007BFF");
        mediumButton = createSmallerButton("Medium", "#007BFF");
        hardButton = createSmallerButton("Hard", "#007BFF");
        difficultyBox.getChildren().addAll(easyButton, mediumButton, hardButton);
        difficultyBox.setAlignment(Pos.CENTER); // Align center
        difficultyBox.setVisible(false);
        difficultyButton.setOnAction(e -> toggleDifficultyBox());

        easyButton.setOnAction(e -> {
            clearDifficultyBorders();
            easyButton.setStyle(getButtonStyle("#007BFF", 18,
                    "10px 20px", "2px solid black"));
            setDifficulty(TetrisGame.Difficulty.EASY);
        });

        mediumButton.setOnAction(e -> {
            clearDifficultyBorders();
            mediumButton.setStyle(getButtonStyle("#007BFF", 18,
                    "10px 20px", "2px solid black"));
            setDifficulty(TetrisGame.Difficulty.MEDIUM);
        });

        hardButton.setOnAction(e -> {
            clearDifficultyBorders();
            hardButton.setStyle(getButtonStyle("#007BFF", 18,
                    "10px 20px", "2px solid black"));
            setDifficulty(TetrisGame.Difficulty.HARD);
        });

        startButton.setOnAction(e -> startGame());
        exitButton.setOnAction(e -> System.exit(0));

        menuBox.getChildren().addAll(gameOverText, highScoreText, currentScoreText,
                startButton, difficultyButton, difficultyBox, exitButton);

        gameOverText.setVisible(false); //initially hidden
    }

    public VBox getMenuBox() {
        return menuBox;
    }

    private void startGame() {
        menuBox.setVisible(false);
        if (startGame != null) startGame.run();
    }

    public void setHighScoreText(int highScore) {
        highScoreText.setText("High Score: " + highScore);
    }

    public int getHighScore() {
        return Integer.parseInt(highScoreText.getText().split(": ")[1]);
    }

    public void updateScore(int score) {
        highScoreText.setText("High Score: " + Math.max(score, getHighScore()));
        currentScoreText.setText("Score: " + score);
    }

    public void showGameOver() {
        gameOverText.setVisible(true);
    }

    private Button createButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + bgColor + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 20; " +
                "-fx-border-radius: 12; " +
                "-fx-background-radius: 12; " +
                "-fx-cursor: hand; " +
                "-fx-border: none; " +
                "-fx-padding: 15px 25px;");

        // Hover effect
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + bgColor + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 20; " +
                    "-fx-border-radius: 12; " +
                    "-fx-background-radius: 12; " +
                    "-fx-cursor: hand; " +
                    "-fx-border: none; " +
                    "-fx-padding: 15px 25px; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 5);");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + bgColor + "; " +
                    "-fx-text-fill: #FFFFFF; " +
                    "-fx-font-size: 20; " +
                    "-fx-border-radius: 12; " +
                    "-fx-background-radius: 12; " +
                    "-fx-background-radius: 12; " +
                    "-fx-cursor: hand; " +
                    "-fx-border: none; " +
                    "-fx-padding: 15px 25px;");
        });
        return button;
    }

    private Button createSmallerButton(String text, String bgColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + bgColor + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: 18; " + // slightly smaller font
                "-fx-border-radius: 10; " +  // slightly smaller radius
                "-fx-background-radius: 10; " +
                "-fx-cursor: hand; " +
                "-fx-border: none; " +
                "-fx-padding: 12px 20px;"); // slightly reduced padding

        // Hover effect
        button.setOnMouseEntered(e -> {
            if (button != selectedDifficultyButton) {
                button.setStyle("-fx-background-color: " + bgColor + "; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-size: 18; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-border: none; " +
                        "-fx-padding: 12px 20px; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 5);");
            }
        });

        button.setOnMouseExited(e -> {
            if (button != selectedDifficultyButton) {
                button.setStyle("-fx-background-color: " + bgColor + "; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-font-size: 18; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-border: none; " +
                        "-fx-padding: 12px 20px;");
            }
        });

        return button;
    }

    private void toggleDifficultyBox() {
        difficultyBox.setVisible(!difficultyBox.isVisible());
    }

    private void setDifficulty(TetrisGame.Difficulty difficulty) {
        if (difficulty == TetrisGame.Difficulty.EASY) {
            selectedDifficultyButton = easyButton;
        } else if (difficulty == TetrisGame.Difficulty.MEDIUM) {
            selectedDifficultyButton = mediumButton;
        } else if (difficulty == TetrisGame.Difficulty.HARD) {
            selectedDifficultyButton = hardButton;
        }
        gameInstance.setDifficulty(difficulty);
    }

    private void clearDifficultyBorders() {
        easyButton.setStyle(getButtonStyle("#007BFF", 18, "10px 20px", "none"));
        mediumButton.setStyle(getButtonStyle("#007BFF", 18, "10px 20px", "none"));
        hardButton.setStyle(getButtonStyle("#007BFF", 18, "10px 20px", "none"));
    }

    private String getButtonStyle(String bgColor, int fontSize, String padding, String border) {
        String borderColor = "black";
        String borderWidth = "2px";
        String borderStyle = "solid";

        if ("none".equals(border)) {
            borderWidth = "0px";
        }

        return "-fx-background-color: " + bgColor + "; " +
                "-fx-text-fill: #FFFFFF; " +
                "-fx-font-size: " + fontSize + "; " +
                "-fx-border-radius: 12; " +
                "-fx-background-radius: 12; " +
                "-fx-cursor: hand; " +
                "-fx-border-color: " + borderColor + ";" +
                "-fx-border-width: " + borderWidth + ";" +
                "-fx-border-style: " + borderStyle + ";" +
                "-fx-padding: " + padding + ";";
    }
}
