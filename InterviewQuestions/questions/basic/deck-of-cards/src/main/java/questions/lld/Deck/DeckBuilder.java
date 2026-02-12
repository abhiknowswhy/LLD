package questions.lld.Deck;

/**
 * Builder class for constructing Deck instances with fluent API
 */
public class DeckBuilder {
    private int numDecks = 1;
    private int numJokers = 0;

    /**
     * Set the number of standard decks to include
     * @param numDecks number of decks (default: 1)
     * @return this builder for chaining
     */
    public DeckBuilder withDecks(int numDecks) {
        this.numDecks = numDecks;
        return this;
    }

    /**
     * Set the number of jokers per deck
     * @param numJokersPerDeck number of jokers per deck (default: 0)
     * @return this builder for chaining
     */
    public DeckBuilder withJokers(int numJokersPerDeck) {
        this.numJokers = numJokersPerDeck;
        return this;
    }

    /**
     * Build and return the Deck instance
     * @return configured Deck
     */
    public Deck build() {
        return new Deck(numDecks, numJokers);
    }
}
