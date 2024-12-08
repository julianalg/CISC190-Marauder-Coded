package edu.sdccd.cisc190.players.bots;

/**
 * MrBrooks is a bot with decent luck and solid aura, providing decent chances for high winnings.
 * This class implements the Singleton pattern to ensure a single instance is used throughout the application.
 */
public class MrBrooks extends Bot {

    // Constants for MrBrooks's attributes
    private static final String NAME = "MrBrooks";
    private static final int INITIAL_MONEY = 1000;
    private static final double LUCK = 0.5;
    private static final double AURA = 0.7;

    // Singleton instance
    private static final MrBrooks instance = new MrBrooks();

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes MrBrooks with predefined attributes.
     */
    private MrBrooks() {
        super(NAME, INITIAL_MONEY, LUCK, AURA);
    }

    /**
     * Provides the singleton instance of MrBrooks.
     *
     * @return The single instance of MrBrooks.
     */
    public static MrBrooks getInstance() {
        return instance;
    }

    /**
     * Resets MrBrooks's money to the initial value.
     * Useful for restarting games or simulations.
     */
    public void resetMoney() {
        setMoney(INITIAL_MONEY);
    }

    /**
     * Returns a string representation of MrBrooks's current state.
     *
     * @return A string describing MrBrooks's attributes.
     */
    @Override
    public String toString() {
        return String.format("MrBrooks{name='%s', money=%d, luck=%.2f, aura=%.2f}", getName(), getMoney(), getLuck(), getAura());
    }
}
