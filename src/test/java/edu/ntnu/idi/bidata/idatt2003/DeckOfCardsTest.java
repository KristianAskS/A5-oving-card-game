package edu.ntnu.idi.bidata.idatt2003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckOfCardsTest {

  private DeckOfCards deck;

  @BeforeEach
  void setUp() {
    deck = new DeckOfCards();
  }

  @Test
  void testDeckInitialization() {
    assertEquals(52, deck.getCardCount(), "Deck should contain 52 cards at initialization.");
  }

  @Test
  void testDealHand_ValidAmount() {
    List<PlayingCard> hand = deck.dealHand(5);
    assertEquals(5, hand.size(), "Dealt hand should contain 5 cards.");
  }

  @Test
  void testDealHand_InvalidAmount() {
    assertThrows(IllegalArgumentException.class, () -> deck.dealHand(0),
        "Should throw exception for amount less than 1.");
    assertThrows(IllegalArgumentException.class, () -> deck.dealHand(53),
        "Should throw exception for amount greater than 52.");
  }

  @Test
  void testDealHand_UniqueCards() {
    List<PlayingCard> hand1 = deck.dealHand(5);
    List<PlayingCard> hand2 = deck.dealHand(5);
    assertNotEquals(hand1, hand2, "Two dealt hands should not be identical due to shuffling.");
  }

  @Test
  void testDeckShuffleEffectiveness() {
    List<PlayingCard> firstDeal = deck.dealHand(10);
    List<PlayingCard> secondDeal = deck.dealHand(10);
    assertNotEquals(firstDeal, secondDeal, "Shuffling should produce different card orders.");
  }
}