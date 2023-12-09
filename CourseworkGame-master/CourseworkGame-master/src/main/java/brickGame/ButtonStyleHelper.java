package brickGame;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

public class ButtonStyleHelper {

    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        applyStyles(button);
        return button;
    }

    private static void applyStyles(Button button) {
        String baseStyle = "-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 5 20 5 20; " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 15; " +
                "-fx-border-color: #4CAF50; " +
                "-fx-border-width: 2;";

        button.setStyle(baseStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(
                baseStyle + "-fx-background-color: #8fc691;" // Adjust the color for hover effect
        ));

        // Remove hover effect
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
    }
}

