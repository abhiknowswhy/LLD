package questions.lld.Deck.Card;

import questions.lld.Deck.Card.Enums.Rank;
import questions.lld.Deck.Card.Enums.Suit;

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
