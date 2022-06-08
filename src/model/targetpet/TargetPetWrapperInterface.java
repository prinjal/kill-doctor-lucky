package model.targetpet;

/**
 * TargetPetWrapperInterface holds methods to
 * mutate target and pet object like moving the
 * target and pet, hitting the target and
 * fetching details of target and pet.
 */
public interface TargetPetWrapperInterface {


  /**
   * Moves target from one space to another
   * in the world monotonically.
   *
   */
  void moveTarget();

  /**
   * Retrieves target details like target name,
   * space, and damage that target can withstand.
   *
   * @return string of details of target.
   */
  String getTargetDetails();

  /**
   * Get target's current space name.
   *
   * @return string of target's current space name.
   */
  String getTargetSpaceName();

  /**
   * Moves pet to a particular space provided
   * by the player.
   *
   * @param spaceName space name where target
   *                  need to be moved.
   */
  void movePet(String spaceName);

  /**
   * Hits the target with the item damage provided
   * in the parameter and reduces it health accordingly.
   *
   * @param itemDamage integer value of item damage
   *                   that will be reduced from target's
   *                   health.
   */
  void hitTarget(int itemDamage);

  /**
   * Get pet's current space name.
   *
   * @return string of pet's current space name.
   */
  String getPetSpaceName();

  /**
   * Gets the health of the target.
   *
   * @return integer value of health of the target.
   */
  int getTargetHealth();
}
