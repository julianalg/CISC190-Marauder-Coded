package edu.sdccd.cisc190.players.bots;

/**
 * HondaBoyz is a bot with maximum luck and minimum aura, representing low chances of winning.
 * This class follows the Singleton pattern to ensure only one instance exists throughout the application.
 */
public class HondaBoyz extends Bot {

    // Constants for HondaBoyz's attributes
    private static final String NAME = "HondaBoyz";
    private static final int INITIAL_MONEY = 1000;
    private static final double MAX_LUCK = 1.0;
    private static final double MIN_AURA = 0.1;

    // Singleton instance
    private static final HondaBoyz instance = new HondaBoyz();

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes HondaBoyz with predefined attributes.
     */
    private HondaBoyz() {
        super(NAME, INITIAL_MONEY, MAX_LUCK, MIN_AURA);
    }

    /**
     * Provides the singleton instance of HondaBoyz.
     *
     * @return The single instance of HondaBoyz.
     */
    public static HondaBoyz getInstance() {
        return instance;
    }

    /**
     * Resets HondaBoyz's money to the initial value.
     * Useful for restarting games or simulations.
     */
    public void resetMoney() {
        setMoney(INITIAL_MONEY);
    }

    /**
     * Returns a string representation of HondaBoyz's current state.
     *
     * @return A string describing HondaBoyz's attributes.
     */
    @Override
    public String toString() {
        return String.format("HondaBoyz{name='%s', money=%d, luck=%.2f, aura=%.2f}", getName(), getMoney(), getLuck(), getAura());
    }
}
