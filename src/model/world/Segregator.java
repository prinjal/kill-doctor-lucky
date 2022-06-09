package model.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Segregator class parses the input file and segregates it
 * into different parts such as world, spaces, and items.
 */
public class Segregator {

  private final List<String> allLines;
  private final int spaceCount;
  private final int itemCount;

  /**
   * Constructor method to set space count and item count.
   *
   * @throws IOException throws this exception if file path is not correct.
   */
  public Segregator() {
    try {
      allLines = new ArrayList<>();
      InputStream is = getClass().getResourceAsStream("mansion.txt");
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = "";
      while ((line = reader.readLine()) != null) {
        allLines.add(line);
      }

    } catch (IOException e) {
      throw new IllegalArgumentException("Enter correct path");
    }
    spaceCount = Integer.parseInt(allLines.get(3));
    itemCount = Integer.parseInt(allLines.get(spaceCount + 4));
  }

  /**
   * Constructor method to set space count and item count of a custom world
   * specification.
   * @param path of the custom world specification.
   */
  public Segregator(String path) {
    try {
      allLines = Files.readAllLines(Paths.get(path));
    } catch (IOException e) {
      throw new IllegalArgumentException("Enter correct path");
    }
    spaceCount = Integer.parseInt(allLines.get(3));
    itemCount = Integer.parseInt(allLines.get(spaceCount + 4));
  }

  /**
   * Gets world specification.
   *
   * @return List of world.
   */
  public List<String> getWorld() {
    List world = allLines.subList(0, 1);
    return world;
  }

  /**
   * Get space specification.
   *
   * @return List of string of spaces.
   */
  public List<String> getSpaces() {
    List spaces = allLines.subList(4, spaceCount + 4);
    return spaces;
  }

  /**
   * Get item specification.
   *
   * @return List of string of items.
   */
  public List<String> getItems() {
    List items = allLines.subList(spaceCount + 5, allLines.size());
    return items;
  }

  /**
   * Get target specification.
   *
   * @return List of string of target.
   */
  public List<String> getTarget() {
    List target = allLines.subList(1, 2);
    return target;
  }

  /**
   * Get pet specification.
   *
   * @return List of string of pet specification.
   */
  public List<String> getPet() {
    List pet = allLines.subList(2, 3);
    return pet;
  }

  /**
   * Gets item details and creates a map of items.
   *
   * @return Map of item details.
   */
  public Map<Integer, List<String>> getItemDetails() {
    List<String> items = getItems();
    List<String> item;
    int location;
    int damage;
    String name;
    Map<Integer, List<String>> itemMap = new HashMap<Integer, List<String>>();
    for (int itemCounter = 0; itemCounter < items.size(); itemCounter++) {
      item = List.of(items.get(itemCounter).toString().split(" "));
      location = Integer.parseInt(String.valueOf(item.get(0)));
      damage = Integer.parseInt(String.valueOf(item.get(1)));
      name = String.join(" ", item.subList(2, item.size()));
      List<String> lstItem = new ArrayList<String>();
      lstItem.add(String.valueOf(damage));
      lstItem.add(name);
      if (itemMap.get(location) == null) {
        itemMap.put(location, lstItem);
      } else {
        itemMap.get(location).add(String.valueOf(damage));
        itemMap.get(location).add(name);
      }

    }
    return itemMap;
  }

}
