package edu.sdccd.cisc190.services;

import edu.sdccd.cisc190.machines.*;
import edu.sdccd.cisc190.players.bots.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public class SlotMachineManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlotMachineManager.class);

    // Instances of slot machines
    static DiamondDash diamondDash = new DiamondDash();
    static HondaTrunk hondaTrunk = new HondaTrunk();
    static MegaMoolah megaMoolah = new MegaMoolah();
    static RainbowRiches rainbowRiches = new RainbowRiches();
    static TreasureSpins treasureSpins = new TreasureSpins();

    // Executor Service to handle thread pool management
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    // List to manage bot services
    private static volatile boolean stopRequested = false;
    public static List<BotService> botServices = new ArrayList<>();

    /**
     * Getter that returns the value of stopRequested
     * @return the value of stopRequested (true or false)
     */
    public static boolean getStopRequested() {
        return stopRequested;
    }

    /**
     * Main method to initialize and manage bot services and slot machines
     * Assigns bots to slot machines, starts their threads, and manages periodic tasks
     */
    public static void main() {
        LOGGER.info("Initializing SlotMachineManager");

        // List of bots
        List<Bot> bots = List.of(
                Chase.getInstance(),
                HondaBoyz.getInstance(),
                MrBrooks.getInstance(),
                ProfessorHuang.getInstance(),
                AnitaMaxWynn.getInstance()
        );

        // List of slot machines
        List<Slot> slotMachines = List.of(diamondDash, hondaTrunk, megaMoolah, rainbowRiches, treasureSpins);

        // Start a service for each bot
        for (int i = 0; i < bots.size(); i++) {
            Bot bot = bots.get(i);
            Slot machine = slotMachines.get(i % slotMachines.size()); // Assign initial machine
            BotService botService = new BotService(bot, machine);

            // Wrap botService in a task and submit it to the executor service
            executorService.submit(botService);
            botServices.add(botService);

            // Log the bot's assignment
            LOGGER.debug("Assigned {} to {}", bot.getName(), machine.getClass().getSimpleName());

            // Periodically trigger spins for this bot
            executorService.submit(() -> spinTask(botService));
        }

        // Start a task to rotate machines periodically
        executorService.submit(() -> rotateSlotMachines(slotMachines));
    }

    /**
     * Periodically triggers spins for the given botService
     * @param botService The BotService instance to manage the bot's spin
     */
    private static void spinTask(BotService botService) {
        try {
            while (!stopRequested) {
                Thread.sleep((long) (Math.random() * 6000 + 5000));
                if (stopRequested) break;
                botService.triggerSpin();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.info("Spin task interrupted for {}", botService.getBot().getName(), e);
        }
    }

    /**
     * Rotates the slot machines assigned to bots.
     * Each bot is moved to the next slot machine in the list.
     * @param slotMachines The list of available slot machines
     */
    private static void rotateSlotMachines(List<Slot> slotMachines) {
        try {
            while (!stopRequested) {
                Thread.sleep(60000); // Rotate machines every minute
                if (stopRequested) break;

                for (int i = 0; i < botServices.size(); i++) {
                    BotService botService = botServices.get(i);
                    Slot newMachine = slotMachines.get((i + 1) % slotMachines.size()); // Rotate to the next machine
                    botService.setSlotMachine(newMachine);
                    LOGGER.info("Rotated {} to {}", botService.getBot().getName(), newMachine.getClass().getSimpleName());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.info("Rotation task interrupted", e);
        }
    }

    /**
     * Stops all threads managed by this class.
     * Signals threads to stop and interrupts them to end execution
     */
    public static void stopAllThreads() {
        stopRequested = true;
        LOGGER.info("Stopping all threads...");

        // Shut down the executor service
        executorService.shutdownNow();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                LOGGER.warn("Some tasks did not terminate in time.");
            }
        } catch (InterruptedException e) {
            LOGGER.warn("Thread interruption during shutdown", e);
            Thread.currentThread().interrupt();
        }

        LOGGER.info("All threads have been stopped.");
    }

    /**
     * Resets all threads to original state
     * Used for JUnit testing
     */
    public static void reset() {
        stopRequested = false;
        botServices.clear();
        executorService.shutdownNow();
    }
}
