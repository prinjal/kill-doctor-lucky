# CS 5010 Semester Project

This repo represents the coursework for CS 5010, the Spring 2022 Edition!

**Name:** Prinjal Dave

**Email:** dave.pri@northeastern.edu

**Preferred Name:** Prinjal



### About/Overview

This program is in the Second phase of creating Dr Lucky's board game.
The program will virtually create any custom world which can store objects of
rectangular shape (Spaces) which itself has a capability of virtually storing
items and a target. Players (Human/Computer) can be added and they can move
between different neighboring spaces and pick items in spaces.
Players can hit the target with the items they hold and target has a pet
which will make the room invisible and will move in dfs manner in the world.
Players will be able to see other players only if they are in same rooms or
are in neighbor rooms.
There are various other features which are mentioned below in list of features.



### List of Features

1. Can create a parent (world) to store children (spaces) which itself has a capabilty of storing couple of objects (items and target).
2. Play game by adding players and computer players (which can play a game by themselves). Game includes several spaces which a player
can enter while its creation. They can also pick items present in the space, has a functionality of moving from one space to neighboring
spaces and can look around for detailed view of neighboring spaces. All of these counts as a turn. Along with these a specific player,
space details can be retreived and the world can be drawn as a graphical respresentation and can be stored in local disk in PNG format.
3. A player can only see another player if they are in same room or in neighboring rooms.
4. Player can hit the target with the item that they hold and a player can also move the pet from one space to another.
5. Computer player will always hit the target if they are in same room and no other players are watching them.
6. Whosoever hits the target last wins the game.
7. If the target remains alive and the turns reaches the total number of allowed turns then the game gets over and target is thought to be
leving the world. 



### How to Run

1. Copy the txt file which specifies the design of World, Items, Spaces and Target in the /res folder.
2. Open terminal and change directory to the /res folder.
3. Run the Milestone_3_Gameplay.jar with couple of command line arguments:
    a: File which contains specification of the world (without spaces).
    b: Total number or turns for the game.
    Example: java -jar "Milestone_3_Gameplay.jar milestone.txt 50".



### How to Use the Program

The program can be played with 1-7 number of Human and Computer players.
Each player can enter their name, the space they wants to enter and the capacity of the bag.
Each player will be called out with their respective names and they can make their respective moves.
With every turn the target and its pet will move in the world and and players will be able to
identify the location of the target at the start of their turn.
Players will try to kill the target with the items they hold and whosoever makes the last hit wins the game.


### Example Runs

There are 3 Example Run files in the ./res/Example Run folder. Each file has their own test case validation which are as listed below:
Note: Copy the example run text files in an editor which have reference to lines (For Example: Sublime Text). 
1. The Example Run file consists of several test cases:
    a: Visibility of a neighboring space with pet. (57-74) As per the world image stored in the ./res/ folder we can see
    that Billiard Room has Armory as its neighbor however, it is not seen by the player since the pet starts in armory which in turn 
    makes the space invisible.

    b: Human player moving the pet. (394-396)

    c: Human Player hitting the target. (756-765) As seen the target charcter's health gets decreased by 3.

    d: Human Player hitting the target which gets dodged. (663-665)

    e: Target character escapes with his life and game ends. (894-897)

2. File named as Computer Player Wins.txt demonstrates that the Computer player wins.

3. File named as Human Player Wins.txt demonstrates that the Human player wins.

4. The Pet DFS.txt file shows how pet moves in DFS manner.

### Design/Model Changes

v1.0
1. World interface was created.
2. In the previous design the functionality of Driver class is now given to the FacadeController class and FacadeInterface and Driver class will only couple with FacadeController.
3. Segregator class is created to parse the specification file and provide to FacadeController.
4. All the interfaces are refactored/renamed to "InterfacName"Interface from I"InterfaceName" to match checkstyle.
5. Signature of getSpaceLocation method in World class is changed to List<SpaceInterface> from List<String>.
6. Signature getNeighbors method in World class is changed to List<List> from List<String>.
7. Missing multiplicities and access modifiers were added.

v2.0
1. PlayerWrapper has been created.
2. Getters of list of objects are removed from all the classes.
3. PlayerWrapper and Player (class/interface has been added) and it has a relationship with the world object.
4. Controller package is created to store the game controller class and the models which take respective actions.
5. Model is segregated with different packages like items, space, target, world and player.
6. Other facade class is created which is made interactable for the controller.

v3.0
1. Methods like HitTarget, MovePet has been created in PlayerWrapper.
2. PlayerWrapper class also handles the visiblity of other players including the pet algorithm.
3. TargetPetWrapper class has been created which takes care of mutating target and pet objects
and relocates them in different spaces around the world.
4. Two more attributes are added in the space class which shows the presence of target and pet in the particular space.
5. Couple of commands are added to Hit the target and Move the pet.



### Assumptions

1. It has been assumed that the coordinates of upper left corner will always be greater than coordinates of lower left corner.
2. Names of all the spaces in the specification file should be distinct.
3. Names of all the items in the specification file should be distinct.
4. There is only one Target (singleton).
5. It has been assumed that there are no more than 7 Human an 7 Computer players. This assumption is made for the program
to become easily interactanble by the user. Since it doesn't have any GUI adding more players will make the output look messier
and difficult to change turns. This is a modular assumption which means that this can be changed at any point of time.
6. Computer player will only be picking the valid choices from the list of items and spaces.
7. Computer player might select to pick an item even though if its space doesn't have any, but the turn will later get
invalid and computer player will take another chance to execute a different action.
8. Player will be shown neighbor and items while selecting move and pick item turns respectively. Having done this
the look around move is still worth taking a turn as it shows which items and players are present in the neighbor space
along with their neighbors.
9. Player will not be able to see the neighbor where the pet is present but will still be able 
to move to that space if they remember neighbor of their current space. This has been done since the requirement 
listed that the pet should make the space invisible and not immovable.
  

### Limitations

1. The sequnce of objects in the specification file should be provided in the order mentioned in the mock specification file otherwise it would error out. 
2. FacadeController could be optimized a bit more by providing the specific values requested by client to restrict the client from mutating objects.
3. The command line argument for path of the specificaiton file should not have any spaces.
4. Game can be played by only 1-7 human and 1-7 computer players with limited number of turns.


### Citations
1. https://www.geeksforgeeks.org
2. https://www.tutorialspoint.com/index.htm

