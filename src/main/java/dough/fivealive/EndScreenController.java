package dough.fivealive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndScreenController {

    @FXML
    private Label winnerLabel;

    @FXML
    private Button returnToMenuButton;

    @FXML
    private Button returnToGameSetupButton;

    public void setWinnerName(String winnerName) {
        winnerLabel.setText("Congratulations, " + winnerName + "!");
    }

    @FXML
    private void returnToGameSetup() {
        System.out.println("Returning to setup...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerSetup.fxml"));
            Parent playerSetupRoot = loader.load();

            Stage stage = (Stage) returnToGameSetupButton.getScene().getWindow();
            Scene playerSetupScene = new Scene(playerSetupRoot);
            stage.setScene(playerSetupScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load PlayerSetup.fxml.");
        }
    }

    @FXML
    private void returnToMainMenu() {
        System.out.println("Returning to menu...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent playerSetupRoot = loader.load();

            Stage stage = (Stage) returnToMenuButton.getScene().getWindow();
            Scene playerSetupScene = new Scene(playerSetupRoot);
            stage.setScene(playerSetupScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainMenu.fxml.");
        }
    }
}
