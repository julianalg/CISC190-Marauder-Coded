package edu.sdccd.cisc190.services;

import edu.sdccd.cisc190.machines.Slot;
import edu.sdccd.cisc190.players.bots.Bot;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BotService class manages the behavior of a bot interacting with a slot machine.
 * It includes functionality to pause, unpause and spin the bot on the slot machine.
 **/
public class BotService implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotService.class); // TODO: Log messages for bot service actions
    private final Bot bot;           // The bot instance this service manages
    private Slot slotMachine;        // The slot machine the bot interacts with
    private volatile boolean spinFlag = false; // Flag to indicate the bot should spin
    private static final BooleanProperty pauseFlag = new SimpleBooleanProperty(false); // Flag to pause the bot
    private static final Object lock = new Object(); // Shared static lock object

    /**
     * Constructor for BotService
     * @param bot   The bot instance managed by this service
     * @param slotMachine   The slot machine this bot interacts with
     * */
    public BotService(Bot bot, Slot slotMachine) {
        this.bot = bot;  // TODO: Store the bot in the service
        this.slotMachine = slotMachine; // TODO: Store the slot machine in the service
    }

    /**
    *  Returns the bot instance managed by this service
     * @return The bot instance
     */
    public Bot getBot() {
        return bot;  // TODO: Return the bot object when needed
    }

    /**
     * Returns the slot machine instance managed by this service
     * @return the slot machine instance
     */
    public Slot getSlotMachine() {
        return slotMachine;  // TODO: Return the slot machine object
    }

    /**
     * Sets the spin flag to true, triggering a spin for the bot
     * */
    public void triggerSpin() {
        spinFlag = true;  // TODO: Tell the bot to spin by setting the flag to true
    }

    /**
     * Changes the slot machine this bot interacts with
     * @param newSlotMachine The new slot machine to associate with this bot
     * */
    public synchronized void setSlotMachine(Slot newSlotMachine) {
        this.slotMachine = newSlotMachine;  // TODO: Change the slot machine for the bot
    }

    /**
     * Runs the bot service in a separate thread.
     * The bot performs spins on its slot machine when triggered, and respects the pause flag.
     **/
    @SuppressWarnings("BusyWait")  // TODO: This suppresses a warning about busy-waiting. It's not ideal but okay for now.
    @Override
    public void run() {
        while (true) {  // TODO: Infinite loop that keeps the bot running
            try {
                synchronized (lock) {
                    while (pauseFlag.get()) { // TODO: If the pause flag is true, the bot will wait
                        lock.wait(); // Wait if the thread is paused
                    }
                }

                if (spinFlag) {  // TODO: Check if the bot should spin
                    synchronized (this) { // TODO: Ensure thread safety for these operations
                        int newBalance = slotMachine.botPlay(bot); // Simulate the spin on the slot machine
                        bot.setMoney(newBalance); // TODO: Update the bot's balance after the spin
                        LOGGER.info("{} spun on {} and new balance: {}", bot.getName(), slotMachine.getClass().getSimpleName(), newBalance);

                        Platform.runLater(() -> {  // TODO: Add UI updates here if needed
                            // Platform.runLater is where you can update the UI thread
                        });

                        spinFlag = false; // TODO: Reset the spin flag after the spin
                    }
                }

                Thread.sleep(500); // TODO: Sleep for a little bit to avoid overloading the system with too many checks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // TODO: Handle interruption gracefully
                LOGGER.warn("Thread interrupted", e); // TODO: Log any interruption events
                break; // TODO: Exit the loop if interrupted
            }
        }
    }
}
