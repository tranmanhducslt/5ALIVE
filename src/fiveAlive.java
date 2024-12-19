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
        while (true) {
            for (Player player : players) {
                if (player.isLost()) {
                    players = removePlayer(players, player);
                    continue;
                }
                System.out.println("Current count: " + table.getCount());
                System.out.println(player.getName() + "'s turn");
                System.out.println("Your hand: " + player.getHand());
                System.out.print("Enter card index to play: ");
                int index = scanner.nextInt();
                if (index < 0 || index >= player.getHand().size()) {
                    System.out.println("Invalid index! Try again.");
                    continue;
                }
                Card card = player.getHand().get(index);
                player.playCard(card, table);
                if (player.isLost()) {
                    System.out.println(player.getName() + " has lost all their lives!");
                }
                if (player.getHand().isEmpty()) {
                    table = new Table(players);
                }
            }
            if (players.size() == 1) {
                System.out.println("Game over! " + players.get(0).getName() + " wins!");
                break;
        }
    }
}
public static List<Player> removePlayer(List<Player> players, Player player) {
        players.remove(player);
        return players;
    }
}