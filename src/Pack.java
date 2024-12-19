package src;
import java.util.*;
public class Pack{ // parent class for packs: draw, discard and player
    protected ArrayList<Card> cards;
    public Pack(){ cards = new ArrayList<Card>(); }
    public Pack(Pack p){ cards = new ArrayList<Card>(p.cards); }
    public boolean isEmpty(){ return cards.isEmpty(); }
    public void shuffle(){ Collections.shuffle(cards); }
    public void reset(){ cards.clear(); }
    public void addCard(Card c){ cards.add(0, c); }
    public int size(){ return cards.size(); }
    public Card pickCard(int index, boolean playIt){
        if (cards.size() <= index) return null;
        Card c = cards.get(index);
        if (playIt) cards.remove(index); return c;
    }
    public void addCard(Card c, int index){ 
        cards.add(index, c); 
    }
}
class PackDraw extends Pack{ // draw pack in game 
    private ArrayList<Card> cards;
    public PackDraw(){
        for (int i = 1; i <= 8; ++i){
            switch(i){
                case 8: // 1 of each
                    cards.add(new Card(cardType.SEVEN));
                    cards.add(new Card(cardType.BOMB));
                    cards.add(new Card(cardType.REDEAL));
                case 7: // 2 of each
                    cards.add(new Card(cardType.SIX));
                    cards.add(new Card(cardType.EQ_10));
                    cards.add(new Card(cardType.DRAW_1));
                    cards.add(new Card(cardType.DRAW_2));
                case 6: // 3 of each
                    cards.add(new Card(cardType.EQ_0));
                case 5: // 4 of each
                    cards.add(new Card(cardType.FIVE));
                    cards.add(new Card(cardType.SKIP));
                    cards.add(new Card(cardType.PASS));
                case 4: // 5 of each
                    cards.add(new Card(cardType.EQ_21));
                case 3: // 6 of each
                    cards.add(new Card(cardType.REVERSE));
                case 1: // 8 of each
                    cards.add(new Card(cardType.ZERO));
                    cards.add(new Card(cardType.ONE));
                    cards.add(new Card(cardType.TWO));
                    cards.add(new Card(cardType.THREE));
                    cards.add(new Card(cardType.FOUR));
                default: continue; // no type has 7 of each
            }
        }
        shuffle();
    }
    public PackDraw(Pack p){
        cards = new ArrayList<Card>(p.cards);
    }
}
class PackDiscard extends Pack{ // discard pack in game
}
class PackHand extends Pack{ // player pack in game
    
}