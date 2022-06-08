package model.targetpet;

import java.util.List;

/**
 * Target class follows a singleton design pattern
 * and sets all the data members through a static
 * method.
 * It can also have the access of all the data members.
 */
public class Target implements TargetInterface {

  private final String targetName;
  private int targetHealth;
  private int targetSpaceLocation;
  private String targetSpaceName;
  private final List<String> spaceNames;

  /**
   * TargetConstructor sets the class attributes like
   * name of the target, its health, name of the spaces of the
   * world, and target's initial location.
   *
   * @param targetHealth health of the target with which it
   *                     enters the world.
   * @param targetName   name of the target.
   * @param spaceNames   name of all the spaces of the world.
   */
  public Target(int targetHealth, String targetName,
                List<String> spaceNames) {
    if (targetHealth <= 0 || targetName == null || spaceNames == null) {
      throw new IllegalArgumentException("Invalid target health, name or space names");
    }
    this.targetName = targetName;
    this.targetHealth = targetHealth;
    this.spaceNames = spaceNames;
    this.targetSpaceLocation = 0;
    this.targetSpaceName = spaceNames.get(targetSpaceLocation);

  }


  @Override
  public int getTargetHealth() {
    return targetHealth;
  }

  @Override
  public int getTargetLocation() {
    return targetSpaceLocation;
  }

  @Override
  public String getTargetName() {
    return targetName;
  }


  @Override
  public void moveTarget() {
    if (targetSpaceLocation >= spaceNames.size() - 1) {
      targetSpaceLocation = 0;
    } else {
      targetSpaceLocation = targetSpaceLocation + 1;
    }
    this.targetSpaceName = spaceNames.get(targetSpaceLocation);
  }

  @Override
  public String getTargetSpaceName() {
    return targetSpaceName;
  }

  @Override
  public String toString() {
    return String.format("Target name: '%s' Health: '%d' Space Location: %s",
            targetName, targetHealth, targetSpaceName);
  }

  @Override
  public void hitTarget(int itemDamage) {
    if (itemDamage <= 0) {
      throw new IllegalArgumentException("Item damage cannot be 0");
    }
    targetHealth -= itemDamage;
  }
}
