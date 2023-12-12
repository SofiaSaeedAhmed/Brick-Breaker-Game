package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class initializes the paddle in the game according to the dimensions provided as parameters. Reference:
 *
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Main.java">PaddleInitializer.java Link</a>
 */

public class PaddleInitializer {

    /**
     *  initializes and returns a Rectangle representing the paddle in the game, based on the specified dimensions.
     *  The paddle is filled with an image pattern from a file ("block.jpg").
     * @param xBreak
     * @param yBreak
     * @param breakWidth
     * @param breakHeight
     * @return rect
     */
    public static Rectangle initPaddle(double xBreak, double yBreak, int breakWidth, int breakHeight) {
        Rectangle rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);

        ImagePattern pattern = new ImagePattern(new Image("block.jpg"));
        rect.setFill(pattern);

        return rect;
    }
}

