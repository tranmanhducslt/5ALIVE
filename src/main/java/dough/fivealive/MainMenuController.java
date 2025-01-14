package dough.fivealive;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button exitGameButton;

    @FXML
    private Button openSettingButton;

    @FXML
    private Button openTutorialButton;

    @FXML
    private Button startSetupButton;

    @FXML
    public void exitGame(ActionEvent event) {
        System.out.println("Exiting game...");
        System.exit(0);
    }

    @FXML
    public void openSetupScreen(ActionEvent event) {
        System.out.println("Starting setup screen...");
        try {
            // Load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerSetup.fxml"));
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
    public void startSettings(ActionEvent event) {
        System.out.println("Starting settings screen...");
    }

    @FXML
    public void startTutorial(ActionEvent event) {
        System.out.println("Starting tutorial screen...");
    }
}
