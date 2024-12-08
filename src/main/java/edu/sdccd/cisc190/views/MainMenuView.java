package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.services.PlayerSavesService;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.layout.*;
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
 * TODO: MainMenuView is the main menu screen of the Casino application.
 *       It gives users access to the game options and other features.
 *       Handles user data and pausing bots, too.
 */
public class MainMenuView extends Application {

    /**
     * TODO: A list of motivational links to inspire users.
     */
    private static final String[] MOTIVATIONAL_URLS = {
            "https://www.instagram.com/reel/C_JDcZVya_1/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 1
            "https://www.instagram.com/reel/DAZR6WlSsVk/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 2
            "https://www.instagram.com/reel/DCz7-k5JxLT/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 3
            "https://www.instagram.com/reel/DB1tqWqNWL8/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 4
            "https://www.instagram.com/reel/DB9nUPfS1WC/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 5
            "https://www.instagram.com/reel/DBpDgUVoFcK/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 6
            "https://www.instagram.com/reel/DB8nzu7oW8K/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 7
            "https://www.instagram.com/reel/C7ZnLuWoRbW/?igsh=NTc4MTIwNjQ2YQ==", // Motivation 8
            "https://www.instagram.com/reel/C_8R_SJPOe6/?igsh=NTc4MTIwNjQ2YQ=="  // Motivation 9
    };

    /**
     * TODO: Primary stage of the application, used to set up and display the UI.
     */
    static Stage primaryStage;

    /**
     * TODO: Main entry point of the JavaFX application. This method is called when the app starts.
     */
    @Override
    public void start(Stage primaryStage) {
        MainMenuView.primaryStage = primaryStage;
        setupWindow(primaryStage); // Set up the main menu
    }

    /**
     * TODO: Setup the window for the main menu. Adds header, user info, and buttons.
     */
    static void setupWindow(Stage primaryStage) {
        VBox layout = createMainLayout(); // Create the main layout (a container)
        primaryStage.setTitle("Casino Menu"); // Set the title of the window

        // Add header and user info like username and money
        layout.getChildren().addAll(
                createHeader(),
                createUserInfo("Username: %s".formatted(HumanPlayer.getInstance().getName())),
                createUserInfo("Money: $%d".formatted(HumanPlayer.getInstance().getMoney()))
        );

        // Add buttons for different slot machine options
        addSlotOptionButtons(layout, primaryStage);

        Button motivationButton = createMotivationButton(); // Create motivation button
        layout.getChildren().add(motivationButton); // Add it to the layout

        // Set up and show the scene (everything inside the window)
        setupScene(primaryStage, layout);
    }

    /**
     * TODO: Create a button that opens a random motivational URL when clicked.
     *       It opens the URL in the web browser to inspire the user.
     */
    private static Button createMotivationButton() {
        Button motivationButton = createSecondaryButton("Motivation", "Get inspired to keep going!");

        motivationButton.setOnAction(_ -> {
            Random random = new Random();
            int randomIndex = random.nextInt(MOTIVATIONAL_URLS.length); // Pick a random URL
            String selectedUrl = MOTIVATIONAL_URLS[randomIndex];

            try {
                Desktop desktop = Desktop.getDesktop(); // Open URL in desktop browser
                desktop.browse(new URI(selectedUrl));
            } catch (IOException | URISyntaxException e) {
                showMessage("Failed to open the link. Please try again.");
            }
        });

        return motivationButton; // Return the button
    }

