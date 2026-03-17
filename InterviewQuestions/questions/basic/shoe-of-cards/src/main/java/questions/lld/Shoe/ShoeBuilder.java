package questions.lld.Shoe;

/**
 * Builder class for constructing Shoe instances with fluent API
 */
public class ShoeBuilder {
    private int numDecks = 1;
    private int numJokers = 0;

    /**
     * Set the number of standard decks to include
     * @param numDecks number of decks (default: 1)
     * @return this shoe builder for chaining
     */
    public ShoeBuilder withDecks(int numDecks) {
        this.numDecks = numDecks;
        return this;
    }

    /**
     * Set the number of jokers per deck
     * @param numJokersPerDeck number of jokers per deck (default: 0)
     * @return this shoe builder for chaining
     */
    public ShoeBuilder withJokers(int numJokersPerDeck) {
        this.numJokers = numJokersPerDeck;
        return this;
    }

    /**
     * Build and return the Shoe instance
     * @return configured Shoe
     */
    public Shoe build() {
        return new Shoe(numDecks, numJokers);
    }
}
