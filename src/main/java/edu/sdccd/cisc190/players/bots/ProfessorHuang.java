package edu.sdccd.cisc190.players.bots;

/**
 * Professor Huang is a bot that will be playing in the background
 * instantiate a new instance of Professor Huang to implement in the application
 * High aura and solid luck attributes = greater potential for winning
 */
public class ProfessorHuang extends Bot {
    // TODO: Let's make a unique instance of Professor Huang. It's a single instance! Just one, baby! 
    private static final ProfessorHuang instance = new ProfessorHuang();

    // TODO: This constructor makes Professor Huang special! He’s got his name, money, luck, and aura here. You gotta give him some love.
    private ProfessorHuang() {
        super("Professor Huang", 1000, 0.95, 0.6); // Initial money, luck, and aura values
    }

    // TODO: Get this unique Professor Huang out into the world, let him shine! This method gets the instance, don’t make a new one! Keep it one and only!
    public static ProfessorHuang getInstance() {
        return instance;
    }

}
