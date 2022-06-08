package model.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.space.Space;
import model.space.SpaceInterface;

/**
 * World class is responsible for setting world
 * data members as well as creation of all the spaces
 * and getting their neighbors.
 * It follows a singleton design pattern and creates world instance
 * through a public static method.
 */
public class World implements WorldInterface {

  private static WorldInterface world;
  private final String worldName;
  private final List<Integer> worldSize;
  private final Map<String, SpaceInterface> spaceObjectMap;
  private final Map<String, List<String>> spaceCoordinates;


  /**
   * World constructor sets data member of this class
   * with private accessibility to restrict user
   * from creating more than one object of this
   * class.
   * Note: world object has not been initialized in the
   * constructor since it will be created through a static factory
   * method and checked whether a new instance of node is being
   * attempted to create. In such case the factory method will
   * return the current world object to make it singleton.
   *
   * @param worldSize Initializes the world size.
   * @param worldName Initializes the world name.
   */

  private World(List<Integer> worldSize, String worldName) {

    this.spaceObjectMap = new HashMap<>();
    this.spaceCoordinates = new HashMap<>();
    this.worldName = worldName;
    this.worldSize = worldSize;
  }

  /**
   * createWorld method sets data member of this class
   * in a private constructor to restrict user
   * from creating more than one object of this
   * class.
   *
   * @param inputList Takes List as input to set world size and world name.
   * @return Returns World object by setting its values.
   */

  public static WorldInterface createWorld(List<String> inputList) {
    if (inputList == null) {
      throw new IllegalArgumentException("Input list cannot be null");
    }
    List<Integer> worldSize = new ArrayList<>();
    String worldName = "";
    List<String> worldList = List.of(inputList.get(0).split(" "));
    if (Integer.parseInt(worldList.get(0).trim()) < 0
            || Integer.parseInt(worldList.get(1).trim()) < 0) {
      throw new IllegalArgumentException("World size cannot be in negative");
    } else {
      worldSize.add(Integer.parseInt(worldList.get(0).trim()));
      worldSize.add(Integer.parseInt(worldList.get(1).trim()));
      for (int x = 2; x < worldList.size(); x++) {
        worldName = String.format(worldName + (worldList.get(x) + " "));
      }
      world = new World(worldSize, worldName.trim());
      return world;
    }
  }


  /**
   * isSpaceAvailable method checks all the constraints
   * in order to create a valid space.
   *
   * @param inputSpaceList Takes list of spaces data as input.
   * @return Returns boolean weather space can be created or not.
   */

  private boolean isSpaceAvailable(List<String> inputSpaceList) {
    if (inputSpaceList == null) {
      throw new IllegalArgumentException("InputSpaceList cannot be null");
    }
    List<String> worldList = new ArrayList<>();
    String str = inputSpaceList.get(0);
    String[] strng = str.split(" ");
    for (String s : strng) {
      if (!("").equals(s)) {
        worldList.add(s);
      }
    }
    List<Integer> spaceListRows = new ArrayList<>();
    List<Integer> spaceListCols = new ArrayList<>();
    for (int count = 0; count < 4; count++) {
      if ((count % 2) == 0) {
        spaceListRows.add(Integer.parseInt(String.valueOf(worldList.get(count))));
      } else {
        spaceListCols.add(Integer.parseInt(String.valueOf(worldList.get(count))));
      }
    }

    Collections.sort(spaceListRows);
    Collections.sort(spaceListCols);
    int rowLow = spaceListRows.get(0);
    int rowHigh = spaceListRows.get(1);
    int colLow = spaceListCols.get(0);
    int colHigh = spaceListCols.get(1);


    if ((rowLow > worldSize.get(0)) || (rowHigh > worldSize.get(0))
            || (colLow > worldSize.get(1)) || (colHigh > worldSize.get(1))) {
      return false;
    } else if (rowLow < 0 || rowHigh < 0 || colLow < 0 || colHigh < 0) {
      return false;
    } else if (rowLow == rowHigh || colLow == colHigh) {

      return false;
    } else if (!(spaceCoordinates.isEmpty())) {


      for (Map.Entry<String, List<String>> entry : spaceCoordinates.entrySet()) {
        List<String> value;
        value = entry.getValue();
        List row = new ArrayList();
        row.add(value.get(0));
        row.add(value.get(2));
        List col = new ArrayList();
        col.add(value.get(1));
        col.add(value.get(3));
        int mapRowLow = Integer.parseInt(String.valueOf(row.get(0)));
        int mapRowHigh = Integer.parseInt(String.valueOf(row.get(1)));
        int mapColLow = Integer.parseInt(String.valueOf(col.get(0)));
        int mapColHigh = Integer.parseInt(String.valueOf(col.get(1)));
        if ((((colLow >= mapColLow) && (colLow <= mapColHigh))
                || ((colHigh >= mapColLow) && (colHigh <= mapColHigh)))
                && (((rowLow >= mapRowLow) && (rowLow <= mapRowHigh))
                || ((rowHigh >= mapRowLow) && (rowHigh <= mapRowHigh)))) {
          return false;
        } else {
          continue;
        }
      }
      return true;
    } else {
      return true;
    }
  }


