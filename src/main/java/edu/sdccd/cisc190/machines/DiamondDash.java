package edu.sdccd.cisc190.machines;

/**
 * Diamond Dash is a type of slot in the casino
 * Uses the super constructor to set values of attributes inherited from Slots
 * low risk, varying payout slot
 */
public class DiamondDash extends Slot {
    public DiamondDash() {
        // todo: Super constructor is called to initialize the slot with specific symbols and payout values
        super(new String[]{"💍", "💠", "💎"}, 1000, 15, 2);
    }

    /**
     * Overrides method in Slots
     * If player does not get full match, they only lose half their bet
     * @param moneyAmount The amount of money the user currently has
     * @param spunRow the symbols array the user spun
     * @param bet The amount of money the user has bet
     * @return the user's new money after payout
     */
    @Override
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        // todo: Check the result of the spunRow to see if there is a win condition
        int winningCondition = evaluateWinCondition(spunRow);

        // todo: Use switch-case to handle different winning conditions
        return switch (winningCondition) {
            case 0 -> // No match, so the player loses half their bet
                    (int) (moneyAmount - bet * 0.5); // Half of the bet is subtracted from the user's money
            case 3 -> // Three-symbol match, so the player wins based on the payout
                    (int) (moneyAmount + Math.floor(bet * returnAmt)); // Player wins money based on bet and returnAmt
            default -> moneyAmount; // todo: If no win condition is met, the money stays the same
        };
    }
}
