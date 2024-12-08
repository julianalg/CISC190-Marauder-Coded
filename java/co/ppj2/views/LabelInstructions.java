package co.ppj2.views;

public class LabelInstructions extends LabelBase {

    // Constructor to initialize the label with custom text and background color for instructions
    public LabelInstructions(String text) {
        super(text); // Call the parent constructor to set the initial text
        setStyle("-fx-background-color: lightgreen;"); // Set a unique background color for instruction labels

        // TODO: Consider making the background color and text color configurable
        //       This would allow different instruction labels to have varied visual styles based on context.
        // Example: setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: " + textColor + ";");

        // TODO: Add custom styles for specific situations (e.g., error instructions might have a red background).
        // You could use conditionals to change the style dynamically.
    }

    // TODO: Add functionality to update the label's text dynamically if instructions change during the game.
    // This would allow the instructions label to be updated as the game progresses.
    // public void updateText(String newText) {
    //     setText(newText); // Change the text of the label
    // }

    // TODO: Add event listeners to modify the label's appearance on interaction.
    // For example, changing the background color when the mouse hovers over the instruction label.
    // setOnMouseEntered(e -> setStyle("-fx-background-color: yellow;"));
}
