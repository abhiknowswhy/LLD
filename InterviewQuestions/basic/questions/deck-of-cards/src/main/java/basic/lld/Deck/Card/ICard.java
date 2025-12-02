package basic.lld.Deck.Card;

import basic.lld.Deck.Card.Enums.Rank;
import basic.lld.Deck.Card.Enums.Suit;

public interface ICard {
    Rank getRank();
    
    Suit getSuit();
}