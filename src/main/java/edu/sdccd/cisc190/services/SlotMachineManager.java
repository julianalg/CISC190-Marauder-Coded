import edu.sdccd.cisc190.machines.*;
import edu.sdccd.cisc190.players.bots.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages slot machines and bots interacting with them.
 * Handles the creation, assignment, rotation and control of bot threads interacting with different slot machines
 * */

public class SlotMachineManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlotMachineManager.class);

    // Instances of slot machines
    static DiamondDash diamondDash = new DiamondDash(); // TODO: Create a DiamondDash machine
    static HondaTrunk hondaTrunk = new HondaTrunk(); // TODO: Create a HondaTrunk machine
    static MegaMoolah megaMoolah = new MegaMoolah(); // TODO: Create a MegaMoolah machine
    static RainbowRiches rainbowRiches = new RainbowRiches(); // TODO: Create a RainbowRiches machine
    static TreasureSpins treasureSpins = new TreasureSpins(); // TODO: Create a TreasureSpins machine

    // Lists to manage bot threads and services
    private static volatile boolean stopRequested = false; // TODO: This will stop the bots when it's true
    public static List<Thread> botThreads = new ArrayList<>(); // TODO: Keep track of all the threads for bots
    public static List<BotService> botServices = new ArrayList<>(); // TODO: Keep track of bot services

    /**
     * Getter that to obtain the boolean value of stopRequested
     * @return the value of stopRequested (true or false)
     */
    public static boolean getStopRequested() {
        return stopRequested; // TODO: Return if stopping is requested
    }

    /**
     * Main method to initialize and manage bot services and slot machines
     * Assigns bots to slot machines, starts their threads, and manages periodic tasks
     * */
    public static void main() {
        LOGGER.info("Initializing SlotMachineManager");

        // List of bots
        List<Bot> bots = List.of(
                Chase.getInstance(), // TODO: Get an instance of Chase bot
                HondaBoyz.getInstance(), // TODO: Get an instance of HondaBoyz bot
                MrBrooks.getInstance(), // TODO: Get an instance of MrBrooks bot
                ProfessorHuang.getInstance(), // TODO: Get an instance of ProfessorHuang bot
                AnitaMaxWynn.getInstance() // TODO: Get an instance of AnitaMaxWynn bot
        );

        // List of slot machines
        List<Slot> slotMachines = List.of(diamondDash, hondaTrunk, megaMoolah, rainbowRiches, treasureSpins);

        // Start a service for each bot
        for (int i = 0; i < bots.size(); i++) {
            Bot bot = bots.get(i); // TODO: Get the bot from the list
            Slot machine = slotMachines.get(i % slotMachines.size()); // Assign initial machine, wrap around if needed
            BotService botService = new BotService(bot, machine); // TODO: Create a BotService for the bot

            // Wrap botService in a thread and start it
            Thread botThread = new Thread(botService); // TODO: Wrap the botService in a thread
            botThread.start(); // TODO: Start the bot thread
            botThreads.add(botThread); // TODO: Add bot thread to the list
            botServices.add(botService); // TODO: Add bot service to the list

            // Log the bot's assignment
            LOGGER.debug("Assigned {} to {}", bot.getName(), machine.getClass().getSimpleName());

            // Periodically trigger spins for this bot
            Thread spinThread = getThread(botService); // TODO: Create a spin thread for the bot
            botThreads.add(spinThread); // TODO: Add the spin thread to the list
        }

        // Start a thread to rotate machines
        Thread rotationThread = getThread(slotMachines); // TODO: Create a thread to rotate slot machines
        botThreads.add(rotationThread); // TODO: Add the rotation thread to the list
    }

    @SuppressWarnings("BusyWait")
    private static Thread getThread(List<Slot> slotMachines) {
        Thread rotationThread = new Thread(() -> {
            try {
                while (!stopRequested) {
                    Thread.sleep(60000); // Rotate machines every 60 seconds
                    if (stopRequested) break; // Stop if requested
                    rotateSlotMachines(slotMachines); // Rotate machines
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.info("Thread has been interrupted", e); // TODO: Log if thread is interrupted
            }
        });

        rotationThread.start(); // TODO: Start the rotation thread
        return rotationThread; // TODO: Return the thread so we can add it to the list
    }

    @SuppressWarnings("BusyWait")
    private static Thread getThread(BotService botService) {
        Thread spinThread = new Thread(() -> {
            try {
                while (!stopRequested) {
                    Thread.sleep((long) (Math.random() * 6000 + 5000)); // TODO: Random delay between 5-11 seconds
                    if (stopRequested) break; // Stop if requested
                    botService.triggerSpin(); // TODO: Trigger the bot's spin
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.info("Thread has been interrupted", e); // TODO: Log if thread is interrupted
            }
        });

        spinThread.start(); // TODO: Start the spin thread
        return spinThread; // TODO: Return the spin thread to be added to the list
    }

    /**
     * Rotates the slot machines assigned to bots.
     * Each bot is moved to the next slot machine in the list.
     * @param slotMachines The list of available slot machines
     * */
    private static void rotateSlotMachines(List<Slot> slotMachines) {
        for (int i = 0; i < botServices.size(); i++) {
            BotService botService = botServices.get(i); // Get the bot service
            Slot newMachine = slotMachines.get((i + 1) % slotMachines.size()); // Rotate to the next machine
            botService.setSlotMachine(newMachine); // TODO: Set new machine for the bot
            LOGGER.info("Rotated {} to {}", botService.getBot().getName(), newMachine.getClass().getSimpleName());
        }
    }

    /**
     * Stops all threads managed by this class.
     * Signals threads to stop and interrupts them to end execution
     * */
    public static void stopAllThreads() {
        stopRequested = true; // TODO: Set stopRequested to true

        // Interrupt all bot threads
        for (Thread botThread : botThreads) {
            if (botThread.isAlive()) { // TODO: Check if the bot thread is still running
                try {
                    botThread.interrupt(); // TODO: Interrupt the bot thread
                    botThread.join(1000); // Wait for 1 second for the thread to finish
                } catch (InterruptedException e) {
                    LOGGER.warn("Failed to stop thread: {}", botThread.getName(), e); // TODO: Log any failure stopping a thread
                }
            }
        }

        LOGGER.info("All threads have been stopped."); // TODO: Log that all threads have been stopped
    }

    /**
     * Resets all threads to their original state
     * Used for junit testing
     */
    public static void reset() {
        stopRequested = false; // TODO: Reset stopRequested to false
        botThreads.clear(); // TODO: Clear the list of bot threads
        botServices.clear(); // TODO: Clear the list of bot services
    }
}
