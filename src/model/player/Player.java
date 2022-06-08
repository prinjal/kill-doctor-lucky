package model.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.item.ItemsInterface;

/**
 * Player class stores the attributes like
 * name, type, space, item capacity
 * and list of items of a particular
 * player object. It can also revert the details
 * of a player by getters.
 */
public class Player implements PlayerInterface {
  private final String playerName;
  private final PlayerType playerType;
  private String spaceName;
  private int spaceLocation;
  private final int itemCapacity;
  private final Map<String, ItemsInterface> itemMap;
  private final StrategyInterface strategy;

  /**
   * Player constructor validates and sets attributes like
   * player name, type, space name, space location and item capacity
   * that player has while its creation.
   *
   * @param playerName    name of the player.
   * @param playerType    type of the player (Human/Computer).
   *                      This gets derived from PlayerType enum class.
   * @param spaceName     name of the space player wants to start the game from.
   * @param spaceLocation location of the space player wants to start in.
   * @param itemCapacity  bag capacity of a player to store items.
   */
  public Player(String playerName, PlayerType playerType,
                String spaceName, int spaceLocation, int itemCapacity, StrategyInterface strategy) {
    if (playerName == null || "".equals(playerName)) {
      throw new IllegalArgumentException("Enter a valid player name");
    } else if (playerType == null
            || (!playerType.equals(PlayerType.HUMAN)
            && !playerType.equals(PlayerType.COMPUTER))) {
      throw new IllegalArgumentException("Player type not appropriate");
    } else if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    } else if (itemCapacity < 0) {
      throw new IllegalArgumentException("Item capacity cannot be negative");
    }

    this.playerName = playerName;
    this.playerType = playerType;
    this.spaceName = spaceName;
    this.spaceLocation = spaceLocation;
    this.itemCapacity = itemCapacity;
    this.itemMap = new HashMap<>();
    this.strategy = strategy;
  }


  @Override
  public void addItem(ItemsInterface item) {
    if (item == null) {
      throw new IllegalArgumentException("Item cannot be null");
    } else if (itemMap.size() >= itemCapacity) {
      throw new IllegalStateException("No space left in bag");
    }
    itemMap.put(item.getItemName(), item);
  }

  @Override
  public void setPlayerSpace(String spaceName, int spaceIndex) {
    if (spaceName == null || spaceIndex < 0) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    String spaceNameClone = spaceName;
    this.spaceName = spaceNameClone;
    this.spaceLocation = spaceIndex;
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public String getPlayerSpaceName() {
    return spaceName;
  }

  @Override
  public int getPlayerSpaceLocation() {
    return spaceLocation;
  }

  @Override
  public List<String> getPlayerItemList() {
    List<String> itemsList = new ArrayList<>();
    List<ItemsInterface> item = new ArrayList<>();
    itemMap.values().stream().sorted(Comparator.comparingInt(ItemsInterface::getItemDamage));
    for (Map.Entry<String, ItemsInterface> entry : itemMap.entrySet()) {
      item.add(entry.getValue());
    }
    Comparator<ItemsInterface> comparator = Comparator.comparingInt(ItemsInterface::getItemDamage);
    item.sort(comparator.reversed());
    for (ItemsInterface s : item) {
      itemsList.add(s.getItemName());
    }
    return itemsList;
  }


  @Override
  public PlayerType getPlayerType() {
    return playerType;
  }

  @Override
  public ItemsInterface getItem(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null ");
    }
    if (itemMap.containsKey(itemName)) {
      return itemMap.get(itemName);
    } else {
      throw new IllegalArgumentException("Item not present");
    }
  }

  @Override
  public void removeItem(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null ");
    }
    if (itemMap.containsKey(itemName)) {
      itemMap.remove(itemName);
    } else {
      throw new IllegalArgumentException("Item not present");
    }
  }

  @Override
  public StrategyInterface getStrategy() {
    return strategy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else {
      if (obj instanceof PlayerInterface) {
        PlayerInterface other = (Player) obj;
        return other.getPlayerName().equals(this.getPlayerName());
      } else {
        return false;
      }
    }
  }


  @Override
  public int hashCode() {
    return Objects.hash(playerName);
  }


  @Override
  public String toString() {
    String playerNameDetails;
    playerNameDetails = String.format("Name: %s\nType: %s\nSpace name: %s, Space Index: %d"
                    + "\nItem list: %s\nBag capacity: %d",
            playerName, playerType.toString(), spaceName,
            spaceLocation, itemMap.values(),
            itemCapacity);
    return playerNameDetails;
  }

}
