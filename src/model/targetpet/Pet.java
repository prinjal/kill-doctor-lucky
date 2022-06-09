package model.targetpet;

import java.util.List;

/**
 * Target has a pet which enters with the target
 * and moves around the world. Players can move the pet
 * and the pet will make its current location invisible
 * from the other spaces. This class holds pet attributes
 * and has setters and getters for the space where pet is
 * currently located.
 */
public class Pet implements PetInterface {
  private final String petName;
  private int spaceIndex;
  private String spaceName;
  private final List<String> spaceNames;

  /**
   * Initialize the class attributes by setting pet
   * name, space of the pet, space index and all the
   * space names of the world.
   *
   * @param petName    name of the pet.
   * @param spaceNames name of all the spaces of the world.
   */
  public Pet(String petName, List<String> spaceNames) {
    if (petName == null || spaceNames == null) {
      throw new IllegalArgumentException("Pet name or space name cannot be null");
    }
    this.petName = petName;
    this.spaceNames = spaceNames;
    this.spaceIndex = 0;
    this.spaceName = spaceNames.get(0);
  }

  @Override
  public String getSpaceName() {
    return spaceName;
  }

  @Override
  public void setSpaceName(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    this.spaceName = spaceName;
  }

  @Override
  public String toString() {
    return new StringBuilder()
            .append("Pet Name: ")
            .append(petName)
            .append(" ")
            .append("Pet Space: ")
            .append(spaceName)
            .toString();
  }
}
