package questions.lld;

import questions.lld.Shoe.Shoe;
import questions.lld.Shoe.Card.ICard;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoeTest {
    
    @Test
    public void testShoeCreationAndBasicOperations() {
        System.out.println("=== Shoe of Cards Test ===\n");

        // Create a shoe with 2 standard decks and 2 jokers per deck
        Shoe shoe = Shoe.builder()
                .withDecks(2)
                .withJokers(2)
                .build();

        // Verify shoe has correct number of cards
        // 2 decks × 52 cards + 2 decks × 2 jokers = 108 cards
        assertEquals(108, shoe.remainingCards(), 
            "Shoe should have 108 cards (2 standard decks + 4 jokers)");
        System.out.println("Created shoe with " + shoe.remainingCards() + " cards");
        System.out.println("(2 decks × 52 cards + 2 decks × 2 jokers = 108 cards)\n");

        // Shuffle the shoe
        shoe.shuffle();
        System.out.println("Shuffled shoe\n");
        assertEquals(108, shoe.remainingCards(), 
            "Shoe should still have 108 cards after shuffle");

        // Deal 5 cards
        System.out.println("Dealing 5 cards:");
        for (int i = 0; i < 5; i++) {
            ICard card = shoe.deal();
            assertNotNull(card, "Card should not be null");
            System.out.println("  " + (i + 1) + ". " + card);
        }

        // Verify remaining cards
        assertEquals(103, shoe.remainingCards(), 
            "Shoe should have 103 cards remaining after dealing 5");
        System.out.println("\nRemaining cards: " + shoe.remainingCards());
    }
}
