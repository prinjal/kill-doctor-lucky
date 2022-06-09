package model.player;

import java.util.List;

/**
 * StrategyInterface handles the algorithm for
 * human and computer player. The computer player
 * comprise taking valid turns and moves randomly
 * while the human player will throw a custom exception
 * which will be used to ask for user input.
 */
public interface StrategyInterface {

  /**
   * Sets the playerWrapper object to use it in
   * computing algorithms for taking turns.
   * @param playerWrapper object to compute methods
   *                      for taking moves.
   */
  void setPlayerWrapper(PlayerWrapperInterface playerWrapper);

  /**
   * Restricts the computer player to take turn
   * in a certain range and asks the human player
   * to enter a valid turn by throwing exception.
   * @param bound range in which the random number
   *              should be generated.
   * @return integer value of the randomly generated number.
   */
  int getTurn(int bound);

  /**
   * Returns a random space from the world.
   * @return string of a random space from the world.
   */
  String getRandomSpace();

  /**
   * Returns the item with most damage for the
   * computer player and the human player
   * will throw exception to ask for user input
   * for using the item which will be needed
   * to hit the target.
   * @return the item name with most damage for the
   *         computer player.
   */
  String getHitItem();

  /**
   * Returns a random space from the list of neighbors
   * for the computer player and asks for user input
   * for the human player.
   * @return string of the randomly generated space name
   *         from the list of neighbors.
   */
  String getMovePlayer();

  /**
   * Gets a random item from the list of items present in
   * the current room for the computer player while
   * asks the human player to provide an item name to
   * pick the respective item.
   * @return string of the random item name that
   *         needs to be picked.
   */
  String getPickItem();

  /**
   * Returns a random space from the world.
   * @param spaceNames name of the spaces in the world.
   * @return string of the random space from the world.
   */
  String getCreatePlayerSpace(List<String> spaceNames);

}
