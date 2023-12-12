package brickGame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  This class provides a flexible framework for game development with the ability to customize game actions and manage time-related functionalities.
 *  It helps to start and stop the game engine. Reference:
 *
 *  <a href="https://github.com/kooitt/CourseworkGame/blob/master/src/main/java/brickGame/GameEngine.java">GameEngine.java Link</a>
 */

public class GameEngine {

    private OnAction onAction;          // defines methods related to game actions
    private int fps = 15;               // frames per second, initially set to 15
    private ScheduledExecutorService scheduler; // more modern and flexible way to schedule tasks at fixed rates or with fixed delays.

    /**
     * a boolean flag indicating whether the game engine is currently stopped. Initially set to true
     */
    public boolean isStopped = true;
    private boolean paused = false;

    /**
     * setting the onAction instance, which implements game-related actions
     * @param onAction
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * a method for setting the frames per second and converting it to milliseconds
     * @param fps
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    /**
     *  a thread for updating the game method
     */
    private synchronized void Update() {
        scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(() -> {
            if (!paused) {
                onAction.onUpdate();
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    /**
     * initializes the game engine
     */
    private void Initialize() {
        onAction.onInit();
    }

    /**
     * responsible for starting the physics calculation loop on the physics thread
     */
    private synchronized void PhysicsCalculation() {
//        scheduler.scheduleAtFixedRate(() -> {
//            onAction.onPhysicsUpdate();
//        }, 0, fps, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            if (!paused) {
                onAction.onPhysicsUpdate();
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }


    /**
     * acts as clock for updating the time of the game
     */
    private void TimeStart() {
        scheduler.scheduleAtFixedRate(() -> {
            time++;
            onAction.onTime(time);
        }, 0, 1, TimeUnit.MILLISECONDS);
    }


    /**
     * starts game engine by calling the Initialize, Update, PhysicsCalculation, and TimeStart methods, effectively starting the game loop.
     * It sets the isStopped flag to false.
     */
    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    /**
     * halts the game engine by setting the isStopped flag to true and shutting down the scheduler if it is running.
     */
    public void stop() {
        if (!isStopped) {
            isStopped = true;

            // Shut down the scheduler
            if (scheduler != null) {
                scheduler.shutdownNow();
            }
        }
    }


    /**
     *  sets the paused flag to true, pausing the game engine's update and physics calculation loops.
     */
    public void pause() {
        paused = true;
    }

    /**
     * sets the paused flag to false, allowing the game engine to resume its update and physics calculation loops.
     */
    public void resume() {
        paused = false;
    }

    /**
     *  returns a boolean indicating whether the game engine is currently paused.
     * @return paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * keeps track of elapsed time in the game
     */
    private long time = 0;


    /**
     * This interface declares methods that represent different actions in the game such as
     * update state, initialize, physics update and update time.
     */
    public interface OnAction {

        /**
         * Invoked when the game needs to update its state.
         */
        void onUpdate();

        /**
         * Invoked during the initialization phase of the game.
         */
        void onInit();

        /**
         * Invoked for physics-related updates.
         */
        void onPhysicsUpdate();

        /**
         * Invoked to notify listeners about the elapsed time in the game.
         * @param time
         */
        void onTime(long time);
    }
}


