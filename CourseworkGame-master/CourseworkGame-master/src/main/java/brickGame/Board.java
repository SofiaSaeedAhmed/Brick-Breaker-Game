package brickGame;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class encapsulates the logic for initializing and maintaining the game board,
 * including the types and colors of bricks present. Reference:
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/Main.java">Board.java Link</a>
 */

public class Board {
    private final ArrayList<Block> blocks;
    private boolean isExistHeartBlock;
    private boolean isExistLongBlock;
    private final Color[] colors;

    /**
     * Initializes an empty list of blocks, sets flags for the existence of heart and longer blocks to false, and defines an array of colors.
     * It then calls the initBoard method to populate the game board based on the specified level.
     * @param level
     */
    public Board(int level) {
        blocks = new ArrayList<>();
        isExistHeartBlock = false;
        colors = new Color[]{
                Color.MAGENTA,
                Color.RED,
                Color.GOLD,
                Color.CORAL,
                Color.AQUA,
                Color.VIOLET,
                Color.GREENYELLOW,
                Color.ORANGE,
                Color.PINK,
                Color.SLATEGREY,
                Color.YELLOW,
                Color.TOMATO,
                Color.TAN,
        };

        initBoard(level);
    }

    /**
     * Generates a random number r to select a block type and set it on the game board.
     * Heart and long can only be set once in a level.
     */
    public void initBoard(int level) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else if (r % 10 == 4){
                    if (!isExistLongBlock) {
                        type = Block.BLOCK_LONGER;
                        isExistLongBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                }else {
                    type = Block.BLOCK_NORMAL;
                }
                blocks.add(new Block(j, i, colors[r % (colors.length)], type));
            }
        }
    }

    /**
     * getter method to return blocks
     * @return blocks
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}


