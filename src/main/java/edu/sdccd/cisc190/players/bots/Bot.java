package edu.sdccd.cisc190.players.bots;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Create the behavior of the bots that will be playing in the background
 * Getters and setters to obtain the bots' name, money, luck, and aura to display and run their play
 */
public abstract class Bot {
    // TODO: "Hee-hee! Define the bot's name. This is something special, baby!"
    private final String name;
    
    // TODO: "Shamon! Define the bot's money using a special property."
    private final IntegerProperty money = new SimpleIntegerProperty();
    
    // TODO: "Luck is what makes things happen! Set the bot's luck."
    private final double luck;
    
    // TODO: "Aura is all about the vibes! Set the bot's aura."
    private final double aura;

    // TODO: "Hee-hee! Constructor for the Bot. It sets the name, starting money, luck, and aura."
    public Bot(String name, int initialMoney, double luck, double aura) {
        this.name = name; // TODO: "The name of the bot, baby!"
        this.money.set(initialMoney); // TODO: "Starting money for the bot, it's the thrill of the game!"
        this.luck = luck; // TODO: "Luck sets the energy right, yeah!"
        this.aura = aura; // TODO: "Aura defines the bot's vibes, can't fake that!"
    }

    // TODO: "Wanna know the bot's name? Here's how you get it!"
    public String getName() {
        return name; // TODO: "You got it, you know their name now!"
    }

    // TODO: "Get the bot's money, just like that!"
    public int getMoney() {
        return money.get(); // TODO: "Money talks, baby!"
    }

    // TODO: "Set the bot's money, let's make 'em rich or poor!"
    public void setMoney(int value) {
        money.set(value); // TODO: "Change the money, make the game interesting!"
    }

    // TODO: "Get the money property, we need it for the scene!"
    public IntegerProperty moneyProperty() {
        return money; // TODO: "The property gives us access to the money in real-time!"
    }

    // TODO: "Luck makes the difference in this game, so get that luck!"
    public double getLuck() {
        return luck; // TODO: "Luck's always on your side, right?"
    }

    // TODO: "Get the aura, feel the energy of the bot!"
    public double getAura() {
        return aura; // TODO: "The aura brings the power!"
    }
}
