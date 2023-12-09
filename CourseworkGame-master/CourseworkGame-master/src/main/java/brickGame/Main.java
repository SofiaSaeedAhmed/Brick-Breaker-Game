package brickGame;

// imports for javaFx
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


// imports for other functionality
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class Main extends Application implements EventHandler<KeyEvent>, GameEngine.OnAction {

    private int level = 0;      // current game level

    private double xBreak = 0.0f;   // x coordinates of paddle
    private double centerBreakX;
    private double yBreak = 640.0f;     // y coordinates of paddle

    private final int breakWidth     = 130;
    private final int breakHeight    = 30;
    private final int halfBreakWidth = breakWidth / 2;

    private final int sceneWidth = 500;
    private final int sceneHeight = 700;

    private final int LEFT  = 1;
    private final int RIGHT = 2;

    private Circle ball;           // represents ball as a circle object
    private double xBall;
    private double yBall;

    // flags indicating special conditions in the game
    private boolean isGoldStatus      = false;
    private boolean isExistHeartBlock = false;

    // flag to indicate resume countdown
    private boolean isCountingDown = false;

    private Rectangle rect;             // represents a rectangle
    private final int ballRadius = 10;

    private int destroyedBlockCount = 0;        // counts the number of blocks destroyed

    private int  heart    = 3;      // initial number of lives
    private int  score    = 0;

    // time related variables
    private long time     = 0;
    public long hitTime  = 0;
    private long goldTime = 0;

    private GameEngine engine;      // instance of the engine path
    public static String savePath    = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private ArrayList<Block> blocks = new ArrayList<>();
    private final ArrayList<Bonus> chocos = new ArrayList<>();


    // list of colours for the blocks
    private final Color[]          colors = new Color[]{
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

    // labels to display score, lives left and level of game
    public  Pane             root;
    private Label            scoreLabel;
    private Label            heartLabel;
    public Label            levelLabel;
    public Label            welcome;

    private boolean loadFromSave = false;   // Flag to indicate whether to load the game from a save file.

    Stage  primaryStage;            // the main window of the application

    // buttons for loading and starting a new game
    Button load    = null;
    Button newGame = null;

    private boolean gameStarted = false;  // Flag to track whether the game has started

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if(!gameStarted){
            createMainMenu();  // Show the main menu initially
            gameStarted = true;
        }
        else {
            startGame();
        }
        primaryStage.setTitle("Brick Breaker Main Menu");
        primaryStage.show();
    }

    private void createMainMenu() {
        root = new Pane();
        // Load and set the background image
        Image backgroundImage = new Image("file:C:\\Users\\sofia\\OneDrive\\Desktop\\CourseworkGame-master\\CourseworkGame-master\\src\\main\\resources\\Sofia's game background.png");
        root.setBackground(new Background(new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        // Set up the start button
        Button startButton = new Button("Start Game");
        startButton.setTranslateX(220);
        startButton.setTranslateY(430);

        // Make the button invisible
        startButton.setOpacity(0.0);

        startButton.setOnAction(event -> startGame());

        root.getChildren().addAll(startButton);
        Scene menuScene = new Scene(root, sceneWidth, sceneHeight);
        primaryStage.setScene(menuScene);
    }

    private void startGame() {
        // checks if game is being loaded from save, if not, it proceeds to set up the initial stage for new game
        if (!loadFromSave) {
            level++;
            // if game is not loaded and level is greater than 1, it displays level up
            if (level > 1) {
                new Score().showMessage("Level Up :)", this);
            }
            // if level 18 is reached, it shows that you have won
            if (level == 8) {
                new Score().showWin(this);
                return;
            }

            // calls methods to initialize components of the game
            Random random = new Random();
            xBall = random.nextInt(sceneWidth) + 1;
            yBall = random.nextInt(sceneHeight - 200) + ((level + 1) * Block.getHeight()) + 15;

            // calls ball initializer
            ball = BallInitializer.initBall(ballRadius);

            // calls paddle initializer
            rect = PaddleInitializer.initPaddle(xBreak, yBreak, breakWidth, breakHeight);

            // Initialize the Board
            Board board = new Board(level);
            blocks = board.getBlocks(); // Retrieve blocks from the Board

            // button initialization
            load = new Button("Load Game");
            newGame = new Button("Start New Game");

            // button initialization using ButtonStyleHelper
            load = ButtonStyleHelper.createStyledButton("Load Game");
            newGame = ButtonStyleHelper.createStyledButton("Begin Playing");

            // set button position
            load.setTranslateX(190);
            load.setTranslateY(300);
            newGame.setTranslateX(190);
            newGame.setTranslateY(340);
        }

        root = new Pane(); // Creates a new Pane named root to serve as the root container for the UI elements.

        // initialize score, level and heart label on the pane
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);

        // if the game is not loaded, then add elements, as well as newGame button
        if (!loadFromSave) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, newGame);
        } else {
            // add everything except newGame button
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        }

        // iterate through the block list and add corresponding rect elements to root
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }

        // new scene object and initialize elements
        Scene scene = new Scene(root, sceneWidth, sceneHeight);

        // apply style to UI
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("The Brick Breaker Game");
        // set scene for the primary stage and show
        primaryStage.setScene(scene);
        primaryStage.show();

        // check if the game is loaded from a save file
        if (!loadFromSave) {
            // the game is not loaded so check level
            if (level > 1 && level < 8) {

                // hide load game and start new game buttons
                load.setVisible(false);
                newGame.setVisible(false);

                // game engine initialization and start
                engine = new GameEngine();
                engine.setOnAction(this);
                engine.setFps(120);
                engine.start();
            }

            load.setOnAction(Event -> {
                // calls the load game method
                loadGame();
                // hide buttons
                load.setVisible(false);
                newGame.setVisible(false);
            });

            newGame.setOnAction(Event -> {
                engine = new GameEngine();
                engine.setOnAction(Main.this);
                engine.setFps(120);
                engine.start();

                load.setVisible(false);
                newGame.setVisible(false);
            });
        } else // if a new game is loaded from a save, create a new instance of the game engine
        {
            engine = new GameEngine();
            engine.setOnAction(this);
            engine.setFps(120);
            engine.start();
            loadFromSave = false;
        }
    }




    // Entry point of JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    // event handling
    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT -> move(LEFT);
            case RIGHT -> move(RIGHT);
            case S -> saveGame();
            case SPACE -> togglePause();
        }
    }



    //  responsible for handling the paddle's movement
    private void move(final int direction) {        // direction tells whether to move left or right
        new Thread(() -> {
            int sleepTime = 4;
            for (int i = 0; i < 30; i++) {
                if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                    return;
                }
                if (xBreak == 0 && direction == LEFT) {
                    return;
                }
                if (direction == RIGHT) {
                    xBreak++;
                } else {
                    xBreak--;
                }
                centerBreakX = xBreak + halfBreakWidth;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i >= 20) {
                    sleepTime = i;
                }
            }
        }).start();
    }

    // ball movements
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private boolean collideToBreak               = false;
    private boolean collideToBreakAndMoveToRight = true;
    private boolean collideToRightWall           = false;
    private boolean collideToLeftWall            = false;
    private boolean collideToRightBlock          = false;
    private boolean collideToBottomBlock         = false;
    private boolean collideToLeftBlock           = false;
    private boolean collideToTopBlock            = false;


    // velocity of the ball in X and Y direction
    private double vX = 1.000;

    // A method that resets all the collision flags to their initial state. It's used to clear the collision flags at the beginning of each frame or when needed.
    private void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }


    // call other functions in this for ball movement and checking collisions
    private void setPhysicsToBall() {
        moveBall();
        checkCollisionWithWalls();
        checkCollisionWithPaddle();
        checkCollisionWithBlocks();
    }

    private void moveBall() {
        double vY = 1.000;      // vertical velocity of ball

        // if ball goes down, the yBall position is increased by vY
        if (goDownBall) {
            yBall += vY;
        } else { // ball goes up, the yBall position decreased by vY
            yBall -= vY;
        }

        // if ball goes right the xBall position increased by vX
        if (goRightBall) {
            xBall += vX;
        } else { // if ball goes left, the xBall position is decreased by vX
            xBall -= vX;
        }
    }

    private void checkCollisionWithWalls() {

        // top wall collision check
        // if yBall position is less than or equal to zero
        if (yBall <= 0) {
            resetCollideFlags();
            // ball moves down
            goDownBall = true;
            return;
        }

        // bottom wall position check
        if (yBall >= sceneHeight) {
            // go down is false, ball goes up
            goDownBall = false;

            // if ball is not gold:
            if (!isGoldStatus) {
                // live will be lost
                heart--;
                new Score().show((double) sceneWidth / 2, (double) sceneHeight / 2, -1, this);

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }
            }
        }

        // right wall collision
        if (xBall >= sceneWidth) {
            resetCollideFlags();
            collideToRightWall = true;
        }

        // left wall collision
        if (xBall <= 0) {
            resetCollideFlags();
            collideToLeftWall = true;
        }
    }

    private void checkCollisionWithPaddle() {
        // condition is checking if the ball's vertical position is at or below the paddle's top edge, considering the ball's radius.
        if (yBall >= yBreak - ballRadius) {
            // This checks if the ball is within the horizontal bounds of the paddle.
            if (xBall >= xBreak && xBall <= xBreak + breakWidth) {
                // If the ball collides with the paddle:
                hitTime = time;
                resetCollideFlags();
                collideToBreak = true;
                goDownBall = false;

                // calculates the relative position of the ball to the center of the paddle
                double relation = (xBall - centerBreakX) / ((double) breakWidth / 2);

                // Calculate Ball's Horizontal Velocity
                // adjusting the ball's movement based on its position relative to the paddle
                if (Math.abs(relation) <= 0.3) {
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                }

                // Set Direction of Ball After Collision based on whether the ball is to the
                // right or left of the paddle after the collision
                collideToBreakAndMoveToRight = xBall - centerBreakX > 0;
            }
        }
    }

    private void checkCollisionWithBlocks() {
        // if ball hits the paddle then go right is set according to collide to break and move to right (T or F)
        if (collideToBreak) {
            goRightBall = collideToBreakAndMoveToRight;
        }

        // if it collides with the right wall, ball should move to left
        if (collideToRightWall) {
            goRightBall = false;
        }

        // if it collides with left wall, ball should move to right
        if (collideToLeftWall) {
            goRightBall = true;
        }

        // if it collides with a block to right, ball should go left
        if (collideToRightBlock) {
            goRightBall = false;
        }

        // if it collides with a block to the left, ball should go right
        if (collideToLeftBlock) {
            goRightBall = true;
        }

        // if it collides with the top of the block, it should go up
        if (collideToTopBlock) {
            goDownBall = false;
        }

        // if it collides with the bottom of a block, it should go down
        if (collideToBottomBlock) {
            goDownBall = true;
        }
    }

    private void checkDestroyedCount() {
        // Check if All Blocks are Destroyed
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");      // print to console

            nextLevel();
        }
    }

    private void saveGame() {
        // Initialize thread
        new Thread(() -> {
            // create directory and file
            new File(savePathDir).mkdirs();
            File file = new File(savePath);
            /// create output stream
            ObjectOutputStream outputStream = null;
            try {
                // write primitive data types

                // write game states

                outputStream = new ObjectOutputStream(new FileOutputStream(file));

                outputStream.writeInt(level);
                outputStream.writeInt(score);
                outputStream.writeInt(heart);
                outputStream.writeInt(destroyedBlockCount);


                outputStream.writeDouble(xBall);
                outputStream.writeDouble(yBall);
                outputStream.writeDouble(xBreak);
                outputStream.writeDouble(yBreak);
                outputStream.writeDouble(centerBreakX);
                outputStream.writeLong(time);
                outputStream.writeLong(goldTime);
                outputStream.writeDouble(vX);

                // write boolean flags
                outputStream.writeBoolean(isExistHeartBlock);
                outputStream.writeBoolean(isGoldStatus);
                outputStream.writeBoolean(goDownBall);
                outputStream.writeBoolean(goRightBall);
                outputStream.writeBoolean(collideToBreak);
                outputStream.writeBoolean(collideToBreakAndMoveToRight);
                outputStream.writeBoolean(collideToRightWall);
                outputStream.writeBoolean(collideToLeftWall);
                outputStream.writeBoolean(collideToRightBlock);
                outputStream.writeBoolean(collideToBottomBlock);
                outputStream.writeBoolean(collideToLeftBlock);
                outputStream.writeBoolean(collideToTopBlock);

                // write block information
                ArrayList<BlockSerializable> blockSerializables = new ArrayList<>();
                for (Block block : blocks) {
                    if (block.isDestroyed) {
                        continue;
                    }
                    blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
                }

                outputStream.writeObject(blockSerializables);

                // shows game saved message
                new Score().showMessage("Game Saved", Main.this);

                // exception handling
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // this method calls other methods to load the game
    private void loadGame() {

        // create a new instance of LoadSave class
        LoadSave loadSave = new LoadSave();
        loadSave.read();                    // read the saved game state

        // call other methods
        updateGameParameters(loadSave);
        clearExistingBlocksAndChocolates();
        populateBlocksFromSave(loadSave.blocks);
        RestartGamefromLoaded();
    }

    private void updateGameParameters(LoadSave loadSave) {
        // Update various game parameters with the loaded values
        isExistHeartBlock = loadSave.isExistHeartBlock;
        isGoldStatus = loadSave.isGoldStatus;
        goDownBall = loadSave.goDownBall;
        goRightBall = loadSave.goRightBall;
        collideToBreak = loadSave.collideToBreak;
        collideToBreakAndMoveToRight = loadSave.collideToBreakAndMoveToRight;
        collideToRightWall = loadSave.collideToRightWall;
        collideToLeftWall = loadSave.collideToLeftWall;
        collideToRightBlock = loadSave.collideToRightBlock;
        collideToBottomBlock = loadSave.collideToBottomBlock;
        collideToLeftBlock = loadSave.collideToLeftBlock;
        collideToTopBlock = loadSave.collideToTopBlock;
        level = loadSave.level;
        score = loadSave.score;
        heart = loadSave.heart;
        destroyedBlockCount = loadSave.destroyedBlockCount;
        xBall = loadSave.xBall;
        yBall = loadSave.yBall;
        xBreak = loadSave.xBreak;
        yBreak = loadSave.yBreak;
        centerBreakX = loadSave.centerBreakX;
        time = loadSave.time;
        goldTime = loadSave.goldTime;
        vX = loadSave.vX;

    }

    private void clearExistingBlocksAndChocolates() {
        // Clear existing blocks and chocolates
        blocks.clear();
        chocos.clear();
    }

    private void populateBlocksFromSave(ArrayList<BlockSerializable> blockSerializables) {
        // Populate the blocks list with blocks from the saved state
        blockSerializables.forEach(ser -> {
            int r = new Random().nextInt(200);
            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
        });
    }

    // Try to restart the game with the loaded state
    private void RestartGamefromLoaded() {
        try {
            // sets flag to true and calls start()
            loadFromSave = true;
            start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // transition the game to the next level
    private void nextLevel() {
        // start thread
        Platform.runLater(() -> {
            try {
                // Sets the horizontal velocity of the ball to 1
                vX = 1.000;

                // stop game engine, reset all flags and ball goes down to true
                engine.stop();
                resetCollideFlags();
                goDownBall = true;

                // resets gold status and if heart exists
                isGoldStatus = false;
                isExistHeartBlock = false;

                // reset hit time, time and gold time
                hitTime = 0;
                time = 0;
                goldTime = 0;

                clearExistingBlocksAndChocolates();

                // reset count of destroyed blocks and start primary stage
                destroyedBlockCount = 0;
                start(primaryStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // restarts the whole game
    public void restartGame() {

        try {
            // reset level, heart and score parameter to initial values
            level = 0;
            heart = 3;
            score = 0;

            // reset x velocity of ball, destroyed block count and collide flags
            vX = 1.000;
            destroyedBlockCount = 0;
            resetCollideFlags();

            // ball should now go down when games starts again
            goDownBall = true;

            // resets gold status and heart block existence, time , hit time and gold time
            isGoldStatus = false;
            isExistHeartBlock = false;
            hitTime = 0;
            time = 0;
            goldTime = 0;

            clearExistingBlocksAndChocolates();
            start(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // onUpdate method
    @Override
    public void onUpdate() {
        Platform.runLater(this::updateUI);

        if (isBallWithinBlockBounds()) {
            checkBlockHits();
        }
    }

    // Method to update UI elements
    private void updateUI() {
        scoreLabel.setText("Score: " + score);
        heartLabel.setText("Heart : " + heart);

        rect.setX(xBreak);
        rect.setY(yBreak);

        ball.setCenterX(xBall);
        ball.setCenterY(yBall);

        updateChocos();
    }

    // Method to check if the ball is within the vertical bounds where blocks are present
    private boolean isBallWithinBlockBounds() {
        return yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop();
    }

    // Method to check if the ball hits a block and handle accordingly
    private void checkBlockHits() {
        for (final Block block : blocks) {
            int hitCode = block.checkHitToBlock(xBall, yBall);

            if (hitCode != Block.NO_HIT) {
                handleBlockHit(block, hitCode);
            }
        }
    }

    // Method to handle the effects of hitting a block
    private void handleBlockHit(Block block, int hitCode) {
        score += 1;
        new Score().show(block.x, block.y, 1, this);
        block.rect.setVisible(false);
        block.isDestroyed = true;
        destroyedBlockCount++;
        resetCollideFlags();

        if (block.type == Block.BLOCK_CHOCO) {
            handleChocoBlockHit(block);
        }

        if (block.type == Block.BLOCK_STAR) {
            handleStarBlockHit();
        }

        if (block.type == Block.BLOCK_HEART) {
            heart++;
        }

        setCollisionFlags(hitCode);
    }

    // Method to handle the effects of hitting a choco block
    private void handleChocoBlockHit(Block block) {
        final Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = time;
        Platform.runLater(() -> root.getChildren().add(choco.choco));
        chocos.add(choco);
    }

    // Method to handle the effects of hitting a star block
    private void handleStarBlockHit() {
        goldTime = time;
        ball.setFill(new ImagePattern(new Image("goldball.png")));
        System.out.println("gold ball");
        root.getStyleClass().add("goldRoot");
        isGoldStatus = true;
    }

    // Method to set collision flags based on hit code
    private void setCollisionFlags(int hitCode) {
        if (hitCode == Block.HIT_RIGHT) {
            collideToRightBlock = true;
        } else if (hitCode == Block.HIT_BOTTOM) {
            collideToBottomBlock = true;
        } else if (hitCode == Block.HIT_LEFT) {
            collideToLeftBlock = true;
        } else if (hitCode == Block.HIT_TOP) {
            collideToTopBlock = true;
        }
    }

    // Method to update the position of choco bonuses
    private void updateChocos() {
        for (Bonus choco : chocos) {
            choco.choco.setY(choco.y);
        }
    }


    private void togglePause() {
        if (engine != null) {
            if (engine.isPaused()) {
                startResumeCountdown();
            } else {
                engine.pause();
            }
        }
    }

    private void startResumeCountdown() {
        if (engine.isPaused()) {
            // If already paused, initiate countdown and then resume
            countdownAndResume();
        }
    }

    private void countdownAndResume() {
        isCountingDown = true;
        // Display countdown timer on the screen
        new Thread(() -> {
            try {
                for (int i = 3; i > 0; i--) {
                    final int count = i;
                    Platform.runLater(() -> new Score().showMessage(String.valueOf(count), Main.this));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Platform.runLater(() -> {
                    isCountingDown = false;
                    engine.resume();
                });
            }
        }).start();
    }

    @Override
    public void onInit() {

    }


    @Override
    public void onPhysicsUpdate() {

        // Check if all blocks are destroyed and perform necessary actions
        checkDestroyedCount();

        // Update the physics of the ball based on its movement and collisions
        setPhysicsToBall();

        // Check if the gold ball effect duration has exceeded 5000 milliseconds.
        updateGoldBallEffect();

        // Update the position of choco bonuses and check for collisions with the paddle.
        updateChocoBonusesPosition();

    }

    // Method to update the gold ball effect
    private void updateGoldBallEffect() {
        if (goldTime > 0 && time - goldTime > 5000) {

            // Remove the "goldRoot" style class
            root.getStyleClass().remove("goldRoot");
            ball.setFill(new ImagePattern(new Image("ball.png")));

            // Set gold status back to false
            isGoldStatus = false;

            // Reset goldTime after the effect is removed
            goldTime = 0;
        }
    }


    // Method to update the position of choco bonuses and check for collisions with the paddle
    private void updateChocoBonusesPosition() {
        for (Bonus choco : chocos) {
            if (choco.y > sceneHeight || choco.taken) {
                continue;
            }
            if (choco.y >= yBreak && choco.y <= yBreak + breakHeight && choco.x >= xBreak && choco.x <= xBreak + breakWidth) {
                // If a choco bonus collides with the paddle, mark it as taken, hide it, and increase the score.
                System.out.println("You Got it and +3 score for you");
                choco.taken = true;
                choco.choco.setVisible(false);
                score += 3;
                new Score().show(choco.x, choco.y, 3, this);
            }
            // Update the Y position of the choco bonus based on its creation time.
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }
    }


    // This method is called to update the current time in the game.
    @Override
    // sets the 'time' variable to the provided time value.
    public void onTime(long time) {
        this.time = time;
    }

}

