package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.machines.*;
import edu.sdccd.cisc190.services.PlayerSavesService;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
 */
public class SlotMachineView extends Application {

    private static final Label betAmount = new Label();
    private static final Label maxBet = new Label();
    private static final Label minBet = new Label();
    private static final Label returnAmount = new Label();
    private static final Label slot1 = new Label("❓");
    private static final Label slot2 = new Label("❓");
    private static final Label slot3 = new Label("❓");
    private static final Label won = new Label("Spin to see!");
    private static final Label money = new Label();

    static Button spinButton = createStyledButton("Spin");
    static Button changeBet = createStyledButton("Change Bet");
    static Button mainMenu = createStyledButton("Return to Main Menu");

    static MainMenuView.SlotOptions machineSelect;
    static Slot slotMachine;

    private static IntegerProperty playerMoney = new SimpleIntegerProperty(HumanPlayer.getInstance().getMoney());

    @Override
    public void start(Stage primaryStage) {
        showWindow(primaryStage, 0, MainMenuView.SlotOptions.DIAMOND_DASH);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showWindow(Stage primaryStage, int betAmt, MainMenuView.SlotOptions selectedMachine) {

        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads();
            Platform.exit();
        });

        machineSelect = selectedMachine;
        slotMachine = createSlotMachine(selectedMachine);

        primaryStage.setTitle("Casino - Slot Machine");

        setupLabels(betAmt);
        setupSlotSymbols();
        setupButtons(primaryStage);

        HBox slotInformation = new HBox(10, maxBet, minBet, returnAmount);
        slotInformation.setAlignment(Pos.CENTER);

        HBox slotsRow = new HBox(20, slot1, slot2, slot3);
        slotsRow.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, betAmount, won, money, slotInformation, slotsRow, spinButton, changeBet, mainMenu);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #660000); -fx-padding: 30px;");

        Scene scene = new Scene(layout, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void setupLabels(int betAmt) {
        betAmount.setText("You're betting: $%d".formatted(betAmt));
        setLabelStyle(betAmount, "Arial", FontWeight.BOLD, 20, Color.LIGHTGOLDENRODYELLOW);

        infoSetText(maxBet, minBet, returnAmount);
        setLabelStyle(won, "Arial", FontWeight.BOLD, 24, Color.GOLD);
        setLabelStyle(money, "Arial", FontWeight.BOLD, 20, Color.LIGHTGREEN);

        money.textProperty().bind(playerMoney.asString("Balance: $%d"));
    }

    private static void setupSlotSymbols() {
        slot1.setStyle("-fx-font-size: 60px;");
        slot2.setStyle("-fx-font-size: 60px;");
        slot3.setStyle("-fx-font-size: 60px;");
        slot1.setTextFill(Color.ORANGERED);
        slot2.setTextFill(Color.ORANGERED);
        slot3.setTextFill(Color.ORANGERED);
    }

    private static void setupButtons(Stage primaryStage) {
        spinButton.setOnAction(_ -> spin(primaryStage));
        changeBet.setOnAction(_ -> {
            primaryStage.close();
            BetView.showWindow(primaryStage, machineSelect);
        });
        mainMenu.setOnAction(_ -> {
            primaryStage.close();
            MainMenuView.setupWindow(primaryStage);
        });
    }

    private static Slot createSlotMachine(MainMenuView.SlotOptions selectedMachine) {
        switch (selectedMachine) {
            case HONDA_TRUNK: return new HondaTrunk();
            case TREASURE_SPINS: return new TreasureSpins();
            case MEGA_MOOLAH: return new MegaMoolah();
            case RAINBOW_RICHES: return new RainbowRiches();
            default: return new DiamondDash();
        }
    }

    static void infoSetText(Label maxBet, Label minBet, Label returnAmount) {
        maxBet.setText("Max. Bet: %d".formatted(slotMachine.getMaxBet()));
        setLabelStyle(maxBet, "Arial", FontWeight.SEMI_BOLD, 15, Color.RED);

        minBet.setText("Min. Bet: %d".formatted(slotMachine.getMinBet()));
        setLabelStyle(minBet, "Arial", FontWeight.SEMI_BOLD, 15, Color.RED);

        returnAmount.setText("Return: %s".formatted(slotMachine.getReturnAmt()));
        setLabelStyle(returnAmount, "Arial", FontWeight.SEMI_BOLD, 15, Color.RED);
    }

    private static void setLabelStyle(Label label, String fontFamily, FontWeight weight, int fontSize, Color textColor) {
        label.setFont(Font.font(fontFamily, weight, fontSize));
        label.setTextFill(textColor);
    }

    private static void spin(Stage primaryStage) {
        int betAmt = Integer.parseInt(betAmount.getText().replaceAll("\\D", ""));
        if (!slotMachine.canBet(betAmt)) {
            showAlert("Invalid Bet", "Your bet is outside the allowed range or exceeds your balance.");
            primaryStage.close();
            BetView.showWindow(primaryStage, machineSelect);
            return;
        }

        String[] symbols = slotMachine.generateSpunSymbols();
        slot1.setText(symbols[0]);
        slot2.setText(symbols[1]);
        slot3.setText(symbols[2]);

        int newBalance = slotMachine.calculatePayout(playerMoney.get(), symbols, betAmt);
        playerMoney.set(newBalance);

        if (slotMachine.evaluateWinCondition(symbols) >= 2) {
            won.setText("Wow, you won!");
        } else {
            won.setText("You lost :(");
        }

        if (playerMoney.get() <= 0) {
            showAlert("Game Over", "You're out of money! Better luck next time.");
            PlayerSavesService.deleteState();
            primaryStage.close();
        }
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        );

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

        return button;
    }
}
