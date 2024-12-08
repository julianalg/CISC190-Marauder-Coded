package co.ppj2.views;

public class LabelWin extends LabelBase {
    // Field to store the win message for the label
    public String winMessage;

    // Constructor to initialize the label with a win message and a custom background color
    public LabelWin(String text) {
        super(text); // Call the parent constructor to set the text for the label
        setStyle("-fx-background-color: lightcoral;"); // Set a background color specific to win messages

        // TODO: Consider making the background color customizable or dynamic based on the win context
        //       For example, allowing different colors for different types of wins (e.g., different colors for player 1 or player 2 wins).
        // Example: setStyle("-fx-background-color: " + backgroundColor + ";");

        // TODO: Add a visual effect or animation (e.g., a pulsing effect or a "confetti" animation) to emphasize the win message.
        // This could create a more celebratory experience for the user.
        // Animation could be added using JavaFX animations.
    }

    // TODO: Implement a method to dynamically update the win message if needed, in case of changes to the win message.
    // This can be used if you want to provide more specific or detailed win-related information.
    // public void updateWinMessage(String newMessage) {
    //     winMessage = newMessage; // Update win message
    //     setText(newMessage); // Update the displayed text in the label
    // }

    // TODO: Add event handling if you want users to interact with the win message (e.g., clicking to close or go to the leaderboard).
    // This could be useful to trigger actions after the game ends.
    // Example: setOnMouseClicked(e -> handleClick());
    // private void handleClick() {
    //     // Action to take when the user clicks the win message, like showing the leaderboard
    // }
}
