package brickGame;

import java.io.Serializable;

public class BlockSerializable implements Serializable {
    // represent position of the block in a 2D grid or array
    public final int row;
    public final int j;

    // represents type of block
    public final int type;

    // constructor
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}
