package edu.sdccd.cisc190.machines;


/**
 * Mega Moolah is a type of slot in the casino
 * Uses the super constructor to set values of attributes inherited from Slots
 * Medium risk, medium reward slot
 */
public class MegaMoolah extends Slot {
    
    // TODO: Constructor to initialize the MegaMoolah slot machine
    // Using the super constructor to set the slot symbols, payout, min bet, and number of symbols per row
    public MegaMoolah() {
        super(new String[]{"\uD83D\uDCB0", "\uD83E\uDD11", "\uD83D\uDCB8"}, 1000, 10, 3);
    }

    /**
     * Overrides the calculatePayout method in Slots
     * User only lose $15 if they do not get a full match, else they win 3 times their bet
     * @param moneyAmount The amount of money the user currently has
     * @param spunRow The result of the slot spin (an array of symbols)
     * @param bet The amount of money the user has bet
     * @return the player's new money after payout
     */
    @Override
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        // TODO: Check if the user wins or loses based on the spun symbols
        int winningCondition = evaluateWinCondition(spunRow);
        
        // TODO: Handle the different payout cases based on the winning condition
        return switch (winningCondition) {
            case 0 -> // No match
                    // TODO: If no match, subtract a small amount of the user's money (losing $15)
                    (int) (moneyAmount - Math.floor(minBet * returnAmt * 0.5)); 
            case 3 -> // Three-symbol match
                    // TODO: If the user gets 3 matching symbols, give them 3 times their bet as reward
                    (int) (moneyAmount + Math.floor(bet * returnAmt)); 
            default -> moneyAmount; // If not a win or loss, return the same amount of money
        };
    }

}
