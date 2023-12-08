package brickGame;

// imports for javaFx
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

// score class
public class Score {
    // show method --> responsible for displaying a score animation at a specified position - x and y
    // takes parameter score and gives reference to main class
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


    public void showGameOver(final Main main) {
        // Platform.runLater is a method in JavaFX that allows you to schedule a piece of code to be run on the JavaFX Application Thread.
        Platform.runLater(() -> {
            Label label = new Label("Game Over :(");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);

            Button restart = new Button("Restart");
            restart.setTranslateX(220);
            restart.setTranslateY(300);
            restart.setOnAction(event -> main.restartGame());
            main.root.getChildren().addAll(label, restart);

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(3000), // Adjust the duration as needed
                    event -> main.root.getChildren().removeAll(label, restart)
            ));
            timeline.play();
        });
    }

    public void showWin(final Main main) {
        Platform.runLater(() -> {
            Label label = new Label("You Win :)");
            label.setTranslateX(200);
            label.setTranslateY(250);
            label.setScaleX(2);
            label.setScaleY(2);
            main.root.getChildren().addAll(label);

            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(
                    Duration.millis(3000), // Adjust the duration as needed
                    event -> main.root.getChildren().remove(label)
            ));
            timeline.play();
        });
    }
}
