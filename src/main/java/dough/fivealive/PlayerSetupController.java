/* 
 * Author: Phuong Nguyen [ID]
 * Purpose: For the player setup screen
*/

package dough.fivealive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerSetupController {

    @FXML
    private ComboBox<String> playerCountComboBox;

    @FXML
    private VBox playerNamesContainer;

    @FXML
    private Button startGameButton;

    @FXML
    private Button returnMenuButton;

    // List to store player names
    private final List<TextField> playerNameFields = new ArrayList<>();

    @FXML
    public void initialize() {
        // Add a listener to the ComboBox to dynamically update the TextFields
        playerCountComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updatePlayerNameFields(Integer.parseInt(newValue));
        });
    }

    private void updatePlayerNameFields(int playerCount) {
        // Clear existing fields
        playerNamesContainer.getChildren().clear();
        playerNameFields.clear();

        // Add TextFields for each player
        for (int i = 1; i <= playerCount; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Player " + i + " Name");
            playerNamesContainer.getChildren().add(textField);
            playerNameFields.add(textField);
        }
    }

    @FXML
    public void startGame() {
        // Get player names from the TextFields
        List<String> playerNames = new ArrayList<>();
        for (TextField textField : playerNameFields) {
            String name = textField.getText().trim();
            if (name.isEmpty()) {
                name = "Player " + (playerNames.size() + 1);
            }
            playerNames.add(name);
        }

        // Debug
        System.out.println("Starting game with players: " + playerNames);

        // Load the game screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dough/fivealive/GameScreen.fxml"));
            Parent gameRoot = loader.load();

            // Pass player names to the game screen controller
            GameScreenController controller = loader.getController();
            controller.initializeGame(playerNames);

            // Switch to the game screen
            Stage stage = (Stage) playerNamesContainer.getScene().getWindow();
            Scene gameScene = new Scene(gameRoot);
            stage.setScene(gameScene);
            stage.setTitle("Five Alive - Game");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void returnMenuScreen() {
        System.out.println("Returning to menu...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent mainMenuRoot = loader.load();

            Stage stage = (Stage) returnMenuButton.getScene().getWindow();
            Scene mainMenuScene = new Scene(mainMenuRoot);
            stage.setScene(mainMenuScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainMenu.fxml.");
        }
    }
}
