import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author : Zachary Doll
 */

public class Menu {
    private VBox menuBox;
    private Text highScoreText;
    private Text currentScoreText;
    private Text gameOverText = new Text("Game Over!");
    private Runnable startGame;

    public Menu(Runnable startGame) {
        this.startGame = startGame;

        menuBox = new VBox(10);
        menuBox.setVisible(true);
        menuBox.setAlignment(Pos.CENTER);
        highScoreText = new Text("High Score: " + 0);
        currentScoreText = new Text("Score: " + 0);
        Button startButton = new Button("New Game");
        Button exitButton = new Button("Exit");

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
        if(startGame != null) startGame.run();
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
}
