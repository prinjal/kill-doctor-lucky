package modeltest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.space.Space;
import model.space.SpaceInterface;
import model.world.World;
import model.world.WorldInterface;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * SpaceTest tests the Space class and ISpace interface.
 * Tests comprise verification of setters, getters
 * invalid values of space coordinates which might have a negative
 * or an overlapping space.
 */
public class SpaceTest {
  private static WorldInterface world;
  private static SpaceInterface space;
  private static List<String> coordinates;


  /**
   * Tests Space class and its interface
   * which comprise validation checks of adding items
   * and getters/setters of space object.
   */
  @BeforeClass
  public static void setup() {
    List<String> inputWorld = new ArrayList<>();
    inputWorld.add("36 30 Doctor Lucky's Mansion");
    world = World.createWorld(inputWorld);
    List<String> spaces = new ArrayList<>();
    Collections.addAll(spaces,
            " 0 24 17 29 Green House");
    coordinates = new ArrayList<>();
    coordinates.add("5 21 22 23");
    space = new Space("Kitchen", 3, coordinates);
    List itemList = new ArrayList();
    Collections.addAll(itemList, "3", "Revolver");
    space.addItems(itemList);


  }


  @Test
  public void addItems() {
    List itemList = new ArrayList();
    Collections.addAll(itemList, "2", "Letter Opener");
    space.addItems(itemList);
    List<String> expected = new ArrayList<>();
    expected.add("Letter Opener");
    assertEquals(expected.get(0), space.getItemNames().get(1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void addItemsNegativeDamage() {
    List itemList = new ArrayList();
    Collections.addAll(itemList, "-3", "Revolver");
    space.addItems(itemList);
  }

  @Test
  public void getSpaceName() {
    assertEquals("Kitchen", space.getSpaceName());
  }

  @Test
  public void getSpaceIndex() {
    assertEquals(3, space.getSpaceIndex());
  }

  @Test
  public void getSpaceCoordinates() {

    List<String> expected = new ArrayList<>();
    for (String s : coordinates) {
      expected.add(s);
    }
    assertEquals(expected, space.getSpaceCoordinates());
  }

  @Test
  public void getSpaceItems() {
    List<String> expected = new ArrayList<>();
    expected.add("Revolver");
    assertEquals(expected.get(0), space.getItemNames().get(0));
  }


  @Test
  public void testToString() {
    String expected = "Kitchen, Space Index: '3',[Revolver- Letter Opener]"
            + ",Player(s): [],Pet Present?: false";
    assertEquals(expected, space.toString());
  }

  @Test
  public void testEquals() {
    Space spaceEquals = new Space("Kitchen", 2, List.of("5 12 14 20"));
    assertEquals(spaceEquals, space);
  }

}