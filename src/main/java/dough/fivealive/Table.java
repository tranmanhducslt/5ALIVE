package dough.fivealive;
import java.util.*;

public class Table {
    private int count; // Current count on the table
    private PackDraw drawPack;
    private PackDiscard discardPack;
    private int currentPlayerIndex; // keep track of whose turn it is
    private boolean isClockwise;
    private boolean skipNextPlayer;
    private boolean bombCardPlayed;

    // Constructor, also to reset every new round
    public Table(List<Player> players) {
        for (Player player : players) 
            if (!player.getHand().isEmpty()) 
                player.lostLive();
        this.currentPlayerIndex = 0;
        this.count = 0;
        this.isClockwise = true;
        this.skipNextPlayer = false;
        this.bombCardPlayed = false;
        this.drawPack = new PackDraw();
        this.discardPack = new PackDiscard();
        dealCards(players);
    }

    // Return the player at the current index
    public Player getCurrentPlayer(List<Player> players) {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer(List<Player> players) {
        if(isClockwise){
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
        else{
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        }
        
    }

    public void reverseDirection() {
        isClockwise = !isClockwise;
    }
    public static List<Player> removePlayer(List<Player> players, Player player, Table table) {
        int playerIndex = players.indexOf(player);
        players.remove(player);

        // Adjust index to point to the next player
        if (playerIndex <= table.currentPlayerIndex) {
            table.currentPlayerIndex--;  // Shift index back if needed
        }
        return players;
    }

    // Method to deal 10 cards to each player at the start of a round
    public void dealCards(List<Player> players) {
        for (Player player : players) 
            for (int i = 0; i < 10; i++) 
                if (!drawPack.isEmpty()) 
                    player.addCard(drawPack.pickCard(0, true));
                    // deal a card and remove it from remaining cards
    }

    // Method to refill from discard pack if no more cards to draw
    public void refill(PackDiscard discardPack){
        if (drawPack.isEmpty()) {
            drawPack = new PackDraw(discardPack);
            discardPack.reset();
        }
    }

    // Method to update the count when a player plays a card
    public void updateCount(Card card, List<Player> players) {
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
                for (Player player: players) {
                    if (player != getCurrentPlayer(players)) {
                        if (drawPack.isEmpty()) refill(discardPack);
                        player.addCard(drawPack.pickCard(0, true));
                    }
                }
                break;
            case DRAW_2:
                for (Player player: players) {
                    if (player != getCurrentPlayer(players)) {
                        for (int i = 0; i < 2; i++) {
                            if (drawPack.isEmpty()) refill(discardPack);
                            player.addCard(drawPack.pickCard(0, true));
                        }
                    }
                }
                break;
            case PASS:
                System.out.println(getCurrentPlayer(players).getName() + " passes their turn.");
                break;
            case REVERSE:
                isClockwise = !isClockwise;
                System.out.println("Play now proceeds in the opposite direction");
                break;
            case SKIP:
                System.out.println("Next player is skipped!");
                skipNextPlayer = true;
                break;
            case REDEAL:
                System.out.println("REDEAL card played! All players' hands are collected and reshuffled.");
                List<Card> allCards = new ArrayList<>();
            
                // Collect all cards from players' hands
                for (Player player : players) {
                    allCards.addAll(player.getHand().getCards());
                    player.getHand().clear();  // Empty hand
                }
                        Collections.shuffle(allCards);
            
                // Deal the cards starting with the next player
                int startIndex = (currentPlayerIndex + 1) % players.size();
                int cardIndex = 0;
                while (cardIndex < allCards.size()) {
                    for (int i = 0; i < players.size() && cardIndex < allCards.size(); i++) {
                        Player player = players.get((startIndex + i) % players.size());
                        player.addCard(allCards.get(cardIndex++));  // Deal one card to each player
                    }
                }
                count = 0;
                System.out.println("All hands reshuffled and dealt. Count is reset to 0.");
                break;
            case BOMB:
                System.out.println("BOMB card played. Count is reseted to 0. All other players must immediately discard a ZERO.");
                count = 0;
                bombCardPlayed = true;
        }
    }
    public boolean shouldSkipNextPlayer(){
        return skipNextPlayer;
    }
    public void resetSkipFlag(){
        skipNextPlayer = false;
        return;
    }

    public void discardCard(Card card){
        discardPack.addCard(card, 0);
    }
    public void resetCount(){
        count = 0;
    }
    public boolean bombCheck(){
        return bombCardPlayed;
    }
    public void resetBombFlag(){
        bombCardPlayed = false;
    }

    // Getter for the current count
    public int getCount() {
        return count;
    }
}
