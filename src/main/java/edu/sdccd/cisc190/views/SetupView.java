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
    
    // TODO: This is where we save the player's name
    static String userName;

    /**
     * The entry point for the JavaFX application. Determines whether to load existing player data
     * or show the sign-in window for new players.
     *
     * @param primaryStage the primary stage for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // TODO: Check if player data exists to skip the sign-in window
        if (PlayerSavesService.loadState()) {
            // TODO: If player data exists, go straight to the Main Menu
            Stage mainMenuStage = new Stage();
            MainMenuView.setupWindow(mainMenuStage);
            primaryStage.close(); // TODO: Close the sign-in window if loading player data
        } else {
            // TODO: If no player data exists, show the sign-in window
            showSignInWindow(primaryStage);
        }
    }

    /**
     * Displays the sign-in window for the user to enter their name.
     * @param primaryStage the primary stage for the sign-in window.
     */
    private void showSignInWindow(Stage primaryStage) {

        primaryStage.setOnCloseRequest(_ -> {
            // TODO: Make sure to stop all threads when closing the app
            SlotMachineManager.stopAllThreads();
            Platform.exit(); // TODO: Exit the platform properly
        });

        primaryStage.setTitle("Casino - Sign In"); // TODO: Set window title

        // TODO: Create labels, text fields, and buttons for sign-in screen
        Label welcomeLabel = new Label("Welcome to the Casino!");
        Label nameLabel = new Label("What's your name?");
        nameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.GOLD); // TODO: Make the name label stand out with gold color

        TextField nameField = new TextField();
        nameField.setPromptText("Enter Your Name");
        nameField.setPrefWidth(250); // TODO: Set the width of the text field

        Button submitButton = new Button("Enter the Casino");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // TODO: Style the button with a cool gradient effect
        submitButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        );

        // TODO: Change button color when hovering over it
        submitButton.setOnMouseEntered(_ -> submitButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        ));

        submitButton.setOnMouseExited(_ -> submitButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        ));

        // TODO: Style the welcome label to make it look awesome
        welcomeLabel.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-prompt-text-fill: #aaaaaa; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );

        // TODO: Style the name text field to match the dark theme
        nameField.setStyle(
                "-fx-background-color: #333333; " +
                        "-fx-text-fill: white; " +
                        "-fx-prompt-text-fill: #aaaaaa; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10px;"
        );

        // TODO: Define the action when the submit button is clicked
        submitButton.setOnAction(_ -> {
            userName = nameField.getText(); // TODO: Save the player's name
            HumanPlayer tempPlayer = HumanPlayer.getInstance();
            tempPlayer.setUsername(userName); // TODO: Set the username in the player object
            tempPlayer.setMoney(1000); // Default starting money if no file was loaded
            primaryStage.close(); // Close the sign-in window

            Stage newWindow = new Stage(); // Open the main menu window
            MainMenuView.setupWindow(newWindow); 
        });

        // TODO: Layout all the components in the sign-in window
        VBox layout = new VBox(20); // Spacing between components
        layout.getChildren().addAll(welcomeLabel, nameLabel, nameField, submitButton);
        layout.setAlignment(Pos.CENTER); // TODO: Center align all the components
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000000, #660000);" + // Casino gradient
                        "-fx-padding: 20px;"
        );

        // TODO: Set up and display the scene
        Scene scene = new Scene(layout, 350, 250); // Compact window size
        primaryStage.setScene(scene);
        primaryStage.show(); // Show the sign-in window
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args the command-line arguments (if any).
     */
    public static void main(String[] args) {
        launch(args); // TODO: Launch the JavaFX application
    }
}
