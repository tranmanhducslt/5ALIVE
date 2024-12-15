import java.util.*;
import game.card; // Correct import

public class Player{
    private String name;
    private int lives;
    private boolean lost;
    private List<Card> hand;
    private String message;
    public Player(String name){
        this.name = name;
        this.lives = 5;
        this.hand = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void lostLive() {
        this.lives--;
        if (this.lives <= 0) {
            System.out.println(name + " has lost all their lives!");
        }
    }

    public void playCard(Card card, int tableValue) {
        tableValue += card.getValue(); // Add card value to table

        if (tableValue > 21) {
            lostLive();  // Player loses 1 live
        }

        hand.remove(card); // Remove card from hand after playing it
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
    }
    public String sendMsg(){
        System.out.println("Message sent..");
    }
    public boolean goOut(){

    }
    public void pickCard(Card card) {
        hand.add(card);
    }
    public List<Card> getHand() {
        return hand;
    }
}
