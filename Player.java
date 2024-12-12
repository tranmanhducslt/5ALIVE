import java.util.*;
public class Player{
    private String name;
    private int lives;
    private boolean lost;
    private packHand hand;
    private String message;
    public Player(String name){
        this.name = name;
        this.lives = 5;
        this.hand = new packHand();
    }
    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
        }
    }
    public card playCard(int index) {
        if (index < 0 || index >= hand.size()) {
            System.out.println("Invalid");
            return null;
        }
        return hand.pickCard(index, true);
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
}