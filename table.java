import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import javax.smartcardio.Card;

import game.card;

public class table extends player{
    private <card> cards;
    private int table_id;
    private <player> players;
    List<card> deck = createDeck();
    List<remainCard> remainDeck = remainCard();
    public sumTable=0;

    //update the remain card after a round
    public static List<Card> remainCard() { 
        List<Card> deck = deck.rm(players.cards); 
        for (player playes: list_of_players){ { 
            for (int i = 0; i < 4; i++) { 
                // Assuming 4 copies of each card 
                deck.add(new Card(value)); 
            } 
        } 
        return deck; 
    }
    void addPlayer(){
        //add player to table
    }
    //shuffle the deck
    public static void shuffleDeck(List<Card> deck) { 
        Collections.shuffle(deck); 
    }
    //deal the cards
    void deal(){
        //deal the cards
    }
    //update the desk after a round
    void updateDeck(){
        //update the deck
    }
    //add all the cards to the table
    public static List<Card> createDeck() { 
        List<Card> deck = new ArrayList<>(); 
        for (Integer value : ALL_CARDS) { 
            for (int i = 0; i < 4; i++) { 
                // Assuming 4 copies of each card 
                deck.add(new Card(value)); 
            } 
        } 
        return deck; 
    }
    //reset the table after 1 player deal all their card
    void resetTable(){
        //reset the table
    }
} 

}
class draw{
    private int draw_id;
    private int user_id;
    private int table_id;
    private int amount;
    private int time;
    
    public draw(int draw_id, int user_id, int table_id, int amount, int time){
        this.draw_id = draw_id;
        this.user_id = user_id;
        this.table_id = table_id;
        this.amount = amount;
        this.time = time;
    }
    public int getDrawId(){
        return draw_id;
    }
    public int getUserId(){
        return user_id;
    }
    public int getTableId(){
        return table_id;
    }
    public int getAmount(){
        return amount;
    }
    public int getTime(){
        return time;
    }
    //add a draw to the database
    public void addDraw(){
        //add draw to database
    }
}
