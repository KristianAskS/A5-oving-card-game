package edu.ntnu.idi.bidata.idatt2003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the PlayingCard class.
 */
class PlayingCardTest {

  @Test
  void testValidPlayingCardCreation() {
    PlayingCard card = new PlayingCard('H', 10);
    assertEquals('H', card.getSuit(), "Suit should be H");
    assertEquals(10, card.getFace(), "Face should be 10");
  }

  @Test
  void testInvalidSuitThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new PlayingCard('X', 5),
        "Invalid suit should throw an exception");
  }

  @Test
  void testInvalidFaceThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new PlayingCard('H', 0),
        "Face below 1 should throw an exception");
    assertThrows(IllegalArgumentException.class, () -> new PlayingCard('D', 14),
        "Face above 13 should throw an exception");
  }

  @Test
  void testGetAsString() {
    PlayingCard card = new PlayingCard('S', 1);
    assertEquals("S1", card.getAsString(), "Card string representation should be S1");
  }

  @Test
  void testEqualsAndHashCode() {
    PlayingCard card1 = new PlayingCard('C', 7);
    PlayingCard card2 = new PlayingCard('C', 7);
    PlayingCard card3 = new PlayingCard('D', 7);

    assertEquals(card1, card2, "Cards with the same suit and face should be equal");
    assertNotEquals(card1, card3, "Cards with different suits should not be equal");
    assertEquals(card1.hashCode(), card2.hashCode(), "Hash codes should match for equal cards");
  }
}