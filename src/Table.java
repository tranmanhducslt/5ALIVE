package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    private int count; // Current count on the table
    private List<Card> totalCards; // All cards in the game
    private List<Card> remainingCards; // Cards not dealt to players or played

    // Constructor
    public Table() {
        this.count = 0;
        this.totalCards = Card.addCard(); // Initialize with all cards
        this.remainingCards = new ArrayList<>(totalCards); // Initially, all cards are remaining
        Card.shuffle(remainingCards); // Shuffle the deck
    }

    // Method to deal 10 cards to each player at the start of a round
    public void dealCards(List<Player> players) {
        for (Player player : players) {
            for (int i = 0; i < 10; i++) {
                if (!remainingCards.isEmpty()) {
                    player.pickCard(remainingCards.remove(0)); // Deal a card and remove it from remaining cards
                }
            }
        }
    }

    // Method to update the count when a player plays a card
    public void updateCount(Card card) {
        this.count += card.getValue();
    }

    // Method to reset the round when a player empties their hand
    public void resetRound(List<Player> players) {
        System.out.println("Round reset! All players lose 1 life except the player who emptied their hand.");

        // Reduce lives for all players except the one who reset the round
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                player.lostLive();
            }
        }

        // Reset the table count and reinitialize remaining cards
        this.count = 0;
        this.remainingCards = new ArrayList<>(totalCards);
        Card.shuffle(remainingCards);

        // Deal 10 new cards to each player
        dealCards(players);
    }

    // Getter for the current count
    public int getCount() {
        return count;
    }

    // Getter for remaining cards
    public List<Card> getRemainingCards() {
        return remainingCards;
    }
}
