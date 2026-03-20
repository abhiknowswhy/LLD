package questions.lld.Shoe.Card;

import questions.lld.Shoe.Card.Enums.Rank;
import questions.lld.Shoe.Card.Enums.Suit;

public interface ICard {
    Rank getRank();
    
    Suit getSuit();

    String toString();
}