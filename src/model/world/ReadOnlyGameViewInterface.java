package model.world;

import java.util.List;
import java.util.Map;
import model.player.PlayerType;

/**
 * Provides read only access to getter methods
 * to fetch state of the components of the game.
 */
public interface ReadOnlyGameViewInterface {
  /**
   * Gets the current player's name.
   *
   * @return string of the name of current player.
   */
  String getCurrentPlayerName();

  /**
   * Gets the type of the current player(Human/Computer).
   *
   * @return type of the current player.
   */
  PlayerType getCurrentPlayerType();

  /**
   * Retrieves the neighbors for the current
   * player's space.
   *
   * @return list of string of current player's neighbor spaces.
   */
  List<String> getCurrentPlayerNeighbors();

  /**
   * Gets space name of the current player.
   *
   * @return string of current player space name.
   */
  String getPlayerSpaceName(String s);

  /**
   * Gets number of items that current player carries.
   *
   * @return integer of number of items that current player
   *         carries.
   */
  int getCurrentPlayerItemCount();

  /**
   * Detailed information of the player is provided
   * which includes name of the player, player type,
   * item capacity, space and items that player
   * is currently holding.
   *
   * @param playerName name of the player whose
   *                   detail need to be fetched.
   * @return string of detailed information of a player.
   */
  String getPlayerDetails(String playerName);

  /**
   * Retrieves the items that a player can pick
   * from the current space.
   *
   * @return list of string of items.
   */
  List<String> getPickableItemNames();

  /**
   * Gets world name.
   *
   * @return world object.
   */
  String getWorldName();

  /**
   * Get the world size in terms of rows
   * and columns.
   *
   * @return List of two integer number of rows and number of columns
   */
  List<Integer> getWorldSize();

  /**
   * Gets list of all the spaces in the world.
   *
   * @return list of string of all the spaces.
   */
  List<String> getSpaceNames();

  /**
   * Retrieves detailed information of a particular
   * space which includes space name, location,
   * items in the space and players in the space.
   *
   * @param spaceName name of space whose details
   *                  need to be fetched.
   * @return string of details of space.
   */
  String getSpaceDetails(String spaceName);

  /**
   * Retrieves the map of space which includes
   * name and coordinate of all the spaces.
   *
   * @return map of space and its coordinates.
   */
  Map<String, List<String>> getSpaceCoordinates();

  /**
   * Details of target's name, health,
   * and current space can be fetched
   * with this method.
   *
   * @return string of target details.
   */
  String getTargetDetails();

  /**
   * List of string of items that the current player holds.
   *
   * @return list of string of items.
   */
  List<String> getCurrentPlayerItemList();

  /**
   * Check computer can hit target or not.
   *
   * @return true if computer should hit target
   *         false otherwise.
   */
  boolean checkComputerTargetHit();

  /**
   * Gets the name of the player who wins the game
   * and an empty string if there is no winner.
   *
   * @return string of the name of the player who wins the game.
   */
  String getWinner();

  /**
   * Gets a random space from all the spaces in the world.
   *
   * @return string of the random space name
   */
  String getRandomSpace();

  /**
   * Gets the item name for computer player to hit the target.
   *
   * @return string of the item name to hit the target.
   */
  String getHitItem();

  /**
   * Gets a random space to move the computer player across
   * different places in the world.
   *
   * @return string of the space name.
   */
  String getMovePlayer();

  /**
   * Picks a random item from the space for the computer player
   * and asks the human player to pick the item present in the room.
   *
   * @return string of the item name.
   */
  String getPickItem();


  /**
   * Retrieves space name of the target.
   * @return string of target's space name.
   */
  String getTargetSpace();

  /**
   * Retrieves space name of the pet.
   * @return string of pet's space name.
   */
  String getPetSpaceName();

  /**
   * Retrieves name of all the players in the game.
   * @return list of string all the player names.
   */
  List<String> getPlayerNames();

}
