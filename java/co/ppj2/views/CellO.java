package co.ppj2.views;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class CellO extends CellBase {
    public CellO() {
        // Create an ellipse to represent the 'O' token
        Ellipse ellipse = new Ellipse(50, 50, 40, 40); // TODO: Consider making the size of the ellipse dynamic or adjustable.
        ellipse.setStroke(Color.BLACK); // Set the border color for the 'O' token
        ellipse.setFill(Color.WHITE); // Fill color for 'O' token (default is white)
        this.getChildren().add(ellipse); // Add the ellipse to the cell
    }

    // Set the token to 'O' (this cell is always 'O')
    @Override
    public void setToken(char token) {
        // TODO: Since this cell is always 'O', you could skip this method or throw an exception if an invalid token is passed.
        this.token = 'O'; // Set the token value to 'O'
        // TODO: Consider adding a visual effect when setting the token (e.g., animate the 'O' being placed).
    }
}
