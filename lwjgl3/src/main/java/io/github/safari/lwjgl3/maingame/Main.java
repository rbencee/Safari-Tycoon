package lwjgl3.src.main.java.io.github.safari.lwjgl3.maingame;
import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            GameModel gameModel = new GameModel();
            GameView gameFrame = null;
            try {
                gameFrame = new GameView(gameModel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
