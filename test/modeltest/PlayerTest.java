package modeltest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.item.Items;
import model.player.HumanStrategy;
import model.player.Player;
import model.player.PlayerInterface;
import model.player.PlayerType;
import org.junit.Before;
import org.junit.Test;

/**
 * PlayerTest class tests the edge cases of the
 * Player class method. It includes testing of setting
 * player to a space, setting item with a player
 * and getting information of a particular player.
 */
public class PlayerTest {
  private PlayerInterface player;

  /**
   * Setup class initializes a player object
   * which is used in several test cases.
   */
  @Before
  public void setup() {
    player = new Player("Test",
            PlayerType.HUMAN,
            "Armory",
            0, 1, new HumanStrategy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setInvalidPlayerName() {
    PlayerInterface player = new Player(null,
            PlayerType.HUMAN,
            "SpaceName",
            10, 5, new HumanStrategy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setInvalidSpaceName() {
    PlayerInterface player = new Player("Player",
            PlayerType.HUMAN,
            null,
            10, 5, new HumanStrategy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void setInvalidPlayerType() {
    PlayerInterface player = new Player("Test",
            null,
            "Armory",
            0, 1, new HumanStrategy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addNullItem() {
    player.addItem(null);
  }

  @Test(expected = IllegalStateException.class)
  public void addItemsMoreThanCapacity() {
    Items item1 = new Items(0, 10, "Pan");
    Items item2 = new Items(0, 5, "Revolver");
    //Program will allow to add one item since player's bag capacity is 1
    //as set while creating the player in setup method
    player.addItem(item1);

    //Program should throw IllegalStateException while adding another item
    player.addItem(item2);
  }

  @Test
  public void addItem() {
    Items items = new Items(0, 10, "Pan");
    player.addItem(items);
    List<String> expected = new ArrayList<>();
    expected.add("Pan");
    assertEquals(expected, player.getPlayerItemList());
  }

  @Test(expected = IllegalStateException.class)
  public void addItemAboveBagLimit() {
    PlayerInterface player = new Player("Test",
            PlayerType.HUMAN,
            "Armory",
            0, 1, new HumanStrategy());
    Items item1 = new Items(0, 10, "Pan");
    player.addItem(item1);
    Items item2 = new Items(0, 5, "Revolver");
    player.addItem(item2);
  }


  @Test(expected = IllegalArgumentException.class)
  public void setNullPlayerSpace() {
    player.setPlayerSpace(null, 0);
  }

  @Test
  public void setPlayerSpace() {
    player.setPlayerSpace("Armory", 0);
    String expected = "Armory";
    assertEquals(expected, player.getPlayerSpaceName());
  }


  @Test
  public void getPlayerName() {
    String expected = "Test";
    assertEquals(expected, player.getPlayerName());
  }

  @Test
  public void getPlayerSpaceName() {
    String expected = "Armory";
    assertEquals(expected, player.getPlayerSpaceName());
  }

  @Test
  public void getPlayerSpaceLocation() {
    int expected = 0;
    assertEquals(expected, player.getPlayerSpaceLocation());
  }

  @Test
  public void getPlayerItemList() {
    PlayerInterface player = new Player("Test",
            PlayerType.HUMAN,
            "Armory",
            0, 1, new HumanStrategy());
    Items item = new Items(0, 10, "Pan");
    player.addItem(item);
    List<String> expected = new ArrayList<>();
    expected.add("Pan");
    assertEquals(expected, player.getPlayerItemList());
  }

  @Test
  public void getPlayerType() {
    String expected = "Human";
    assertEquals(expected, player.getPlayerType().toString());
  }

  @Test
  public void testEquals() {
    PlayerInterface playerClone = new Player("Test",
            PlayerType.HUMAN,
            "Armory",
            0, 1, new HumanStrategy());
    assertEquals(playerClone, player);
  }


  @Test
  public void testToString() {
    String expected = "Name: Test\n"
            + "Type: Human\n"
            + "Space name: Armory, Space Index: 0\n"
            + "Item list: []\n"
            + "Bag capacity: 1";
    assertEquals(expected, player.toString());
  }

}