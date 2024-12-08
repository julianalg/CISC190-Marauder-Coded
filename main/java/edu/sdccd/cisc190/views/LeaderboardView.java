package edu.sdccd.cisc190.views;

import edu.sdccd.cisc190.players.HumanPlayer;
import edu.sdccd.cisc190.players.bots.*;
import edu.sdccd.cisc190.services.SlotMachineManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private static final ObservableList<LeaderboardEntry> entries = FXCollections.observableArrayList();
    public static TableView<LeaderboardEntry> leaderboardTable;

    @Override
    public void start(Stage primaryStage) {
        addMoneyListeners();
        showWindow(primaryStage);
    }

    /**
     * Adds listeners for money property changes of all players.
     */
    private static void addMoneyListeners() {
        HumanPlayer.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        AnitaMaxWynn.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        HondaBoyz.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        MrBrooks.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        ProfessorHuang.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
        Chase.getInstance().moneyProperty().addListener((_, _, _) -> updateLeaderboard());
    }

    /**
     * Updates the leaderboard table by refreshing the data.
     */
    private static void updateLeaderboard() {
        leaderboardTable.refresh();
    }

    /**
     * Displays the leaderboard window.
     *
     * @param primaryStage The main stage for the application.
     */
    public static void showWindow(Stage primaryStage) {
        VBox layout = createMainLayout();
        primaryStage.setTitle("Leaderboard");

        // Set the onCloseRequest handler to stop threads and exit the application
        primaryStage.setOnCloseRequest(_ -> {
            SlotMachineManager.stopAllThreads();
            Platform.exit();
        });

        // Add header to the layout
        layout.getChildren().add(createHeader());

        // Create and populate TableView
        leaderboardTable = createLeaderboardTable();
        layout.getChildren().add(leaderboardTable);

        // Create and style the main menu button
        Button mainMenu = createStyledButton("Main Menu", event -> MainMenuView.setupWindow(primaryStage));
        layout.getChildren().add(mainMenu);

        // Setup and display the scene
        setupScene(primaryStage, layout);
    }

    /**
     * Creates the main layout for the leaderboard window.
     *
     * @return A VBox layout with predefined styles and spacing.
     */
    private static VBox createMainLayout() {
        VBox layout = new VBox(20);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #000000, #660000);" + "-fx-padding: 30px;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        return layout;
    }

    /**
     * Creates a styled header text for the leaderboard window.
     *
     * @return A styled Text object representing the header.
     */
    private static Text createHeader() {
        Text header = new Text("Leaderboard");
        header.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        header.setFill(Color.GOLD);
        return header;
    }

    /**
     * Creates the TableView for displaying the leaderboard.
     *
     * @return A TableView populated with leaderboard entries.
     */
    private static TableView<LeaderboardEntry> createLeaderboardTable() {
        TableView<LeaderboardEntry> table = new TableView<>();
        table.setPrefHeight(300);

        // Define columns
        TableColumn<LeaderboardEntry, String> nameColumn = createColumn("Name", "name", 150);
        TableColumn<LeaderboardEntry, Integer> moneyColumn = createColumn("Money", "money", 150);

        table.getColumns().addAll(nameColumn, moneyColumn);
        table.setItems(getSortedLeaderboardData());
        return table;
    }

    /**
     * Creates a table column dynamically.
     *
     * @param title    The title of the column.
     * @param property The property the column should display.
     * @param width    The width of the column.
     * @return A TableColumn for the leaderboard.
     */
    private static <T> TableColumn<LeaderboardEntry, T> createColumn(String title, String property, int width) {
        TableColumn<LeaderboardEntry, T> column = new TableColumn<>(title);
        column.setCellValueFactory(cellData -> {
            if ("name".equals(property)) {
                return (T) new javafx.beans.property.SimpleStringProperty(cellData.getValue().name());
            } else {
                return (T) cellData.getValue().money().asObject();
            }
        });
        column.setPrefWidth(width);
        return column;
    }

    /**
     * Gets the sorted data for the leaderboard table.
     * Initializes the list if it's empty and sorts entries by money.
     *
     * @return An ObservableList containing sorted leaderboard entries.
     */
    private static ObservableList<LeaderboardEntry> getSortedLeaderboardData() {
        if (entries.isEmpty()) {
            entries.addAll(
                    new LeaderboardEntry(HumanPlayer.getInstance().getName(), HumanPlayer.getInstance().moneyProperty()),
                    new LeaderboardEntry(AnitaMaxWynn.getInstance().getName(), AnitaMaxWynn.getInstance().moneyProperty()),
                    new LeaderboardEntry(Chase.getInstance().getName(), Chase.getInstance().moneyProperty()),
                    new LeaderboardEntry(HondaBoyz.getInstance().getName(), HondaBoyz.getInstance().moneyProperty()),
                    new LeaderboardEntry(MrBrooks.getInstance().getName(), MrBrooks.getInstance().moneyProperty()),
                    new LeaderboardEntry(ProfessorHuang.getInstance().getName(), ProfessorHuang.getInstance().moneyProperty())
            );
        }
        FXCollections.sort(entries, (entry1, entry2) -> Integer.compare(entry2.money().get(), entry1.money().get()));
        return entries;
    }

    /**
     * Creates a styled button for navigation to the main menu.
     *
     * @param text         The text to display on the button.
     * @param eventHandler The event handler for the button click.
     * @return A styled Button object.
     */
    private static Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle(createButtonStyle("#ffcc00", "#ff9900", "black"));
        button.setOnAction(eventHandler);
        button.setOnMouseEntered(_ -> button.setStyle(createButtonStyle("#ff9900", "#ff6600", "white")));
        button.setOnMouseExited(_ -> button.setStyle(createButtonStyle("#ffcc00", "#ff9900", "black")));
        return button;
    }

    /**
     * Generates a CSS style string for buttons.
     *
     * @param topColor    The gradient's top color.
     * @param bottomColor The gradient's bottom color.
     * @param textColor   The text color.
     * @return A CSS style string.
     */
    private static String createButtonStyle(String topColor, String bottomColor, String textColor) {
        return "-fx-background-color: linear-gradient(to bottom, " + topColor + ", " + bottomColor + ");" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10px 20px;";
    }

    /**
     * Sets up and displays the scene for the primary stage.
     *
     * @param primaryStage The main stage of the application.
     * @param layout       The layout to display on the stage.
     */
    private static void setupScene(Stage primaryStage, VBox layout) {
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Represents a single entry in the leaderboard.
     *
     * @param name  The name of the player.
     * @param money The money property of the player.
     */
    public record LeaderboardEntry(String name, IntegerProperty money) {
    }
}
