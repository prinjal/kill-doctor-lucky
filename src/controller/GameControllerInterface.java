package controller;

/**
 * GameControllerInterface sets the blueprint
 * for the GameController Class. It basically handles
 * all the actions from the user through the input from view.
 */
public interface GameControllerInterface {

  /**
   * Method to start the game and set the view visible.
   */
  void playGame();

  /**
   * Method to process the action given by the user through the view.
   *
   * @param command integer number of the action.
   */
  void processMoves(int command);

  /**
   * Overloaded Method of process moves which takes two inputs command and the input value
   * for the moving the player to specified location.
   *
   * @param command  int value of the action
   * @param roomName name of the room.
   */
  void processMoves(int command, String roomName);

  /**
   * Method which accepts the custom specification of the world.
   *
   * @param s path of the file
   */
  void setModel(String s);

  /**
   * Method which checks the state of the game, returns true if the game is over.
   *
   * @return boolean value, true if game is over else false.
   */
  boolean isGameOver();
}
