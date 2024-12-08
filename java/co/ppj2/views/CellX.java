package co.ppj2.views;

import javafx.scene.shape.Line;

public class CellX extends CellBase {
    public CellX() {
        // Create two lines to represent the 'X' token
        Line line1 = new Line(10, 10, 90, 90); // Diagonal line from top-left to bottom-right
        Line line2 = new Line(90, 10, 10, 90); // Diagonal line from top-right to bottom-left
        this.getChildren().addAll(line1, line2); // Add the lines to the cell

        // TODO: Consider adding a visual effect, such as animating the 'X' being drawn or adding a delay for better user experience.
    }

    // Set the token to 'X' (this cell is always 'X')
    @Override
    public void setToken(char token) {
        // TODO: Ensure that 'setToken' is necessary here, as this cell is always 'X'. Consider throwing an exception if a different token is passed.
        this.token = 'X'; // Set the token to 'X'

        // TODO: Consider adding an optional visual effect when setting the token, like an animation or a fade-in effect.
    }
}
