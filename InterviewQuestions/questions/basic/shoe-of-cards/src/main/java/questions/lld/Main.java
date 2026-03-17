package questions.lld;

import questions.lld.Shoe.Shoe;
import questions.lld.Shoe.Card.ICard;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Shoe of Cards Test ===\n");

        // Create a shoe with 2 standard decks and 2 jokers per deck
        Shoe shoe = Shoe.builder()
                .withDecks(2)
                .withJokers(2)
                .build();

        System.out.println("Created shoe with " + shoe.remainingCards() + " cards");
        System.out.println("(2 decks × 52 cards + 2 decks × 2 jokers = 108 cards)\n");

        // Shuffle the shoe
        shoe.shuffle();
        System.out.println("Shuffled shoe\n");

        // Deal 5 cards
        System.out.println("Dealing 5 cards:");
        for (int i = 0; i < 5; i++) {
            ICard card = shoe.deal();
            System.out.println("  " + (i + 1) + ". " + card);
        }

        System.out.println("\nRemaining cards: " + shoe.remainingCards());
    }
}