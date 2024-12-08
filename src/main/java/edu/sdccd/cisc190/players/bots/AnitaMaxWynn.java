package edu.sdccd.cisc190.players.bots;

/**
 * Anita Max Wynn is a bot that will be playing in the background
 * instantiate a new instance of Anita Max Wynn to implement in the application
 * High luck, low aura = decent chances of winning
 */
public class AnitaMaxWynn extends Bot {
    // TODO: Hee-hee! This is the one and only instance of Anita Max Wynn, so we keep it private and static
    private static final AnitaMaxWynn instance = new AnitaMaxWynn();

    // TODO: Shamone! Here we create the constructor for Anita Max Wynn! It's private, baby, so no one can mess with it directly.
    private AnitaMaxWynn() {
        super("Anita Max Wynn", 1000, 0.8, 0.3); // Initial money (1000), luck (0.8), and aura (0.3) values, just like MJ's rhythm
    }

    // TODO: Woooo! This is how you get the one and only Anita Max Wynn from the class. No duplicates, just one smooth operator.
    public static AnitaMaxWynn getInstance() {
        return instance;
    }
}
