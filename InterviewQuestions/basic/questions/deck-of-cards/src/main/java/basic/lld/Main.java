package basic.lld;

import basic.lld.Deck.Deck;
import basic.lld.Deck.Card.ICard;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Deck of Cards Test ===\n");

        // Create a deck with 2 standard decks and 2 jokers per deck
        Deck deck = Deck.builder()
                .withDecks(2)
                .withJokers(2)
                .build();

        System.out.println("Created deck with " + deck.remainingCards() + " cards");
        System.out.println("(2 decks × 52 cards + 2 decks × 2 jokers = 108 cards)\n");

        // Shuffle the deck
        deck.shuffle();
        System.out.println("Shuffled deck\n");

        // Deal 5 cards
        System.out.println("Dealing 5 cards:");
        for (int i = 0; i < 5; i++) {
            ICard card = deck.deal();
            System.out.println("  " + (i + 1) + ". " + card);
        }

        System.out.println("\nRemaining cards: " + deck.remainingCards());
    }
}