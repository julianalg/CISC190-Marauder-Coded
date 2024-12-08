package co.ppj2.views;

import co.ppg2.controllers.GameController;
import co.ppg2.controllers.PlayerDataController;
import co.ppg2.model.Player;
import javafx.scene.Scene;
import javafx.scene.control.Label; // TODO: Import Label for instructions display
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GameView {
    private final GameController gameController; // Game logic controller
    private final Stage primaryStage; // JavaFX primary window stage
    private LabelInstructions labelInstructions; // Instructions display label

    // Constructor for GameView, accepting game controller and stage for the scene
    public GameView(GameController gameController, Stage primaryStage) {
        this.gameController = gameController;
        this.primaryStage = primaryStage;
    }

    // TODO: Consider adding setup for initial game conditions, such as initializing players
    public void launchGame() {
        // Create a GridPane to hold the game cells (3x3 grid for TicTacToe)
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true); // Optionally set grid lines for visual clarity

        // Initialize and add the cells to the grid
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                CellEmpty cell = new CellEmpty(gameController, this, i, j); // Create an empty cell
                gameController.setCell(i, j, cell); // Associate the cell with the GameController
                gridPane.add(cell, j, i); // Add the cell to the grid layout
            }
        }

        // Create instructions label to show whose turn it is
        labelInstructions = new LabelInstructions(gameController.getCurrentPlayer().getUsername() + "'s turn");

        // Set up BorderPane to arrange the game elements
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane); // Place the game grid in the center
        borderPane.setBottom(labelInstructions); // Place instructions at the bottom

        // Set up and show the JavaFX scene
        Scene scene = new Scene(borderPane, 450, 170); // Adjust the scene size as needed
        primaryStage.setTitle("TicTacToe"); // Set window title
        primaryStage.setScene(scene); // Set the scene to the primary stage
        primaryStage.show(); // Show the stage to the user
    }

    // Update the instructions label with the provided text
    public void updateLabel(String text) {
        labelInstructions.setText(text); // Update the text of the instructions label
    }

    // Handle a tie scenario and show the leaderboard
    public void handleTie() {
        updateLabel("It is a tie!"); // Inform the user of the tie
        // TODO: Consider adding an option for restarting the game or returning to the main menu
        LeaderboardPopup.showLeaderboard(PlayerDataController.loadPlayers()); // Show the leaderboard
    }

    // Handle the game over scenario, showing who won and updating the leaderboard
    public void handleGameOver(char token) {
        Player winner = gameController.getWinner(token); // Get the winner based on the token
        updateLabel(winner.getUsername() + " won!"); // Display the winner’s username

        // TODO: Consider adding a pop-up or animation for the winner announcement

        gameController.updateLeaderboard(token); // Update the leaderboard with the game results
        // TODO: Provide an option to restart the game after a win or tie
    }
}
