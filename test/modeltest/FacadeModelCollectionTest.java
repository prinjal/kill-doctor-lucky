package modeltest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.world.FacadeModelCollection;
import model.world.Segregator;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * FacadeModelCollection tests the facade class
 * which setups up driver run in constructor
 * to create world, spaces and itmes.
 * This class tests world, spaces and item
 * objects which are returned by facade class.
 */
public class FacadeModelCollectionTest {
  private static FacadeModelCollection fc;

  @BeforeClass
  public static void setup() {
    fc = new FacadeModelCollection();
    fc.initGame(new Segregator());
  }


  @Test
  public void getWorldName() {
    String worldName = fc.getWorldName();
    assertEquals("Doctor Lucky's Mansion", worldName);
  }

  @Test
  public void getWorldSize() {
    List<Integer> worldSize = new ArrayList<>();
    worldSize.add(36);
    worldSize.add(30);
    assertEquals(worldSize, fc.getWorldSize());
  }

  @Test
  public void getSpacesNames() {
    List<String> spaceName = fc.getSpaceNames();
    assertEquals("Armory", spaceName.get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getInvalidSpaceDetails() {
    String spaceDetails = fc.getSpaceDetails("InvalidSpace");
  }

  @Test
  public void getValidSpaceDetails() {
    String spaceDetails = "Armory, Space Index: '0',[Revolver],Player(s): [],Pet Present?: false\n";
    assertEquals(spaceDetails, fc.getSpaceDetails("Armory"));
  }

}