package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    private int count; // Current count on the table
    private PackDraw drawPack;

    // Constructor, also to reset every new round
    public Table() {
        this.count = 0;
        this.drawPack = new PackDraw();
    }

    // Method to deal 10 cards to each player at the start of a round
    public void dealCards(List<Player> players) {
        for (Player player : players) 
            for (int i = 0; i < 10; i++) 
                if (!drawPack.isEmpty()) 
                    player.pickCard(drawPack.pickCard(0, true));
                    // deal a card and remove it from remaining cards
    }

    // Method to update the count when a player plays a card
    public void updateCount(Card card) {
        switch(card.getCardType()){
            case ZERO:
                count += 0;
                break;
            case ONE:
                count += 1;
                break;
            case TWO:
                count += 2;
                break;
            case THREE:
                count += 3;
                break;
            case FOUR:
                count += 4;
                break;
            case FIVE:
                count += 5;
                break;
            case SIX:
                count += 6;
                break;
            case SEVEN:
                count += 7;
                break;
            // will continue later
        }
    }

    // Getter for the current count
    public int getCount() {
        return count;
    }
}
