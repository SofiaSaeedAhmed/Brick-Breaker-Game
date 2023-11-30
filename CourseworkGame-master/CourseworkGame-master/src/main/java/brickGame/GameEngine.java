package brickGame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {

    private OnAction onAction;          // defines methods related to game actions
    private int fps = 15;               // frames per second, initially set to 15
    private ScheduledExecutorService scheduler; // more modern and flexible way to schedule tasks at fixed rates or with fixed delays.
    public boolean isStopped = true;        // a boolean flag indicating whether the game engine is currently stopped. Initially set to true
    private boolean paused = false;

    // setting the onAction instance, which implements game-related actions
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    // a method for setting the frames per second and converting it to milliseconds
    public void setFps(int fps) {
        this.fps = 1000 / fps;
    }

    // a new thread for updating the game method
    private synchronized void Update() {
        scheduler = Executors.newScheduledThreadPool(2);

        //scheduler.scheduleAtFixedRate(() -> onAction.onUpdate(), 0, fps, TimeUnit.MILLISECONDS);

        scheduler.scheduleAtFixedRate(() -> {
            if (!paused) {
                onAction.onUpdate();
            }
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    // initializes the game engine
    private void Initialize() {
        onAction.onInit();
    }

    // responsible for starting the physics calculation loop on the physics thread
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


    private void TimeStart() {
        // Add a new task to the existing scheduler for time updates
        scheduler.scheduleAtFixedRate(() -> {
            time++;
            onAction.onTime(time);
        }, 0, 1, TimeUnit.MILLISECONDS);
    }


    // method starts game engine
    public void start() {
        time = 0;
        Initialize();
        Update();
        PhysicsCalculation();
        TimeStart();
        isStopped = false;
    }

    // method to stop game engine
    public void stop() {
        if (!isStopped) {
            isStopped = true;

            // Shut down the scheduler
            if (scheduler != null) {
                scheduler.shutdownNow();
            }
        }
    }


    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }

    public boolean isPaused() {
        return paused;
    }




    private long time = 0;      // variable keeps track of elapsed time in the game

    // This interface declares methods that represent different actions in the game.
    public interface OnAction {
        void onUpdate();        // Invoked when the game needs to update its state.

        void onInit();          // Invoked during the initialization phase of the game.

        void onPhysicsUpdate();      // Invoked for physics-related updates.

        void onTime(long time);      // Invoked to notify listeners about the elapsed time in the game.
    }
}
