import java.util.*;


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

    public boolean isLost() {
        return lost;
    }
    public void playCard(Card card, Table table) {
        // Add the card value to the table count
        table.updateCount(card);

        // Remove the card from the player's hand
        hand.remove(card);

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