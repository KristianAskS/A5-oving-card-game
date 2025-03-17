package edu.ntnu.idi.bidata.idatt2003;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Represents a hand of playing cards. Provides methods for analyzing the hand.
 *
 * @author kristianselmer
 */
public class CardHand {

  private final List<PlayingCard> cards;

  /**
   * Creates a CardHand instance with the given list of cards.
   *
   * @param cards List of PlayingCard objects representing the hand.
   */
  public CardHand(List<PlayingCard> cards) {
    if (cards == null || cards.isEmpty()) {
      throw new IllegalArgumentException("A hand cannot be empty.");
    }
    this.cards = cards;
  }

  /**
   * Returns the cards in the hand.
   *
   * @return A list of PlayingCard objects.
   */
  public List<PlayingCard> getCards() {
    return cards;
  }

  /**
   * Checks if the hand contains a flush (all cards have the same suit).
   *
   * @return true if the hand is a flush, false otherwise.
   */
  public boolean hasFlush() {
    return cards.stream()
        .map(PlayingCard::getSuit)
        .distinct()
        .count() == 1;
  }

  /**
   * Returns all cards of a specific suit.
   *
   * @param suit The suit to filter by ('H', 'D', 'C', 'S').
   * @return A list of PlayingCard objects matching the suit.
   */
  public List<PlayingCard> getCardsBySuit(char suit) {
    return cards.stream()
        .filter(card -> card.getSuit() == suit)
        .collect(Collectors.toList());
  }

  /**
   * Returns the total sum of all card values in the hand. Ace is counted as 1, and other cards
   * retain their face value.
   *
   * @return The sum of all card values.
   */
  public int getTotalValue() {
    return cards.stream()
        .mapToInt(PlayingCard::getFace)
        .sum();
  }

  /**
   * Checks if the hand contains a specific card.
   *
   * @param suit The suit of the card ('H', 'D', 'C', 'S').
   * @param face The face value of the card (1-13).
   * @return true if the card is found in the hand, false otherwise.
   */
  public boolean containsCard(char suit, int face) {
    return cards.stream()
        .anyMatch(card -> card.getSuit() == suit && card.getFace() == face);
  }

  /**
   * Returns a string representation of the hand in the format "H4 H12 C3 D11 S1".
   *
   * @return A formatted string representing the hand.
   */
  @Override
  public String toString() {
    return cards.stream()
        .map(PlayingCard::getAsString)
        .collect(Collectors.joining(" "));
  }

  /**
   * Groups the cards by suit and returns a formatted string.
   *
   * @return A string showing how many cards of each suit are present.
   */
  public String getCardDistribution() {
    Map<Character, Long> suitCounts = cards.stream()
        .map(PlayingCard::getSuit)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    return suitCounts.entrySet().stream()
        .map(entry -> entry.getKey() + ": " + entry.getValue())
        .collect(Collectors.joining(", "));
  }
}