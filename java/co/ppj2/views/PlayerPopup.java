package co.ppj2.views;

import co.ppg2.model.Player;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PlayerPopup {

    // Method to show a popup where users can enter a username for the player
    public static Player showPopup(String playerLabel) {
        Stage popupStage = new Stage();  // Create a new stage for the popup window
        VBox vbox = new VBox();  // Create a VBox layout for the popup
        Label label = new Label("Enter username for " + playerLabel + ":");  // Label asking for username
        TextField usernameField = new TextField();  // TextField for user input
        Button submitButton = new Button("Submit");  // Button to submit the username

        // Action when submit button is clicked
        submitButton.setOnAction(e -> {
            if (!usernameField.getText().isEmpty()) {  // Check if the username is not empty
                popupStage.close();  // Close the popup window
            } else {
                // TODO: Display a warning message if the username is empty.
                // If the username field is empty, prompt the user to enter a valid username.
                // For example, show a message like: "Username cannot be empty!"
            }
        });

        // Add the components to the VBox layout
        vbox.getChildren().addAll(label, usernameField, submitButton);

        // Set the scene with the VBox layout and show the popup
        Scene scene = new Scene(vbox, 300, 150);
        popupStage.setTitle(playerLabel + " Setup");  // Set the title of the popup window
        popupStage.setScene(scene);  // Set the scene for the popup
        popupStage.showAndWait();  // Show the popup and wait for the user to interact with it

        // TODO: Allow username validation to ensure it meets criteria (e.g., no special characters).
        // You could add validation to ensure the username is suitable, such as checking for disallowed characters
        // or enforcing a minimum/maximum length. Display a message if the validation fails.

        // TODO: Provide a "Cancel" button to allow the user to cancel the action.
        // You can add a cancel button alongside the submit button, allowing the user to cancel the action if they don't
        // want to proceed with entering the username. The cancel button could close the popup without creating a player.
        // Example: Add a button to cancel the username input and close the popup.

        return new Player(usernameField.getText()); // Create and return a new Player object with the entered username
    }

    // TODO: Improve UI appearance:
    // Consider adding some padding, spacing, or custom styles to make the popup more visually appealing.
    // You could style the labels, buttons, and text fields with CSS or inline styles to make the UI more user-friendly.

    // TODO: Handle duplicate usernames:
    // Implement a feature that checks if the entered username already exists in the current game or system.
    // If the username already exists, prompt the user to choose a different name or alert them that the name is already taken.
    // Example: Show a message like "Username already taken. Please choose another."

    // TODO: Accessibility improvements:
    // Ensure that the popup is accessible to all users, including those using screen readers.
    // This can include providing proper labels for screen readers, improving the layout for readability, and ensuring that
    // the popup is properly focused and dismissed when appropriate.
}
