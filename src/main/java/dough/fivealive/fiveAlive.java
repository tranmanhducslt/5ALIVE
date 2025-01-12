package dough.fivealive;
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
            currentPlayer.playCard(index, table, players);
            // if count exceed 21, reset count
            if(table.getCount() > 21){
                System.out.println("Count exceeded 21, count now resets to 0");
                table.resetCount();
            }
            
            // Skip next player when a skip card is played
            if (table.shouldSkipNextPlayer() == true) {
                table.resetSkipFlag();
                table.nextPlayer(players);
                System.out.println("Skipping " + table.getCurrentPlayer(players).getName());
            }
            // BOMB card played
            if(table.bombCheck() == true){
                for (Player player : players) {
                    if (player != currentPlayer) {  // Skip the current player
                        if (!player.hasCard(cardType.ZERO)) {
                            player.lostLive();
                            System.out.println(player.getName() + " loses 1 life.");
                        } else {
                            player.discardCard(cardType.ZERO, table);
                            System.out.println(player.getName() + " discarded their ZERO!");
                        }
                    }
                }
                table.resetBombFlag();
            }
            

            // Check if the player lost
            if (currentPlayer.isLost()) {
                System.out.println(currentPlayer.getName() + " has lost all their lives!");
                players = table.removePlayer(players, currentPlayer, table); // write a method to remove player from the arraylist
                continue;
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
}