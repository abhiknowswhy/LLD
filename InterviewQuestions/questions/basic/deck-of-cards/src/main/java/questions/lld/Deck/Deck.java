package questions.lld.Deck;

import questions.lld.Deck.Card.Card;
import questions.lld.Deck.Card.ICard;
import questions.lld.Deck.Card.Joker;
import questions.lld.Deck.Card.Enums.Rank;
import questions.lld.Deck.Card.Enums.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<ICard> cards;
    private final int numDecks;
    private final int numJokersPerDeck;

    /**
     * Package-private constructor - use DeckBuilder to create instances
     */
    public Deck(int numDecks, int numJokersPerDeck) {
        this.numDecks = numDecks;
        this.numJokersPerDeck = numJokersPerDeck;
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    /**
     * Initialize deck with all standard cards and jokers
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
     * Add jokers to the deck (private - only called during initialization)
     * @param count number of jokers to add
     */
    private void addJokers(int count) {
        for (int i = 0; i < count; i++) {
            cards.add(new Joker());
        }
    }

    /**
     * Shuffle the deck
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Deal a card from the top of the deck
     * @return the dealt card, or null if deck is empty
     */
    public ICard deal() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(0);
    }

    /**
     * Burn a card from the top of the deck (discard without dealing)
     * @return the burned card, or null if deck is empty
     */
    public ICard burn() {
        return deal();
    }

    /**
     * Get remaining cards in the deck
     * @return number of cards left in the deck
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
     * Reset deck to initial state
     */
    public void reset() {
        cards.clear();
        initializeDeck();
    }

    /**
     * Get a DeckBuilder for fluent deck creation
     * @return new DeckBuilder instance
     */
    public static DeckBuilder builder() {
        return new DeckBuilder();
    }
}
