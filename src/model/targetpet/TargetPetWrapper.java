package model.targetpet;

import java.util.List;

/**
 * TargetPetWrapper holds methods to mutate the target
 * and the pet object as per the turn taken by the
 * player. It includes hitting the target, moving the pet
 * and retrieves information of target and pet.
 */
public class TargetPetWrapper implements TargetPetWrapperInterface {
  private TargetInterface target;
  private PetInterface pet;
  private final List<String> spaceNames;

  /**
   * TargetPetWrapper constructor sets the class variables like
   * target object, pet object and name of all the spaces of the world.
   * @param targetList list of the target specification that needs
   *                   to be parsed in order to create a target object.
   * @param petList list of pet specification that needs to be parsed
   *                in order to create a pet object.
   * @param spaceNames name of all the spaces of the world.
   */
  public TargetPetWrapper(List<String> targetList, List<String> petList, List<String> spaceNames) {
    if (targetList == null || petList == null || spaceNames == null) {
      throw new IllegalArgumentException("Target list, Pet list and space names cannot be null");
    }
    this.spaceNames = spaceNames;
    this.target = createTarget(targetList);
    this.pet = createPet(petList);
  }


  private TargetInterface createTarget(List<String> targetList) {

    if (targetList == null) {
      throw new IllegalArgumentException("Target list cannot be null");
    }

    this.target = new Target(Integer.parseInt(
            List.of(targetList.get(0).split(" ")).get(0)),
            String.join(" ", List.of(targetList
                    .get(0).split(" ")).subList(1, List.of(targetList
                    .toString().split(" ")).size())), spaceNames);
    return target;
  }


  private PetInterface createPet(List<String> petList) {
    if (petList == null) {
      throw new IllegalArgumentException("Pet list cannot be null");
    }
    this.pet = new Pet(petList.get(0), spaceNames);
    return pet;
  }

  @Override
  public void moveTarget() {
    target.moveTarget();
  }

  @Override
  public String getTargetDetails() {
    String targetDetails = target.toString();
    return targetDetails;
  }

  @Override
  public String getTargetSpaceName() {
    return target.getTargetSpaceName();
  }

  @Override
  public String getPetSpaceName() {
    return pet.getSpaceName();
  }

  @Override
  public int getTargetHealth() {
    return target.getTargetHealth();
  }



  @Override
  public void movePet(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    pet.setSpaceName(spaceName);
  }

  @Override
  public void hitTarget(int itemDamage) {
    if (itemDamage <= 0) {
      throw new IllegalArgumentException("Item damage cannot be 0");
    }
    target.hitTarget(itemDamage);
  }
}
