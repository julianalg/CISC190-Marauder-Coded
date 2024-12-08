package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.machines.Slot;
import edu.sdccd.cisc190.machines.*;
import edu.sdccd.cisc190.services.PlayerSavesService;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * The SlotMachineView class represents the user interface for the slot machine gameplay.
 * It displays the slot machine reels, bet information, and controls for spinning the slots,
 * changing the bet, or returning to the Main Menu.
 */
public class SlotMachineView extends Application {

    // TODO: Labels to show information for betting and gameplay. 
    // This is where we tell the player the bet and other important stuff. We like to keep it fancy!

    private static final Label betAmount = new Label();
    private static final Label maxBet = new Label();
    private static final Label minBet = new Label();
    private static final Label returnAmount = new Label();
    private static final Label slot1 = new Label("❓");
    private static final Label slot2 = new Label("❓");
    private static final Label slot3 = new Label("❓");
    private static final Label won = new Label("Spin to see!");
    private static final Label money = new Label("Balance: $%d".formatted(HumanPlayer.getInstance().getMoney()));

    // TODO: These buttons are for the player's interaction with the game. Spin, change the bet, or go back to the main menu.
    // You gotta keep the energy up with these controls, right?

    static Button spinButton = createStyledButton("Spin");
    static Button changeBet = createStyledButton("Change Bet");
    static Button mainMenu = createStyledButton("Return to Main Menu");

    // TODO: The slot machine type is being selected here. This part decides which slot machine to show. Pretty cool!
    static MainMenuView.SlotOptions machineSelect;
    static Slot slotMachine;

    /**
     * The entry point for the JavaFX application.
     *
     * @param primaryStage the primary stage for the slot machine gameplay window.
     */
    @Override
    public void start(Stage primaryStage) {
        // TODO: Show the window with default values, making sure everything's set up right.
        // Bet amount is 0 to start, and we're using the Diamond Dash machine. Let's gooooooo!
        showWindow(primaryStage, 0, MainMenuView.SlotOptions.DIAMOND_DASH);
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        launch(args); // We launching! Let's get this game started!
    }

