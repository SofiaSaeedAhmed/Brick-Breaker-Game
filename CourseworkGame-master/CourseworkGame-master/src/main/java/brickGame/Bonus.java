package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

/**
 * This class is responsible for handling the implementation of the bonus which falls when choco brick is hit. Reference:
 *
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Bonus.java">Bonus.java Link</a>
 */

public class Bonus implements Serializable {
    /**
     * rectangle to represent choco brick
     */
    public Rectangle choco;

    /**
     * x coordinate of brick
     */
    public double x;

    /**
     * y coordinate of brick
     */
    public double y;

    /**
     * the time when brick was created
     */
    public long timeCreated;

    /**
     * boolean field to represent if bonus has been taken
     */
    public boolean taken = false;

    /**
     * draws the bonus object after setting the x and y position on grid for block
     * @param row
     * @param column
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + ((double) Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + ((double) Block.getHeight() / 2) - 15;

        draw();
    }

    /**
     * creates and fills image for the bonus object
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url;
        // based on  result of if statement, sets picture to bonus1 or bonus2
        // introduces randomness

        if (new Random().nextInt(20) % 2 == 0) { // if random number is even
            url = "bonus1.png";
        } else {                    // if randomly generated number is odd
            url = "bonus2.png";
        }

        choco.setFill(new ImagePattern(new Image(url)));
    }
}


