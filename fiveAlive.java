import java.io.*;
import java.util.*;
public class fiveAlive{
    enum cardType{ // types of cards
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, DRAW_1, DRAW_2, PASS, REVERSE, SKIP, EQ_21, EQ_10, EQ_0, REDEAL, BOMB
    }
    public static class card{ // single card
        private cardType type;
        public cardType getCardType(){ return type; }
        public void setCardType(cardType type){ this.type = type; }
        public card(cardType type){ setCardType(type); }
    }
    public static class deck{ // draw deck in game 
        private ArrayList<card> cards;
        public deck(){
            cards = new ArrayList<card>();
            for (int i = 1; i <= 8; ++i){
                switch(i){
                    case 8: // 1 of each
                        cards.add(new card(cardType.SEVEN));
                        cards.add(new card(cardType.BOMB));
                        cards.add(new card(cardType.REDEAL));
                    case 7: // 2 of each
                        cards.add(new card(cardType.SIX));
                        cards.add(new card(cardType.EQ_10));
                        cards.add(new card(cardType.DRAW_1));
                        cards.add(new card(cardType.DRAW_2));
                    case 6: // 3 of each
                        cards.add(new card(cardType.EQ_0));
                    case 5: // 4 of each
                        cards.add(new card(cardType.FIVE));
                        cards.add(new card(cardType.SKIP));
                        cards.add(new card(cardType.PASS));
                    case 4: // 5 of each
                        cards.add(new card(cardType.EQ_21));
                    case 3: // 6 of each
                        cards.add(new card(cardType.REVERSE));
                    case 1: // 8 of each
                        cards.add(new card(cardType.ZERO));
                        cards.add(new card(cardType.ONE));
                        cards.add(new card(cardType.TWO));
                        cards.add(new card(cardType.THREE));
                        cards.add(new card(cardType.FOUR));
                    default: continue; // no type has 7 of each
                }
            }
        }
    }
    public static void main(String args[]){
        System.out.println("Test!");
    }
}