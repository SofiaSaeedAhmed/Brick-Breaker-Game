package brickGame;

import java.io.Serializable;

/**
 * This class is designed to represent the position and type of a block in a 2D grid or array. Reference:
 *
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/BlockSerializable.java">BlockSerializable.java Link</a>
 */

public class BlockSerializable implements Serializable {
    /**
     * represent position of the block in a 2D grid or array
     */
    public final int row;

    /**
     * represent position of the block in a 2D grid or array
     */
    public final int j;

    /**
     * represents type of block
     */
    public final int type;

    /**
     *  initializes the position and type of a block in a 2D grid or array.
     * @param row
     * @param j
     * @param type
     */
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}

