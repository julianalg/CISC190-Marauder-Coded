package edu.sdccd.cisc190.machines;

/**
 * Diamond Dash is a type of slot in the casino.
 * A low-risk, varying payout slot that uses the super constructor
 * to set values of attributes inherited from Slots.
 */
public class DiamondDash extends Slot {
    private static final int NO_MATCH = 0;
    private static final int FULL_MATCH = 3;
    private static final double PARTIAL_LOSS_FACTOR = 0.5;

    public DiamondDash() {
        super(new String[]{"💍", "💠", "💎"}, 1000, 15, 2);
    }

    /**
     * Overrides the calculatePayout method in Slots.
     * If the player does not get a full match, they lose half their bet.
     *
     * @param moneyAmount The amount of money the user currently has.
     * @param spunRow The symbols array the user spun.
     * @param bet The amount of money the user has bet.
     * @return The user's new money amount after payout.
     * @throws IllegalArgumentException if bet is negative or spunRow is null.
     */
    @Override
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        if (spunRow == null || bet < 0) {
            throw new IllegalArgumentException("Invalid input: spunRow cannot be null and bet must be non-negative.");
        }

        int winningCondition = evaluateWinCondition(spunRow);
        return switch (winningCondition) {
            case NO_MATCH -> (int) (moneyAmount - bet * PARTIAL_LOSS_FACTOR);
            case FULL_MATCH -> (int) (moneyAmount + Math.floor(bet * returnAmt));
            default -> moneyAmount; // Default to no change for other conditions.
        };
    }
}
