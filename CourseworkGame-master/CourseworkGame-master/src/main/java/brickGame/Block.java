package brickGame;

// import statements for graphical elements
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;        // support serialization, convert into sequence of bytes to save in a file

/**
 * This class encapsulates the attributes and behaviors of game blocks for use in a graphical gaming environment.
 * It includes variables for the block's position, color, type, and graphical representation.
 *  The class also provides a method to check if a ball hits the block and returns the hit direction.
 *  Accessor methods allow retrieval of block dimensions and padding. Reference:
 *  * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Block.java">Block.java Link</a>
 */
// instances of this class can be serialized
public class Block implements Serializable {

    // instance for block is created with specific initial parameters
    // parameters are padding and dimensions
    // block (row, column, color, type)
    private final static Block block = new Block(-1, -1, Color.TRANSPARENT, 99); // normal block = 99

    // class variables

    /**
     * Variable for row representation
     */
    public int row;

    /**
     * Variable for column representation
     */
    public int column;


    /**
     * block is destroyed or not
     */
    public boolean isDestroyed = false;

    /**
     * To set brick color
     */
    protected final Color color;

    /**
     * To define brick type
     */
    public int type;

    public int x;
    public int y;

    // changed the parameters to final
    private final int width = 100;
    private final int height = 30;
    private final int paddingTop = height * 2;
    private final int paddingH = 50;

    /**
     * object rect that represents the graphical representation of the block
     */
    public Rectangle rect;

    /**constants --> define possible hit directions
     *
     */
    public static int NO_HIT = -1;

    /**constants --> define possible hit directions
     *
     */
    public static int HIT_RIGHT = 0;

    /**constants --> define possible hit directions
     *
     */
    public static int HIT_BOTTOM = 1;

    /**constants --> define possible hit directions
     *
     */
    public static int HIT_LEFT = 2;

    /**constants --> define possible hit directions
     *
     */
    public static int HIT_TOP = 3;

    // constants --> define possible block types

    /**
     *  Integer representation of the normal brick
     */
    public static int BLOCK_NORMAL = 99;

    /**
     *  Integer representation of the choco brick
     */
    public static int BLOCK_CHOCO = 100;

    /**
     *  Integer representation of the star brick
     */
    public static int BLOCK_STAR = 101;

    /**
     *  Integer representation of the heart brick
     */
    public static int BLOCK_HEART = 102;

    /**
     *  Integer representation of the longer brick
     */
    public static int BLOCK_LONGER = 103;


    /**constructor --> initializes block properties and calls the draw method
     * @param row
     * @param column
     * @param color
     * @param type
     */
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    /**
     * sets up the graphical representation of the block and fills image depending on the type
     */
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
        } else if (type == BLOCK_LONGER){
            Image image = new Image("longer.png");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        }else {
            rect.setFill(color);
        }

    }


    /**
     * checks whether the ball hits the block and returns in the hit direction
     * @param xBall
     * @param yBall
     * @return the direction to hit
     */
    public int checkHitToBlock(double xBall, double yBall) {


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

    /**
     * provide access to properties such as padding
     */

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    /**
     * provide access to properties such as padding
     */

    public static int getPaddingH() {
        return block.paddingH;
    }

    /**
     * provide access to properties such as dimensions
     */

    public static int getHeight() {
        return block.height;
    }

    /**
     * provide access to properties such as dimensions
     */
    public static int getWidth() {
        return block.width;
    }

}
