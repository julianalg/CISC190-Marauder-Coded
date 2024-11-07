package edu.sdccd.cisc190.interfaces;

import edu.sdccd.cisc190.HumanPlayer;
import edu.sdccd.cisc190.Slot;
import edu.sdccd.cisc190.machines.DiamondDash;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {

    public static final String APP_NAME_FILE = "AppName.txt";

    @Override
    public void start(Stage primaryStage) {
        showWindow(primaryStage);
    }

    public static void showWindow(Stage primaryStage) {
        primaryStage.setTitle("Casino Game Menu");

        // Username and Money Labels
        Text usernameLabel = new Text("Username: " + HumanPlayer.getInstance().getUsername());
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        usernameLabel.setFill(Color.GOLD);

        Text moneyLabel = new Text("Money: $" + HumanPlayer.getInstance().getMoney().toString());
        moneyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        moneyLabel.setFill(Color.LIGHTGREEN);

        // Creating menu buttons with a consistent style
        Button option1Button = createStyledButton("Diamond Dash");
        Button option2Button = createStyledButton("Honda Trunk");
        Button option3Button = createStyledButton("Mega Moolah");
        Button option4Button = createStyledButton("Rainbow Riches");
        Button option5Button = createStyledButton("Treasure Spins");
        Button quitButton = createStyledButton("Quit");

        // Set button actions
        option1Button.setOnAction(e -> {
            primaryStage.close();
            Stage newWindow = new Stage();
            Bet.showWindow(newWindow);
        });

        quitButton.setOnAction(e -> {
            primaryStage.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Cya");
            alert.setContentText("Come back soon! 99.9% of gamblers quit before hitting it big!");
            alert.showAndWait();
        });

        // Layout setup with spacing and padding
        VBox layout = new VBox(15);
        layout.setStyle("-fx-background-color: darkslateblue; -fx-padding: 20;");
        layout.getChildren().addAll(usernameLabel, moneyLabel, option1Button, option2Button, option3Button, option4Button, option5Button, quitButton);

        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Scene and Stage setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        );

        // Hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff9900, #ff6600);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        ));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff9900);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10px 20px;"
        ));

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}