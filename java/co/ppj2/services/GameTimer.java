package co.ppj2.services;

import javafx.application.Platform;
import java.util.HashMap;
import java.util.Map;

public class GameTimer implements Runnable {
    private final Map<String, Long> playerTotalTime; // Total time spent by each player
    private final Map<String, Integer> playerMoves; // Number of moves made by each player
    private String currentPlayer; // Current player whose time is being tracked
    private long startTime; // Start time of the current player's move
    private boolean running; // Timer state

    public GameTimer() {
        playerTotalTime = new HashMap<>();
        playerMoves = new HashMap<>();
        running = false;
    }

    // Starts the timer for the current player
    public synchronized void startTimer(String playerName) {
        if (running) cancelTimer(); // TODO: Check if canceling and starting a new timer is the intended behavior.
        currentPlayer = playerName;
        startTime = System.currentTimeMillis();
        running = true;
        new Thread(this).start(); // TODO: Consider using a scheduled executor service instead of creating a new thread.
    }

    // Stops the timer and records the elapsed time
    public synchronized void cancelTimer() {
        if (!running) return; // TODO: Add error handling in case cancelTimer is called without a running timer.
        long elapsedTime = System.currentTimeMillis() - startTime;
        playerTotalTime.put(currentPlayer, playerTotalTime.getOrDefault(currentPlayer, 0L) + elapsedTime);
        playerMoves.put(currentPlayer, playerMoves.getOrDefault(currentPlayer, 0) + 1);
        running = false;
    }

    // Computes the average time per move for a player
    public synchronized double getAverageTimePerMove(String playerName) {
        long totalTime = playerTotalTime.getOrDefault(playerName, 0L);
        int moves = playerMoves.getOrDefault(playerName, 0);
        return moves == 0 ? 0.0 : (totalTime / (double) moves) / 1000.0; // Convert to seconds
        // TODO: Consider returning a default value (e.g., NaN) if there are no moves instead of 0.0 for clarity.
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(500); // Update every half second if needed
                Platform.runLater(() -> {
                    // TODO: Update GUI components to reflect the current time or status if necessary.
                    // TODO: Consider throttling the updates if they are too frequent and impact performance.
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // TODO: Add more specific error handling if needed.
            }
        }
    }

    // TODO: Add methods to reset the timer or player statistics (wins, losses, etc.) if required.
    // TODO: Consider handling edge cases such as overlapping timer starts or handling time during interruptions.
}

