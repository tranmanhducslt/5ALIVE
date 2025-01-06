package src;
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
    public void discardCard(cardType type, Table table) {
        Iterator<Card> iterator = hand.getCards().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getCardType() == type) {
                iterator.remove();
                table.discardCard(card);
                break;
            }
        }
    }
    // Clear a player's hand, useful for redeal card
    public void clearHand() {
        hand.getCards().clear();
    }
}