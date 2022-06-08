package model.targetpet;

/**
 * PetInterface assigns and retrieves the space name
 * of the pet.
 */
public interface PetInterface {

  /**
   * Return current pet space name.
   *
   * @return string of current pet space name.
   */
  String getSpaceName();

  /**
   * Mutates pets space name when
   * a pet is moved from one space
   * to another.
   *
   * @param spaceName space name which
   *                  needs to be set.
   */
  void setSpaceName(String spaceName);
}
