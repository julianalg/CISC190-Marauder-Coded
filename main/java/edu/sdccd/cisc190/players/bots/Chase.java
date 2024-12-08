package edu.sdccd.cisc190.players.bots;

/**
 * Chase is a bot with low luck and aura, representing low capacity for winning.
 * This class implements the Singleton pattern to ensure a single instance is used throughout the application.
 */
public class Chase extends Bot {

    // Constants for Chase's attributes
    private static final String NAME = "Chase Allan";
    private static final int INITIAL_MONEY = 1000;
    private static final double LUCK = 0.25;
    private static final double AURA = 0.1;

    // Singleton instance
    private static final Chase instance = new Chase();

    /**
     * Private constructor to enforce Singleton pattern.
     */
    private Chase() {
        super(NAME, INITIAL_MONEY, LUCK, AURA); // Initialize with predefined values
    }

    /**
     * Provides the singleton instance of Chase.
     *
     * @return The single instance of Chase.
     */
    public static Chase getInstance() {
        return instance;
    }

    /**
     * Resets Chase's money to the initial value.
     * Useful for restarting games or simulations.
     */
    public void resetMoney() {
        setMoney(INITIAL_MONEY);
    }

    /**
     * Returns a string representation of Chase's current state.
     *
     * @return A string describing Chase's attributes.
     */
    @Override
    public String toString() {
        return String.format("Chase{name='%s', money=%d, luck=%.2f, aura=%.2f}", getName(), getMoney(), getLuck(), getAura());
    }
}
