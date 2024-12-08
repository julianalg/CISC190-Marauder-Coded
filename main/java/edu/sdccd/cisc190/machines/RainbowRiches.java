package edu.sdccd.cisc190.machines;

/**
 * Rainbow Riches is a type of slot machine in the casino.
 * It uses the super constructor to set values of attributes inherited from the Slot class.
 * This slot machine has medium to high risk and medium to high reward.
 *
 * Symbols: 🌈 (rainbow), 🌧️ (rain), 🌤️ (sun)
 * Jackpot: 1000
 * Bet per spin: 25
 * Reels: 5
 */
public class RainbowRiches extends Slot {

    // Constants for better readability and maintainability
    private static final String[] SYMBOLS = {"\uD83C\uDF08", "\uD83C\uDF27", "\uD83C\uDF24"}; // 🌈, 🌧️, 🌤️
    private static final int JACKPOT = 1000;  // Jackpot amount
    private static final int BET_PER_SPIN = 25;  // Bet amount per spin
    private static final int REELS = 5;  // Number of reels

    /**
     * Constructor for RainbowRiches slot machine.
     */
    public RainbowRiches() {
        super(SYMBOLS, JACKPOT, BET_PER_SPIN, REELS);
    }
}
