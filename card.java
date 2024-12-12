enum cardType{ // types of cards
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, DRAW_1, DRAW_2, PASS, REVERSE, SKIP, EQ_21, EQ_10, EQ_0, REDEAL, BOMB
}
public class card {
    private cardType type;
    public cardType getCardType(){ return type; }
    public void setCardType(cardType type){ this.type = type; }
    public card(cardType type){ setCardType(type); }
}
