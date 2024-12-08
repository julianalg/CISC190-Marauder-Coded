package co.ppj2.views;

import co.ppg2.controllers.GameController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;

public class CellEmpty extends CellBase {
    private static final int CELL_SIZE = 100; // TODO: Consider making CELL_SIZE configurable or adjustable based on screen size or resolution.
    private final int row; // Row index of the cell in the grid
    private final int col; // Column index of the cell in the grid
    private final GameController gameController; // Controller that manages the game logic
    private final GameView gameView; // View to update the user interface

    // Constructor initializing the cell with game controller, view, and position (row/column)
    public CellEmpty(GameController gameController, GameView gameView, int row, int col) {
        this.gameController = gameController;
        this.gameView = gameView;
        this.row = row;
        this.col = col;

        // TODO: Extract rectangle creation into a helper method to avoid duplication and improve code reusability.
        Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
        rect.setFill(Color.LIGHTGRAY); // TODO: Allow the color to be customizable based on game state (e.g., highlight selected cell).
        this.getChildren().add(rect);

        // Set border around the cell
        this.setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(1)
                )
        ));

        // Add mouse click event handler
        this.setOnMouseClicked(e -> handleMouseClick());
    }

    // Set the token ('X' or 'O') on this cell and update the display accordingly
    @Override
    public void setToken(char token) {
        this.token = token; // Update token value
        this.getChildren().clear(); // Clear previous token (if any)
        if (token == 'X') {
            this.getChildren().add(new CellX()); // Add 'X' cell representation
        } else if (token == 'O') {
            this.getChildren().add(new CellO()); // Add 'O' cell representation
        }
    }

    // Handle mouse click event to make a move in the game
    private void handleMouseClick() {
        if (token == ' ') { // Only allow move if the cell is empty
            char currentPlayerToken = gameController.getWhoseTurn(); // Get the token of the current player
            gameController.setCell(row, col, this); // Update game state with the current move
            setToken(currentPlayerToken);  // Update display with the player's token

            // TODO: Add animation or visual feedback for the player's move (e.g., a short flash or sound effect).

            // Check for win or tie condition after the move
            if (gameController.isWon(currentPlayerToken)) {
                gameView.handleGameOver(currentPlayerToken); // Handle game over if the current player wins
            } else if (gameController.isFull()) {
                gameView.handleTie(); // Handle tie if the board is full and no winner
            } else {
                gameController.switchTurn(); // Switch to the other player's turn
                gameView.updateLabel(gameController.getCurrentPlayer().getUsername() + "'s turn"); // Update turn label
            }
        }
        // TODO: Handle invalid clicks or re-clicking on a cell that is already filled (e.g., show a message or visual feedback).
    }
}
