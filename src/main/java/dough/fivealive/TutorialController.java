/* 
 * Author: Phuong Nguyen [ID]
 * Purpose: Controller for the tutorial screen
*/

package dough.fivealive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TutorialController {

    @FXML
    private Button returnMenuButton;

    @FXML
    public void returnMenuScreen() {
        System.out.println("Returning to menu...");

        try {
            // load setup scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent playerSetupRoot = loader.load();

            Stage stage = (Stage) returnMenuButton.getScene().getWindow();
            Scene playerSetupScene = new Scene(playerSetupRoot);
            stage.setScene(playerSetupScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load MainMenu.fxml.");
        }
    }
}
