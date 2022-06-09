package model.player;

import java.util.List;
import model.item.ItemsInterface;

/**
 * PlayerInterface sets a blueprint for
 * a player class. It includes method to
 * add item, set player in a particular space,
 * and get player details.
 */
public interface PlayerInterface {

  /**
   * Validates the item object and adds it to
   * the player.
   *
   * @param item object of the item which need to be
   *             added to player object.
   */
  void addItem(ItemsInterface item);

  /**
   * Validates and sets player object's space name attribute.
   *
   * @param spaceName name of the space that need to
   *                  be set for this player object.
   * @param spaceIndex space index that need to be
   *                   set for the player.
   */
  void setPlayerSpace(String spaceName, int spaceIndex);

  /**
   * Retrieves name of the player.
   *
   * @return name of the player as a string.
   */
  String getPlayerName();

  /**
   * Retrieves player's current space name.
   *
   * @return player's current space name as a String.
   */
  String getPlayerSpaceName();

  /**
   * Gets the player's current space location(index).
   *
   * @return player object's current space location.
   */
  int getPlayerSpaceLocation();

  /**
   * Gets the list of items that the player object holds.
   *
   * @return list of strings of item that player holds.
   */
  List<String> getPlayerItemList();

  /**
   * Gets the type of the player object (Human/Computer).
   *
   * @return the type of the player object as an enum PlayerType.
   */
  PlayerType getPlayerType();

  /**
   * Returns item object of the item mentioned in parameter.
   * @param itemName name of the item which needs to be picked.
   * @return ItemInterface object.
   */

  ItemsInterface getItem(String itemName);

  /**
   * Removes item specified in the parameter from
   * the player possession.
   * @param itemName name of item which needs to be removed.
   */
  void removeItem(String itemName);


  /**
   * Get strategy uses the player's respective strategy to
   * use the relevant algorithm.
   * @return strategy of the player which will compute
   *         the algorithm.
   */
  StrategyInterface getStrategy();
}
