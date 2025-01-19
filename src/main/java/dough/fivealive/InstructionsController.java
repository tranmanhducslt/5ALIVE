/* 
 * Author: Duc Tran [1589830]
 * Purpose: Window for game instructions
*/
package dough.fivealive;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InstructionsController {

    @FXML
    private TextArea instructionsTextArea;

    @FXML
    public void initialize() {
        // Load the instructions from the rules.md file
        try (InputStream inputStream = getClass().getResourceAsStream("/txt/rules.md");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            instructionsTextArea.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) instructionsTextArea.getScene().getWindow();
        stage.close();
    }
}