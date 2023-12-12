# COMP2042_CW_hcysa3
# Compilation Instructions: 
The code for the game is located in project/AhmedSofiaSaeed_JavaVersion.zip file. You extract all the files and run it on IntelliJ or any other Java IDE. Make sure you incorporate the javaFX sdk from ypur library. Moreover, the resources file should have the following pictures with exact names, please dont change the name of any of the following files since all the images have been used in the game:    
* ball.png
* goldball.png
* bg.png
* heart.jpg
* win.png
* longer.png
* block.jpg
* bonus1.png
* bonus2.png
* lost.png
* choco.jpg
* style.css

# Additions:

**1- Pause and resume with countdown feature**    

This feature pauses the game when player presses the space bar. While the game is paused, the screen background color changes to a light pink to indicate that the game is in a paused state. The game can be resumed again by pressing the space bar, this starts a coundown on the screen (3...2...1) so that the player gets ready and then the game restarts from where it was paused. Moreover, everytime the game is paused or resumed, a message is printed on the console.    

**Methods used for coding this feature:**    

* _togglePause()_: Toggles the pause state of the game engine. If the engine is not null, it checks whether the engine is currently paused. If is it currently paused, it calls the countdown method to resume the game; otherwise, it pauses the engine and updates the UI. 

* _startResumeCountdown()_: If engine is already paused, initiate countdown and then call the resume method. Removes a CSS style class from the UI root element.
  
* _countdownAndResume()_: Displays the countdown on the screen. After the countdown, resumes the game engine by updating the UI on the JavaFX application thread.

* _handle(KeyEvent event)_: Contains a case where if Space Bar is clicked, it calls the `togglePause()` method.

* _Style.css_: Added a .pauseRoot sections with background color set to #c78aa3. This will be added when the game is paused and removed once game is resumed.

_Note:_ All these methods are in the main.java file except for the style.css file changes.

**2- Menu Screens for main menu, win game and lost game**    

This feature helps the game look more aesthetic and user friendly. When the game is launched, a Main Menu screen (an image) shows up which has the title of the game, instructions to play the game and a "start" button. When the start button is clicked, the game proceeds to the screen with paddle, labels and blocks and the user can now play the game. If the user wins the game (completes level 7), the win screen shows up. This screen has an image which contains a winning message, a pretty trophy with the total score written on top and a "play again" button. The play again button restarts the game from scratch for the user to play.   

If the user loses the game (all lives lost), a lost game background image comes up with a "Better luck next time" message and a "Play again" button. The score, heart, and level labels are still shown with the bricks left so the user can see at what point they lost the game.    

All the background screens are designed by me using Canva.

**Methods used for coding this feature:**    

* _start(Stage primaryStage)_: I have refactored the orignal code for this and broken it into different methods. If game is not started, it will show the main menu screen by calling its method, or else it will directly call the start game method (for level ups and restart  game).
  
*   _createMainMenu()_: Creates a new Pane called root to serve as the main container for UI elements. Loads a background image. Creates a "Start Game" buttonwith a specific position and sets the button's initial opacity to 0.0, making it invisible so that only the button designed in the image can be seen. Then proceeds to the initial scene for the game.
  
* _startGame()_: This method has the condition - if level == 8 is reached, it calls the showWinScreen method.

* _showWinScreen()_: Creates new pane, loads background image, creates play again button, sets its position according to the background image and makes it invisible. Then proceeds to the initial scene for the game.

* _checkCollisionWithWalls()_: This method contains the call to game lost in the `score.java` file. If heart == 0 it calls `Score().showGameOver(this)`.

* _showGameOver()_: This method sets up a game over screen with a background image and a "Restart" button, updating the UI on the JavaFX Application Thread.

_Note:_ All methods mentioned above are in the main.java file except for _showGameOver()_, this is in the score.java class file.    

**3- Feature for increasing paddle width if a special brick is hit**    

I thought of adding another special brick (the way we have choco, heart and stars). This brick will increase the width of the paddle for that level only, making it easier to play the game. When the next level is reached, the paddle goes back to orignal width.     

The coding logic is that, when the longer brick is hit, the flag is set to true and brickWdith (paddle width) is set to 150 now and width of paddle is increased. Every method that uses brickWdith has an if..else statement which sees if the flag is true or not. If it is true, 150 is used to calculations or else, 130 is used. When the game restarts, the flag is set to false again. When a new level starts, the flag is set to false.

**Methods used for coding this feature:**    

* In block.java file: Defined brick longer and assigned it 103 number. In the draw() method, I added the longer block type and set its image to fill.
  
* In board.java file: I added a boolean flag (similar to the one heart block has), which will help note that if one of longer brick is there, then theres no need to add more in a particular level. (Same as heart brick logic)

* In main.java file: I added a boolean flag (similar to the heart block), this flag helps note that has the paddle width increased or not as the paddle width (defined as brickWdith in our game) is esstential for paddle movements and physics calculations to calculate collisions. This part was very hard to code and I had to make changes to move(), checkCollisionWithPaddle(), restartGame(), handleBlockHit() and startGame().
  
