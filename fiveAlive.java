import java.io.*;
import java.util.*;
public class fiveAlive{
    enum cardType{
        ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, DRAW_1, DRAW_2, PASS_ME, REVERSE, SKIP, EQ_21, EQ_10, EQ_0, REDEAL, BOMB
    }
    public static class card{
        private cardType type;
        public cardType getCardType(){ return type; }
        public void setCardType(cardType type){ this.type = type; }
    }
    public static void main(String args[]){
        System.out.println("Test!");
    }
}