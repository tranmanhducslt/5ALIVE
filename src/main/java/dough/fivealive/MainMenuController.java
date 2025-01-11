package dough.fivealive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button startButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button exitButton;

    @FXML
    public void startGame() {
        System.out.println("Starting the game...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerSetup.fxml"));
            Parent playerSetupRoot = loader.load();

            Stage stage = (Stage) startButton.getScene().getWindow();
            Scene playerSetupScene = new Scene(playerSetupRoot);
            stage.setScene(playerSetupScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load PlayerSetup.fxml.");
        }
    }

    public void startSettings() {
        System.out.println("Opening settings...");
        // Transition to settings screen
    }

    public void exitGame() {
        System.out.println("Exiting game...");
        System.exit(0);
    }
}
