package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

public class Bonus implements Serializable {
    // type of object --> gives bonus
    public Rectangle choco;

    // x and y coordinates of brick
    public double x;
    public double y;

    // the time when brick was created
    public long timeCreated;

    // boolean field to represent if bonus has been taken
    public boolean taken = false;

    public Bonus(int row, int column) {

        // draw the bonus object after setting the x and y position on grid for block
        x = (column * (Block.getWidth())) + Block.getPaddingH() + ((double) Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + ((double) Block.getHeight() / 2) - 15;

        draw();
    }

    // creates the bonus object
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
