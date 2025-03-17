package edu.ntnu.idi.bidata.idatt2003;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CardHandTest {

  private CardHand handWithFlush;
  private CardHand mixedHand;

  @BeforeEach
  void setUp() {
    handWithFlush = new CardHand(List.of(
        new PlayingCard('H', 2),
        new PlayingCard('H', 5),
        new PlayingCard('H', 7),
        new PlayingCard('H', 10),
        new PlayingCard('H', 12)
    ));

    mixedHand = new CardHand(List.of(
        new PlayingCard('H', 4),
        new PlayingCard('D', 12),
        new PlayingCard('C', 3),
        new PlayingCard('D', 11),
        new PlayingCard('S', 1)
    ));
  }

  @Test
  void testConstructor_NullOrEmptyList_ThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new CardHand(null));
    assertThrows(IllegalArgumentException.class, () -> new CardHand(List.of()));
  }

  @Test
  void testGetCards_ReturnsCorrectCards() {
    assertEquals(5, handWithFlush.getCards().size());
    assertEquals(5, mixedHand.getCards().size());
  }

  @Test
  void testHasFlush() {
    assertTrue(handWithFlush.hasFlush());
    assertFalse(mixedHand.hasFlush());
  }

  @Test
  void testGetCardsBySuit() {
    List<PlayingCard> hearts = mixedHand.getCardsBySuit('H');
    assertEquals(1, hearts.size());
    assertEquals(4, hearts.get(0).getFace());
  }

  @Test
  void testGetTotalValue() {
    assertEquals(36, handWithFlush.getTotalValue()); // 2 + 5 + 7 + 10 + 12 = 36
    assertEquals(31, mixedHand.getTotalValue()); // 4 + 12 + 3 + 11 + 1 = 31
  }

  @Test
  void testContainsCard() {
    assertTrue(mixedHand.containsCard('D', 12));
    assertFalse(mixedHand.containsCard('H', 10));
  }

  @Test
  void testToString() {
    assertEquals("H2 H5 H7 H10 H12", handWithFlush.toString());
    assertEquals("H4 D12 C3 D11 S1", mixedHand.toString());
  }
}