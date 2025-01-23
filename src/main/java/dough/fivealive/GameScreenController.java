/* 
 * Author: Phuong Nguyen [ID], Nhu Nguyen [ID]
 * Purpose: For the game screen
*/

package dough.fivealive;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    private Table table; // Game table to manage the state
    private List<Player> players; // List of players in the game

    public void initializeGame(List<String> playerNames) {
        if (otherPlayersContainer == null) {
            System.out.println("Error: playersContainer is null. Check GameScreen.FXML file.");
            return;
        }
        // Initialize players and table
        players = new ArrayList<>();
        for (String name : playerNames) {
            players.add(new Player(name));
        }
        table = new Table(players);

        // Debug
        System.out.println("Game initialized with players: " + playerNames);

        // Update UI with initial state
        updatePlayersContainer();
        updateGameState();
    }

    private void updateGameState() {
        Player currentPlayer = table.getCurrentPlayer(players);
        System.out.println(currentPlayer.getName() + "'s lives: " + currentPlayer.getLives()); // debug
        updateCurrentPlayer(currentPlayer);
        updatePlayerHand(currentPlayer.getHand()); // Refresh the hand
        currentCountLabel.setText("Current Count: " + table.getCount());
    }

    private void updateCurrentPlayer(Player player) {
        currentPlayerLabel.setText("Current Player: " + player.getName());
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

            // Add click event with the card's index
            int index = i;
            cardImage.setOnMouseClicked(event -> handleCardPlay(index));

            playerHandContainer.getChildren().add(cardImage);
        }
    }


    private void updatePlayersContainer() {
        otherPlayersContainer.getChildren().clear();
        for (Player player : players) {
            Label playerLabel = new Label(player.getName() + " - Lives: " + player.getLives());
            playerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #444444; "
                    + "-fx-padding: 5 10; -fx-border-color: white; -fx-border-width: 1; -fx-border-radius: 5; "
                    + "-fx-background-radius: 5;");
            playerLabel.setMinWidth(200);
            playerLabel.setMaxWidth(400);
            playerLabel.setWrapText(true); // Enable wrapping if needed
            otherPlayersContainer.getChildren().add(playerLabel);
        }
    }

    private void handleCardPlay(int index) {
        Player currentPlayer = table.getCurrentPlayer(players);

        // Check and remove players with 0 lives before the current player plays
        List<Player> playersToRemove = new ArrayList<>();
        for (Player player : players) {
            if (player.getLives() <= 0) {
                System.out.println(player.getName() + " has lost all their lives!");
                playersToRemove.add(player); // Mark for removal
            }
        }

        for (Player playerToRemove : playersToRemove) {
            players = table.removePlayer(players, playerToRemove, table);
        }

        // Check if the game is over
        if (players.size() == 1) {
            String winnerName = players.get(0).getName();
            showGameEndScene(winnerName);
            return;
        }

        // Current player plays a card
        Card playedCard = currentPlayer.playCard(index, table, players);

        // Update recently played card image
        if (playedCard != null) {
            String imagePath = "/img/C-" + playedCard.getCardType().name() + ".png";
            recentlyPlayedCardImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        }

        // Update count and reset if exceeded
        if (table.getCount() > 21) {
            table.resetCount();
            currentCountLabel.setText("Count exceeded 21. Resetting count.");
        } else {
            currentCountLabel.setText("Current Count: " + table.getCount());
        }

        // Check and remove players with 0 lives AFTER the current player plays
        List<Player> playersToRemove2 = new ArrayList<>();
        for (Player player : players) {
            if (player.getLives() <= 0) {
                System.out.println(player.getName() + " has lost all their lives!");
                playersToRemove2.add(player); // Mark for removal
            }
        }

        for (Player playerToRemove2 : playersToRemove2) {
            Table.removePlayer(players, playerToRemove2, table);
        }
        // Handle special cards
        if (players.size() > 1) { // Skip these checks if the game is over
            if (table.shouldSkipNextPlayer()) {
                table.resetSkipFlag();
                nextPlayer(); // Skip next player
            }
            if (table.bombCheck()) {
                handleBombCard(currentPlayer);
                table.resetBombFlag();
            }
        }

        // Check if anyone went out (played all cards)
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                System.out.println(player.getName() + " has gone out! All other players lose 1 life.");
                List<Player> affectedPlayers = new ArrayList<>();
                for (Player otherPlayer : players) {
                    if (otherPlayer != player) {
                        otherPlayer.lostLive(); // Deduct 1 life
                        if (otherPlayer.getLives() <= 0) {
                            affectedPlayers.add(otherPlayer); // Mark for removal if life <= 0
                        }
                    }
                }

                // Remove any players who lost all lives as a result
                for (Player affectedPlayer : affectedPlayers) {
                    System.out.println(affectedPlayer.getName() + " has lost all their lives due to going out penalty!");
                    players = table.removePlayer(players, affectedPlayer, table);
                }

                // Reset the table after someone goes out
                table = new Table(players);
                break; // Exit loop once table is reset
            }
        }

        // Final game-over check
        if (players.size() == 1) {
            String winnerName = players.get(0).getName();
            showGameEndScene(winnerName);
            return;
        }

        updatePlayersContainer(); // Update UI
        nextPlayer();
    }


    private void handleBombCard(Player currentPlayer) {
        for (Player player : players) {
            if (player != currentPlayer) {
                if (!player.hasCard(cardType.ZERO)) {
                    player.lostLive();
                    System.out.println(player.getName() + " cannot play a ZERO and loses 1 life.");
                } else {
                    player.discardCard(cardType.ZERO, table);
                    System.out.println(player.getName() + " discarded their ZERO!");
                }
            }
        }
    }

    private void nextPlayer() {
        table.nextPlayer(players);
        updateGameState();
    }
    private void showGameEndScene(String winnerName) {
        try {
            // Load the Game Over FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dough/fivealive/EndScreen.fxml"));
            Parent root = loader.load();

            // Set the winner's name
            EndScreenController controller = loader.getController();
            controller.setWinnerName(winnerName);

            // Switch to the new scene
            Stage primaryStage = (Stage) currentPlayerLabel.getScene().getWindow();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Game Over");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
