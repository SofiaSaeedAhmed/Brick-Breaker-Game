package brickGame;

// import statements for graphical elements
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;        // support serialization, convert into sequence of bytes to save in a file
import java.util.Random;
// later deserialized into an object

// instances of this class can be serialized
public class Block implements Serializable {

    // instance for block is created with specific initial parameters
    // parameters are padding and dimensions
    // block (row, column, color, type)
    private final static Block block = new Block(-1, -1, Color.TRANSPARENT, 99); // normal block = 99

    // class variables
    public int row;
    public int column;


    public boolean isDestroyed = false;     // block is destroyed or not

    private final Color color;
    public int type;

    public int x;
    public int y;

    // changed the parameters to final
    private final int width = 100;
    private final int height = 30;
    private final int paddingTop = height * 2;
    private final int paddingH = 50;

    public Rectangle rect;              // object rect that represents the graphical representation of the block

    // constants --> define possible hit directions a
    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    // constants --> define possible block types
    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;


    // constructor --> initializes block properties and calls the draw method
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {

        // sets up the graphical representation of the block

        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        // uses image depending on the block type

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }

    }


    public int checkHitToBlock(double xBall, double yBall) {

        // checks whether the ball hits the block and returns in the hit direction

        if (isDestroyed) {          // initialized to false, so means no hit
            return NO_HIT;
        }

        if (xBall >= x && xBall <= x + width && yBall == y + height) {
            return HIT_BOTTOM;
        }

        if (xBall >= x && xBall <= x + width && yBall == y) {
            return HIT_TOP;
        }

        if (yBall >= y && yBall <= y + height && xBall == x + width) {
            return HIT_RIGHT;
        }

        if (yBall >= y && yBall <= y + height && xBall == x) {
            return HIT_LEFT;
        }

        return NO_HIT;
    }

    // provide access to properties such as dimensions and padding

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}
