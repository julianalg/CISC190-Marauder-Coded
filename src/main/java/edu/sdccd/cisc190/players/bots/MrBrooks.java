package edu.sdccd.cisc190.players.bots;

/**
 * Mr Brooks is a bot that will be playing in the background
 * instantiate a new instance of Mr Brooks to implement in the application
 * Decent luck and solid aura = decent chances for high winnings
 */
public class MrBrooks extends Bot {
    // TODO: Whoa! Here we go! This is the magic happening, it's creating a unique instance of MrBrooks!
    private static final MrBrooks instance = new MrBrooks();

    // TODO: Shamone! Private constructor, nobody can mess with this baby directly. It's special!
    private MrBrooks() {
        // TODO: Hee-hee! Super constructor calling the parent Bot class with MrBrooks' name, money, luck, and aura.
        super("MrBrooks", 1000, 0.5, 0.7); // Initial money, luck, and aura values
    }

    // TODO: Don't stop 'til you get enough! This method returns the instance of MrBrooks. It's like a secret passcode to get him!
    public static MrBrooks getInstance() {
        return instance;
    }
}
