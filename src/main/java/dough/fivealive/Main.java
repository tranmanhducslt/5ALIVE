/* 
 * Author: Phuong Nguyen [ID]
 * Purpose: The main file for the whole application
*/

package dough.fivealive;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // Start the application and all the classes
    @Override
    public void start(Stage primaryStage) throws Exception {
        AudioManager audioManager = AudioManager.getInstance();
        audioManager.playBackgroundMusic();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Five Alive!");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/L-ICON.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
