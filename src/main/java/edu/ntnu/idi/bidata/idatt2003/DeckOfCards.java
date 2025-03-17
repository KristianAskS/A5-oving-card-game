package edu.ntnu.idi.bidata.idatt2003;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents a deck of cards.
 *
 * @author kristianselmer
 */
public class DeckOfCards {

  private static final char[] SUITS = {'H', 'D', 'C', 'S'};
  private final List<PlayingCard> cards;


  /**
   * Creates a new DeckOfCards instance.
   */
  public DeckOfCards() {
    cards = new ArrayList<>();

    for (char suit : SUITS) {
      for (int face = 1; face <= 13; face++) {
        cards.add(new PlayingCard(suit, face));
      }
    }


  }

  /**
   * Deals a specified amount of cards from the deck.
   *
   * @param amount The amount of cards to deal.
   * @return A list of dealt cards.
   * @throws IllegalArgumentException if the amount is not between 1 and 52.
   */
  public List<PlayingCard> dealHand(int amount) {
    if (amount < 1 || amount > 52) {
      throw new IllegalArgumentException("Amount must be between 1 and 52");
    }
    List<PlayingCard> dealtCards = new ArrayList<>();

    // shuffle the deck
    Collections.shuffle(cards, new Random());

    return cards.stream().limit(amount).collect(Collectors.toList());
  }

  /**
   * Returns the number of cards in the deck.
   *
   * @return The number of cards in the deck.
   */
  public int getCardCount() {
    return cards.size();
  }
}
