package brickGame;

// imports for javaFx
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;

/**
 * This class is responsible for controlling the animations of all the displays such as "Level up" and "+1".
 * Moreover, it is also responsible to show the game over screen. Reference:
 *
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Score.java">Score.java Link</a>
 */

// score class
public class Score {
    /**
     * Responsible for displaying a score animation at a specified position - x and y
     * takes parameter score and gives reference to main class
     * @param x
     * @param y
     * @param score
     * @param main
     */

    public void show(final double x, final double y, int score, final Main main) {

        String sign = (score >= 0) ? "+" : "";
        final Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);

        Platform.runLater(() -> {
            main.root.getChildren().add(label);
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(300),
                event -> main.root.getChildren().remove(label) // Remove label after animation
        ));
        timeline.play();


        // create a thread for score animation
        new Thread(() -> {
            // loop iterates 21 times
            for (int i = 0; i < 21; i++) {
                try {
                    label.setScaleX(i);                 // set x axis of label to current value of i
                    label.setScaleY(i);                 // set y-axis of label to current value of i
                    label.setOpacity((20 - i) / 20.0);  // set opacity of label based on current iteration
                    // gives fading effect
                    Thread.sleep(15);              // thread sleeps for 15 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();                // thread stops if interrupted, exception handling
                }
            }
        }).start();
    }

    /**
     *  Displays a message label at a specific position, providing an animation effect similar to the show method.
     *  It takes care of adding and removing the label and includes a separate thread for animation adjustments.
     * @param message
     * @param main
     */
    public void showMessage(String message, final Main main) {
        final Label label = new Label(message);
        label.setTranslateX(220);
        label.setTranslateY(340);

        Platform.runLater(() -> {
            main.root.getChildren().add(label);
        });

        // add timeline to fix label errors
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(
                Duration.millis(300),
                event -> main.root.getChildren().remove(label)
        ));
        timeline.play();

        // Start a new thread for the message animation
        new Thread(() -> {
            // loop iterates 21 times
            for (int i = 0; i < 21; i++) {
                try {
                    // Adjust label properties for animation effect
                    label.setScaleX(Math.abs(i-10));
                    label.setScaleY(Math.abs(i-10));
                    label.setOpacity((20 - i) / 20.0);
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /**
     * Displays a game over screen with a background image ("lost.png") and a restart button.
     * It utilizes JavaFX Platform.runLater to update UI components on the main application thread.
     * The restart button triggers the restartGame method in the Main class when clicked.
     * @param main
     */
    public void showGameOver(final Main main) {
        // Platform.runLater is a method in JavaFX that allows you to schedule a piece of code to be run on the JavaFX Application Thread.
        Platform.runLater(() -> {

            Image bgimage = new Image("lost.png");
            BackgroundSize backgroundSize = new BackgroundSize(700, 500, true, true, true, false);
            BackgroundImage backgroundImg = new BackgroundImage(bgimage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
            Background background = new Background(backgroundImg);

            // Set the background for the root
            main.root.setBackground(background);

            Button restart = new Button("Restart");
            restart.setTranslateX(180);
            restart.setTranslateY(450);
            restart.setPrefWidth(140);

            // Make the button invisible
            restart.setOpacity(0.0);

            restart.setOnAction(event -> main.restartGame());
            main.root.getChildren().addAll(restart);
        });
    }

}


