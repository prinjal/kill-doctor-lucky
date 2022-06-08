package model.world;

import java.util.List;
import java.util.Map;
import model.space.SpaceInterface;

/**
 * World interface is responsible for creation of all the spaces
 * and setting their neighbors.
 */
public interface WorldInterface {

  /**
   * createSpace method creates space by checking
   * constraints internally with isSpaceAvailable method.
   *
   * @param spaceList Takes space list to create spaces
   * @param itemsMap  map of items which should be added in this space.
   */
  void createSpacesItems(List<String> spaceList, Map<Integer, List<String>> itemsMap);


  /**
   * getWorldName method returns name of the
   * world as a string.
   *
   * @return world name as a String
   */

  String getWorldName();

  /**
   * getWorldSize method returns worldSize as
   * an Integer array.
   *
   * @return world size as an Integer array
   */

  List<Integer> getWorldSize();


  /**
   * Returns list of all the spaces in the world.
   *
   * @return list of string of all the spaces in the world.
   */
  List<String> getSpaceNames();


  /**
   * getSpaceDetailsMap method returns map of all Space.
   *
   * @param spaceName takes space name as an input
   * @return space details of the space taken as parameter
   */
  String getSpaceDetails(String spaceName);

  /**
   * Retrieves map of space which has string of
   * all the spaces and its coordinates.
   *
   * @return map of space name and its coordinates.
   */
  Map<String, List<String>> getSpaceCoordinates();

  /**
   * Gets the space object of a particular space.
   * Note: This object is deliberately not cloned since
   * we would need to provide this method to PlayerWrapper
   * who has to mutate class data to add player object
   * and remove item.
   *
   * @param spaceName name of the space whose
   *                  object need to be retrieved.
   * @return SpaceInterface space object.
   */
  SpaceInterface getSpaceObject(String spaceName);


  /**
   * Fetch neighbors of the space entered in parameter.
   *
   * @param spaceName name of the space whose
   *                  neighbors need to be fetched.
   * @return list of string whose neighbors need to be fetched.
   */
  List<String> getSpaceNeighbors(String spaceName);


}
