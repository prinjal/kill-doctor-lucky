# CS 5010 Semester Project

This repo represents the coursework for CS 5010, the Spring 2022 Edition!

**Name:** Prinjal Mayur Dave

**Email:** dave.pri@northeastern.edu

**Preferred Name:** Prinjal



### About/Overview

A board game which is loosely coupled from the Doctor Lucky board game. Implemented the game using Java MVC Architecture.
For View used Swing library.



### List of Features

1. Create a graphical representation of the world map and also, provide the functionality to use custom speicification .
2. Add a human-controlled player to the game.
3. Add a computer-controlled player to the game.
4. Move a player.
5. Allow a player to pick up an item.
6. Allow a player to look around.
7. Allow a player to attack.
8. Allow a player to move the pet.



### How to Run

1. Run Cmd
2. Type command => `java -jar <path of jar file> <max-turns>`. for e.g. ==> (java -jar milestone.jar 5)
3. Press enter


### How to Use the Program

1. Run the jar file as per instruction mentioned in how to run.

2. After running the program you will be prompted with a welcome screen. Click on the Menu bar and start the game.

3. Add the player which can be computer/human(Upto 10 players) with carry limit and room name of your choice.

4. Take turn if the turn is of human then the program will prompt you to take a turn.

5. If the turn is of computer then the computer will automatically take a turn and prompt you what the computer did.

6. In this manner play the game, as soon as the total max turns are exhausted or the target dies, the game quits.


### Example Runs

List any example runs that you have in res/ directory and provide a description of what each example represents or does. Make sure that your example runs are provided as *plain text files*.



### Design/Model Changes

View
- Removed the standalone JComboBox class and used the JOptionPane instead for taking input from the user.
- Removed the ButtonListener class.
- Removed the DrawLookAround class, instead `DrawWorld` handles the logic of lookAround and of creating the world.
- Added a custom layout manager class which handles the layout of the `DrawWorld` class.
- Added more methods in GameView class viz. addMenuBar, getActionInput, etc

Controller
- Removed the Switch-Case logic for all the commands and instead used the hashmap to call the commands.

Model
- Implemented the Strategy Pattern for the computer and human player, depending upon the type it will call the method, thus made complete process polymorphic.


### Assumptions

Carry Limit 
- For Human Player - Minimum limit is considered to be 4 and maximum limit is considered to be 8.
- For Computer Player - Carry limit is considered to be 5.

Player Visibility
- Player A will only be able to see Player B's details when they are in same/neighboring spaces.

### Limitations

- Meets all the requirements specified in the Milestone 4 except the scrollbar.


### Citations

1. Using of Buffered Image. https://docs.oracle.com/en/java/javase/11/docs/api/java.desktop/java/awt/image/BufferedImage.html
2. Graphics 2D. https://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
3. DFS approach - https://www.youtube.com/watch?v=pcKY4hjDrxk&t=836s&ab_channel=AbdulBari
4. Layout managers - https://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
5. Swing Components - https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html



