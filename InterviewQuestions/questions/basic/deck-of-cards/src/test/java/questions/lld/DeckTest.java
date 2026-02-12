package questions.lld;

import questions.lld.Deck.Deck;
import questions.lld.Deck.Card.ICard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    
    @Test
    public void testDeckCreationAndBasicOperations() {
        System.out.println("=== Deck of Cards Test ===\n");

        // Create a deck with 2 standard decks and 2 jokers per deck
        Deck deck = Deck.builder()
                .withDecks(2)
                .withJokers(2)
                .build();

        // Verify deck has correct number of cards
        // 2 decks × 52 cards + 2 decks × 2 jokers = 108 cards
        assertEquals(108, deck.remainingCards(), 
            "Deck should have 108 cards (2 standard decks + 4 jokers)");
        System.out.println("Created deck with " + deck.remainingCards() + " cards");
        System.out.println("(2 decks × 52 cards + 2 decks × 2 jokers = 108 cards)\n");

        // Shuffle the deck
        deck.shuffle();
        System.out.println("Shuffled deck\n");
        assertEquals(108, deck.remainingCards(), 
            "Deck should still have 108 cards after shuffle");

        // Deal 5 cards
        System.out.println("Dealing 5 cards:");
        for (int i = 0; i < 5; i++) {
            ICard card = deck.deal();
            assertNotNull(card, "Card should not be null");
            System.out.println("  " + (i + 1) + ". " + card);
        }

        // Verify remaining cards
        assertEquals(103, deck.remainingCards(), 
            "Deck should have 103 cards remaining after dealing 5");
        System.out.println("\nRemaining cards: " + deck.remainingCards());
    }
}
