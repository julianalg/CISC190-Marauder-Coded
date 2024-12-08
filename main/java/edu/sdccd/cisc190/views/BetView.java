package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.machines.*;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import static edu.sdccd.cisc190.views.SlotMachineView.slotMachine;

public class BetView extends Application {

    private static final String FONT_FAMILY = "Verdana";
    private static final String BUTTON_STYLE = "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
            "-fx-text-fill: black;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10px 20px;";
    private static final String HOVER_BUTTON_STYLE = "-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 10px 20px;";
    private static final String ERROR_MESSAGE = "Invalid bet amount! Please enter a valid number.";

    static int betAmt;
    private static final Label maxBet = new Label();
    private static final Label minBet = new Label();
    private static final Label returnAmount = new Label();

    @Override
    public void start(Stage primaryStage) {
        // Placeholder for launching the window
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showWindow(Stage primaryStage, MainMenuView.SlotOptions selectedMachine) {
        primaryStage.setTitle("Casino - Place Your Bet");

        // Set the onCloseRequest handler
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads();
            Platform.exit();
        });

        // Initialize selected slot machine
        slotMachine = initializeSlotMachine(selectedMachine);

        // Create components
        VBox layout = createBetLayout(primaryStage, selectedMachine);

        // Set up the scene and display it on the primary stage
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Slot initializeSlotMachine(MainMenuView.SlotOptions selectedMachine) {
        switch (selectedMachine) {
            case HONDA_TRUNK: return new HondaTrunk();
            case TREASURE_SPINS: return new TreasureSpins();
            case MEGA_MOOLAH: return new MegaMoolah();
            case RAINBOW_RICHES: return new RainbowRiches();
            default: return new DiamondDash();
        }
    }

    private static VBox createBetLayout(Stage primaryStage, MainMenuView.SlotOptions selectedMachine) {
        // Create prompt label for bet amount
        Label nameLabel = new Label("How much do you want to bet?");
        nameLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.GOLD);

        // Create labels for slot information
        SlotMachineView.infoSetText(maxBet, minBet, returnAmount);

        // Create text field for bet amount
        TextField numericTextField = createBetTextField();

        // Create buttons
        Button mainMenu = createStyledButton("Main Menu", primaryStage);
        Button submitButton = createSubmitButton(primaryStage, numericTextField, selectedMachine);

        // Layout for slot information
        HBox slotInformation = new HBox(10, maxBet, minBet, returnAmount);
        slotInformation.setAlignment(Pos.CENTER);

        // Arrange all elements in a vertical layout
        VBox layout = new VBox(20);
        layout.getChildren().addAll(nameLabel, slotInformation, numericTextField, submitButton, mainMenu);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #660000);" +
                "-fx-padding: 30px;");

        return layout;
    }

    private static TextField createBetTextField() {
        TextField numericTextField = new TextField();
        numericTextField.setPromptText("Enter numbers only");
        numericTextField.setPrefWidth(250);
        numericTextField.setStyle("-fx-background-color: #333333; -fx-text-fill: white; " +
                "-fx-prompt-text-fill: #aaaaaa; -fx-background-radius: 10; -fx-padding: 10px;");

        numericTextField.textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numericTextField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        return numericTextField;
    }

    private static Button createStyledButton(String text, Stage primaryStage) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle(BUTTON_STYLE);
        button.setOnMouseEntered(_ -> button.setStyle(HOVER_BUTTON_STYLE));
        button.setOnMouseExited(_ -> button.setStyle(BUTTON_STYLE));

        if ("Main Menu".equals(text)) {
            button.setOnAction(_ -> MainMenuView.setupWindow(primaryStage));
        }

        return button;
    }

    private static Button createSubmitButton(Stage primaryStage, TextField numericTextField, MainMenuView.SlotOptions selectedMachine) {
        Button submitButton = createStyledButton("Place Bet", primaryStage);

        submitButton.setOnAction(_ -> {
            if (!numericTextField.getText().isEmpty()) {
                try {
                    betAmt = Integer.parseInt(numericTextField.getText());
                    primaryStage.close();

                    // Open the SlotMachineView with the bet amount
                    Stage newWindow = new Stage();
                    SlotMachineView.showWindow(newWindow, betAmt, selectedMachine);
                } catch (NumberFormatException e) {
                    showErrorMessage(primaryStage);
                }
            }
        });

        return submitButton;
    }

    private static void showErrorMessage(Stage primaryStage) {
        Label errorLabel = new Label(ERROR_MESSAGE);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 14));

        // Display the error message (you can adjust where and how it appears)
        VBox layout = new VBox(20, errorLabel);
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
