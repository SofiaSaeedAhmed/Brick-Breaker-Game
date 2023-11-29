# COMP2042_CW_hcysa3
# Compilation Instructions: 
Provide a clear, step-by-step guide on how to compile the
code to produce the application. Include any dependencies or special settings
required.

# Implemented and Working Properly: 
List the features that have been successfully
implemented and are functioning as expected. Provide a brief description of each.

# Implemented but Not Working Properly 
List any features that have been
implemented but are not working correctly. Explain the issues you encountered,
and if possible, the steps you took to address them.

**1- PaddleMove.java file**



# Features Not Implemented: 
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.

# New Java Classes: 
Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.

**1- New Board.java file made**

`Main.java` had a `initBoard()` method which I separated into a different class file called `Board.java`. This class file also has a `get()` method.

**Purpose:** This refactoring creates a Board class responsible for initializing and managing the game board. The Main class now creates an instance of Board and adds its blocks to the root of the scene. This separation enhances code organization and maintainability.  

  
**Location:** This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.  


**2- New BallInitializer.java file made**  

`Main.java` had a `initBall()` method called in `public void start(Stage primaryStage) throws Exception` section and initialized further in the code. I separated this into a different file called `BallInitializer().java`. Moreover the `xBall` and `yBall` calculations are now done in the main method where `initBall()` was called before instead of putting them in the `BallInitializer.java` class. This was done because if I coded that in the separate class, the ball was dropping from the very top instead of dropping from the center and above the paddle.

**Purpose:** Refactoring the `initBall()` method into a different class can offer several advantages in terms of code organization, readability, and maintainability.    

**Location:** This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.    

**3- New PaddleInitializer.java file made**    

`Main.java` had a `initBreak()` method in `public void start(Stage primaryStage) throws Exception` section and initialized further in the code. I separated this into a different file called `PaddleInitializer().java`.    

**Purpose:** Refactoring the `initBreak()` method into a different class can offer several advantages in terms of code organization, readability, and maintainability.    

**Location:** This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.    


# Modified Java Classes:
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.

**1- Lambda expressions Added**    

* In `GameEngine.java`, for all the Threads, I changed `Runnable()` to lambda expressions. I also removed `@Override` in the classes to simplify the syntax.  

* In `Main.java` for the part `load.setOnAction(Event ->{`, I added lambda expression at all places and removed `@Override` in the code to simplify the syntax.    

* In `Main.java` for the `private ArrayList<Block> blocks = new ArrayList<Block>()`, I changed it to `private ArrayList<Block> blocks = new ArrayList<>()` and for `private ArrayList<Bonus> chocos = new ArrayList<Bonus>()` I changed to `private final ArrayList<Bonus> chocos = new ArrayList<>()`.    

  By using the diamond operator, the compiler will infer the generic type (in this case, Block) based on the left-hand side of the assignment. This can make your   code cleaner and more concise.


**2- Changed threads in GameEngine**  

In `GameEngine.java`, IntelliJ was giving an error indicating that `Thread.sleep()` in a loop like this may lead to inefficient CPU usage, also known as busy-waiting. This was causing glitches in the game and was also potentially wasting CPU resources. Busy-waiting is generally discouraged because it can lead to high CPU usage and inefficient resource utilization. Hence, I introduced the `ScheduledExecutorService` to schedule periodic tasks at a fixed rate, eliminating the need for manual sleep in a loop.

* _**Initialization of ScheduledExecutorService:**_  

    I added a `ScheduledExecutorService` named scheduler as a class field.
    This executor service is initialized using `Executors.newScheduledThreadPool(2)`, creating a pool of two threads for scheduling tasks.  

  
* _**Update to Update() method:**_  

    I replaced the manual loop with `Thread.sleep()` in the `Update()` method with a scheduled task using `scheduler.scheduleAtFixedRate()`.
    The `onAction.onUpdate()` method is scheduled to run at a fixed rate of fps milliseconds.   


* _**Update to PhysicsCalculation() method:**_

    I replaced the manual loop in the  `PhysicsCalculation()` method with a scheduled task using `scheduler.scheduleAtFixedRate()`.
    The `onAction.onPhysicsUpdate()` method is scheduled to run at a fixed rate of fps milliseconds.  


* _**Update to TimeStart() method:**_  

    In the original code, the `TimeStart()` method had a manual loop with `Thread.sleep(1)` to increment the time variable at a fine-grained rate. I replaced this manual loop with a scheduled task using `scheduler.scheduleAtFixedRate()`.  
    The `onAction.onTime(time)` method is scheduled to run at a fixed rate of 1 millisecond.  


* _**Removal of timeThread:**_  

    The `timeThread` field is no longer necessary as the time-related tasks are now scheduled using the `ScheduledExecutorService`.  


* _**Changes to stop() method:**_  

    The `stop()` method was updated to shut down the `ScheduledExecutorService(scheduler)` using `scheduler.shutdownNow()` when the game engine is stopped. This ensures that the scheduled tasks are stopped gracefully.  

These modifications improve the efficiency of the code by using a modern concurrency mechanism (ScheduledExecutorService) for scheduling tasks at fixed rates, eliminating the busy-waiting warning and providing a cleaner and more maintainable code structure and also reduces the amount of code written.    

**2- Refactor the `setPhysicsToBall` method in Main.java by breaking it down into separate functions**    

I separated `setPhysicsToBall()` method into 4 different methods - `moveBall()` , `checkCollisionWithWalls()`, `checkCollisionWithPaddle()` and `checkCollisionWithBlocks()`. The `setPhysicsToBall()` method now calls these 4 methods.

I have reorganized the `setPhysicsToBall()` method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality.    

**3- Refactor the `loadGame()` method in Main.java by breaking it down into separate functions**    

I separated `loadGame()` method into 4 different methods- `updateGameParameters(LoadSave loadSave)`, `clearExistingBlocksAndChocolates()`, `populateBlocksFromSave(ArrayList<BlockSerializable> blockSerializables)` and  `RestartGamefromLoaded()`. The `loadGame()` method now creates a new instance of LoadSave class, reads the saved game state and calls the 4 methods.    

I have reorganized the `loadGame()` method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality.    

# Unexpected Problems: 
Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.
