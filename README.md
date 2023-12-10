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

**1- Show message in Score.java file**    

The `score.java` file had a problem in the `showMessage()` method due to which the animations such as "Level Up" or "+1" for score kept getting stuck on the screen instead of disappearing in some time. To fix the problem, I created a JavaFX `Timeline` object, which allows you to define actions that should occur at specified intervals. This fixed the problem of display staying for a while, however, still sometimes "+1" or "-1" doesnt appear when a brick is broken or lives are lost (heart gets reduced tho).

**2- Increase paddle width feature**    

In the `main.java` file, I have implemented a feature in which if the ball hits the `Block.Long` brick, the paddle width will increase until the next level is reached. I have explained the details of this feature in the "Additions" part of this readme file. As easy as it seemed to implement the feature when I got the idea, it was actually hard to adjust the collision and paddle movement with the new width. Hence, even tho I have made all the changes I can in the `move()` method, `restart()` and collision methods to address the issue, sometimes the increased width paddle works fine and sometimes it doesn't move properly. I can't understand how to solve this issue.    

The reason I kept this feature in the game is because even after taking steps to solve the issue, the issue only comes sometimes and other times it works fine. I put it a lot of hardwork to implement it hence, I thought of keeping it instead of removing it from the game. 

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

**4- New ButtonStyleHelper.java file made**    

I wanted to style the `Begin Playing` button in `startGame()` method declared in Main.java file so I made a separate class file to put the styling code for the button.    

**Location:** This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.    


# Modified Java Classes:
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.

## Refactoring:

Refactoring offers multiple advantages in software development, including improved code maintainability, enhanced readability, bug fixing, and performance optimization.    

**1- Lambda expressions Added**    

* In `GameEngine.java`, for all the Threads, I changed `Runnable()` to lambda expressions. I also removed `@Override` in the classes to simplify the syntax.  

* In `Main.java` for the part `load.setOnAction(Event ->{`, I added lambda expression at all places and removed `@Override` in the code to simplify the syntax.    

* In `Main.java` for the `private ArrayList<Block> blocks = new ArrayList<Block>()`, I changed it to `private ArrayList<Block> blocks = new ArrayList<>()` and for `private ArrayList<Bonus> chocos = new ArrayList<Bonus>()` I changed to `private final ArrayList<Bonus> chocos = new ArrayList<>()`.    

By using the diamond operator, the compiler will infer the generic type (in this case, Block) based on the left-hand side of the assignment. This can make your   code cleaner and more concise.

* In `Main.java` for the `nextLevel()` method, I changed `Runnable()` to lambda expressions.



            
**2- Refactor the `setPhysicsToBall` method in Main.java by breaking it down into separate functions**    

I separated `setPhysicsToBall()` method into 4 different methods - `moveBall()` , `checkCollisionWithWalls()`, `checkCollisionWithPaddle()` and `checkCollisionWithBlocks()`. The `setPhysicsToBall()` method now calls these 4 methods.

I have reorganized the `setPhysicsToBall()` method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality.        

**Functions of each method**    

* _**moveBall()**_
  
Controls the movement of a ball in a two-dimensional space. It uses two boolean variables, `goDownBall` and `goRightBal`l, to determine the direction in which the ball should move. The method updates the position of the ball (xBall and yBall) based on the values of these boolean variables and predefined velocity values.

* _**checkCollisionWithWalls()**_    

Checks collision with top and bottom walls to update direction flags accordingly. If bottom wall is hit, then a life is lost unless its a gold ball.    

* _**checkCollisionWithPaddle()**_    

Checks if the ball hit the paddle or not to update directions and variables accordingly.    

* _**checkCollisionWithBlocks()**_    

Checks if ball collided with any side (top, bottom, left and right) and do calculations accordingly.


**3- Refactor the `loadGame()` method in Main.java by breaking it down into separate functions**    

I separated `loadGame()` method into 4 different methods- `updateGameParameters(LoadSave loadSave)`, `clearExistingBlocksAndChocolates()`, `populateBlocksFromSave(ArrayList<BlockSerializable> blockSerializables)` and  `RestartGamefromLoaded()`. The `loadGame()` method now creates a new instance of LoadSave class, reads the saved game state and calls the 4 methods.    

I have reorganized the `loadGame()` method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality.    

**4- Remove Redundant statement**    

The `nextLevel()` method in `Main.java` had the statement `engine.stop()` written twice, so I deleted the redundant statement.    

**5- Refactor `onUpdate()` method in Main.java by breaking it down into separate functions**    

I separated `onUpdate()` method into 8 different methods- `updateUI()`, `isBallWithinBlockBounds()`, `checkBlockHits()`, `handleBlockHit(Block block, int hitCode)`, `handleChocoBlockHit(Block block)`, `handleStarBlockHit()`, `setCollisionFlags(int hitCode)`, and `updateChocos()`.    

**6- Refactor `onUpdate()` method in Main.java by breaking it down into separate functions**    

I separated `onPhysicsUpdate()` method into 2 different methods- `updateGoldBallEffect()` and `updateChocoBonusesPosition()`.    

**7- Refactor `start(Stage primaryStage)` method in Main.java by breaking it down into separate functions**    

I separated `start(Stage primaryStage)` method in 3 different methods- `createMainMenu()`, `showWinScreen()`, and `startGame()`.

## Other Modifications:

**1- Changed threads in `GameEngine.java`**  

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

**2- Use of `clearExistingBlocksAndChocolates()`:**    

I made this method while refactoring `loadGame()` and I used this in `restartGame()` and `nextLevel()` methods. I removed some lines of code in these methods and used this instead. Hence, it was visible that refactoring helps to make other methods smaller and concise as well.    

**3- Use of enhanced switch statment**    

In `handle(KeyEvent event)`, I made use of enhanced switch statement which makes the code more concise and easy to read.

# Unexpected Problems: 
Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.

**1- Gold Ball background**
In the method, `updateGoldBallEffect()`, it is written that if the Gold ball has stayed from 5 seconds, it should be converted back to the normal ball. Even tho the gold ball goes back to normal ball and the functionality also changes from gold ball to normal ball, the problem is that sometimes the goldRoot is not removed so the background stays until the next level comes. 
This problem should not come as I have coded `root.getStyleClass().remove("goldRoot");` and `ball.setFill(new ImagePattern(new Image("ball.png")));` one after the other, so if one picture is changing the background should also be changed. The functionality of the game however is not affected and it runs smoothly.    

I tried my best to fix this error but it still occurs sometimes.
