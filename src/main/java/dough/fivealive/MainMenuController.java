/* 
 * Author: Duc Tran [1589830], Phuong Nguyen [ID]
 * Purpose: To control the main menu screen
*/

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dough/fivealive/TutorialScreen.fxml"));
            Parent tutorialRoot = loader.load();

            Stage stage = (Stage) startTutorialButton.getScene().getWindow();
            Scene tutorialScene = new Scene(tutorialRoot);
            stage.setScene(tutorialScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load TutorialScreen.fxml.");
        }
    }

    @FXML
    public void startSettingsScreen() {
        System.out.println("Opening settings...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dough/fivealive/SettingScreen.fxml"));
            Parent settingRoot = loader.load();

            Stage stage = (Stage) startSettingsButton.getScene().getWindow();
            Scene settingScene = new Scene(settingRoot);
            stage.setScene(settingScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load SettingScreen.fxml.");
        }
    }

    @FXML
    public void exitGame() {
        System.out.println("Exiting game...");
        System.exit(0);
    }
}