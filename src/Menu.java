import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    public Menu(Runnable startGame) {
        this.startGame = startGame;

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

        startButton.setOnAction(e -> startGame());
        exitButton.setOnAction(e -> System.exit(0));

        menuBox.getChildren().addAll(gameOverText, highScoreText, currentScoreText, startButton, exitButton);

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
                    "-fx-cursor: hand; " +
                    "-fx-border: none; " +
                    "-fx-padding: 15px 25px;");
        });
        return button;
    }

}
