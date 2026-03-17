package questions.lld.Shoe.Card;

import questions.lld.Shoe.Card.Enums.Rank;
import questions.lld.Shoe.Card.Enums.Suit;

public class Joker implements ICard {
    
    public Joker() {
    }

    @Override
    public Rank getRank() {
        return null;
    }

    @Override
    public Suit getSuit() {
        return null;
    }

    @Override
    public String toString() {
        return "Joker";
    }
}
