package modeltest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.targetpet.Target;
import model.targetpet.TargetInterface;
import model.targetpet.TargetPetWrapper;
import model.targetpet.TargetPetWrapperInterface;
import model.world.World;
import model.world.WorldInterface;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Target class is tested in this test class.
 * It checks singleton validation of the target class.
 * It comprises validation of creating,
 * moving and getting the correct values of the target.
 */
public class TargetTest {
  private static WorldInterface world;
  private static TargetPetWrapperInterface targetPetWrapper;
  private static TargetInterface target;

  /**
   * Sets up singleton class World and Target.
   */
  @BeforeClass
  public static void setup() {
    List<String> inputWorld = new ArrayList<>();
    inputWorld.add("36 30 Doctor Lucky's Mansion");
    world = World.createWorld(inputWorld);
    List<String> inputSpace = new ArrayList<>();
    inputSpace.add("26 13 27 18 Foyer");
    inputSpace.add("12 10 15 17 Armory");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3", "Revolver"));
    world.createSpacesItems(inputSpace, itemsMap);
    targetPetWrapper = new TargetPetWrapper(List.of("50 Doctor Lucky"),
            List.of("Fortune the Cat"), world.getSpaceNames());
    target = new Target(50, "Doctor Lucky", world.getSpaceNames());
  }


  @Test
  public void moveTarget() {
    assertEquals(0, target.getTargetLocation());
    target.moveTarget();
    //assertEquals(1, target.getTargetLocation());
  }

  @Test
  public void setTarget() {
    int location = target.getTargetLocation();
    int health = target.getTargetHealth();
    String name = target.getTargetName();
    assertEquals(health, target.getTargetHealth());
    assertEquals(location, target.getTargetLocation());
    assertEquals(name, target.getTargetName());
  }


  @Test
  public void getTargetHealth() {
    int expected = 50;
    assertEquals(expected, target.getTargetHealth());
  }


  @Test
  public void getTargetName() {
    String expected = "Doctor Lucky";
    assertEquals(expected, target.getTargetName());
  }

  @Test
  public void testToString() {
    String expected = String.format("Target name: 'Doctor Lucky' "
            + "Health: '50' Space Location: Armory");
    assertEquals(expected, target.toString());
  }


}