package src;
import java.util.*;

public class Table {
    private int count; // Current count on the table
    private PackDraw drawPack;
    private PackDiscard discardPack;

    // Constructor, also to reset every new round
    public Table(){
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

    public void refill(PackDiscard discardPack){
        if (drawPack.isEmpty()) {
            drawPack = new PackDraw(discardPack);
            discardPack.reset();
        }
    }

    // Method to update the count when a player plays a card
    public void updateCount(Card card) {
        switch(card.getCardType()){
            case ZERO:
            case ONE:
            case TWO:
            case THREE:
            case FOUR:
            case FIVE:
            case SIX:
            case SEVEN:
                count += card.getCardType().ordinal();
                break;
            case EQ_21:
                count = 21;
                break;
            case EQ_10:
                count = 10;
                break;
            case EQ_0:
                count = 0;
                break;
            case DRAW_1:
            case DRAW_2:
            case PASS:
            case REVERSE:
            case SKIP:
            case REDEAL:
            case BOMB:
            // will continue later
        }
    }

    // Getter for the current count
    public int getCount() {
        return count;
    }
}
