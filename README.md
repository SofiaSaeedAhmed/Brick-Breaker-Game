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

# Features Not Implemented: 
Identify any features that you were unable to
implement and provide a clear explanation for why they were left out.

# New Java Classes: 
Enumerate any new Java classes that you introduced for the
assignment. Include a brief description of each class's purpose and its location in the
code.

# Modified Java Classes:
List the Java classes you modified from the provided code
base. Describe the changes you made and explain why these modifications were
necessary.

**1- Lambda expressions Added**    

In `GameEngine.java`, for all the Threads, I changed `Runnable()` to lambda expressions. I also removed `@Override` in the classes to simplify the syntax.

**2- Changed threads in GameEngine**  

In `GameEngine.java`, IntelliJ was giving an error indicating that `Thread.sleep()` in a loop like this may lead to inefficient CPU usage, also known as busy-waiting. This was causing glitches in the game and was also potentially wasting CPU resources. Busy-waiting is generally discouraged because it can lead to high CPU usage and inefficient resource utilization. Hence, I introduced the `ScheduledExecutorService` to schedule periodic tasks at a fixed rate, eliminating the need for manual sleep in a loop.

*_**Initialization of ScheduledExecutorService:**_  

I added a `ScheduledExecutorService` named scheduler as a class field.
This executor service is initialized using `Executors.newScheduledThreadPool(2)`, creating a pool of two threads for scheduling tasks.

_**Update to Update() method:**_  

I replaced the manual loop with `Thread.sleep()` in the `Update()` method with a scheduled task using `scheduler.scheduleAtFixedRate()`.
The `onAction.onUpdate()` method is scheduled to run at a fixed rate of fps milliseconds.

_**Update to PhysicsCalculation() method:**_

I replaced the manual loop in the  `PhysicsCalculation()` method with a scheduled task using `scheduler.scheduleAtFixedRate()`.
The `onAction.onPhysicsUpdate()` method is scheduled to run at a fixed rate of fps milliseconds.

_**Update to TimeStart() method:**_  

In the original code, the `TimeStart()` method had a manual loop with `Thread.sleep(1)` to increment the time variable at a fine-grained rate. I replaced this manual loop with a scheduled task using `scheduler.scheduleAtFixedRate()`.  
The `onAction.onTime(time)` method is scheduled to run at a fixed rate of 1 millisecond.

_**Removal of timeThread:**_  

The `timeThread` field is no longer necessary as the time-related tasks are now scheduled using the `ScheduledExecutorService`.

_**Changes to stop() method:**_  

The `stop()` method was updated to shut down the `ScheduledExecutorService(scheduler)` using `scheduler.shutdownNow()` when the game engine is stopped. This ensures that the scheduled tasks are stopped gracefully.

These modifications improve the efficiency of the code by using a modern concurrency mechanism (ScheduledExecutorService) for scheduling tasks at fixed rates, eliminating the busy-waiting warning and providing a cleaner and more maintainable code structure and also reduces the amount of code written.

# Unexpected Problems: 
Communicate any unexpected challenges or issues you
encountered during the assignment. Describe how you addressed or attempted to
resolve them.
