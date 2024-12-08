package edu.sdccd.cisc190.players.bots;

/**
 * ProfessorHuang is a bot with high aura and solid luck, offering a greater potential for winning.
 * This class implements the Singleton pattern to ensure a single instance is used throughout the application.
 */
public class ProfessorHuang extends Bot {

    // Constants for ProfessorHuang's attributes
    private static final String NAME = "Professor Huang";
    private static final int INITIAL_MONEY = 1000;
    private static final double LUCK = 0.95;
    private static final double AURA = 0.6;

    // Singleton instance
    private static final ProfessorHuang instance = new ProfessorHuang();

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes ProfessorHuang with predefined attributes.
     */
    private ProfessorHuang() {
        super(NAME, INITIAL_MONEY, LUCK, AURA);
    }

    /**
     * Provides the singleton instance of ProfessorHuang.
     *
     * @return The single instance of ProfessorHuang.
     */
    public static ProfessorHuang getInstance() {
        return instance;
    }

    /**
     * Resets ProfessorHuang's money to the initial value.
     * Useful for restarting games or simulations.
     */
    public void resetMoney() {
        setMoney(INITIAL_MONEY);
    }

    /**
     * Returns a string representation of ProfessorHuang's current state.
     *
     * @return A string describing ProfessorHuang's attributes.
     */
    @Override
    public String toString() {
        return String.format("ProfessorHuang{name='%s', money=%d, luck=%.2f, aura=%.2f}", getName(), getMoney(), getLuck(), getAura());
    }
}
