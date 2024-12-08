package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.services.PlayerSavesService;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * The SetupView class is the first screen of the Casino Royale application.
 * It prompts the user to enter their name and serves as the gateway to the Main Menu.
 * If player data already exists, the application skips this screen and proceeds to the Main Menu.
 */
public class SetupView extends Application {
    private static String userName;

    @Override
    public void start(Stage primaryStage) {
        // Check if player data file exists and load it
        if (PlayerSavesService.loadState()) {
            // Proceed directly to the MainMenu if data was loaded
            Stage mainMenuStage = new Stage();
            MainMenuView.setupWindow(mainMenuStage);
            primaryStage.close();
        } else {
            // Show sign-in window if no data was loaded
            showSignInWindow(primaryStage);
        }
    }

    /**
     * Displays the sign-in window for the user to enter their name.
     * @param primaryStage the primary stage for the sign-in window.
     */
    private void showSignInWindow(Stage primaryStage) {
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads();
            Platform.exit();
        });

        primaryStage.setTitle("Casino - Sign In");

        // Create labels, text field, and button for the sign-in window
        Label welcomeLabel = createLabel("Welcome to the Casino!", FontWeight.BOLD, 20, Color.WHITE, Color.BLACK);
        Label nameLabel = createLabel("What's your name?", FontWeight.BOLD, 16, Color.GOLD, Color.BLACK);

        TextField nameField = createTextField("Enter Your Name");
        Button submitButton = createStyledButton("Enter the Casino");

        // Define action for the submit button
        submitButton.setOnAction(_ -> {
            userName = nameField.getText();
            HumanPlayer tempPlayer = HumanPlayer.getInstance();
            tempPlayer.setUsername(userName);
            tempPlayer.setMoney(1000); // Default starting money if no file was loaded
            primaryStage.close();

            Stage newWindow = new Stage();
            MainMenuView.setupWindow(newWindow);
        });

        // Layout and styling for the sign-in window
        VBox layout = new VBox(20); // Spacing between components
        layout.getChildren().addAll(welcomeLabel, nameLabel, nameField, submitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #660000); -fx-padding: 20px;");

        // Create and show the scene
        Scene scene = new Scene(layout, 350, 250); // Compact window size
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a styled label with the given properties.
     */
    private Label createLabel(String text, FontWeight fontWeight, int fontSize, Color textFill, Color backgroundFill) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", fontWeight, fontSize));
        label.setTextFill(textFill);
        label.setStyle("-fx-background-color: " + toHex(backgroundFill) + "; -fx-background-radius: 10; -fx-padding: 10px;");
        return label;
    }

    /**
     * Creates a styled TextField with the given prompt text.
     */
    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setPrefWidth(250);
        textField.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-prompt-text-fill: #aaaaaa; -fx-background-radius: 10; -fx-padding: 10px;");
        return textField;
    }

    /**
     * Creates a styled button with the given text.
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900); -fx-text-fill: black; -fx-background-radius: 10; -fx-padding: 10px 20px;");

        // Set hover effects
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600); -fx-text-fill: white; -fx-background-radius: 10; -fx-padding: 10px 20px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900); -fx-text-fill: black; -fx-background-radius: 10; -fx-padding: 10px 20px;"));

        return button;
    }

    /**
     * Converts a Color to its hexadecimal string representation.
     */
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args the command-line arguments (if any).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