  @Override
  public void createSpacesItems(List<String> spaceList, Map<Integer, List<String>> itemsMap) {
    if (spaceList == null || itemsMap == null) {
      throw new IllegalArgumentException("Space list and itemsMap cannot be null");
    }
    List<String> inputSpaceListUnfiltered;
    for (int spaceCounter = 0; spaceCounter < spaceList.size(); spaceCounter++) {
      inputSpaceListUnfiltered = spaceList.subList(spaceCounter, spaceCounter + 1);
      if (isSpaceAvailable(inputSpaceListUnfiltered)) {
        List<String> inputSpaceList = new ArrayList<>();
        String str = inputSpaceListUnfiltered.get(0);
        String[] strng = str.split(" ");
        for (int x = 0; x < strng.length; x++) {
          if (strng[x].equals("")) {
            continue;
          } else {
            inputSpaceList.add(strng[x]);
          }
        }
        List<String> upperLeftPoint = inputSpaceList.subList(0, 2);
        List<String> lowerRightPoint = inputSpaceList.subList(2, 4);
        if ((Integer.parseInt(inputSpaceList.subList(0, 2).get(0))
                > Integer.parseInt(inputSpaceList.subList(2, 4).get(0)))
                || (Integer.parseInt(inputSpaceList.subList(0, 2).get(1))
                > Integer.parseInt(inputSpaceList.subList(2, 4).get(1)))) {
          throw new IllegalArgumentException("Values of upper left corner"
                  + "cannot be greater than values of lower right corner");
        }
        String spaceName = String.join(" ", inputSpaceList.subList(4, inputSpaceList.size()));
        List<String> spaceDetails = new ArrayList<>();
        for (String s : upperLeftPoint) {
          spaceDetails.add(s);
        }
        for (String s : lowerRightPoint) {
          spaceDetails.add(s);
        }

        SpaceInterface space = new Space(spaceName, spaceObjectMap.size(), spaceDetails);
        List<String> itemsList;

        itemsList = itemsMap.get(spaceObjectMap.size());
        if (itemsList != null) {
          space.addItems(itemsList);
        }

        spaceObjectMap.put(spaceName, space);
        spaceCoordinates.put(spaceName, spaceDetails);

      } else {
        throw new IllegalArgumentException("Cannot create space");
      }
    }
    createNeighbors();
  }

