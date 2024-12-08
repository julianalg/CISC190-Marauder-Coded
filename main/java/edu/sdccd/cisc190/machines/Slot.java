package edu.sdccd.cisc190.machines;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.players.bots.Bot;

import java.util.Random;

/**
 * Defines the general behavior of a slot machine.
 * A slot machine has a minimum and maximum bet, symbols to display,
 * and can calculate the player's return amount based on spin outcomes.
 *
 * @author YourName
 * @version 1.0
 */
public abstract class Slot {
    protected String[] symbols; // Slot-specific symbols
    protected int maxBet; // Maximum allowed bet
    protected int minBet; // Minimum allowed bet
    protected double returnAmt; // Return multiplier for wins

    // Constants for win conditions
    private static final int FULL_MATCH = 3;
    private static final int NO_MATCH = 0;

    /**
     * Constructor for the Slot class.
     *
     * @param symbols   Array of symbols used in the slot machine.
     * @param maxBet    Maximum bet amount allowed.
     * @param minBet    Minimum bet amount allowed.
     * @param returnAmt Multiplier for calculating returns on wins.
     */
    public Slot(String[] symbols, int maxBet, int minBet, double returnAmt) {
        if (symbols == null || symbols.length == 0) {
            throw new IllegalArgumentException("Symbols array cannot be null or empty.");
        }
        this.symbols = symbols;
        this.maxBet = maxBet;
        this.minBet = minBet;
        this.returnAmt = returnAmt;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public int getMinBet() {
        return minBet;
    }

    public double getReturnAmt() {
        return returnAmt;
    }

    /**
     * Validates if a bet amount is within the allowed range and the player's balance.
     *
     * @param betAmt The amount the user is attempting to bet.
     * @return True if the bet is valid, false otherwise.
     */
    public boolean canBet(int betAmt) {
        int playerMoney = HumanPlayer.getInstance().getMoney();
        return betAmt >= minBet && betAmt <= maxBet && betAmt <= playerMoney;
    }

    /**
     * Generates a random set of spun symbols.
     *
     * @return An array of randomly selected symbols.
     */
    public String[] generateSpunSymbols() {
        Random rand = new Random();
        String[] spunSlots = new String[3]; // Fixed to 3 slots for simplicity
        for (int i = 0; i < spunSlots.length; i++) {
            spunSlots[i] = symbols[rand.nextInt(symbols.length)];
        }
        return spunSlots;
    }

    /**
     * Evaluates the win condition of a spin.
     *
     * @param arr Array of spun symbols.
     * @return FULL_MATCH if all symbols match, otherwise NO_MATCH.
     */
    public int evaluateWinCondition(String[] arr) {
        if (arr == null || arr.length < 3) {
            throw new IllegalArgumentException("Invalid spun symbols array.");
        }
        return (arr[0].equals(arr[1]) && arr[1].equals(arr[2])) ? FULL_MATCH : NO_MATCH;
    }

    /**
     * Calculates the player's new balance after a spin.
     *
     * @param moneyAmount The player's current balance.
     * @param spunRow     The result of the spin (symbols array).
     * @param bet         The amount bet by the player.
     * @return The player's updated balance.
     */
    public int calculatePayout(int moneyAmount, String[] spunRow, int bet) {
        int winningCondition = evaluateWinCondition(spunRow);
        return switch (winningCondition) {
            case NO_MATCH -> moneyAmount - bet;
            case FULL_MATCH -> (int) (moneyAmount + Math.floor(bet * returnAmt));
            default -> moneyAmount;
        };
    }

    /**
     * Simulates a bot playing the slot machine.
     *
     * @param bot The bot player.
     * @return The bot's updated money amount.
     */
    public int botPlay(Bot bot) {
        double betVarianceMultiplier = 0.8 + (Math.random() * 0.4); // Range [0.8, 1.2]
        int bet = (int) (bot.getMoney() * bot.getAura() * betVarianceMultiplier);

        // Simulate win/loss based on bot's luck
        boolean botWins = Math.random() <= bot.getLuck();
        return botWins ? bot.getMoney() + bet : bot.getMoney() - bet;
    }
}
