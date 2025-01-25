/*
 * Author: Phuong Nguyen [ID]
 * Purpose: Controller for the Settings screen
 */

package dough.fivealive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingScreenController {

    @FXML
    private Slider backgroundMusicSlider; // Slider for background music volume

    @FXML
    private Slider soundEffectsSlider; // Slider for sound effects volume

    @FXML
    private Button returnToMenuButton;

    private AudioManager audioManager; // Reference to the AudioManager

    /**
     * Initializes the settings screen with default values and event listeners.
     */
    public void initialize() {
        // Set initial slider values (default or based on current audio settings)
        backgroundMusicSlider.setValue(AudioManager.getBackgroundMusicVolume() * 100);
        soundEffectsSlider.setValue(AudioManager.getSoundEffectsVolume() * 100);

        // Add listeners to sliders to update audio settings in real time
        backgroundMusicSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100;
            AudioManager.setBackgroundMusicVolume(volume); // Update background music volume
        });

        soundEffectsSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100;
            AudioManager.setSoundEffectsVolume(volume); // Update sound effects volume
        });
    }

    @FXML
    public void returnToMenu() {
        System.out.println("Returning to menu...");

        try {
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

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
}