  private void createNeighbors() {

    for (Map.Entry<String, SpaceInterface> entry : spaceObjectMap.entrySet()) {
      List<String> coordinates = entry.getValue().getSpaceCoordinates();
      List<String> neighbors = new ArrayList<>();
      int inputUpperLeftRow = Integer.parseInt(String.valueOf(coordinates.get(0)));
      int inputUpperLeftCol = Integer.parseInt(String.valueOf(coordinates.get(1)));
      int inputLowerRightRow = Integer.parseInt(String.valueOf(coordinates.get(2)));
      int inputLowerRightCol = Integer.parseInt(String.valueOf(coordinates.get(3)));

      if (!(spaceCoordinates.isEmpty())) {
        for (Map.Entry<String, List<String>> entryDetails : spaceCoordinates.entrySet()) {
          int upperLeftRow = Integer.parseInt(entryDetails.getValue().get(0));
          int upperLeftCol = Integer.parseInt(entryDetails.getValue().get(1));
          int lowerRightRow = Integer.parseInt(entryDetails.getValue().get(2));
          int lowerRightCol = Integer.parseInt(entryDetails.getValue().get(3));

          if ((inputLowerRightRow == upperLeftRow - 1)
                  || (inputUpperLeftRow == lowerRightRow + 1)) {
            if ((inputUpperLeftCol >= upperLeftCol) && (inputUpperLeftCol <= lowerRightCol)
                    || ((inputLowerRightCol >= upperLeftCol)
                    && (inputLowerRightCol <= lowerRightCol))) {
              neighbors.add(entryDetails.getKey());

            } else if ((upperLeftCol >= inputUpperLeftCol) && (upperLeftCol <= inputLowerRightCol)
                    && (lowerRightCol >= inputUpperLeftCol)
                    && (lowerRightCol <= inputLowerRightCol)) {
              neighbors.add(entryDetails.getKey());
            }
          } else if ((inputLowerRightCol == upperLeftCol - 1)
                  || (inputUpperLeftCol == lowerRightCol + 1)) {
            if ((inputLowerRightRow >= upperLeftRow)
                    && (inputLowerRightRow <= lowerRightRow)
                    || ((inputUpperLeftRow >= upperLeftRow)
                    && (inputUpperLeftRow <= lowerRightRow))) {
              neighbors.add(entryDetails.getKey());
            } else if ((upperLeftRow >= inputUpperLeftRow) && (upperLeftRow <= inputLowerRightRow)
                    && (lowerRightRow >= inputUpperLeftRow)
                    && (lowerRightRow <= inputLowerRightRow)) {
              neighbors.add(entryDetails.getKey());
            }
          }

        }
        entry.getValue().addNeighbors(neighbors);
      }
    }
  }

  @Override
  public String getWorldName() {
    return worldName;
  }


  @Override
  public List<Integer> getWorldSize() {
    List<Integer> worldSize = new ArrayList<>();
    for (Integer size : this.worldSize) {
      worldSize.add(size);
    }
    return worldSize;
  }


  @Override
  public List<String> getSpaceNames() {
    List<String> spaceDetailsName = new ArrayList<>();
    Map<Integer, String> spaceDetailsMap = new HashMap<>();
    spaceObjectMap.entrySet().forEach(e -> spaceDetailsMap
            .put(e.getValue().getSpaceIndex(), e.getKey()));
    for (Map.Entry<Integer, String> entry : spaceDetailsMap.entrySet()) {
      spaceDetailsName.add(entry.getValue());
    }
    return spaceDetailsName;
  }

  @Override
  public Map<String, List<String>> getSpaceCoordinates() {
    Map<String, List<String>> spaceDetailsMapClone = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : spaceCoordinates.entrySet()) {
      List<String> spaceCoordinateClone = new ArrayList<>();
      for (String s : entry.getValue()) {
        spaceCoordinateClone.add(s);
      }
      spaceDetailsMapClone.put(entry.getKey(), spaceCoordinateClone);
    }
    return spaceDetailsMapClone;
  }

  @Override
  public SpaceInterface getSpaceObject(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    } else if (spaceObjectMap.get(spaceName) == null) {
      throw new IllegalArgumentException("Enter a valid space name");
    } else {
      return spaceObjectMap.get(spaceName);
    }
  }

  @Override
  public List<String> getSpaceNeighbors(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    List<String> neighbors = new ArrayList<>();
    if (spaceObjectMap.containsKey(spaceName)) {
      spaceObjectMap.get(spaceName).getNeighbors().forEach(s -> neighbors.add(s));
      return neighbors;
    } else {
      throw new IllegalArgumentException("Space not found");
    }
  }

  @Override
  public String getSpaceDetails(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    String spaceDetails;
    if (spaceObjectMap.get(spaceName) == null) {
      throw new IllegalArgumentException("Space not available");
    } else {
      spaceDetails = spaceObjectMap.get(spaceName).toString();
    }
    if (spaceDetails == null) {
      throw new IllegalArgumentException("Space not available");
    }
    return new StringBuilder(spaceDetails)
            .append("\n").toString();
  }


  @Override
  public String toString() {
    return String.format("World name: '%s' World size: %s",
            worldName, worldSize.toString());
  }

}
