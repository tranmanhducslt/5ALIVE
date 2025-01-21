/* 
 * Author: Phuong Nguyen [ID]
 * Purpose: For the game screen
*/

package dough.fivealive;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

        Card playedCard = currentPlayer.playCard(index, table, players);
        if (playedCard != null) {
            String imagePath = "/img/C-" + playedCard.getCardType().name() + ".png";
            recentlyPlayedCardImage.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        }

        // Update count and check for exceeding 21
        if (table.getCount() > 21) {
            table.resetCount();
            currentCountLabel.setText("Count exceeded 21. Resetting count.");
        } else {
            currentCountLabel.setText("Current Count: " + table.getCount());
        }

        // Handle special cards
        if (table.shouldSkipNextPlayer()) {
            table.resetSkipFlag();
            nextPlayer(); // Skip next player
        }
        if (table.bombCheck()) {
            handleBombCard(currentPlayer);
            table.resetBombFlag();
        }

        if (currentPlayer.isLost()) {
            System.out.println(currentPlayer.getName() + " has lost all their lives!");
            players = table.removePlayer(players, currentPlayer, table);
        }
        updatePlayersContainer();

        // Check if only one player remains
        if (players.size() == 1) {
            currentPlayerLabel.setText("Game Over! " + players.get(0).getName() + " wins!");
            return;
        }

        // Move to the next turn
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
}
