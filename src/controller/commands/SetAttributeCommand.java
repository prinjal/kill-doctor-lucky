package controller.commands;

/**
 * Interface which extends command interface and adds the functionality of
 * setting up the attribute.
 */
public interface SetAttributeCommand extends CommandInterface {

  /**
   * Method to set the attribute of the class.
   *
   * @param attribute value of the attribute
   */
  void setAttribute(String attribute);
}