# Implemented and Working Properly: 

1- Main menu screen: This gives a main menu image with game instructions and play button.    

2- Labels: Score, Heart, Level get updated properly. When brick is hit, score increases. When life is lost, heart decreases. Level is completed, next level starts.   

3- Paddle movement: Moves well with left and right keys. When the paddle width is 130, it always works properly. Accepts all collisions.    

4- Gold Ball: Gold ball stays for 5 seconds when start brick is hit and during this time, life is not lost.    

5- Heart Brick: When it is hit, lives gets increased by 1.    

6- Choco brick: When it is hit, the bonus comes depending on a random number. If paddle catched bonus, score is incremented by 3. If paddle misses, nothing happens, score stays same.    

7- Game won screen: Appears when game is won (7 levels completed) with an aesthetic background, total score displayed and play again button. The button works well.

8- Game lost screen: Appears when all lives are lose, with an aesthetic background. All the labels are displayed with bricks left for that level and restart game button which works well.    

9- Pause and resume with Space bar: When game is paused, pink background come. When space bar is pressed again, it resumes with coundown and pink background goes away.    

10- Save game: Game can be saved by clicking S.    

11- Normal bricks: Appear randomly and are hit properly.    

12- Heart comes only once: this works well, only 1 heart brick per level.    

13- Longer brick comes only once: this works well, only 1 longer brick per level.    

14- All collisions work fine - walls, bricks, and paddle.

# Implemented but Not Working Properly 

**1- Show message in Score.java file**    

The `score.java` file had a problem in the `showMessage()` method due to which the animations such as "Level Up" or "+1" for score kept getting stuck on the screen instead of disappearing in some time. To fix the problem, I created a JavaFX `Timeline` object, which allows you to define actions that should occur at specified intervals. This fixed the problem of display staying for a while, however, still sometimes "+1" or "-1" doesnt appear when a brick is broken or lives are lost (heart gets reduced tho).

**2- Increase paddle width feature**    

In the `main.java` file, I have implemented a feature in which if the ball hits the `Block.Long` brick, the paddle width will increase until the next level is reached. I have explained the details of this feature in the "Additions" part of this readme file. As easy as it seemed to implement the feature when I got the idea, it was actually hard to adjust the collision and paddle movement with the new width. Hence, even tho I have made all the changes I can in the `move()` method, `restart()` and collision methods to address the issue, sometimes the increased width paddle works fine and sometimes it doesn't move properly. I can't understand how to solve this issue.    

The reason I kept this feature in the game is because even after taking steps to solve the issue, the issue only comes sometimes and other times it works fine. I put it a lot of hardwork to implement it hence, I thought of keeping it instead of removing it from the game. 

# Features Not Implemented: 
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.

# New Java Classes: 

**1- New Board.java file made**

`Main.java` had a `initBoard()` method which I separated into a different class file called `Board.java`. This class file also has a `get()` method.

_Purpose_: This refactoring creates a Board class responsible for initializing and managing the game board. The Main class now creates an instance of Board and adds its blocks to the root of the scene. This separation enhances code organization and maintainability.  

  
_Location_: This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.  


**2- New BallInitializer.java file made**  

`Main.java` had a `initBall()` method called in `public void start(Stage primaryStage) throws Exception` section and initialized further in the code. I separated this into a different file called `BallInitializer().java`. Moreover the `xBall` and `yBall` calculations are now done in the main method where `initBall()` was called before instead of putting them in the `BallInitializer.java` class. This was done because if I coded that in the separate class, the ball was dropping from the very top instead of dropping from the center and above the paddle.

_Purpose_: Refactoring the `initBall()` method into a different class can offer several advantages in terms of code organization, readability, and maintainability.    

_Location_: This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.    

**3- New PaddleInitializer.java file made**    

`Main.java` had a `initBreak()` method in `public void start(Stage primaryStage) throws Exception` section and initialized further in the code. I separated this into a different file called `PaddleInitializer().java`.    

_Purpose_: Refactoring the `initBreak()` method into a different class can offer several advantages in terms of code organization, readability, and maintainability.    

_Location_: This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.    

**4- New ButtonStyleHelper.java file made**    

I wanted to style the `Begin Playing` button in `startGame()` method declared in Main.java file so I made a separate class file to put the styling code for the button.    

_Location_: This file is located in the BrickGame folder along with all other classes- Main.java, Block.java, Bonus.java etc.    


# Modified Java Classes:

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

**Functions of each method**    

* _moveBall()_
  
  Controls the movement of a ball in a two-dimensional space. It uses two boolean variables, `goDownBall` and `goRightBal`l, to determine the direction in which     the ball should move. The method updates the position of the ball (xBall and yBall) based on the values of these boolean variables and predefined velocity         values.

* _checkCollisionWithWalls():_ Checks collision with top and bottom walls to update direction flags accordingly. If bottom wall is hit, then a life is lost unless its a gold ball.    

* _checkCollisionWithPaddle():_ Checks if the ball hit the paddle or not to update directions and variables accordingly.    

* _checkCollisionWithBlocks():_ Checks if ball collided with any side (top, bottom, left and right) and do calculations accordingly.    

