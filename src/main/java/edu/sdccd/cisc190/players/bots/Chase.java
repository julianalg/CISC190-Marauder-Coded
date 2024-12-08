package edu.sdccd.cisc190.players.bots;

/**
 * Chase is a bot that will be playing in the background
 * instantiate a new instance of Chase to implement in the application
 * low luck and aura = low capacity for winning
 */
public class Chase extends Bot {

    private static final Chase instance = new Chase();  // TODO: Whoa, we got an instance right here! One and only, like me!

    private Chase() {
        super("Chase Allan", 1000, 0.25, 0.1); // TODO: Starting money, luck, and aura values. Smooth, right? 1000 bucks, but low luck and aura.
    }

    public static Chase getInstance() {
        return instance;  // TODO: Gimme the instance! We only need one Chase in this world. It's smooth like butter.
    }

}
