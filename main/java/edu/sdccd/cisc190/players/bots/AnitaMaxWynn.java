package edu.sdccd.cisc190.players.bots;

/**
 * Anita Max Wynn is a bot player in the casino.
 * This bot is designed with high luck and low aura, giving it decent chances of winning.
 *
 * Singleton pattern is used to ensure only one instance of this bot exists.
 *
 * @version 1.0
 * @see Bot
 */
public class AnitaMaxWynn extends Bot {

    // Singleton instance of Anita Max Wynn
    private static final AnitaMaxWynn instance = new AnitaMaxWynn();

    // Configuration constants
    private static final String NAME = "Anita Max Wynn";
    private static final int INITIAL_MONEY = 1000;
    private static final double LUCK = 0.8; // High luck
    private static final double AURA = 0.3; // Low aura

    /**
     * Private constructor for Singleton pattern.
     * Initializes the bot's attributes using the super constructor from the Bot class.
     */
    private AnitaMaxWynn() {
        super(NAME, INITIAL_MONEY, LUCK, AURA);
    }

    /**
     * Retrieves the Singleton instance of Anita Max Wynn.
     *
     * @return The single instance of Anita Max Wynn.
     */
    public static AnitaMaxWynn getInstance() {
        return instance;
    }
}
