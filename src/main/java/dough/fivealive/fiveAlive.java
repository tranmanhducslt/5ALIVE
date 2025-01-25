/* 
 * Author: Duc Tran [1589830], Nhu Nguyen [ID], Phuong Nguyen [ID]
 * Purpose: To represent the main game logic
*/

package dough.fivealive;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class fiveAlive {
    private List<Player> players;
    private Player currentPlayer;
    private Table table;
    private Consumer<Void> uiUpdateCallback;
    private Consumer<Player> winCallback;

    public void setWinCallback(Consumer<Player> winCallback) {
        this.winCallback = winCallback;
    }


    public fiveAlive(List<Player> playerInput) {
        players = playerInput;
        table = new Table(players);
        currentPlayer = table.getCurrentPlayer(players);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean checkWin() {
        if (players.size() == 1) {
            System.out.println("Game over! " + players.get(0).getName() + " wins!");
            if (uiUpdateCallback != null) {
                uiUpdateCallback.accept(null);
            }
            if (winCallback != null) {
                winCallback.accept(players.get(0));
            }
            return true;
        }
        return false;
    }


    public int getCount() {
        return table.getCount();
    }

    public Card getRecentlyPlayedCard() {
        return table.getRecentlyPlayedCard();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setUiUpdateCallback(Consumer<Void> uiUpdateCallback) {
        this.uiUpdateCallback = uiUpdateCallback;
    }

    public void playCard(int index) {
        if (index < 0 || index >= currentPlayer.getHand().size()) {
            throw new IllegalArgumentException("Invalid card index");
        }

        currentPlayer.playCard(index, table, players);

        // if count exceed 21, take life and reset count
        if (table.getCount() > 21) {
            System.out.println("Count has exceeded 21, now resetting...");
            table.resetCount();
        }

        // Skip next player when a skip card is played
        if (table.shouldSkipNextPlayer()) {
            table.resetSkipFlag();
            table.nextPlayer(players);
        }

        // BOMB card played
        if (table.bombCheck()) {
            for (Player player : players) {
                if (player != currentPlayer) {  // Skip the current player
                    if (!player.hasCard(cardType.ZERO)) {
                        player.lostLive();
                    } else {
                        player.discardCard(cardType.ZERO, table);
                    }
                }
            }
            table.resetBombFlag();
        }

        // Check if anyone went out
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                table = new Table(players);  // Reset the table
            }
        }

        // Check if any player has lost
        // (in some cases, many can BOMB out at once)
        List<Player> playersToRemove = new ArrayList<>();
        for (Player player : players) {
            if (player.isLost()) {
                System.out.println(player.getName() + " has lost all their lives and is eliminated!");
                playersToRemove.add(player);
            }
        }
        for (Player player : playersToRemove) {
            players = table.removePlayer(players, player, table);
        }
        checkWin();

        // Move to next player
        table.nextPlayer(players);
        currentPlayer = table.getCurrentPlayer(players);

        // Update the UI
        if (uiUpdateCallback != null) {
            uiUpdateCallback.accept(null);
        }
    }
}
