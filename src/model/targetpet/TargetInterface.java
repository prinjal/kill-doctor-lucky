package model.targetpet;

/**
 * ITarget interface can get all the data members of
 * the Target class.
 * It can also move the target monotonically.
 */
public interface TargetInterface {
  /**
   * Retrieves health of the target.
   *
   * @return Health of target in Integer.
   */
  int getTargetHealth();

  /**
   * Gets the current target location
   * in the world (space index).
   *
   * @return Location of target in Integer.
   */
  int getTargetLocation();

  /**
   * Gets name of the target.
   *
   * @return Name of target in String.
   */
  String getTargetName();

  /**
   * Moves target from one space to another
   * in the world monotonically.
   */
  void moveTarget();

  /**
   * Hits target and reduces its health
   * by the item(damage) used by the player.
   * @param itemDamage integer value that is reduced
   *                   from target's health upon a
   *                   successful hit.
   */
  void hitTarget(int itemDamage);

  /**
   * Gets target's current space name.
   *
   * @return string of spacename.
   */
  String getTargetSpaceName();

}
