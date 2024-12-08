package co.ppj2.controllers;

import co.ppg2.services.GameTimer;
import co.ppg2.model.Player;
import co.ppg2.views.CellBase;
import co.ppg2.views.GameView;
import co.ppg2.views.LeaderboardPopup;
import java.util.ArrayList;

public class GameController {
    private char whoseTurn = 'X';
    private final CellBase[][] cells = new CellBase[3][3];
    private final Player playerX;
    private final Player playerO;
    private GameTimer gameTimer;
    private GameView gameView;
    private final ArrayList<Player> players;

    public GameController(Player playerX, Player playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.players = PlayerDataController.loadPlayers(); // Load existing players
    }

    public char getWhoseTurn() {
        return whoseTurn;
    }

    public Player getCurrentPlayer() {
        return (whoseTurn == 'X') ? playerX : playerO;
    }

    public void switchTurn() {
        if (gameTimer != null) {
            gameTimer.cancelTimer(); // Stop the timer for the current player
            whoseTurn = (whoseTurn == 'X') ? 'O' : 'X';
            gameTimer.startTimer(getCurrentPlayer().getUsername()); // Start the timer for the next player
        }
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i][j] == null || cells[i][j].getToken() == ' ') {
                    return false; // TODO: Consider handling cells that may not be initialized or contain unexpected values.
                }
            }
        }
        return true;
    }

    public boolean isWon(char token) {
        // TODO: Refactor to avoid repetitive code for checking rows, columns, and diagonals.
        for (int i = 0; i < 3; i++) {
            if (cells[i][0].getToken() == token && cells[i][1].getToken() == token && cells[i][2].getToken() == token) {
                return true; // Check horizontal win condition
            }
        }
        for (int j = 0; j < 3; j++) {
            if (cells[0][j].getToken() == token && cells[1][j].getToken() == token && cells[2][j].getToken() == token) {
                return true; // Check vertical win condition
            }
        }
        if (cells[0][0].getToken() == token && cells[1][1].getToken() == token && cells[2][2].getToken() == token) {
            return true; // Check diagonal win condition (top-left to bottom-right)
        }
        return cells[0][2].getToken() == token && cells[1][1].getToken() == token && cells[2][0].getToken() == token; // Check diagonal win condition (top-right to bottom-left)
    }

    public void setCell(int row, int col, CellBase cell) {
        cells[row][col] = cell; // TODO: Validate that the row and col are within bounds before setting the cell.
    }

    public void setGameTimer(GameTimer gameTimer) {
        this.gameTimer = gameTimer; // TODO: Ensure the game timer is properly initialized before setting.
    }

    public CellBase getCell(int row, int col) {
        return cells[row][col]; // TODO: Add validation to prevent index out of bounds exception.
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView; // TODO: Ensure that the game view is properly initialized.
    }

    public GameView getGameView() {
        return gameView;
    }

    public Player getWinner(char token) {
        return (token == 'X') ? playerX : playerO; // TODO: Consider adding a check for invalid token input.
    }

    public void updateLeaderboard(char token) {
        Player winner = getWinner(token); // TODO: Add error handling in case token is invalid.
        Player loser = (token == 'X') ? playerO : playerX;

        // TODO: Add a check to ensure the player data is valid before proceeding with leaderboard update.
        for (Player player : players) {
            if (player.getUsername().equals(winner.getUsername())) {
                player.incrementWins();
            } else if (player.getUsername().equals(loser.getUsername())) {
                player.incrementLosses();
            }
        }

        PlayerDataController.savePlayers(players); // TODO: Handle possible errors in saving players' data.

        // Show leaderboard with average time per move
        StringBuilder leaderboardDetails = new StringBuilder();
        for (Player player : players) {
            double avgTime = gameTimer.getAverageTimePerMove(player.getUsername()); // TODO: Ensure avgTime is correctly calculated, handle edge cases.
            leaderboardDetails.append(String.format("%s - Wins: %d, Losses: %d, Avg Time: %.2f seconds\n",
                    player.getUsername(), player.getWins(), player.getLosses(), avgTime));
        }

        LeaderboardPopup.showLeaderboard(players); // TODO: Ensure the leaderboard is shown correctly in the UI.
    }
}
