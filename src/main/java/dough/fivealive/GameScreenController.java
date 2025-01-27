/* 
 * Author: Duc Tran [1589830], Phuong Nguyen [ID], Nhu Nguyen [ID]
 * Purpose: For the game screen
*/

package dough.fivealive;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.stream.Collectors;

import javafx.stage.Stage;

import java.io.IOException;

public class GameScreenController {

    @FXML
    private Label currentCountLabel; // Label to display the current count

    @FXML
    private Label currentPlayerLabel; // Label to display the current player's turn

    @FXML
    private VBox otherPlayersContainer; // VBox to display other players' information

    @FXML
    private HBox playerHandContainer; // HBox to display the player's hand of cards

    @FXML
    private ImageView recentlyPlayedCardImage;

    private fiveAlive game; // The game logic, which will be reflected here

    public void initializeGame(List<String> playerNames) {

        if (otherPlayersContainer == null) {
            System.out.println("Error: playersContainer is null. Check GameScreen.FXML file.");
            return;
        }

        // Initialize players and game (change name strings to Players)
        List<Player> players = playerNames.stream()
                                          .map(Player::new)
                                          .collect(Collectors.toList());
        game = new fiveAlive(players);

        // Set the UI update callback
        game.setUiUpdateCallback((Void) -> {
            Platform.runLater(this::refreshUI);
        });
        game.setWinCallback(this::handleWinEvent);
        // Debug
        System.out.println("Game initialized with players: " + playerNames);
        // Play start sound effect using AudioManager
        AudioManager.playSoundEffect("/audio/gameStart.mp3");
        // Update UI with initial state
        updatePlayersContainer();
        updateGameState();
    }

    private void updateGameState() {
        Player currentPlayer = game.getCurrentPlayer();
        updateCurrentPlayer(currentPlayer);
        updatePlayerHand(currentPlayer.getHand());
        currentCountLabel.setText("Current Count: " + game.getCount());
        updateRecentlyPlayedCard(game.getRecentlyPlayedCard());
    }

    private void updateCurrentPlayer(Player player) {
        currentPlayerLabel.setText("Current Player: " + player.getName());
    }

    @FXML
    private void handleCardClick(MouseEvent event) {
        ImageView clickedCard = (ImageView) event.getSource();
        int cardIndex = playerHandContainer.getChildren().indexOf(clickedCard);
        game.playCard(cardIndex);
        // Play card sound effect using AudioManager
        AudioManager.playSoundEffect("/audio/cardPlay.mp3");
    }

    private void updatePlayerHand(PackHand hand) {
        playerHandContainer.getChildren().clear();

        List<Card> cards = hand.getCards();
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);

            // Construct the image path based on card name
            String imagePath = "/img/C-" + card.getCardType().name() + ".png";
            ImageView cardImage = new ImageView(getClass().getResource(imagePath).toExternalForm());

            cardImage.setFitWidth(70);
            cardImage.setFitHeight(105);

            // Add event handler for card click
            cardImage.setOnMouseClicked(this::handleCardClick);

            playerHandContainer.getChildren().add(cardImage);
        }
    }
        private void updatePlayersContainer() {
            otherPlayersContainer.getChildren().clear();
            Player currentPlayer = game.getCurrentPlayer();
            for (Player player : game.getPlayers()) {
                Label playerLabel = new Label(player.getName() + " - Lives: " + player.getLives());
                if (player.equals(currentPlayer)) {
                    playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: green; "
                            + "-fx-padding: 5 10; -fx-border-color: white; -fx-border-width: 1; -fx-border-radius: 5; "
                            + "-fx-background-radius: 5;");
                } else {
                    playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #444444; "
                            + "-fx-padding: 5 10; -fx-border-color: white; -fx-border-width: 1; -fx-border-radius: 5; "
                            + "-fx-background-radius: 5;");
                }
                playerLabel.setMinWidth(200);
                playerLabel.setMaxWidth(400);
                playerLabel.setWrapText(true); // Enable wrapping if needed
                otherPlayersContainer.getChildren().add(playerLabel);
            }
            // Add an arrow to indicate the direction of play
            String arrowText = game.isClockwise() ? "\u21E9" : "\u21E7"; // Unicode for down and up arrows
            Label arrowLabel = new Label(arrowText);
            arrowLabel.setStyle("-fx-font-size: 80px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #666666;"
                + "-fx-background-radius: 5; -fx-alignment: center;");
            arrowLabel.setMinWidth(200);
            arrowLabel.setMaxWidth(400);
            otherPlayersContainer.getChildren().add(arrowLabel);
        }

        private void updateRecentlyPlayedCard(Card card) {
        if (card != null) {
            String imagePath = "/img/C-" + card.getCardType().name() + ".png";
            recentlyPlayedCardImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        } else {
            recentlyPlayedCardImage.setImage(null);
        }
    }

    public void refreshUI() {
        updatePlayersContainer();
        updateGameState();
    }
    private void handleWinEvent(Player winner) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EndScreen.fxml"));
                Parent endScreenRoot = loader.load();

                // Get the controller and set the winner's name
                EndScreenController endScreenController = loader.getController();
                endScreenController.setWinnerName(winner.getName());

                Stage stage = (Stage) currentPlayerLabel.getScene().getWindow();
                Scene endScreenScene = new Scene(endScreenRoot);
                stage.setScene(endScreenScene);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Failed to load EndScreen.fxml.");
            }
        });
    }
}
