package model.item;

/**
 * Item interface sets item location, item
 * damage and item name.
 * It can also access all the data members of the
 * concrete Item class.
 */
public interface ItemsInterface {

  /**
   * Get item damage of the current item.
   *
   * @return Item damage of current item in Integer.
   */
  int getItemDamage();

  /**
   * Gets item name of the current item.
   *
   * @return String of item name.
   */
  String getItemName();

  /**
   * Gets item location of the current item.
   *
   * @return Location of the item in Integer.
   */
  int getItemLocation();
}
