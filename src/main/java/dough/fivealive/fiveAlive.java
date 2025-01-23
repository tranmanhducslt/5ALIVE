/* 
 * Author: Duc Tran [1589830], Nhu Nguyen [ID], Phuong Nguyen [ID]
 * Purpose: To represent the main game logic
*/

package dough.fivealive;
import java.util.*;
public class fiveAlive {
    private List<Player> players;
    private Player currentPlayer;
    private Table table;
    public Player getCurrentPlayer(){
        return currentPlayer;
    }
    public int getCount(){
        return table.getCount();
    }
    public Card getRecentlyPlayedCard(){
        return table.getRecentlyPlayedCard();
    }
    public List<Player> getPlayers(){
        return players;
    }
    public fiveAlive(List<Player> playerInput) {
        System.out.println("fiveAlive");
        Scanner scanner = new Scanner(System.in);
        players = playerInput;
        table = new Table(players);
        while (true) { // Main game loop
            currentPlayer = table.getCurrentPlayer(players);  // Get the current player
            
            // Check if there is only one player left
            if (players.size() == 1) {
                System.out.println("Game over! " + players.get(0).getName() + " wins!");
                break;
            }
            // Check if the player lost
            if (currentPlayer.isLost()) {
                Player thisPlayer = currentPlayer;
                table.nextPlayer(players);
                currentPlayer = table.getCurrentPlayer(players);
                System.out.println(currentPlayer.getName() + " has lost all their lives!");
                players = Table.removePlayer(players, thisPlayer, table);
                if (players.size() == 1) {
                    System.out.println("Game over! " + players.get(0).getName() + " wins!");
                    break;
                }
            }
            
            // Handle player's turn
            System.out.println("\nCurrent count: " + table.getCount());
            System.out.println(currentPlayer.getName() + "'s turn - Lives: " + currentPlayer.getLives());
            System.out.println("Your hand:\n" + currentPlayer.getHand());
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
                            System.out.println(player.getName() + " cannot play a ZERO and loses 1 life.");
                        } else {
                            player.discardCard(cardType.ZERO, table);
                            System.out.println(player.getName() + " discarded their ZERO!");
                        }
                    }
                }
                table.resetBombFlag();
            }
            
            // Check if anyone went out
            for (Player player : players) {
                if (player.getHand().isEmpty()) {
                    System.out.println(player.getName() + " has gone out! All other players lose 1 life.");
                    table = new Table(players);  // Reset the table
                }
            }

            // Move to next player
            table.nextPlayer(players);
   
        }
        
        scanner.close();
    }
}
