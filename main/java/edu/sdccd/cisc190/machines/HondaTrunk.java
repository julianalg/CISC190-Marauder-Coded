package edu.sdccd.cisc190.machines;

import java.util.Objects;

/**
 * Honda Trunk is a type of slot in the casino.
 * A low-risk, varying payout slot that uses the super constructor
 * to set values of attributes inherited from Slots.
 */
public class HondaTrunk extends Slot {
    private static final int FULL_MATCH = 3;
    private static final int PARTIAL_MATCH = 2;
    private static final int NO_MATCH = 0;
    private static final double PARTIAL_PAYOUT_MULTIPLIER = 0.25;

    public HondaTrunk() {
        super(new String[]{"🚗", "🛻", "🚕"}, 1000, 1, 1.5);
    }

    /**
     * Overrides the evaluateWinCondition() method in Slots.
     * Allows the user to win some money even if they only get a partial match.
     *
     * @param arr Array of random symbols generated from the generateSpunSymbols() method.
     * @return An integer representing whether the user spun a 3-match, 2-match, or no match.
     * @throws IllegalArgumentException if arr is null or does not have exactly 3 elements.
     */
    @Override
    public int evaluateWinCondition(String[] arr) {
        Objects.requireNonNull(arr, "Input array cannot be null.");
        if (arr.length != 3) {
            throw new IllegalArgumentException("Input array must contain exactly 3 elements.");
        }

        if (arr[0].equals(arr[1]) && arr[1].equals(arr[2])) {
            return FULL_MATCH;
        } else if (arr[0].equals(arr[1]) || arr[1].equals(arr[2]) || arr[0].equals(arr[2])) {
            return PARTIAL_MATCH;
        } else {
            return NO_MATCH;
        }
    }

    /**
     * Overrides the calculatePayout method in Slots.
     * If the user gets a partial match, they win a quarter of the full match payout.
     *
     * @param moneyAmount The amount of money the user currently has.
     * @param spunRow The symbols array the user spun.
     * @param bet The amount of money the user has bet.
     * @return The user's new money amount after payout.
     * @throws IllegalArgumentException if spunRow is null or bet is negative.
     */
    @Override
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        Objects.requireNonNull(spunRow, "Spun row cannot be null.");
        if (bet < 0) {
            throw new IllegalArgumentException("Bet amount cannot be negative.");
        }

        int winningCondition = evaluateWinCondition(spunRow);
        return switch (winningCondition) {
            case NO_MATCH -> moneyAmount - bet;
            case PARTIAL_MATCH -> (int) (moneyAmount + Math.floor(bet * returnAmt * PARTIAL_PAYOUT_MULTIPLIER));
            case FULL_MATCH -> (int) (moneyAmount + Math.floor(bet * returnAmt));
            default -> moneyAmount; // Should never occur.
        };
    }
}


/**
     * Overrides method in Slots
     * If user gets a partial match, they win a quarter of the full match payout
     * @param moneyAmount The amount of money the user currently has
     * @param spunRow the symbols array the user spun
     * @param bet The amount of money the user has bet
     * @return the user's new money after payout
     */
    @Override
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        int winningCondition = evaluateWinCondition(spunRow);
        return switch (winningCondition) {
            case 0 -> // No match
                    moneyAmount - bet;
            case 2 ->
                    (int) (moneyAmount + Math.floor(bet * returnAmt * 0.25));
            case 3 -> // Three-symbol match
                    (int) (moneyAmount + Math.floor(bet * returnAmt));
            default -> moneyAmount;
        };
    }

}
