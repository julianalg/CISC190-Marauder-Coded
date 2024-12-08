package edu.sdccd.cisc190.players.bots;

/**
 * Honda Boyz is a bot that will be playing in the background
 * instantiate a new instance of Honda Boyz to implement in the application
 * Max luck and min aura = lowest chances of winning
 */
public class HondaBoyz extends Bot {

    // TODO: Make the HondaBoyz bot a unique instance so it can only exist once.
    private static final HondaBoyz instance = new HondaBoyz(); 

    // TODO: Private constructor for HondaBoyz to prevent other parts of the program from creating new instances.
    private HondaBoyz() {
        // TODO: Calling the parent constructor (Bot) to initialize with money, luck, and aura values.
        super("HondaBoyz", 1000, 1.0, 0.1); // Initial money, luck, and aura values
    }

    // TODO: Create a public method to get the single instance of HondaBoyz.
    public static HondaBoyz getInstance() {
        // TODO: Return the single instance of HondaBoyz.
        return instance; 
    }

}
