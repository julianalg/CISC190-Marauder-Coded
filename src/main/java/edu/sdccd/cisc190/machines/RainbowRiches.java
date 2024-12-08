ts added:

java
Copy code
package edu.sdccd.cisc190.machines;

/**
 * Rainbow Riches is a type of slot in the casino
 * Uses the super constructor to set values of attributes inherited from Slots
 * medium to high risk, medium to high reward slot
 */
public class RainbowRiches extends Slot {
    
    // TODO: Hey! We're making a "RainbowRiches" class. This class is going to be a slot machine.
    // It inherits from the "Slot" class. Let's make sure the slot works great, baby! 🎶

    public RainbowRiches() {
        // TODO: Look at this! We're calling the "super" constructor from the parent "Slot" class.
        // We pass some values here. These values are like the magic inside the slot machine! ✨
        // The first one is a list of symbols, like emojis: 🌈 🎧 🎤
        // Second is the jackpot amount, a big one! 1000! 💰
        // Third is the bet amount, we're going with 25! 🎰
        // Fourth is how many paylines, let's use 5! More chances to win! 🔥

        super(new String[]{"\uD83C\uDF08", "\uD83C\uDF27", "\uD83C\uDF24"}, 1000, 25, 5);
        
        // TODO: This constructor is setting up our Rainbow Riches slot! It's got cool symbols
        // and a chance for a big payout! We're dancing all the way to the bank! 🕺💃
    }
}
