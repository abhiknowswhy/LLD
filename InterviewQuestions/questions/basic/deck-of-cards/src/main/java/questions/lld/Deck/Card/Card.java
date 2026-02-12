package questions.lld.Deck.Card;

import questions.lld.Deck.Card.Enums.Rank;
import questions.lld.Deck.Card.Enums.Suit;

public class Card implements ICard {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public Rank getRank() {
        return rank;
    }

    @Override
    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank.name() + " of " + suit.getName();
    }
}

