package modeltest;

import static org.junit.Assert.assertEquals;

import model.item.Items;
import model.item.ItemsInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * ItemsTest tests the Item class's and IItem interface's
 * setters and getters.
 * It also test invalid input while creating items.
 */
public class ItemsTest {
  private ItemsInterface items;

  @Before
  public void setup() {
    items = new Items(1, 10, "Pan");
  }

  @Test(expected = IllegalArgumentException.class)
  public void addInvalidItems() {
    ItemsInterface items = new Items(-12, -10, "Revolver");
  }


  @Test
  public void getItemName() {
    String expected = "Pan";
    assertEquals(expected, items.getItemName());
  }

  @Test
  public void getItemDamage() {
    int expected = 10;
    assertEquals(expected, items.getItemDamage());
  }

  @Test
  public void getItemLocation() {
    int expected = 1;
    assertEquals(expected, items.getItemLocation());
  }

  @Test
  public void testToString() {
    assertEquals("Item name: 'Pan' Damage: '10'", items.toString());
  }
}