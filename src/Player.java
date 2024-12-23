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
    public void playCard(int index, Table table) {
        table.discardCard(hand.pickCard(index, true));

        // Check if the table count exceeds 21
        if (table.getCount() > 21) {
            System.out.println(name + " caused the table to exceed 21 and loses 1 life!");
            lostLive();
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
    public void setMessage(String message) {
        this.message = message;
    }
    public void sendMsg(){
        System.out.println("Message sent: " + message);
    }
    public void addCard(Card card) {
        hand.addCard(card);
    }
    public PackHand getHand() {
        return hand;
    }
}