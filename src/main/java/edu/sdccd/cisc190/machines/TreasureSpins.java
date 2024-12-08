package edu.sdccd.cisc190.machines;

/**
 * Treasure Spins is a type of slot in the casino
 * Uses the super constructor to set values of attributes inherited from Slots
 * High risk, high reward slot
 */
public class TreasureSpins extends Slot {
    
    // TODO: This constructor creates a new TreasureSpins object
    // It's calling the super constructor from the parent class 'Slot' 
    // and setting up the values for the symbols, jackpot, max bet, and spin cost.
    // Now let's move to the next step, alright? Smooth criminal.
    
    public TreasureSpins() {
        // TODO: Super constructor is being called here to set the values.
        // "\uD83C\uDF53" is a cherry emoji, "\uD83C\uDF4C" is a grape emoji, and "\uD83C\uDF4A" is an apple emoji.
        // We set these values for the symbols in the slot machine. 
        // The jackpot is set to 1000, max bet is 50, and spin cost is 10. Keep it locked, baby.
        super(new String[]{"\uD83C\uDF53", "\uD83C\uDF4C", "\uD83C\uDF4A"}, 1000, 50, 10);
    }
}
