package co.ppj2.views;

import javafx.scene.control.Label;

public class LabelBase extends Label {

    // Constructor to initialize the label with custom text and style
    public LabelBase(String text) {
        super(text); // Call the parent constructor to set the label's text

        // TODO: Consider making the font size, background color, and other styles configurable
        //       so that it can be reused in various parts of the application with different styles.
        setStyle("-fx-font-size: 16px; -fx-background-color: lightblue;"); // Set initial style for the label

        // TODO: Add any event listeners (like hover effects or click actions) if needed for further interaction.
        // For example, a hover effect to change background color when the user hovers over the label:
        // setOnMouseEntered(e -> setStyle("-fx-background-color: lightgreen;"));
    }

    // TODO: Implement a method to update the text dynamically if needed
    // public void updateText(String newText) {
    //     setText(newText); // Update label text
    // }

    // TODO: Add validation to ensure only valid styles are set (e.g., checking for font size limits).
    //       This could help prevent errors when using this component in different contexts.
}
