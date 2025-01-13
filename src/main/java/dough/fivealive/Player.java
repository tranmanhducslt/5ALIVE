package dough.fivealive;
import java.util.*;

public class Player{
    private String name;
    private int lives;
    private PackHand hand;
    private String message;
    public Player(String name){
        this.name = name;
        this.lives = 5;
        this.hand = new PackHand();
    }
    public String getName() {
        return name;
    }
    public int getLives() {
        return lives;
    }
    public boolean isLost() {
        return lives <= 0;
    }
    public void lostLive() {
        this.lives--;
        if (this.lives <= 0) {
            System.out.println(name + " has lost all their lives!");
        }
    }
    // Method to play a card from the player's hand
    public void playCard(int index, Table table, List<Player> players) {
        Card playedCard = hand.pickCard(index, true);
        table.discardCard(playedCard);
        table.updateCount(playedCard, players);
        // Check if the table count exceeds 21
        if (table.getCount() > 21) {
            System.out.println(name + " caused the table to exceed 21 and loses 1 life!");
            lostLive();
            // Continue later
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    // Method to rename a player
    public void rename() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a new name: ");
        String newName = scanner.nextLine();
        setName(newName);
        System.out.println("Player's name has been changed to: " + this.name);
        scanner.close();
    }
    public void sendMsg(String message){
        this.message = message;
        System.out.println("Message sent: " + message);
    }
    public void addCard(Card card) {
        hand.addCard(card);
    }
    public PackHand getHand() {
        return hand;
    }
    public boolean hasCard(cardType type) {
        for (Card card : hand.getCards()) {
            if (card.getCardType() == type) {
                return true;
            }
        }
        return false;
    }
    
    // Method to discard a card from the player's hand, returns true if successful
    public boolean discardCard(cardType type, Table table) {
        for (Card card : hand.getCards()) 
            if (card.getCardType() == type) {
                hand.getCards().remove(card);
                table.discardCard(card);
                return true;
            }
        return false;
    }
    
    // Method to clear a player's hand, useful for redeal card
    public void clearHand() {
        hand.getCards().clear();
    }
}