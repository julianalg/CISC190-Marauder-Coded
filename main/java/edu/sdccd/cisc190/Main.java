package edu.sdccd.cisc190;

import edu.sdccd.cisc190.services.SlotMachineManager;
import edu.sdccd.cisc190.views.SetupView;

public class Main {

    public static void main(String[] args) {
        SlotMachineManager.main();
        SetupView.launch(SetupView.class, args);
    }

}