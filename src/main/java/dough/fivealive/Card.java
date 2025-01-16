/* 
 * Author: Duc Tran [1589830]
 * Purpose: To represent a single card in the game
*/

package dough.fivealive;

enum cardType{ // types of cards
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, DRAW_1, DRAW_2, PASS, REVERSE, SKIP, EQ_21, EQ_10, EQ_0, REDEAL, BOMB
}
public class Card {
    private cardType type;
    public cardType getCardType(){ return type; }
    public void setCardType(cardType type){ this.type = type; }
    public Card(cardType type){ setCardType(type); }
}
