package brickGame;


public class GameEngine {

    private OnAction onAction;          // defines methods related to game actions
    private int fps = 15;               // frames per second, initially set to 15
    private Thread updateThread;        // thread to update game logic, repeatedly execute game logic at a specified frame rate
    private Thread physicsThread;       // thread for physics related calculations
    public boolean isStopped = true;    // a boolean flag indicating whether the game engine is currently stopped. Initially set to true

    // setting the onAction instance, which implements game-related actions
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    // a method for setting the frames per second and converting it to milliseconds
    /**
     * @param fps set fps and we convert it to millisecond
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }
    

    // a new thread for updating the game method
        // method runs in a loop, calls onAction.update() and then sleeps for the specified duration (fps)
        // the thread continues until it is interrupted
    private synchronized void Update() {
        updateThread = new Thread(() -> {
            while (!updateThread.isInterrupted()) {     // loop will end if thread is interrupted
                try {
                    onAction.onUpdate();
                    Thread.sleep(fps);      // puts the thread to sleep for a specified duration (fps)
                                            // done to control the rate at which game logic is updated
                }
                catch (InterruptedException e) {   // executed if an interruption of type "InterruptedException" occurs
                    e.printStackTrace();        // prints the exception, useful for debugging
                }
            }

        });
        updateThread.start();
    }

    // initializes the game engine
    private void Initialize() {
        onAction.onInit();
    }

    // responsible for starting the physics calculation loop on the physics thread
    private synchronized void PhysicsCalculation() {
        physicsThread = new Thread(() -> {     // runnable defines the code that will be executed when thread is started
            // loop will execute until the thread is not interrupted
            while (!physicsThread.isInterrupted()) {
                try {
                    onAction.onPhysicsUpdate();     // update physics related stuff in game
                    Thread.sleep(fps);              // sleep for duration specified by frames per second
                                                    // sleep duration helps regulate the rate at which physics calculations take place
                }
                catch (InterruptedException e) { // interruption exceptions can be thrown when loop is interrupted during sleep
                    e.printStackTrace();        // catches and prints interruption exceptions during sleep
                }
            }

        });

        physicsThread.start();      // starts the physics thread initiates the physics calculation loop

    }

    // method starts game engine
        // initializes time, calls initialization method, starts update and physics calculation thread and begins time thread
        //  initlizes isstopped (if gameengine is stopped) to false;
    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    // method to stop game engine
        // checks if the engine is not already stopped --> sets the isStopped to true and stops update,physics and timethread
    public void stop() {
        if (!isStopped) {
            isStopped = true;

            // Interrupt the threads instead of stopping them abruptly
            if (updateThread != null) {
                updateThread.interrupt();
            }
            if (physicsThread != null) {
                physicsThread.interrupt();
            }
            if (timeThread != null) {
                timeThread.interrupt();
            }
        }
    }


    private long time = 0;      // variable keeps track of elapsed time in the game

    private Thread timeThread;  // private field of type thread dedicated to handling the time-keeping loop

    // method starts the time keeping loop on the time-thread

    private void TimeStart() {
        timeThread =  new Thread(() -> {
            try {
                while (true) {
                    time++;                  // loop increments the time variable and calls action on time
                    onAction.onTime(time);   // notifies about the elapsed time
                    Thread.sleep(1);    // sleep duration controls the rate at which time increments
                                             // Sleeping for 1 millisecond provides a fine-grained time update, ensuring that the onTime method is called frequently.
                }
            } catch (InterruptedException e) {  // exception thrown when the thread is interrupted in sleep
                e.printStackTrace();
            }
        });
        timeThread.start();
    }





    // variable declared to store the timestamp of the last update
    // can be used to calculate time between updates
    long lastUpdateTime = System.currentTimeMillis();


    // This interface declares methods that represent different actions in the game.
    public interface OnAction {
        void onUpdate();    // Invoked when the game needs to update its state.

        void onInit();      // Invoked during the initialization phase of the game.

        void onPhysicsUpdate();     // Invoked for physics-related updates.

        void onTime(long time);     // Invoked to notify listeners about the elapsed time in the game.
    }

}
