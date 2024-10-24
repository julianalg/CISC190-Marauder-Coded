package edu.sdccd.cisc190.machines;

import edu.sdccd.cisc190.Slot;
import edu.sdccd.cisc190.User;

public class TreasureSpins extends Slot {
    public TreasureSpins() {
        luck = 0.5;
        returnAmt = 10;
        symbols = new String[]{"\uD83C\uDF53", "\uD83C\uDF4C", "\uD83C\uDF4A"};
    }
}