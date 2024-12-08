package co.ppj2.views;

import co.ppg2.controllers.GameController;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class CellView extends StackPane {
    private final Button button; // Button representing the cell
    private final int row; // Row index of the cell in the grid
    private final int col; // Column index of the cell in the grid
    private final GameController gameController; // The game controller managing the game logic

    // Constructor initializes the cell view with a button, row, column, and game controller
    public CellView(int row, int col, GameController gameController) {
        this.row = row;
        this.col = col;
        this.gameController = gameController;

        // Initialize the button and set its size
        button = new Button();
        button.setPrefSize(100, 100); // TODO: Make the button size configurable based on screen size or grid layout.
        getChildren().add(button); // Add button to the cell view

        // Set action for button click
        button.setOnAction(e -> handleClick()); // TODO: Add animations or feedback for when a user clicks the button.
    }

    // Handle the click event when the user selects a cell
    private void handleClick() {
        // TODO: Consider adding a check to disable clicks on cells that are already filled or during game over.

        // Only proceed if the cell is empty
        if (gameController.getCell(row, col) == null || gameController.getCell(row, col).getToken() == ' ') {
            char currentPlayerToken = gameController.getWhoseTurn(); // Get current player's token

            // Set the token in GameController and update button text
            gameController.setCell(row, col, new CellBase() {
                @Override
                public void setToken(char token) {
                    this.token = currentPlayerToken; // Set the token to the current player's token
                    button.setText(String.valueOf(currentPlayerToken)); // Update the button's text to show the token
                }
            });

            // TODO: Consider visual feedback when placing the token, e.g., animation or sound effect.

            // Check if there's a winner or if the board is full
            if (gameController.isWon(currentPlayerToken)) {
                gameController.getGameView().handleGameOver(currentPlayerToken); // Handle win (e.g., show winner screen)
            } else if (gameController.isFull()) {
                gameController.getGameView().handleTie(); // Handle tie if the board is full
            } else {
                // Switch player turn and update UI for the next move
                gameController.switchTurn(); // Switch to the next player's turn
                // TODO: Add a message or update the UI to indicate the next player's turn (e.g., update label with player name).
            }
        }
        // TODO: Handle cases where the user clicks on a non-empty cell (e.g., show an error message or prevent click).
    }
}
