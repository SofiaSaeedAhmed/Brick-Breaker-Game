package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * This class initializes the ball used in the game, adds an image and radius according to the dimensions specified. Reference:
 *
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Main.java">BallInitializer.java Link</a>
 */

public class BallInitializer {

    /**
     * Initializes and returns a JavaFX Circle object representing a ball with a specified radius, filled with an image pattern from a file ("ball.png").
     * @param ballRadius
     * @return ball
     */

    public static Circle initBall(int ballRadius) {
        Random random = new Random();
        Circle ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
        return ball;
    }

}
