package co.ppj2.views;

import co.ppg2.model.Player;
import co.ppg2.Main;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;

public class LeaderboardPopup {

    // Method to display the leaderboard in a popup window
    public static void showLeaderboard(List<Player> players) {
        Stage popupStage = new Stage();  // Create a new stage for the leaderboard popup

        VBox vbox = new VBox();  // Vertical layout for the contents of the leaderboard
        Label label = new Label("Leaderboard:");  // Label to indicate it's the leaderboard

        // ListView to display the player names, wins, and average time per move
        ListView<String> listView = new ListView<>();

        // Sort players by wins in descending order, limit to top 10, and add them to the ListView
        players.stream()
                .sorted((p1, p2) -> Integer.compare(p2.getWins(), p1.getWins()))  // Sort by wins in descending order
                .limit(10)  // Limit to top 10 players
                .forEach(player -> {
                    double avgTime = Main.gameTimer.getAverageTimePerMove(player.getUsername()); // Get average time for the player
                    listView.getItems().add(player + String.format(", Avg Time: %.2f seconds", avgTime));  // Add player details to the list
                });

        vbox.getChildren().addAll(label, listView);  // Add label and list view to the VBox layout

        Scene scene = new Scene(vbox, 300, 300);  // Create the scene with the VBox layout
        popupStage.setTitle("Leaderboard");  // Set the title of the popup window
        popupStage.setScene(scene);  // Set the scene on the stage
        popupStage.showAndWait();  // Show the popup and wait for user interaction

        // TODO: Allow the leaderboard to refresh dynamically if the game continues and more players are added.
        //       Consider adding a refresh button or timer to keep the leaderboard up to date.
        //       Example: Add a button that updates the leaderboard with new data without closing the popup.
        //       (e.g., Add a button in the VBox that when clicked, re-fetches and updates the leaderboard list.)

        // TODO: Add pagination or scrolling for large leaderboards.
        //       If there are more than 10 players, consider allowing scrolling or displaying a "next page" button.
        //       This will improve the user experience for games with many players.

        // TODO: Provide additional statistics, such as win/loss ratio or total games played.
        //       You could display additional stats per player to give users more context about their rankings.
        //       Example: "Wins: 10, Losses: 2, Win/Loss Ratio: 5.0".

        // TODO: Provide an option to view more detailed player profiles or game history.
        //       You could add an "info" button for each player that opens a detailed view of their performance over time.
        //       Example: Showing a history of win/loss streaks or average times for each player.

        // TODO: Customize the appearance of the leaderboard.
        //       Consider adding colors or visual cues for top players (e.g., gold, silver, bronze for the top 3).
        //       This could enhance the visual appeal and highlight top players.
        //       Example: Use different background colors for the top players or add icons.
    }
}
