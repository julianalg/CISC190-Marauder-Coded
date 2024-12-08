package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.services.PlayerSavesService;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Tooltip;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * MainMenuView is the main menu screen of the Casino application.
 * It provides navigation to various sections of the game, including
 * slot machine options, a leaderboard, and motivational resources.
 * The class also handles pausing bots and managing user data.
 */
public class MainMenuView extends Application {
    private static final String CASINO_TITLE = "Casino";
    private static final String[] MOTIVATIONAL_URLS = {
            "https://www.instagram.com/reel/C_JDcZVya_1/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/DAZR6WlSsVk/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/DCz7-k5JxLT/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/DB1tqWqNWL8/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/DB9nUPfS1WC/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/DBpDgUVoFcK/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/DB8nzu7oW8K/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/C7ZnLuWoRbW/?igsh=NTc4MTIwNjQ2YQ==",
            "https://www.instagram.com/reel/C_8R_SJPOe6/?igsh=NTc4MTIwNjQ2YQ=="
    };

    private static final String USERNAME_LABEL = "Username: %s";
    private static final String MONEY_LABEL = "Money: $%d";

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        MainMenuView.primaryStage = primaryStage;
        setupWindow(primaryStage);
    }

    static void setupWindow(Stage primaryStage) {
        VBox layout = createMainLayout();
        primaryStage.setTitle(CASINO_TITLE);

        layout.getChildren().addAll(
                createHeader(),
                createUserInfo(USERNAME_LABEL.formatted(HumanPlayer.getInstance().getName())),
                createUserInfo(MONEY_LABEL.formatted(HumanPlayer.getInstance().getMoney()))
        );

        addSlotOptionButtons(layout, primaryStage);

        Button motivationButton = createMotivationButton();
        layout.getChildren().add(motivationButton);

        setupScene(primaryStage, layout);
    }

    private static Button createMotivationButton() {
        Button motivationButton = createButton("Motivation", "Get inspired to keep going!", false);

        motivationButton.setOnAction(_ -> openRandomMotivationalUrl());

        return motivationButton;
    }

    private static void openRandomMotivationalUrl() {
        Random random = new Random();
        String selectedUrl = MOTIVATIONAL_URLS[random.nextInt(MOTIVATIONAL_URLS.length)];

        try {
            Desktop.getDesktop().browse(new URI(selectedUrl));
        } catch (IOException | URISyntaxException e) {
            showMessage("Failed to open the link. Please try again.");
        }
    }

    private static VBox createMainLayout() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #660000);-fx-padding: 30px;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        return layout;
    }

    private static Text createHeader() {
        Text header = new Text(CASINO_TITLE);
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        header.setFill(Color.GOLD);
        return header;
    }

    private static Text createUserInfo(String text) {
        Text userInfo = new Text(text);
        userInfo.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        userInfo.setFill(Color.WHITE);
        return userInfo;
    }

    private static Button createButton(String text, String tooltipText, boolean isSecondary) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, isSecondary ? 14 : 16));

        String defaultStyle = isSecondary
                ? createButtonStyle("#cccccc", "#888888", "black")
                : createButtonStyle("#ffcc00", "#ff9900", "black");

        String hoverStyle = isSecondary
                ? createButtonStyle("#aaaaaa", "#666666", "white")
                : createButtonStyle("#784800", "#943b00", "white");

        return getButton(tooltipText, button, defaultStyle, hoverStyle);
    }

    private static Button getButton(String tooltipText, Button button, String defaultStyle, String hoverStyle) {
        button.setStyle(defaultStyle);
        button.setOnMouseEntered(_ -> button.setStyle(hoverStyle));
        button.setOnMouseExited(_ -> button.setStyle(defaultStyle));

        if (tooltipText != null) {
            button.setTooltip(createTooltip(tooltipText));
        }

        return button;
    }

    private static String createButtonStyle(String topColor, String bottomColor, String textColor) {
        return "-fx-background-color: linear-gradient(to bottom, %s, %s);-fx-text-fill: %s;-fx-background-radius: 10;-fx-padding: 10px 20px;".formatted(topColor, bottomColor, textColor);
    }

    private static Tooltip createTooltip(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        tooltip.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-padding: 5px;");
        return tooltip;
    }

    private static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void setupScene(Stage primaryStage, VBox layout) {
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads();
            Platform.exit();
        });

        Scene scene = new Scene(layout, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void addSlotOptionButtons(VBox layout, Stage primaryStage) {
        SlotOptions[] options = SlotOptions.values();
        for (SlotOptions option : options) {
            String tooltipText = switch (option) {
                case DIAMOND_DASH -> "Play Diamond Dash for sparkling wins! Min Bet: 15, Max Bet: 1000, Return: 2x";
                case HONDA_TRUNK -> "Spin the wheels with Honda Trunk. Min Bet: 1, Max Bet: 1000, Return: 1.5x";
                case MEGA_MOOLAH -> "Massive jackpots in Mega Moolah! Min Bet: 10, Max Bet: 1000, Return: 3x";
                case RAINBOW_RICHES -> "Discover treasures in Rainbow Riches. Min Bet: 25, Max Bet: 1000, Return: 5x";
                case TREASURE_SPINS -> "Uncover hidden wealth with Treasure Spins. Min Bet: 50, Max Bet: 1000, Return: 10x";
                case LEADERBOARD -> "View the current leaderboard standings.";
                case QUIT -> "Return to the Matrix";
            };

            Button slotButton = createButton(option.getDisplayOption(), tooltipText, option == SlotOptions.QUIT || option == SlotOptions.LEADERBOARD);
            slotButton.setOnAction(_ -> handleSlotOption(primaryStage, option));
            layout.getChildren().add(slotButton);
        }
    }

    private static void handleSlotOption(Stage primaryStage, SlotOptions option) {
        switch (option) {
            case DIAMOND_DASH, HONDA_TRUNK, MEGA_MOOLAH, RAINBOW_RICHES, TREASURE_SPINS ->
                    BetView.showWindow(primaryStage, option);
            case LEADERBOARD -> LeaderboardView.showWindow(primaryStage);
            case QUIT -> quitApplication();
            default -> showMessage("Default option selected.");
        }
    }

    private static void quitApplication() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Goodbye!");
        alert.setContentText("Come back soon! 99.9% of gamblers quit before hitting it big!");
        alert.showAndWait();
        PlayerSavesService.saveState();
        Platform.exit();
        System.exit(0);
    }

    public enum SlotOptions {
        DIAMOND_DASH("Diamond Dash"),
        HONDA_TRUNK("Honda Trunk"),
        MEGA_MOOLAH("Mega Moolah"),
        RAINBOW_RICHES("Rainbow Riches"),
        TREASURE_SPINS("Treasure Spins"),
        LEADERBOARD("Leaderboard"),
        QUIT("Quit");

        private final String displayOption;

        SlotOptions(String displayOption) {
            this.displayOption = displayOption;
        }

        public String getDisplayOption() {
            return displayOption;
        }
    }
}
