package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.players.bots.*;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderboardView extends Application {

    public static TableView<LeaderboardEntry> leaderboardTable; // TODO: Table to show leaderboard data
    private static final ObservableList<LeaderboardEntry> entries = FXCollections.observableArrayList(); // TODO: List that holds leaderboard data

    @Override
    public void start(Stage primaryStage) {

        // Listen to human player money changes - TODO: Listen to money changes and update the leaderboard
        HumanPlayer.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());

        // Add listeners for all bot players - TODO: Listen to money changes for all bots
        AnitaMaxWynn.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        HondaBoyz.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        MrBrooks.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        ProfessorHuang.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        Chase.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());

        showWindow(primaryStage); // TODO: Show the window with all components
    }

    /**
     * Updates the leaderboard by sorting entries based on the amount of money in descending order.
     * TODO: Sort the leaderboard entries by money.
     */
    private static void updateLeaderboard() {
        FXCollections.sort(entries, (entry1, entry2) -> Integer.compare(entry2.money().get(), entry1.money().get())); // TODO: Sorting entries by money
        leaderboardTable.refresh(); // TODO: Refresh the leaderboard table after sorting
    }

    /**
     * Displays the leaderboard window with a sorted list of players and their money amounts.
     * TODO: Create and show the leaderboard window with all players.
     *
     * @param primaryStage The main stage for the application.
     */
    public static void showWindow(Stage primaryStage) {
        VBox layout = createMainLayout(); // TODO: Create the layout for the window
        primaryStage.setTitle("Leaderboard"); // TODO: Set the window title

        // Set the onCloseRequest handler to stop threads and exit the application - TODO: Handle window closing
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads(); // TODO: Stop all threads when closing the window
            Platform.exit(); // TODO: Exit the platform (app)
        });

        // Add header to the layout - TODO: Add the header text
        layout.getChildren().add(createHeader());

        // Create and populate TableView - TODO: Add leaderboard table to the layout
        leaderboardTable = createLeaderboardTable();
        layout.getChildren().add(leaderboardTable);

        // Create and style the main menu button - TODO: Add button to go back to main menu
        Button mainMenu = createStyledButton();
        mainMenu.setOnAction(_ -> MainMenuView.setupWindow(primaryStage)); // TODO: Handle button action
        layout.getChildren().add(mainMenu);

        // Setup and display the scene - TODO: Set up the scene and show the window
        setupScene(primaryStage, layout);
    }

    /**
     * Creates the main layout for the leaderboard window.
     * TODO: Define the layout of the window.
     *
     * @return A VBox layout with predefined styles and spacing.
     */
    private static VBox createMainLayout() {
        VBox layout = new VBox(20); // TODO: Set spacing between elements
        layout.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #000000, #660000);" +
                        "-fx-padding: 30px;" // TODO: Styling for the background and padding
        );
        layout.setAlignment(javafx.geometry.Pos.CENTER); // TODO: Set alignment to center
        return layout;
    }

    /**
     * Creates a styled header text for the leaderboard window.
     * TODO: Create the header text with style.
     *
     * @return A styled Text object representing the header.
     */
    private static Text createHeader() {
        Text header = new Text("Leaderboard"); // TODO: Header text
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 30)); // TODO: Set the font style
        header.setFill(Color.GOLD); // TODO: Set the header text color to gold
        return header;
    }

    /**
     * Creates the TableView for displaying the leaderboard.
     * TODO: Create and set up the table for the leaderboard.
     *
     * @return A TableView populated with leaderboard entries, sorted by money.
     */
    private static TableView<LeaderboardEntry> createLeaderboardTable() {
        TableView<LeaderboardEntry> table = new TableView<>(); // TODO: Create the TableView
        table.setPrefHeight(300); // TODO: Set the preferred height for the table

        // Define columns - TODO: Create columns for Name and Money
        TableColumn<LeaderboardEntry, String> nameColumn = new TableColumn<>("Name"); // TODO: Name column
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().name())); // TODO: Set the name for each entry
        nameColumn.setPrefWidth(150); // TODO: Set the column width

        TableColumn<LeaderboardEntry, Integer> moneyColumn = new TableColumn<>("Money"); // TODO: Money column
        moneyColumn.setCellValueFactory(cellData -> cellData.getValue().money().asObject()); // TODO: Set the money for each entry
        moneyColumn.setPrefWidth(150); // TODO: Set the column width

        // Add columns to the table - TODO: Add columns to the table
        table.getColumns().addAll(nameColumn, moneyColumn);

        // Populate and sort data - TODO: Get and sort leaderboard data
        table.setItems(getSortedLeaderboardData());

        return table; // TODO: Return the table
    }

    /**
     * Gets the sorted data for the leaderboard table.
     * Initializes the list if it's empty and sorts entries by money.
     * TODO: Sort the leaderboard entries when loading data.
     *
     * @return An ObservableList containing sorted leaderboard entries.
     */
    private static ObservableList<LeaderboardEntry> getSortedLeaderboardData() {
        if (entries.isEmpty()) {
            // Add all player entries to the leaderboard - TODO: Add all players to the leaderboard
            entries.addAll(
                    new LeaderboardEntry(HumanPlayer.getInstance().getName(), HumanPlayer.getInstance().moneyProperty()),
                    new LeaderboardEntry(AnitaMaxWynn.getInstance().getName(), AnitaMaxWynn.getInstance().moneyProperty()),
                    new LeaderboardEntry(Chase.getInstance().getName(), Chase.getInstance().moneyProperty()),
                    new LeaderboardEntry(HondaBoyz.getInstance().getName(), HondaBoyz.getInstance().moneyProperty()),
                    new LeaderboardEntry(MrBrooks.getInstance().getName(), MrBrooks.getInstance().moneyProperty()),
                    new LeaderboardEntry(ProfessorHuang.getInstance().getName(), ProfessorHuang.getInstance().moneyProperty())
            );
        }
        FXCollections.sort(entries, (entry1, entry2) -> Integer.compare(entry2.money().get(), entry1.money().get())); // TODO: Sort entries by money
        return entries; // TODO: Return the sorted list
    }

    /**
     * Creates a styled button for navigation to the main menu.
     * TODO: Create a button that goes back to the main menu.
     *
     * @return A styled Button object.
     */
    private static Button createStyledButton() {
        Button button = new Button("Main Menu"); // TODO: Create the button
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // TODO: Set the font style for the button
        button.setStyle(createButtonStyle("#ffcc00", "#ff9900", "black")); // TODO: Set button style

        // Button hover effects - TODO: Add hover effect for the button
        button.setOnMouseEntered(_ -> button.setStyle(createButtonStyle("#ff9900", "#ff6600", "white")));
        button.setOnMouseExited(_ -> button.setStyle(createButtonStyle("#ffcc00", "#ff9900", "black")));

        return button; // TODO: Return the styled button
    }

    /**
     * Generates a CSS style string for buttons.
     * TODO: Create a CSS style for the button.
     *
     * @param topColor    The gradient's top color.
     * @param bottomColor The gradient's bottom color.
     * @param textColor   The text color.
     * @return A CSS style string.
     */
    private static String createButtonStyle(String topColor, String bottomColor, String textColor) {
        return "-fx-background-color: linear-gradient(to bottom, " + topColor + ", " + bottomColor + ");" +
                "-fx-text-fill: " + textColor + ";" + // TODO: Add button text color
                "-fx-background-radius: 10;" + // TODO: Set the button radius
                "-fx-padding: 10px 20px;"; // TODO: Set padding for the button
    }

    /**
     * Sets up and displays the scene for the primary stage.
     * TODO: Set up and show the scene.
     *
     * @param primaryStage The main stage of the application.
     * @param layout       The layout to display on the stage.
     */
    private static void setupScene(Stage primaryStage, VBox layout) {
        Scene scene = new Scene(layout, 600, 600); // TODO: Set the size for the scene
        primaryStage.setScene(scene); // TODO: Set the scene to the primary stage
        primaryStage.show(); // TODO: Show the stage
    }

    public static void main(String[] args) {
        launch(args); // TODO: Start the JavaFX application
    }

    /**
     * Represents a single entry in the leaderboard.
     * TODO: Create a record for leaderboard entry.
     *
     * @param name  The name of the player.
     * @param money The money property of the player.
     */
    public record LeaderboardEntry(String name, IntegerProperty money) {
    }
}
