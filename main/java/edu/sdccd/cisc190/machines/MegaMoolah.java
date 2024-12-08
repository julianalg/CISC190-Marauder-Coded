package edu.sdccd.cisc190.machines;

import java.util.Objects;

/**
 * Mega Moolah is a type of slot in the casino.
 * A medium-risk, medium-reward slot that uses the super constructor
 * to set values of attributes inherited from Slots.
 */
public class MegaMoolah extends Slot {
    private static final int FULL_MATCH = 3;
    private static final int NO_MATCH = 0;
    private static final double PARTIAL_LOSS_MULTIPLIER = 0.5;
    private static final int FIXED_LOSS_AMOUNT = 15;

    public MegaMoolah() {
        super(new String[]{"\uD83D\uDCB0", "\uD83E\uDD11", "\uD83D\uDCB8"}, 1000, 10, 3);
    }

    /**
     * Overrides the calculatePayout method in Slots.
     * The user loses a fixed amount if they do not get a full match; otherwise, they win
     * a multiple of their bet.
     *
     * @param moneyAmount The amount of money the user currently has.
     * @param spunRow The symbols array the user spun.
     * @param bet The amount of money the user has bet.
     * @return The player's new money amount after payout.
     * @throws IllegalArgumentException if spunRow is null, has incorrect length, or bet is negative.
     */
    @Override
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        Objects.requireNonNull(spunRow, "Spun row cannot be null.");
        if (spunRow.length != 3) {
            throw new IllegalArgumentException("Spun row must contain exactly 3 elements.");
        }
        if (bet < 0) {
            throw new IllegalArgumentException("Bet amount cannot be negative.");
        }

        int winningCondition = evaluateWinCondition(spunRow);
        return switch (winningCondition) {
            case NO_MATCH -> Math.max(moneyAmount - FIXED_LOSS_AMOUNT, 0); // Avoid negative money
            case FULL_MATCH -> (int) (moneyAmount + Math.floor(bet * returnAmt));
            default -> moneyAmount; // No change for other cases
        };
    }
}

