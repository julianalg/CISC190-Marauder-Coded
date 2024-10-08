package edu.sdccd.cisc190;

import java.util.*;

public class Slots {
    static int bet;
    static Scanner scanner = new Scanner(System.in);

    public static User main(User userProfile) {
        // Ask user how much they want to bet
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("How much do you wanna bet? (Input a number) $");
                bet = scanner.nextInt();
                validInput = true;  // Exit the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("That's not a number! Try again.");
                scanner.next();  // Clear the invalid input
            }
        }

        // Generates three random symbols by spinning, prints out resulting array
        String[] winningRow = spin();
        System.out.println(Arrays.toString(winningRow));

        // Checks if all symbols are the same
        boolean didWin = isWinner(winningRow);

        // Returns different response based on whether row is winner
        if (didWin) {
            System.out.println("Wow! Good job you win! :D");
            // TODO: add a multiplier for how much the user wins
            System.out.println("You won $" + bet * 10);
            User.money += (bet * 10);
        } else {
            System.out.println("Oops, you didn't win :( Try again! 99% of gamblers quit before hitting big!");
            System.out.println("You lost $" + bet);
            User.money -= bet;
        }

        return userProfile;
        // TODO: add option to spin again

    }

    /*
    * Emulates spinning the slot machine
    * Generates three random symbols from the symbolsArray parameter
    * @param symbolArrays array of symbols that the method pulls randomly from
    * */
     static String[] spin() {
        // Substantiate new Random() object
        Random rand = new Random();

        //declare symbols locally
        String[] symbols = {"\uD83C\uDF53", "\uD83C\uDF4C", "\uD83C\uDF4A"};

        //generate a random index of the symbols array and modify original array
        for (int i = 0; i < symbols.length; i++) {
            symbols[i] = symbols[rand.nextInt(symbols.length)];
        }
        return symbols;
    }

    /*
    * Checks if the array elements in the array are the same
    * Used to verify if a spin was a winner
    * @param isWinner array of symbols that the method checks if all elements are the same
    * */
     static boolean isWinner(String[] arr) {
        //create a HashSet winningSet that stores all elements in winningRow
        HashSet<String> winningSet = new HashSet<>(Arrays.asList(arr));

        //return if the size of the hashset is 1 (all values in HashSet are the same)
        return winningSet.size() == 1;
    }
}
