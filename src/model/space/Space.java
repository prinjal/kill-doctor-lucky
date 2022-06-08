package model.space;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.item.Items;
import model.item.ItemsInterface;
import model.player.PlayerInterface;

/**
 * Space implements ISpace interface to minimize coupling.
 * A particular Space is created from the World.
 * Space class sets space data members,
 * space details list and adds items in respective spaces.
 * It also comprises methods to get data members of space.
 * All the data
 */

public class Space implements SpaceInterface {

  private final int spaceIndex;
  private final String spaceName;
  private final List<String> spaceCoordinates;
  private Map<String, ItemsInterface> itemObjectMap;
  private List<String> neighbors;
  private final Map<String, PlayerInterface> playersMap;
  private boolean isTarget;
  private boolean isPet;

  /**
   * Space constructor validates space name, space index
   * and space details(coordinates) and sets them as class
   * variables. It also initializes map objects of class
   * variables.
   *
   * @param spaceName    name of the space.
   * @param spaceIndex   index of the space(location).
   * @param spaceDetails coordinates of a particular space.
   */
  public Space(String spaceName, int spaceIndex, List<String> spaceDetails) {
    if (spaceName == null || spaceIndex < 0 || spaceDetails == null) {
      throw new IllegalArgumentException("Space parameters cannot be null");
    }
    this.neighbors = new ArrayList<>();
    this.itemObjectMap = new HashMap<>();
    this.playersMap = new HashMap<>();
    this.spaceName = spaceName;
    this.spaceIndex = spaceIndex;
    this.spaceCoordinates = spaceDetails;
    this.isTarget = false;
    this.isPet = false;
  }


  @Override
  public void addItems(List<String> itemList) {
    if (itemList == null) {
      throw new IllegalArgumentException("Item list cannot be null");
    } else {
      int i = 0;
      while (i < itemList.size()) {
        try {
          int itemDamage;
          itemDamage = Integer.parseInt(String.valueOf(itemList.get(i)));
          if (itemDamage < 0) {
            throw new IllegalArgumentException("Item damage cannot be negative");
          }
          ItemsInterface item = new Items(spaceIndex, itemDamage, itemList.get(i + 1));
          itemObjectMap.put(itemList.get(i + 1), item);
          i += 2;
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Item damage should be a number value");
        }
      }
    }
  }

  @Override
  public String getSpaceName() {
    return spaceName;
  }

  @Override
  public int getSpaceIndex() {
    return spaceIndex;
  }

  @Override
  public List<String> getSpaceCoordinates() {
    List<String> spaceCoordinatesClone = new ArrayList<>();
    for (String x : spaceCoordinates) {
      spaceCoordinatesClone.add(x);
    }
    return spaceCoordinatesClone;
  }

  @Override
  public List<String> getItemNames() {
    List<String> itemNames = new ArrayList<>();
    for (Map.Entry<String, ItemsInterface> entry : itemObjectMap.entrySet()) {
      itemNames.add(entry.getKey());
    }
    return itemNames;
  }


  @Override
  public void addNeighbors(List<String> neighbors) {
    if (neighbors == null) {
      throw new IllegalArgumentException("Neighbors cannot be null");
    }
    List<String> neighborClone = new ArrayList<>();
    for (String neighbor : neighbors) {
      neighborClone.add(neighbor);
    }
    this.neighbors = neighborClone;
  }

  @Override
  public List<String> getNeighbors() {
    List<String> neighborsClone = new ArrayList<>();
    for (String neighborNames : neighbors) {
      neighborsClone.add(neighborNames);
    }
    return neighborsClone;
  }

  @Override
  public void addPlayer(PlayerInterface player) {
    if (player == null) {
      throw new IllegalArgumentException("Player instance cannot be null");
    }
    playersMap.put(player.getPlayerName(), player);
  }

  @Override
  public List<String> getPlayers() {
    List<String> players = new ArrayList<>();
    for (Map.Entry<String, PlayerInterface> entrySet : playersMap.entrySet()) {
      players.add(entrySet.getKey());
    }
    return players;
  }

  @Override
  public void removePlayer(String playerName) {
    if (playerName == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }
    playersMap.remove(playerName);
  }

  @Override
  public void removeItem(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Item not found");
    }
    itemObjectMap.remove(itemName);

  }

  @Override
  public ItemsInterface getItemObject(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }
    return itemObjectMap.get(itemName);
  }

  @Override
  public void setPet() {
    isPet = !isPet;
  }

  @Override
  public void setTarget() {
    isTarget = !isTarget;
  }

  @Override
  public boolean isPet() {
    return isPet;
  }

  @Override
  public boolean isTarget() {
    return isTarget;
  }


  @Override
  public String toString() {
    String spaceString;
    String itemDetails = "";
    List<String> itemList = new ArrayList<>();
    for (Map.Entry<String, ItemsInterface> entry : itemObjectMap.entrySet()) {
      itemList.add(entry.getValue().getItemName());
    }
    spaceString = String.format("%s, Space Index: '%d',"
                    + "%s"
                    + "Player(s): %s,"
                    + "Pet Present?: %s",
            spaceName, spaceIndex,
            itemList.size() > 0 ? itemList.toString().replace(',', '-')
                    + "," : "No Items,", getPlayers(), isPet);
    return spaceString.trim();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else {
      if (obj instanceof SpaceInterface) {
        SpaceInterface other = (Space) obj;
        return other.getSpaceName().equals(this.getSpaceName());
      } else {
        return false;
      }
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(spaceName, spaceIndex);
  }


}
