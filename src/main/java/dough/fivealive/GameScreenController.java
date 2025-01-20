package dough.fivealive;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    private VBox playersContainer; // VBox to display other players' information

    @FXML
    private HBox playerLivesContainer; // HBox to display "Five Alive" cards (lives)

    @FXML
    private HBox playerHandContainer; // HBox to display the player's hand of cards

    private Table table; // Game table to manage the state
    private List<Player> players; // List of players in the game

    public void initializeGame(List<String> playerNames) {
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
        // Update UI to reflect the current game state
        Player currentPlayer = table.getCurrentPlayer(players);
        updateCurrentPlayer(currentPlayer);
        updatePlayerLives(currentPlayer.getLives());
        updatePlayerHand(currentPlayer.getHand());
        currentCountLabel.setText("Current Count: " + table.getCount());
    }

    private void updateCurrentPlayer(Player player) {
        currentPlayerLabel.setText("Current Player: " + player.getName());
    }

    private void updatePlayerLives(int lives) {
        playerLivesContainer.getChildren().clear();
        for (int i = 0; i < lives; i++) {
            ImageView lifeCard = new ImageView("/path/to/life_card_image.png");
            lifeCard.setFitWidth(50);
            lifeCard.setFitHeight(70);
            playerLivesContainer.getChildren().add(lifeCard);
        }
    }

    private void updatePlayerHand(PackHand hand) {
        playerHandContainer.getChildren().clear();
        for (Card card : hand.getCards()) {
            // Construct the image path based on card name
            String imagePath = "../resources/img/C-" + card.getCardType().name() + ".png";
            ImageView cardImage = new ImageView(imagePath);

            cardImage.setFitWidth(70);
            cardImage.setFitHeight(100);
            playerHandContainer.getChildren().add(cardImage);

            // Add click event to cards
            cardImage.setOnMouseClicked(event -> handleCardPlay(card));
        }
    }


    private void updatePlayersContainer() {
        playersContainer.getChildren().clear();
        for (Player player : players) {
            Label playerLabel = new Label(player.getName() + " - Lives: " + player.getLives());
            playersContainer.getChildren().add(playerLabel);
        }
    }

    private void handleCardPlay(Card card) {
        Player currentPlayer = table.getCurrentPlayer(players);

        // Play the card
        // currentPlayer.playCard(card, table, players);

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

        // Check for player losing
        if (currentPlayer.isLost()) {
            System.out.println(currentPlayer.getName() + " has lost all their lives!");
            players = table.removePlayer(players, currentPlayer, table);
        }

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
