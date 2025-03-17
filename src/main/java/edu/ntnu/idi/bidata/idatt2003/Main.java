package edu.ntnu.idi.bidata.idatt2003;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  private DeckOfCards deck;
  private CardHand currentHand;

  private FlowPane cardsDisplay;  // Where the dealt “cards” will be shown
  private TextField sumField;
  private TextField heartsField;
  private TextField flushField;
  private TextField queenSpadesField;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    deck = new DeckOfCards();

    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));

    cardsDisplay = new FlowPane();
    cardsDisplay.setPadding(new Insets(10));

    cardsDisplay.setHgap(10);
    cardsDisplay.setVgap(10);
    root.setLeft(cardsDisplay);

    VBox bottomBox = new VBox(10);
    bottomBox.setPadding(new Insets(10));

    HBox buttonRow = new HBox(10);
    buttonRow.setAlignment(Pos.CENTER_LEFT);

    Button dealButton = new Button("Deal Hand");
    dealButton.setOnAction(e -> dealNewHand());

    Button checkButton = new Button("Check Hand");
    checkButton.setOnAction(e -> checkHand());

    buttonRow.getChildren().addAll(dealButton, checkButton);

    HBox infoRow = new HBox(10);
    infoRow.setAlignment(Pos.CENTER_LEFT);

    sumField = new TextField();
    sumField.setEditable(false);
    sumField.setPrefWidth(50);
    Label sumLabel = new Label("Sum:");

    heartsField = new TextField();
    heartsField.setEditable(false);
    heartsField.setPrefWidth(100);
    Label heartsLabel = new Label("Hearts:");

    flushField = new TextField();
    flushField.setEditable(false);
    flushField.setPrefWidth(50);
    Label flushLabel = new Label("Flush:");

    queenSpadesField = new TextField();
    queenSpadesField.setEditable(false);
    queenSpadesField.setPrefWidth(50);
    Label queenLabel = new Label("Q♠:");

    infoRow.getChildren().addAll(
        sumLabel, sumField,
        heartsLabel, heartsField,
        flushLabel, flushField,
        queenLabel, queenSpadesField
    );

    bottomBox.getChildren().addAll(buttonRow, infoRow);
    root.setBottom(bottomBox);

    dealNewHand();

    Scene scene = new Scene(root, 800, 400);
    primaryStage.setTitle("Card Game - Minimal");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void dealNewHand() {
    List<PlayingCard> dealt = deck.dealHand(5);
    currentHand = new CardHand(dealt);

    cardsDisplay.getChildren().clear();

    HashMap<Character, String> suitColor = new HashMap<>();
    suitColor.put('H', "#FF69B4");
    suitColor.put('S', "#1E90FF");
    suitColor.put('D', "#FFA500");
    suitColor.put('C', "#556B2F");

    HashMap<Integer, String> faceToValue = new HashMap<>();
    faceToValue.put(1, "A");
    faceToValue.put(11, "J");
    faceToValue.put(12, "Q");
    faceToValue.put(13, "K");

    HashMap<Character, String> suitToSymbol = new HashMap<>();
    suitToSymbol.put('H', "♥");
    suitToSymbol.put('D', "♦");
    suitToSymbol.put('C', "♣");
    suitToSymbol.put('S', "♠");

    for (PlayingCard card : currentHand.getCards()) {
      String color = suitColor.getOrDefault(card.getSuit(), "#000000");
      String faceStr = faceToValue.getOrDefault(card.getFace(), String.valueOf(card.getFace()));
      String suitSymbol = suitToSymbol.getOrDefault(card.getSuit(), String.valueOf(card.getSuit()));

      Label label = new Label(faceStr + suitSymbol);
      label.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: white;");

      VBox cardBox = new VBox(label);
      cardBox.setAlignment(Pos.CENTER);
      cardBox.setPrefSize(80, 120);
      cardBox.setStyle("-fx-background-color: " + color + ";");

      cardsDisplay.getChildren().add(cardBox);
    }

    sumField.clear();
    heartsField.clear();
    flushField.clear();
    queenSpadesField.clear();
  }

  private void checkHand() {
    if (currentHand == null) {
      return;
    }

    sumField.setText(String.valueOf(currentHand.getTotalValue()));

    // Hearts
    List<PlayingCard> hearts = currentHand.getCardsBySuit('H');
    if (hearts.isEmpty()) {
      heartsField.setText("No hearts");
    } else {
      String heartsText = hearts.stream()
          .map(PlayingCard::getAsString)
          .collect(Collectors.joining(" "));
      heartsField.setText(heartsText);
    }

    flushField.setText(currentHand.hasFlush() ? "Yes" : "No");

    boolean hasQSpades = currentHand.containsCard('S', 12);
    queenSpadesField.setText(hasQSpades ? "Yes" : "No");
  }
}