    /**
     * TODO: Create the main layout for the menu. This controls the appearance.
     *       Uses a gradient background and arranges components vertically.
     */
    private static VBox createMainLayout() {
        VBox layout = new VBox(20); // Vertical box with 20px spacing
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000000, #660000);" +
                        "-fx-padding: 30px;"
        );
        layout.setAlignment(javafx.geometry.Pos.CENTER); // Center the content
        return layout; // Return the layout
    }

    /**
     * TODO: Create the header text for the main menu. This will display "Casino" in gold.
     */
    private static Text createHeader() {
        Text header = new Text("Casino");
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 30)); // Bold and large font
        header.setFill(Color.GOLD); // Set text color to gold
        return header; // Return the header text
    }

    /**
     * TODO: Create a text element to display user information, like the username or money.
     */
    private static Text createUserInfo(String text) {
        Text userInfo = new Text(text);
        userInfo.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18)); // Semi-bold font
        userInfo.setFill(Color.WHITE); // White text color
        return userInfo; // Return the user info text
    }

    /**
     * TODO: Create a styled button with hover effects and an optional tooltip.
     */
    private static Button createStyledButton(String text, String tooltipText) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set font style

        String defaultStyle = createButtonStyle("#ffcc00", "#ff9900", "black");
        String hoverStyle = createButtonStyle("#784800", "#943b00", "white");

        return getButton(tooltipText, button, defaultStyle, hoverStyle); // Return styled button
    }

    /**
     * TODO: Create a secondary button with lighter colors for less important actions.
     */
    private static Button createSecondaryButton(String text, String tooltipText) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14)); // Smaller font for secondary button

        String defaultStyle = createButtonStyle("#cccccc", "#888888", "black");
        String hoverStyle = createButtonStyle("#aaaaaa", "#666666", "white");

        return getButton(tooltipText, button, defaultStyle, hoverStyle); // Return the secondary button
    }

    /**
     * TODO: Helper method to set button styles and tooltips.
     */
    private static Button getButton(String tooltipText, Button button, String defaultStyle, String hoverStyle) {
        button.setStyle(defaultStyle); // Set default style
        button.setOnMouseEntered(_ -> button.setStyle(hoverStyle)); // Change style on hover
        button.setOnMouseExited(_ -> button.setStyle(defaultStyle)); // Reset style on mouse exit

        if (tooltipText != null) {
            button.setTooltip(createTooltip(tooltipText)); // Set tooltip if available
        }

        return button; // Return the button with styles
    }

    /**
     * TODO: Helper method to create button styles based on colors and text.
     */
    private static String createButtonStyle(String topColor, String bottomColor, String textColor) {
        return "-fx-background-color: linear-gradient(to bottom, %s, %s);-fx-text-fill: %s;-fx-background-radius: 10;-fx-padding: 10px 20px;".formatted(topColor, bottomColor, textColor);
    }

    /**
     * TODO: Show an informational message in a popup alert when needed.
     */
    private static void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null); // No header
        alert.setContentText(message); // Show the message
        alert.showAndWait(); // Display the alert
    }

    /**
     * TODO: Setup and show the main scene inside the application window.
     */
    private static void setupScene(Stage primaryStage, VBox layout) {
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads(); // Stop all slot machine threads when closing
            Platform.exit(); // Exit the platform
        });

        // Create the scene and set the layout to be displayed
        Scene scene = new Scene(layout, 800, 800); // Set the scene size (800x800)
        primaryStage.setScene(scene); // Set the scene for the stage
        primaryStage.show(); // Show the stage with the scene
    }

    /**
     * TODO: Add buttons for the different slot machine options.
     *       Each button lets the user choose a game or go to the leaderboard.
     */
    private static void addSlotOptionButtons(VBox layout, Stage primaryStage) {
        SlotOptions[] options = SlotOptions.values(); // Get all slot options
        for (int i = 0; i < options.length; i++) {
            SlotOptions option = options[i];
            Button slotButton;

            String tooltipText = switch (option) {
                case DIAMOND_DASH -> "Play Diamond Dash for sparkling wins! Min Bet: 15, Max Bet: 1000, Return: 2x";
                case HONDA_TRUNK -> "Spin the wheels with Honda Trunk. Min Bet: 1, Max Bet: 1000, Return: 1.5x";
                case MEGA_MOOLAH -> "Massive jackpots in Mega Moolah! Min Bet: 10, Max Bet: 1000, Return: 3x";
                case RAINBOW_RICHES -> "Discover treasures in Rainbow Riches. Min Bet: 25, Max Bet: 1000, Return: 5x";
                case TREASURE_SPINS -> "Uncover hidden wealth with Treasure Spins. Min Bet: 50, Max Bet: 1000, Return: 10x";
                case LEADERBOARD -> "View the current leaderboard standings.";
                case QUIT -> "Return to the Matrix";
            };

            if (i >= options.length - 2) { // Use secondary style for the last buttons
                slotButton = createSecondaryButton(option.getDisplayOption(), tooltipText);
            } else {
                slotButton = createStyledButton(option.getDisplayOption(), tooltipText);
            }

            slotButton.setOnAction(_ -> handleSlotOption(primaryStage, option)); // Set action when button is clicked
            layout.getChildren().add(slotButton); // Add button to the layout
        }
    }

    /**
     * TODO: Handle what happens when a slot machine option is selected.
     */
    private static void handleSlotOption(Stage primaryStage, SlotOptions option) {
        switch (option) {
            case DIAMOND_DASH, HONDA_TRUNK, MEGA_MOOLAH, RAINBOW_RICHES, TREASURE_SPINS ->
                    BetView.showWindow(primaryStage, option); // Show betting window
            case LEADERBOARD -> LeaderboardView.showWindow(primaryStage); // Show leaderboard
            case QUIT -> quitApplication(); // Quit the application
            default -> showMessage("Default option selected."); // Show a default message
        }
    }

    /**
     * TODO: Quit the application and display a goodbye message.
     */
    private static void quitApplication() {
        // Show goodbye message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Goodbye!");
        alert.setContentText("Come back soon! 99.9% of gamblers quit before hitting it big!");
        alert.showAndWait();
        PlayerSavesService.saveState(); // Save player data
        Platform.exit(); // Exit the platform

        // Exit the program
        System.exit(0);
    }

    /**
     * TODO: Enum for different slot machine options. Used to generate buttons and handle user selection.
     */
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

    /**
     * TODO: Helper method to create a tooltip that shows when the user hovers over a button.
     */
    private static Tooltip createTooltip(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        tooltip.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-padding: 5px;");
        return tooltip;
    }
}
