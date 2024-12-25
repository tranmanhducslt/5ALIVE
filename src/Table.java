package src;
import java.util.*;

public class Table {
    private int count; // Current count on the table
    private PackDraw drawPack;
    private PackDiscard discardPack;

    // Constructor, also to reset every new round
    public Table(List<Player> players) {
        for (Player player : players) 
            if (!player.getHand().isEmpty()) 
                player.lostLive();
                player.getHand().reset(); // Clear player's hand
        this.count = 0;
        this.drawPack = new PackDraw();
        this.discardPack = new PackDiscard();
        dealCards(players);
    }

    // Method to deal 10 cards to each player at the start of a round
    public void dealCards(List<Player> players) {
        for (Player player : players) 
            for (int i = 0; i < 10; i++) 
                if (!drawPack.isEmpty()) 
                    player.addCard(drawPack.pickCard(0, true));
                    // deal a card and remove it from remaining cards
    }

    public void refill(PackDiscard discardPack){
        if (drawPack.isEmpty()) {
            drawPack = new PackDraw(discardPack);
            discardPack.reset();
        }
    }
    private void handleBomb() {
        Player currentPlayer = getCurrentPlayer();
    
        for (Player player : players) {
            if (player == currentPlayer) continue; // Skip the player who played the BOMB card
    
            boolean hasZeroCard = false;
    
            // Check if the player has a ZERO card
            for (int i = 0; i < player.getHand().size(); i++) {
                Card card = player.getHand().getCard(i); // Search through the player's hand for ZERO
                if (card.getCardType() == CardType.ZERO) {
                    discardCard(player.getHand().pickCard(i, true));
                    System.out.println(player.getName() + " discarded a ZERO card.");
                    hasZeroCard = true;
                    break;
                }
            }
            if (!hasZeroCard) {
                player.lostLive();
                System.out.println(player.getName() + " has no ZERO card and loses a life.");
            }
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
                nextTurn(); // imma work on this method later
                getCurrentPlayer().pickCard(drawPack.pickCard(0, true)); // later as well
                System.out.println(getCurrentPlayer().getName() + " drew 1 card.");
                break;
            case DRAW_2:
                nextTurn();
                Player currentPlayer = getCurrentPlayer();
                currentPlayer.pickCard(drawPack.pickCard(0, true));
                currentPlayer.pickCard(drawPack.pickCard(0, true));
                System.out.println(currentPlayer.getName() + " drew 2 cards.");
                break;
            case PASS:
                nextTurn();
                break;
            case REVERSE:
                reverseDirection();
                System.out.println("Play direction reversed.");
                break;
            case SKIP:
                nextTurn();
                nextTurn();
                break;
            case REDEAL:
                count = 0;
                break;
            case BOMB:
                handleBomb();
                count = 0;
                break;
            if(count == 21){
                // Check if player has any ZEROs or wildcards in hand

                // If not, player loses a life.
                
            }
            // will continue later
        }
    }

    public void discardCard(Card card){
        discardPack.addCard(card, 0);
    }

    // Getter for the current count
    public int getCount() {
        return count;
    }
    // for debugging
    @Override
    public String toString() {
        return "Table{" +
                "count=" + count +
                ", drawPackSize=" + drawPack.size() +
                ", discardPackSize=" + discardPack.size() +
                '}';
    }
}

