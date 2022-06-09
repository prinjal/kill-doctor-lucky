package model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.player.PlayerWrapper;
import model.player.PlayerWrapperInterface;
import model.targetpet.TargetPetWrapperInterface;

/**
 * FacadeModelCollection is the centralized point of access for the
 * client/Driver class with which it can access world, spaces
 * inside world, items inside spaces and target.
 */
public class FacadeModelCollection {
  private Segregator seg;
  private WorldInterface world;

  /**
   * Constructor method to set all the lists
   * fetched from Segregator class.
   */
  public FacadeModelCollection() {
    this.seg = null;
    this.world = null;
  }

  /**
   * Initializes the game for a custom specification of world
   * format.
   * @param seg segregator object to create world with custom
   *            specification.
   */
  public void initGame(Segregator seg) {
    this.seg = seg;
    this.world = World.createWorld(seg.getWorld());
    world.createSpacesItems(seg.getSpaces(), seg.getItemDetails());
  }

  /**
   * Gets world name.
   *
   * @return world object.
   */
  public String getWorldName() {
    return world.getWorldName();
  }

  /**
   * Get the world size in terms of rows
   * and columns.
   *
   * @return List of two integer number of rows and number of columns
   */
  public List<Integer> getWorldSize() {
    List<Integer> worldSize;
    worldSize = world.getWorldSize();
    return worldSize;
  }

  /**
   * Get names of all the spaces in the world.
   *
   * @return List of string of all the spaces.
   */
  public List<String> getSpaceNames() {
    return world.getSpaceNames();
  }

  /**
   * Get spacedetail of a particular space
   * entered in parameter.
   *
   * @param spaceName name of the space for which details need to be fetched.
   * @return String comprising space details.
   */
  public String getSpaceDetails(String spaceName) {
    String spaceDetails = world.getSpaceDetails(spaceName);
    if (spaceDetails == null) {
      throw new IllegalArgumentException("Space does not exist");
    }
    return spaceDetails;
  }


  /**
   * Retrieves the map of space which includes
   * name and coordinate of all the spaces.
   *
   * @return map of space and its coordinates.
   */
  public Map<String, List<String>> getSpaceCoordinates() {
    Map<String, List<String>> spaceDetailsMapClone = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : world.getSpaceCoordinates().entrySet()) {
      List<String> spaceCoordinateClone = new ArrayList<>();
      for (String s : entry.getValue()) {
        spaceCoordinateClone.add(s);
      }
      spaceDetailsMapClone.put(entry.getKey(), spaceCoordinateClone);
    }
    return spaceDetailsMapClone;
  }

  /**
   * Draws a graphical representation
   * of the world and saves it at the
   * current location as a PNG file.
   */
  public void drawWorld() {
    RenderWorld render = new RenderWorld();
    render.createWorld(this);
  }

  /**
   * Creates instance of a PlayerWrapper class
   * through which all the players will be handled
   * and actions like move, pick item, look around
   * will be taken according to inputs.
   *
   * @return PlayerWrapper instance with which actions
   *         can be executed upon players.
   */
  protected PlayerWrapperInterface getPlayerWrapper(TargetPetWrapperInterface
                                                            targetPetWrapper) {
    if (targetPetWrapper == null) {
      throw new IllegalArgumentException("TargetPetWrapper cannot be null");
    }
    PlayerWrapperInterface playerWrapper;
    playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    return playerWrapper;
  }


}
