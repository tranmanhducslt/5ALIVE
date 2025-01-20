/* 
 * Author: Duc Tran [1589830]
 * Purpose: To represent a single card in the game
*/

package dough.fivealive;
import javafx.scene.image.Image;

enum cardType{ // types of cards
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, DRAW_1, DRAW_2, PASS, REVERSE, SKIP, EQ_21, EQ_10, EQ_0, REDEAL, BOMB
}
public class Card {
    private cardType type;
    public cardType getCardType(){ return type; }
    public void setCardType(cardType type){ this.type = type; }
    public Card(cardType type){ setCardType(type); }
    private Image cardImage;
    private static Image backImage = new Image(Card.class.getResourceAsStream("../resources/img/C-BACK.png"));
    public Image getImage(){
        // load card image only when needed
        if(cardImage == null)
            cardImage = new Image(
                Card.class.getResourceAsStream(("../resources/img/C-" + getCardType() + ".png")));
        return cardImage;
    }

    public static Image getBackImage(){
        return backImage;
    }
}
