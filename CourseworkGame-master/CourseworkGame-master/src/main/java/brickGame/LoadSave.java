package brickGame;

// import classes for file input/output and object serialization
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * This class is used to read the state of a game from a file using ObjectInputStream. Reference:
 *
 * <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/LoadSave.java">LoadSave.java Link</a>
 */
public class LoadSave {
    // true or false
    public boolean          isExistHeartBlock;
    public boolean          isExistLongBlock;
    public boolean          isGoldStatus;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          collideToBreak;
    public boolean          collideToBreakAndMoveToRight;
    public boolean          collideToRightWall;
    public boolean          collideToLeftWall;
    public boolean          collideToRightBlock;
    public boolean          collideToBottomBlock;
    public boolean          collideToLeftBlock;
    public boolean          collideToTopBlock;

    /**
     * variable to store level
     */
    public int              level;

    /**
     * variable to store score
     */
    public int              score;

    /**
     * variable to store lives left
     */
    public int              heart;

    /**
     * variable to store how many blocks destroyed
     */
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;

    /**
     * variable to store total time
     */
    public long             time;

    /**
     * variable to store time left for gold time
     */
    public long             goldTime;

    /**
     * variable to store vertical speed of the ball
     */
    public double           vX;

    /**
     * array list to save info
     */
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();


    /**
     * responsible to read game state from file using ObjectInputStream
     */
    public void read() {


        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(Main.savePath)));

            // read int
            level = inputStream.readInt();
            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();

            // read doubles
            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();

            // read boolean
            isExistHeartBlock = inputStream.readBoolean();
            isGoldStatus = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            collideToBreak = inputStream.readBoolean();
            collideToBreakAndMoveToRight = inputStream.readBoolean();
            collideToRightWall = inputStream.readBoolean();
            collideToLeftWall = inputStream.readBoolean();
            collideToRightBlock = inputStream.readBoolean();
            collideToBottomBlock = inputStream.readBoolean();
            collideToLeftBlock = inputStream.readBoolean();
            collideToTopBlock = inputStream.readBoolean();


            try {
                @SuppressWarnings("unchecked")      // used to suppress the unchecked cast warning that arises
                                 // because the compiler cannot ensure the type safety of the cast.
                ArrayList<BlockSerializable> objBlocks = (ArrayList<BlockSerializable>) inputStream.readObject(); // after reading it casts it to array list
                blocks = objBlocks;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {       // read until some exception comes
            e.printStackTrace();
        }

    }
}
