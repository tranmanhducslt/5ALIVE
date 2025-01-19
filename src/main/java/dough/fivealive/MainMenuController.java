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
    private Button startSetupButton;

    @FXML
    private Button startSettingsButton;

    @FXML
    private Button startTutorialButton;

    @FXML
    private Button quitGameButton;

    @FXML
    public void startSetupScreen() {
        System.out.println("Starting the game...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dough/fivealive/PlayerSetup.fxml"));
            Parent playerSetupRoot = loader.load();

            Stage stage = (Stage) startSetupButton.getScene().getWindow();
            Scene playerSetupScene = new Scene(playerSetupRoot);
            stage.setScene(playerSetupScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load PlayerSetup.fxml.");
        }
    }

    @FXML
    public void startTutorialScreen() {
        System.out.println("Opening tutorial...");

        try {
            // load instructions scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dough/fivealive/Instructions.fxml"));
            Parent instructionsRoot = loader.load();

            Stage stage = new Stage();
            Scene instructionsScene = new Scene(instructionsRoot);
            stage.setScene(instructionsScene);
            stage.setTitle("Instructions");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load Instructions.fxml.");
        }
    }

    @FXML
    public void startSettingsScreen() {
        System.out.println("Opening settings...");
        // Transition to settings screen
    }

    @FXML
    public void exitGame() {
        System.out.println("Exiting game...");
        System.exit(0);
    }
}