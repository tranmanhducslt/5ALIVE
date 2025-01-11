package dough.fivealive;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PlayerSetupController {

    @FXML
    private ComboBox<String> playerCountComboBox;

    @FXML
    private VBox playerNamesContainer;

    @FXML
    private Button startGameButton;

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
        playerNamesContainer.getChildren().clear();
        playerNameFields.clear();

        for (int i = 1; i <= playerCount; i++) {
            TextField textField = new TextField();
            textField.setPromptText("Player " + i + " Name");
            playerNamesContainer.getChildren().add(textField);
            playerNameFields.add(textField);
        }
    }

    @FXML
    public void startGame() {
        List<String> playerNames = new ArrayList<>();
        for (TextField textField : playerNameFields) {
            String name = textField.getText().trim();
            if (name.isEmpty()) {
                name = "Player " + (playerNames.size() + 1);
            }
            playerNames.add(name);
        }

        // Debug: Print player names
        System.out.println("Starting game with players: " + playerNames);

        // move to game screen...
    }
}
