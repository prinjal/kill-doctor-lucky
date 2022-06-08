package model.player;

import java.util.List;

/**
 * PlayerWrapperInterface creates a blueprint for
 * PlayerWrapper class and includes methods which are
 * significant to make a player function and take
 * turns accordingly.
 */
public interface PlayerWrapperInterface {

  /**
   * Creates a player object after validating its attributes from
   * the parameters.
   *
   * @param name         name of the player.
   * @param playerType   type of the player(Human/Computer) enum type.
   * @param spaceName    name of the space.
   * @param itemCapacity bag capacity for this player object.
   */
  void createPlayer(String name, PlayerType playerType, String spaceName, int itemCapacity,
                    StrategyInterface strategy);

  /**
   * Moves a player from one space to another which
   * includes adding player object to the destined space
   * and removing it from the source space. Marked as a turn.
   *
   * @param spaceName name of the space player wants to move.
   */
  void movePlayer(String spaceName);

  /**
   * Picks an item available in the current room
   * and sets it as a list of item object in player
   * object. Marked as a turn.
   *
   * @param itemName name of the item that player wants to pick.
   */
  void pickItem(String itemName);

  /**
   * Retrieves detailed information of the neighboring
   * spaces of the player's current space. Marked as a turn.
   *
   * @return string of neighboring space details.
   */
  String lookAround();

  /**
   * Checks whether the game is over or not by comparing
   * the turns taken in the game with
   * the total number of turns allowed for the whole game.
   *
   * @param turns the total number of allowed turns for whole game.
   * @return true if game is over and false if the game is not over.
   */
  boolean isGameOver(int turns);

  /**
   * Moves pet to the space provided by the
   * user.
   *
   * @param spaceName space name where pet
   *                  needs to be moved.
   */
  void movePet(String spaceName);

  /**
   * Hits the target with the item provided by the user.
   *
   * @param itemName name of the item which will be needed
   *                 to hit the target.
   * @return string of status of the hitTarget move which
   *        includes dodging the move, executing a successful
   *        hit and poking in the eye.
   */
  String hitTarget(String itemName);

  /**
   * Checks whether computer player should hit the target or not.
   *
   * @return yes if computer hits the target and no otherwise
   */
  Boolean checkComputerTargetHit();

  /**
   * Gets a specific player object mentioned in parameter.
   *
   * @param playerName name of the player whose object needs to
   *                   be fetched.
   * @return PlayerInterface type object.
   */
  PlayerInterface getPlayerObject(String playerName);

  /**
   * Gets the name of the current player.
   *
   * @return string of name of the current player.
   */
  String getCurrentPlayerName();

  /**
   * Retrieves type of the current player(Human/Computer).
   *
   * @return type of the current player.
   */
  PlayerType getCurrentPlayerType();

  /**
   * Get the list of neighbors of the player's current space.
   *
   * @return list of string of player's current space neighbors.
   */
  List<String> getCurrentPlayerNeighbors();

  /**
   * Gets the space name of the current player.
   *
   * @return string of current player's space.
   */
  String getPlayerSpace(String playerName);

  /**
   * Gets item count of the current player.
   *
   * @return number of items that current player carries.
   */
  int getCurrentPlayerItemCount();

  /**
   * Retrieves detailed information of a particular player.
   * This includes player's current space, item list, name, type
   * and bag capacity.
   *
   * @param playerName name of the player whose details need
   *                   to be fetched.
   * @return string of current player's details.
   */
  String getPlayerDetails(String playerName);

  /**
   * Gets the list of the items that the current player can pick
   * from its current space.
   *
   * @return list of string of items that can be picked by
   *        current player.
   */
  List<String> getPickableItemNames();

  /**
   * Returns the name of the winner and an empty
   * string if there is no winner.
   * @return string of player name who wins the game.
   */
  String getWinner();


  /**
   * getTurn helper method is solely
   * responsible for changing the turn of the player
   * and setting the current player as the next player
   * from the 0 indexed list of the players in the
   * game. It is set as a private method hence, no
   * class outside this can access this to mutate the
   * turn.
   */
  int makeMove(int bound);

  /**
   * Get current player strategy to compute the algorithm
   * for taking valid turns and moves.
   * @return strategy object to compute the algorithm.
   */
  StrategyInterface getCurrentPlayerStrategy();

  /**
   * Returns names of all the spaces in the world.
   * @return list of string of all the spaces.
   */
  List<String> getSpaceNames();

  /**
   * Retrieves name of all the players in the game.
   * @return list of string all the player names.
   */
  List<String> getPlayerNames();
}
