package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.space.SpaceInterface;
import model.world.World;
import model.world.WorldInterface;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * WorldTest tests World class and IWorld interface.
 * It checks singleton validation of class.
 * It comprises invalid operations of adding space
 * and also checks getters and setters.
 */
public class WorldTest {
  private static WorldInterface world;

  /**
   * Sets up World singleton class
   * and creates couple of spaces
   * which can be checked in further test cases.
   */
  @BeforeClass
  public static void setup() {
    List<String> inputList = new ArrayList<>();
    inputList.add("36 30 Doctor Lucky's Mansion");
    world = World.createWorld(inputList);
    List<String> spaceInput = new ArrayList<>();
    spaceInput.add("12 11 21 20 Dining Hall");
    spaceInput.add("22 19 23 26 Armory");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3", "Revolver"));
    itemsMap.put(1, List.of("4", "Chain Saw"));
    world.createSpacesItems(spaceInput, itemsMap);
  }


  //Invalid Space Creation

  @Test(expected = IllegalArgumentException.class)
  public void createSpaceOverlap() {
    //Space OverLap
    List<String> inputList;
    //Overlapping Space
    inputList = new ArrayList<>();
    inputList.add("22 19 26 18 Billiard");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3 Revolver"));
    world.createSpacesItems(inputList, itemsMap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createSpaceOutOfBounds() {
    List<String> inputList;
    //Space out of bound (World Space)
    inputList = new ArrayList<>();
    inputList.add("38 15 12 20 Armory");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3 Revolver"));
    world.createSpacesItems(inputList, itemsMap);

  }

  @Test(expected = IllegalArgumentException.class)
  public void createNegativeSpace() {
    List<String> inputList;
    //Space Negative
    inputList = new ArrayList<>();
    inputList.add("-12 15 12 20 Armory");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3 Revolver"));
    world.createSpacesItems(inputList, itemsMap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createLinePointSpace() {
    List<String> inputList;

    //Space cannot be line or point
    inputList = new ArrayList<>();
    inputList.add("12 15 12 20 Armory");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3 Revolver"));
    world.createSpacesItems(inputList, itemsMap);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidSpace() {
    List<String> inputList;

    //Upper left corner cannot be greater than lower right corner
    inputList = new ArrayList<>();
    inputList.add("12 15 11 20 Armory");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3 Revolver"));
    world.createSpacesItems(inputList, itemsMap);

    inputList = new ArrayList<>();
    inputList.add("12 15 11 10 Armory");
    world.createSpacesItems(inputList, itemsMap);

    inputList = new ArrayList<>();
    inputList.add("22 29 15 20 Armory");
    world.createSpacesItems(inputList, itemsMap);
  }

  @Test
  public void createNeighbors() {
    String expected = "Armory, Space Index: '1',[Chain Saw],Player(s): [],Pet Present?: false\n";
    assertEquals(expected, world.getSpaceDetails("Armory"));
  }

  @Test
  public void getWorldDetails() {
    List<Integer> worldSize = new ArrayList<>();
    worldSize.add(36);
    worldSize.add(30);
    //World Size
    assertEquals(world.getWorldSize(), worldSize);

    //World Name
    assertEquals("Doctor Lucky's Mansion", world.getWorldName());

  }

  @Test
  public void getSpaceNames() {
    List<String> actual = new ArrayList<>();
    actual = world.getSpaceNames();
    assertTrue(actual.contains("Armory"));
  }

  @Test
  public void getSpaceDetailsMap() {
    Map<String, List<String>> spaceMap = world.getSpaceCoordinates();
    assertEquals(true, world.getSpaceCoordinates().keySet().contains("Armory"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSpaceDetailsInvalid() {
    String actual = world.getSpaceDetails("Invalid Space");
    world.getSpaceDetails(actual);
  }

  @Test
  public void getSpaceDetails() {
    String actual = world.getSpaceDetails("Armory");
    String expected = String.format("Armory, Space Index: '1',"
            + "[Chain Saw],Player(s): [],Pet Present?: false\n");
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInvalidSpaceObject() {
    SpaceInterface space1 = world.getSpaceObject("Invalid Space");
    SpaceInterface space2 = world.getSpaceObject("Test Space");
  }

  @Test
  public void getSpaceObject() {
    SpaceInterface space = world.getSpaceObject("Armory");
    String actual = space.toString();
    String expected = "Armory, Space Index: '1',[Chain Saw],Player(s): [],Pet Present?: false";
    assertEquals(expected, actual);
  }




}