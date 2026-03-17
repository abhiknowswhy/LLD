package questions.lld.Shoe;

import questions.lld.Shoe.Card.Card;
import questions.lld.Shoe.Card.ICard;
import questions.lld.Shoe.Card.Joker;
import questions.lld.Shoe.Card.Enums.Rank;
import questions.lld.Shoe.Card.Enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {
    private final List<ICard> cards;
    private final int numDecks;
    private final int numJokersPerDeck;

    /**
     * Package-private constructor - use ShoeBuilder to create instances
     */
    public Shoe(int numDecks, int numJokersPerDeck) {
        this.numDecks = numDecks;
        this.numJokersPerDeck = numJokersPerDeck;
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    /**
     * Initialize shoe with all standard cards and jokers
     */
    private void initializeDeck() {
        for (int deckNum = 0; deckNum < numDecks; deckNum++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(rank, suit));
                }
            }
        }
        addJokers(numJokersPerDeck * numDecks);
    }

    /**
     * Add jokers to the shoe (private - only called during initialization)
     * @param count number of jokers to add
     */
    private void addJokers(int count) {
        for (int i = 0; i < count; i++) {
            cards.add(new Joker());
        }
    }

    /**
     * Shuffle the shoe
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deal a card from the top of the shoe
     * @return the dealt card, or null if shoe is empty
     */
    public ICard deal() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    /**
     * Burn a card from the top of the shoe (discard without dealing)
     * @return the burned card, or null if shoe is empty
     */
    public ICard burn() {
        return deal();
    }

    /**
     * Get remaining cards in the shoe
     * @return number of cards left in the shoe
     */
    public int remainingCards() {
        return cards.size();
    }

    /**
     * Get all remaining cards
     * @return copy of the cards list
     */
    public List<ICard> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Reset shoe to initial state
     */
    public void reset() {
        cards.clear();
        initializeDeck();
    }

    /**
     * Get a ShoeBuilder for fluent shoe creation
     * @return new ShoeBuilder instance
     */
    public static ShoeBuilder builder() {
        return new ShoeBuilder();
    }
}
