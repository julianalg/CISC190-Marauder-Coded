package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.machines.*;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
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
import javafx.application.Platform;

import static edu.sdccd.cisc190.views.SlotMachineView.slotMachine;

/**
 * The BetView class represents a JavaFX view for users to place their bets on a selected slot machine.
 * It allows users to enter a bet amount, displays slot machine limits and return information,
 * and navigates to the SlotMachineView or MainMenuView.
 */
public class BetView extends Application {
    /**
     * The amount the user chooses to bet.
     */
    static int betAmt;

    // Labels for displaying slot machine betting limits and return amounts
    private static final Label maxBet = new Label();
    private static final Label minBet = new Label();
    private static final Label returnAmount = new Label();

    /**
     * The entry point for JavaFX applications.
     * This method is overridden but not used directly in this class.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        // TODO: Leave this empty for now. It’s used when JavaFX starts the app. No need to touch it!
    }

    /**
     * Main method for launching the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args); // TODO: This runs the JavaFX app. Don't change it!
    }

    /**
     * Displays the betting window where users can place a bet for a selected slot machine.
     *
     * @param primaryStage    The primary stage for the application.
     * @param selectedMachine The selected slot machine type.
     */
    public static void showWindow(Stage primaryStage, MainMenuView.SlotOptions selectedMachine) {
        primaryStage.setTitle("Casino - Place Your Bet"); // TODO: Set the title of the window to "Place Your Bet".

        // Set the onCloseRequest handler to quit the application when the window is closed
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads(); // TODO: Stop all running threads when the window closes.
            Platform.exit(); // TODO: Exit the app gracefully.
        });

        // Initialize the selected slot machine based on user choice
        // TODO: This part checks which slot machine was selected and sets it.
        switch (selectedMachine) {
            case HONDA_TRUNK -> slotMachine = new HondaTrunk();
            case TREASURE_SPINS -> slotMachine = new TreasureSpins();
            case MEGA_MOOLAH -> slotMachine = new MegaMoolah();
            case RAINBOW_RICHES -> slotMachine = new RainbowRiches();
            default -> slotMachine = new DiamondDash();
        }

        // Create a styled label prompting the user for their bet amount
        Label nameLabel = new Label("How much do you want to bet?"); // TODO: This is a label asking the user to enter a bet.
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18)); // TODO: Set the font and style for the label.
        nameLabel.setTextFill(Color.GOLD); // TODO: Make the text color gold.

        // Set up labels to display slot machine limits and expected return
        SlotMachineView.infoSetText(maxBet, minBet, returnAmount); // TODO: Call this function to show max bet, min bet, and return amount.

        // Create a text field for the user to enter their bet amount
        TextField numericTextField = new TextField();
        numericTextField.setPromptText("Enter numbers only"); // TODO: Display hint text for the user.
        numericTextField.setPrefWidth(250); // TODO: Set the preferred width of the text field.
        numericTextField.setStyle(
                "-fx-background-color: #333333; " + // TODO: Set background color.
                        "-fx-text-fill: white; " + // TODO: Set text color to white.
                        "-fx-prompt-text-fill: #aaaaaa; " + // TODO: Set prompt text color to grey.
                        "-fx-background-radius: 10; " + // TODO: Round the corners of the text field.
                        "-fx-padding: 10px;" // TODO: Add padding to the text field.
        );

        // Restrict the input to numeric values only
        numericTextField.textProperty().addListener((_, _, newValue) -> {
            if (!newValue.matches("\\d*")) { // TODO: Only allow digits.
                numericTextField.setText(newValue.replaceAll("\\D", "")); // TODO: Remove non-digit characters.
            }
        });

        // Create the Main Menu button and attach an action to return to the MainMenuView
        Button mainMenu = createStyledButton("Main Menu"); // TODO: Create a styled button for "Main Menu".
        mainMenu.setOnAction(_ -> MainMenuView.setupWindow(primaryStage)); // TODO: Set action to go back to the main menu.

        // Create the Place Bet button to submit the user's bet
        Button submitButton = createStyledButton("Place Bet"); // TODO: Create a styled button for "Place Bet".
        submitButton.setOnAction(_ -> {
            if (!numericTextField.getText().isEmpty()) { // TODO: Check if the user has entered a bet.
                betAmt = Integer.parseInt(numericTextField.getText()); // TODO: Convert the bet amount from text to number.
                primaryStage.close(); // TODO: Close the current window.

                // Open the SlotMachineView with the bet amount and selected slot machine
                Stage newWindow = new Stage(); // TODO: Create a new window for SlotMachineView.
                SlotMachineView.showWindow(newWindow, betAmt, selectedMachine); // TODO: Show the SlotMachineView with the selected machine and bet amount.
            }
        });

        // Create a horizontal box to display slot machine information (max/min bet and return amount)
        HBox slotInformation = new HBox(10, maxBet, minBet, returnAmount); // TODO: Arrange the labels for bet info in a horizontal line.
        slotInformation.setAlignment(Pos.CENTER); // TODO: Center the information horizontally.

        // Arrange all elements in a vertical layout
        VBox layout = new VBox(20); // TODO: Create a vertical layout with 20px space between elements.
        layout.getChildren().addAll(nameLabel, slotInformation, numericTextField, submitButton, mainMenu); // TODO: Add all UI elements to the layout.
        layout.setAlignment(Pos.CENTER); // TODO: Center everything in the layout.
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000000, #660000);" + // TODO: Add a gradient background.
                        "-fx-padding: 30px;" // TODO: Add padding to the layout.
        );

        // Set up the scene and display it on the primary stage
        Scene scene = new Scene(layout, 400, 300); // TODO: Set the scene size.
        primaryStage.setScene(scene); // TODO: Set the scene for the primary stage.
        primaryStage.show(); // TODO: Show the primary stage to the user.
    }

    /**
     * Creates a styled button with hover effects.
     *
     * @param text The text to display on the button.
     * @return A styled Button object.
     */
    private static Button createStyledButton(String text) {
        Button button = new Button(text); // TODO: Create a button with the given text.
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // TODO: Set the font for the button.
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" + // TODO: Set a gradient background color for the button.
                        "-fx-text-fill: black;" + // TODO: Set the text color to black.
                        "-fx-background-radius: 10;" + // TODO: Round the corners of the button.
                        "-fx-padding: 10px 20px;" // TODO: Add padding to the button.
        );

        // Add hover effects for better user interaction
        button.setOnMouseEntered(_ -> button.setStyle( // TODO: Change style when the mouse enters the button.
                "-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600);" + // TODO: Change to a darker gradient.
                        "-fx-text-fill: white;" + // TODO: Change text color to white.
                        "-fx-background-radius: 10;" + // TODO: Keep the rounded corners.
                        "-fx-padding: 10px 20px;" // TODO: Keep the padding the same.
        ));
        button.setOnMouseExited(_ -> button.setStyle( // TODO: Reset button style when mouse exits.
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" + // TODO: Reset to the original gradient.
                        "-fx-text-fill: black;" + // TODO: Reset text color to black.
                        "-fx-background-radius: 10;" + // TODO: Keep rounded corners.
                        "-fx-padding: 10px 20px;" // TODO: Keep the padding consistent.
        ));

        return button; // TODO: Return the styled button.
    }
}
