package model.space;

import java.util.List;
import model.item.ItemsInterface;
import model.player.PlayerInterface;

/**
 * Space interface sets space data members,
 * space details list and adds items in respective spaces.
 * It also comprises methods to get data members of space.
 */
public interface SpaceInterface {

  /**
   * Add neighboring spaces for this space in
   * a list of string.
   *
   * @param neighbors List of string of neighbors.
   */
  void addNeighbors(List<String> neighbors);

  /**
   * Adds item in respective space.
   *
   * @param itemList input of item-list
   */
  void addItems(List<String> itemList);

  /**
   * Gets space name as a String.
   *
   * @return String of space name
   */
  String getSpaceName();

  /**
   * Gets space index as an Integer.
   *
   * @return space index as Integer.
   */
  int getSpaceIndex();

  /**
   * Gets space coordinates.
   *
   * @return List of space coordinates.
   */
  List<String> getSpaceCoordinates();

  /**
   * Gets items of this space.
   *
   * @return List of items inside the current space.
   */
  List<String> getItemNames();

  /**
   * Gets neighbors of this class.
   *
   * @return List of neighbors of this class.
   */
  List<String> getNeighbors();

  /**
   * Validates and adds a player to this space object.
   * @param players player object which needs to be
   *                added in this space object.
   */
  void addPlayer(PlayerInterface players);

  /**
   * Gets list of string of players that are
   * currently in this space object.
   * @return list of string of players.
   */
  List<String> getPlayers();

  /**
   * Remove player from the space by removing its
   * object from the Map of the objects stored
   * in class variables.
   * @param playerName name of the player which
   *                   need to be removed from
   *                   the space.
   */
  void removePlayer(String playerName);

  /**
   * Remove item from the space once player picks
   * the item.
   * @param itemName name of the item which need
   *                 to be removed from the space.
   */
  void removeItem(String itemName);



  /**
   * Gets the item object which needs to be
   * picked by the player object.
   * @param itemName name of the item
   *                 whose object need to
   *                 be fetched.
   * @return item class instance.
   */
  ItemsInterface getItemObject(String itemName);

  /**
   * Changes the availability of pet in the space.
   */
  void setPet();

  /**
   * Changes the availability of target in the space.
   */
  void setTarget();

  /**
   * Returns true if pet is available in current room
   * and false otherwise.
   * @return boolean value of pet's availability.
   */
  boolean isPet();

  /**
   * Returns true if target is available in current room
   * and false otherwise.
   * @return boolean value of target's availability.
   */
  boolean isTarget();
}
