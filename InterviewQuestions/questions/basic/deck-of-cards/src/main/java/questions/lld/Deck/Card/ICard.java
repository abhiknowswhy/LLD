package questions.lld.Deck.Card;

import questions.lld.Deck.Card.Enums.Rank;
import questions.lld.Deck.Card.Enums.Suit;

public interface ICard {
    Rank getRank();
    
    Suit getSuit();
}