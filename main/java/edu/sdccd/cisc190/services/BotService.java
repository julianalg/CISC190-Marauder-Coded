package edu.sdccd.cisc190.services;

import edu.sdccd.cisc190.machines.Slot;
import edu.sdccd.cisc190.players.bots.Bot;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BotService manages the behavior of a bot interacting with a slot machine.
 * It includes functionality to pause, unpause and spin the bot on the slot machine.
 */
public class BotService implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(BotService.class);
    private final Bot bot;           // The bot instance this service manages
    private volatile Slot slotMachine; // The slot machine the bot interacts with
    private volatile boolean spinFlag = false; // Flag to indicate the bot should spin
    private static final BooleanProperty pauseFlag = new SimpleBooleanProperty(false); // Flag to pause the bot
    private final Lock lock = new ReentrantLock(); // Reentrant lock for synchronization

    /**
     * Constructor for BotService
     * @param bot The bot instance managed by this service
     * @param slotMachine The slot machine this bot interacts with
     */
    public BotService(Bot bot, Slot slotMachine) {
        if (bot == null || slotMachine == null) {
            throw new IllegalArgumentException("Bot and SlotMachine cannot be null");
        }
        this.bot = bot;
        this.slotMachine = slotMachine;
    }

    /**
     * Returns the bot instance managed by this service
     * @return The bot instance
     */
    public Bot getBot() {
        return bot;
    }

    /**
     * Returns the slot machine instance managed by this service
     * @return the slot machine instance
     */
    public Slot getSlotMachine() {
        return slotMachine;
    }

    /**
     * Sets the spin flag to true, triggering a spin for the bot
     */
    public void triggerSpin() {
        spinFlag = true;
    }

    /**
     * Changes the slot machine this bot interacts with
     * @param newSlotMachine The new slot machine to associate with this bot
     */
    public synchronized void setSlotMachine(Slot newSlotMachine) {
        if (newSlotMachine != null) {
            this.slotMachine = newSlotMachine;
        }
    }

    /**
     * Runs the bot service in a separate thread.
     * The bot performs spins on its slot machine when triggered, and respects the pause flag.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Handle pause functionality using the ReentrantLock
                lock.lock();
                try {
                    while (pauseFlag.get()) {
                        lock.wait(); // Wait if the thread is paused
                    }
                } finally {
                    lock.unlock();
                }

                // If the spin flag is true, perform the spin
                if (spinFlag) {
                    performSpin();
                }

                // Sleep for a short time to prevent busy-waiting
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.warn("Thread interrupted", e);
                break;
            }
        }
    }

    /**
     * Executes a spin for the bot and updates the bot's money.
     */
    private void performSpin() {
        try {
            int newBalance = slotMachine.botPlay(bot); // Simulate the spin
            bot.setMoney(newBalance); // Update bot's balance
            LOGGER.info("{} spun on {} and new balance: {}", bot.getName(), slotMachine.getClass().getSimpleName(), newBalance);

            // If you need to update the UI or perform other actions, use Platform.runLater here
            Platform.runLater(() -> {
                // Code to update JavaFX UI
            });

        } catch (Exception e) {
            LOGGER.error("Error during bot spin: ", e);
        } finally {
            spinFlag = false; // Reset the spin flag
        }
    }
}
