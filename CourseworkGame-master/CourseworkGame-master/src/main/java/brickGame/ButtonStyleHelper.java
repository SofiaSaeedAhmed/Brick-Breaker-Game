package brickGame;

import javafx.scene.control.Button;

/**
 * This class is responsible for styling the main button used in the game.
 */

public class ButtonStyleHelper {

    /**
     * generates and returns a button with the specified text, applying a set of predefined styles using the applyStyles method.
     * @param text
     * @return button
     */
    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        applyStyles(button);
        return button;
    }

    /**
     * sets the base styling for the button, including background color, text fill, font size, padding, and border properties.
     * It also adds a hover effect.
     * @param button
     */
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

