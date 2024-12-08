package edu.sdccd.cisc190.machines;

/**
 * Treasure Spins is a high-risk, high-reward slot machine in the casino.
 * It uses the super constructor to set values for attributes inherited from the Slot class.
 *
 * Symbols: 🍓 (strawberry), 🍌 (banana), 🍊 (orange)
 * Jackpot: 1000
 * Minimum Bet: 50
 * Return Multiplier: 10
 *
 * @version 1.0
 */
public class TreasureSpins extends Slot {

    // Constants for better maintainability
    private static final String[] SYMBOLS = {"\uD83C\uDF53", "\uD83C\uDF4C", "\uD83C\uDF4A"}; // 🍓, 🍌, 🍊
    private static final int JACKPOT = 1000;  // Jackpot amount
    private static final int MIN_BET = 50;    // Minimum bet amount
    private static final int REELS = 10;      // Return multiplier (risk/reward)

    /**
     * Constructor for TreasureSpins slot machine.
     */
    public TreasureSpins() {
        super(SYMBOLS, JACKPOT, MIN_BET, REELS);
    }
}
