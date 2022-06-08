package model.item;

import java.util.Objects;

/**
 * Items class sets item data members
 * and can have the access of the same.
 */
public class Items implements ItemsInterface {

  private final int itemLocation;
  private final int itemDamage;
  private final String itemName;

  /**
   * Items class stores the attributes of a particular item
   * and returns data of a particular item through getters.
   *
   * @param itemLocation location(spaceIndex) of a particular item.
   * @param itemDamage   damage that an item can cause.
   * @param itemName     name of the item.
   */
  public Items(int itemLocation, int itemDamage, String itemName) {
    if (itemLocation < 0) {
      throw new IllegalArgumentException("Item Location cannot be negative");
    } else if (itemDamage < 0) {
      throw new IllegalArgumentException("Item damage cannot be negative");
    } else if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    } else {
      this.itemLocation = itemLocation;
      this.itemDamage = itemDamage;
      this.itemName = itemName;
    }
  }


  @Override
  public int getItemDamage() {
    return this.itemDamage;
  }

  @Override
  public String getItemName() {
    return this.itemName;
  }

  @Override
  public int getItemLocation() {
    return this.itemLocation;
  }

  @Override
  public String toString() {
    return String.format("Item name: '%s' Damage: '%d'",
            itemName, itemDamage);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else {
      if (obj instanceof ItemsInterface) {
        ItemsInterface other = (Items) obj;
        return other.getItemName().equals(this.getItemName());
      } else {
        return false;
      }
    }
  }


  @Override
  public int hashCode() {
    return Objects.hash(itemName, itemLocation);
  }
}