I have reorganized the `setPhysicsToBall()` method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality.        



**3- Refactor the `loadGame()` method in Main.java by breaking it down into separate functions**    

I separated `loadGame()` method into 4 different methods- `updateGameParameters(LoadSave loadSave)`, `clearExistingBlocksAndChocolates()`, `populateBlocksFromSave(ArrayList<BlockSerializable> blockSerializables)` and  `RestartGamefromLoaded()`. The `loadGame()` method now creates a new instance of LoadSave class, reads the saved game state and calls the 4 methods.    

**Functions of each method**    

* _updateGameParameters(LoadSave loadSave):_ Update various game parameters with the loaded values.

* _clearExistingBlocksAndChocolates():_ Clear existing blocks and chocolates by calling `block.clear()` and `chocos.clear()`.

* _populateBlocksFromSave(ArrayList<BlockSerializable> blockSerializables):_  Populate the blocks list with blocks from the saved state.

* _RestartGamefromLoaded():_ Try to restart the game with the loaded state.
  
I have reorganized the `loadGame()` method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality.    

**4- Remove Redundant statement**    

The `nextLevel()` method in `Main.java` had the statement `engine.stop()` written twice, so I deleted the redundant statement.    

      
**5- Refactor `onUpdate()` method in Main.java by breaking it down into separate functions**    

I separated `onUpdate()` method into 8 different methods- `updateUI()`, `isBallWithinBlockBounds()`, `checkBlockHits()`, `handleBlockHit(Block block, int hitCode)`, `handleChocoBlockHit(Block block)`, `handleStarBlockHit()`, `setCollisionFlags(int hitCode)`, and `updateChocos()`.    

**Functions of each method**   

* _updateUI():_  Method to update UI elements.
  
* _isBallWithinBlockBounds():_ Method to check if the ball is within the vertical bounds where blocks are present.

* _checkBlockHits():_ Check if the ball hits a block and handle accordingly.

* _handleBlockHit(Block block, int hitCode):_ Method to handle the effects of hitting a block. Calls functions for various blocks, star, normal, heart, choco and longer block.

* _handleChocoBlockHit(Block block):_ Method to handle the effects of hitting a choco block. Update score according to bonus.

* _handleStarBlockHit():_ Method to handle the effects of hitting a star block. Add gold root and change the block.

* _setCollisionFlags(int hitCode):_ Method to set collision flags based on hit code.

* _updateChocos():_ Method to update the position of choco bonuses.

I have reorganized the method into smaller, more focused methods to improve readability and maintainability. It also makes the code clearer and easier to manage without altering its functionality. 
  

**6- Refactor `onUpdate()` method in Main.java by breaking it down into separate functions**    

I separated `onPhysicsUpdate()` method into 2 different methods- `updateGoldBallEffect()` and `updateChocoBonusesPosition()`. Now it calls these methods only instead of having their code in one method.    

**Functions of each method**   

* _updateGoldBallEffect():_ Method to update the gold ball effect. Remove the goldball and reset timer.

* _updateChocoBonusesPosition():_ Method to update the position of choco bonuses and check for collisions with the paddle.

**7- Refactor `start(Stage primaryStage)` method in Main.java by breaking it down into separate functions**    

I separated `start(Stage primaryStage)` method in 3 different methods- `createMainMenu()`, `showWinScreen()`, and `startGame()`.  

**Functions of each method**  

* _createMainMenu():_ Sets up a main screen which loads when the game is launched. It displays the keys used to play and a start button. I designed the background image using _Canva_.
  
* _showWinScreen():_ Sets up a screen which is displayed when player wins the game and also displays the total score. It has a background image with I designed using _Canva_ and it has a play again button as well which restarts the game from scratch.
  
* _startGame():_ This initializes all the UI components and other variables of the game. Also defines the levels of the game and calls the `paddleInitializer.java` class.

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

**4- Lost Game screen**    

I changed the `showGameOver()` method in `Score.java` class to now display a screen with an image background which displays that the player has lost. Moreover, it also gives a play again button which restarts the game. This is helpful as it makes the game more user friendly and aesthetically pleasing.    

**5- Remove showWin() method**    

I removed the `showWin()` method in the `Score.java` class as I have already designed a `showWinScreen()` method in the `main.java` class which does the job.    

**6- Style.css file**    

I added a different backgroun color and pauseRoot section with a bg color.

# Unexpected Problems: 

**1- Gold Ball background**
In the method, `updateGoldBallEffect()`, it is written that if the Gold ball has stayed from 5 seconds, it should be converted back to the normal ball. Even tho the gold ball goes back to normal ball and the functionality also changes from gold ball to normal ball, the problem is that sometimes the goldRoot is not removed so the background stays until the next level comes. 
This problem should not come as I have coded `root.getStyleClass().remove("goldRoot");` and `ball.setFill(new ImagePattern(new Image("ball.png")));` one after the other, so if one picture is changing the background should also be changed. The functionality of the game however is not affected and it runs smoothly.    

I tried my best to fix this error but it still occurs sometimes.
