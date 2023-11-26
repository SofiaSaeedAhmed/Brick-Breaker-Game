package brickGame;

// import classes for file input/output and object serialization
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

// loadSave method reads the state of a game from a file using ObjectInputStream
public class LoadSave {
    // true or false
    public boolean          isExistHeartBlock;
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

    // variables
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;
    public long             time;
    public long             goldTime;
    public double           vX;

    // array list to save info
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();


    // read method --> responsible to read game state from file using ObjectInputStream
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

            /*
            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/

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
