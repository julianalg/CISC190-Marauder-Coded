package edu.sdccd.cisc190.machines;


/**
 * Honda Trunk is a type of slot in the casino
 * Uses the super constructor to set values of attributes inherited from Slots
 * low risk, varying payout slot
 */
public class HondaTrunk extends Slot {
    
    // TODO: Hey, this constructor here sets up the symbols and values 
    // for the HondaTrunk slot machine. You know, like "🚗", "🛻", "🚕"!
    public HondaTrunk() {
        super(new String[]{"🚗", "🛻", "🚕"}, 1000, 1, 1.5); // Set symbols, max payout, min bet, return multiplier
    }

    /**
     * Overrides the evaluateWinCondition() method in Slots
     * Allows the user to win some money even if they only get a partial match
     * @param arr Array of random symbols generated from the generateSpunSymbols() method
     * @return if the user spun a 3 match, 2 match, or no match
     */
    @Override
    public int evaluateWinCondition(String[] arr) {
        // TODO: Yo, check if all symbols match, we got a full win! 
        if (arr[0].equals(arr[1]) && arr[1].equals(arr[2])) {
            return 3; // Full match! Wow! That's a win! 
        } else if (arr[0].equals(arr[1]) || arr[1].equals(arr[2]) || arr[0].equals(arr[2])) {
            return 2; // Partial match, still good enough to win something, baby!
        } else {
            return 0; // No match, oh no! You lost your chance, gotta try again!
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
        // TODO: Check if the player won something based on their spin
        int winningCondition = evaluateWinCondition(spunRow);
        
        // TODO: Let's figure out how much money they will get based on what they won, hee-hee!
        return switch (winningCondition) {
            case 0 -> // No match
                    moneyAmount - bet; // Ooooh, no match! You lost your bet, baby!
            case 2 -> 
                    (int) (moneyAmount + Math.floor(bet * returnAmt * 0.25)); // Partial match, you still get some payout, yeah!
            case 3 -> // Three-symbol match
                    (int) (moneyAmount + Math.floor(bet * returnAmt)); // Full match, get the jackpot, huh!
            default -> moneyAmount; // If we don't know what happened, just keep what you had
        };
    }

}