    /**
     * Displays the slot machine gameplay window.
     *
     * @param primaryStage     the primary stage for the application.
     * @param betAmt           the initial betting amount.
     * @param selectedMachine  the type of slot machine selected.
     */
    public static void showWindow(Stage primaryStage, int betAmt, MainMenuView.SlotOptions selectedMachine) {

        // TODO: Close the game when the window is closed. Don't leave anything running, you know?
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads();
            Platform.exit();
        });

        // TODO: Select the slot machine type based on what the player picked. We keep it dynamic and fun.
        machineSelect = selectedMachine;
        switch (selectedMachine) {
            case HONDA_TRUNK -> slotMachine = new HondaTrunk();
            case TREASURE_SPINS -> slotMachine = new TreasureSpins();
            case MEGA_MOOLAH -> slotMachine = new MegaMoolah();
            case RAINBOW_RICHES -> slotMachine = new RainbowRiches();
            default -> slotMachine = new DiamondDash();
        }

        primaryStage.setTitle("Casino - Slot Machine"); // Set the title of the window. It's like the name of our concert!

        // TODO: Bet amount label setup. Make it look good, and let the player know what's up.
        betAmount.setText("You're betting: $%d".formatted(betAmt));
        betAmount.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        betAmount.setTextFill(Color.LIGHTGOLDENRODYELLOW);

        // TODO: Set the max, min, and return amount for the slot machine. The crowd's gotta know what's going on!
        infoSetText(maxBet, minBet, returnAmount);

        // TODO: Slot symbols styling, just a little magic to make the slots shine bright! These are big numbers, baby!
        slot1.setStyle("-fx-font-size: 60px;");
        slot2.setStyle("-fx-font-size: 60px;");
        slot3.setStyle("-fx-font-size: 60px;");
        slot1.setTextFill(Color.ORANGERED);
        slot2.setTextFill(Color.ORANGERED);
        slot3.setTextFill(Color.ORANGERED);

        // TODO: Winning message setup. The player will either win or lose, but we always gotta keep the excitement up.
        won.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        won.setTextFill(Color.GOLD);

        // TODO: Balance label setup. Gotta show the player how much money they have left! Like a treasure chest!
        money.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        money.setTextFill(Color.LIGHTGREEN);

        // TODO: Button actions for interacting with the game. Spin, change bet, or go back to the menu. Your choice!
        spinButton.setOnAction(_ -> spin(betAmt, primaryStage));
        changeBet.setOnAction(_ -> {
            primaryStage.close();
            BetView.showWindow(primaryStage, machineSelect);
        });
        mainMenu.setOnAction(_ -> {
            primaryStage.close();
            MainMenuView.setupWindow(primaryStage);
        });

        // TODO: Arrange labels into a nice horizontal box. We wanna keep things tight and organized.
        HBox slotInformation = new HBox(10, maxBet, minBet, returnAmount);
        slotInformation.setAlignment(Pos.CENTER);

        // TODO: Arrange the slots in a horizontal row. This is where the action happens!
        HBox slotsRow = new HBox(20, slot1, slot2, slot3);
        slotsRow.setAlignment(Pos.CENTER);

        // TODO: The main layout with all the buttons and labels. It's the stage where the magic happens.
        VBox layout = new VBox(20, betAmount, won, money, slotInformation, slotsRow, spinButton, changeBet, mainMenu);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000000, #660000);" +
                        "-fx-padding: 30px;"
        );

        // TODO: Set the scene with our layout. This is the whole vibe for the game!
        Scene scene = new Scene(layout, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // TODO: Helper method to set text for the max, min, and return labels. Making the info clear for the player.
    static void infoSetText(Label maxBet, Label minBet, Label returnAmount) {
        maxBet.setText("Max. Bet: %d".formatted(slotMachine.getMaxBet()));
        maxBet.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        minBet.setText("Min. Bet: %d".formatted(slotMachine.getMinBet()));
        minBet.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        returnAmount.setText("Return: %s".formatted(slotMachine.getReturnAmt()));
        returnAmount.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 15));
        maxBet.setTextFill(Color.RED);
        minBet.setTextFill(Color.RED);
        returnAmount.setTextFill(Color.RED);
    }

    // TODO: Handle the spin button action. This is where the magic of the game happens. Spin the reels, baby!
    private static void spin(int betAmt, Stage primaryStage) {
        // TODO: Check if the bet is valid. If not, show an alert. No way we're letting the player bet wrong!
        if (!slotMachine.canBet(betAmt)) {
            showAlert("Invalid Bet", "Your bet is outside the allowed range or exceeds your balance.");
            primaryStage.close();
            BetView.showWindow(primaryStage, machineSelect);
            return;
        }

        // TODO: Generate symbols when the player spins the machine. It's like the reels are dancing!
        String[] symbols = slotMachine.generateSpunSymbols();
        slot1.setText(symbols[0]);
        slot2.setText(symbols[1]);
        slot3.setText(symbols[2]);

        // TODO: Calculate the payout based on the spun symbols and update the player's balance. Show the money!
        int newBalance = slotMachine.calculatePayout(HumanPlayer.getInstance().getMoney(), symbols, betAmt);
        HumanPlayer.getInstance().setMoney(newBalance);

        // TODO: Check if the player won or lost. We keep it dramatic with the message!
        if (slotMachine.evaluateWinCondition(symbols) >= 2) {
            won.setText("Wow, you won!");
        } else {
            won.setText("You lost :(");
        }

        // TODO: Update the player's balance on the screen. You gotta keep that money flowing!
        money.setText("Balance: $%d".formatted(HumanPlayer.getInstance().getMoney()));

        // TODO: Check if the player is out of money. If so, show an alert and reset the game!
        if (HumanPlayer.getInstance().getMoney() <= 0) {
            showAlert("Game Over", "You're out of money! Better luck next time.");
            PlayerSavesService.deleteState();
            primaryStage.close();
        }
    }

    // TODO: Show an alert to the player with a title and content. Keep it clear and simple, like a good song.
    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // TODO: Create a styled button for the game controls. Buttons gotta shine, baby!
    private static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        );

        // TODO: Button hover effects, just like a smooth dance move. When you hover over it, it changes!
        button.setOnMouseEntered(_ -> button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        ));
        button.setOnMouseExited(_ -> button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        ));

        return button; // Give it to the player, they deserve it!
    }
}
