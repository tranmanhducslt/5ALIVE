package src;
import java.util.*;
public class fiveAlive {
   public static void main(String[] args) {
        System.out.println("fiveAlive");
        List<Player> players = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of players: ");
        int numPlayers = scanner.nextInt();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter player " + (i + 1) + " name: ");
            String name = scanner.next();
            players.add(new Player(name));
        }
        Table table = new Table(players);
        while (true) { // Main game loop
            Player currentPlayer = table.getCurrentPlayer(players);  // Get the current player
            
            // Handle player's turn
            System.out.println("Current count: " + table.getCount());
            System.out.println(currentPlayer.getName() + "'s turn");
            System.out.println("Your hand: " + currentPlayer.getHand());
            System.out.print("Enter card index to play: ");
            int index = scanner.nextInt();

            // If invalid index, retry
            if (index < 0 || index >= currentPlayer.getHand().size()) {
                System.out.println("Invalid index! Try again.");
                continue;
            }

            // Play card and update count
            currentPlayer.playCard(index, table);

            // Check if the player lost
            if (currentPlayer.isLost()) {
                System.out.println(currentPlayer.getName() + " has lost all their lives!");
            }

            // Move to next player
            table.nextPlayer(players);

            // Check if there is only one player left
            if (players.size() == 1) {
                System.out.println("Game over! " + players.get(0).getName() + " wins!");
                break;
            }
        }
        
        scanner.close();
    }
public static List<Player> removePlayer(List<Player> players, Player player) {
        players.remove(player);
        return players;
    }
}