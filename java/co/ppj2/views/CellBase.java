package co.ppj2.views;

import javafx.scene.layout.Pane;

public abstract class CellBase extends Pane {
    protected char token = ' '; // TODO: Consider using an enum (e.g., Token.X, Token.O, EMPTY) instead of a char for better readability and type safety.

    // Returns the current token of the cell
    public char getToken() {
        return token;
    }

    // TODO: Add validation to ensure that the token set is valid (e.g., 'X' or 'O'). This helps prevent setting invalid tokens.
    public abstract void setToken(char token);

    // TODO: Consider adding a method to reset the token, for example, during a new game or when clearing the board.
    // public abstract void resetToken();

    // TODO: Add a method to check if the cell is empty (i.e., has a token of ' '), which could be useful for game logic.
    // public boolean isEmpty() {
    //     return token == ' ';
    // }

    // TODO: Consider adding a visual indicator (e.g., color or border) that changes based on the token to make the board more intuitive.
    // This could be done by overriding the updateUI() or add method here.
}


