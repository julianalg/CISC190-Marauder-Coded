package co.ppj2.model;

import java.io.Serializable;

public class Player implements Serializable {
    private final String username; // TODO: Consider adding validation to ensure the username is not null or empty.
    private int wins;
    private int losses;

    // TODO: Consider adding additional parameters like "average time per move" to expand the Player class.
    public Player(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty"); // TODO: Add proper error handling for invalid username.
        }
        this.username = username;
        this.wins = 0;
        this.losses = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    // TODO: Add a method to reset wins and losses if needed (e.g., for new seasons or resets).
    public void incrementWins() {
        this.wins++; // TODO: Consider adding logic to prevent negative wins (just in case).
    }

    public void incrementLosses() {
        this.losses++; // TODO: Consider adding logic to prevent negative losses (just in case).
    }

    // TODO: Override equals and hashCode for better handling of Player object comparisons, especially when storing in collections.
    @Override
    public String toString() {
        return username + " - Wins: " + wins + ", Losses: " + losses; // TODO: Add a more detailed description of the player (e.g., total games played).
    }

    // TODO: Consider adding methods to track additional statistics (e.g., draw count, total games played).
}
