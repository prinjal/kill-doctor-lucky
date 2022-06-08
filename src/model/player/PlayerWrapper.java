package model.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.space.SpaceInterface;
import model.targetpet.TargetPetWrapperInterface;
import model.world.WorldInterface;

/**
 * PlayerWrapper class holds information and
 * objects of all the Player class. It handles the player
 * object by considering the model requirements
 * by taking turns like move, pick item, look around,
 * and displaying information of space and specific player.
 */
public class PlayerWrapper implements PlayerWrapperInterface {

  private final WorldInterface world;
  private final List<PlayerInterface> playerObjectList;
  private final TargetPetWrapperInterface targetPetWrapper;
  private final Map<String, Boolean> spaceVisitMap;
  private int totalTurns;
  private PlayerInterface currentPlayer;
  private int currentPlayerCounter;
  private List<String> spaceVisitList;
  private int spaceVisitCounter;
  private String winner;
  private int totalPlayers;

  /**
   * PlayerWrapper constructor validates and sets
   * world object. It also initializes the player object
   * list and other class variables.
   * <p>
   * Note: currentPlayer is not initialized in the constructor
   * since it will be assigned an object once the first player is created
   * from the createPlayer method.
   *
   * @param world            object of the World class will be used
   *                         to get a single space object and mutate its
   *                         player and item information when needed.
   * @param targetPetWrapper TargetPetWrapper object to make
   *                         pets and targets functionalities work
   *                         alongside player's turn.
   */
  public PlayerWrapper(WorldInterface world, TargetPetWrapperInterface targetPetWrapper) {
    if (world == null || targetPetWrapper == null) {
      throw new IllegalArgumentException("World and TargetWrapper object cannot be null");
    }
    this.world = world;
    this.playerObjectList = new ArrayList<>();
    this.currentPlayerCounter = 0;
    this.totalTurns = 0;
    this.totalPlayers = 0;
    this.targetPetWrapper = targetPetWrapper;
    this.spaceVisitMap = new HashMap<>();
    this.spaceVisitList = new ArrayList<>();
    this.spaceVisitCounter = 1;
    this.winner = "";
    world.getSpaceObject(world.getSpaceNames().get(0)).setPet();
    world.getSpaceObject(world.getSpaceNames().get(0)).setTarget();
    spaceVisitList = dfs(targetPetWrapper.getPetSpaceName());
  }

  @Override
  public void createPlayer(String playerName,
                           PlayerType playerType, String spaceName, int itemCapacity,
                           StrategyInterface strategy) {
    if (totalPlayers > 10) {
      throw new IllegalArgumentException("Cannot add more than 10 players!!");
    }
    PlayerInterface player;
    SpaceInterface space;
    space = world.getSpaceObject(spaceName);

    if (space == null || spaceName == null) {
      throw new IllegalArgumentException("Space name is not valid");
    } else if (playerName == null || playerType == null) {
      throw new IllegalArgumentException("Player information not passed properly");
    } else if (getPlayersNameList().contains(playerName)) {
      throw new IllegalArgumentException("Enter unique player name");
    } else if (itemCapacity < 0) {
      throw new IllegalArgumentException("Item capacity cannot be negative");
    } else {
      player = new Player(playerName, playerType, spaceName,
              space.getSpaceIndex(), itemCapacity, strategy);
      space.addPlayer(player);
      strategy.setPlayerWrapper(this);
      playerObjectList.add(player);
      currentPlayer = playerObjectList.get(currentPlayerCounter);
      totalPlayers++;
    }
  }

  @Override
  public void movePlayer(String spaceName) {
    SpaceInterface newSpace;
    SpaceInterface prevSpace;
    newSpace = world.getSpaceObject(spaceName);
    prevSpace = world.getSpaceObject(currentPlayer.getPlayerSpaceName());
    if (newSpace == null || spaceName == null) {
      throw new IllegalArgumentException("Enter a valid neighbor space");
    } else if (spaceName.equals(currentPlayer.getPlayerSpaceName())) {
      throw new IllegalArgumentException("Enter a valid neighbor space");
    } else if (!(world.getSpaceObject(currentPlayer.getPlayerSpaceName())
            .getNeighbors().contains(spaceName))) {
      throw new IllegalArgumentException("Enter a valid neighbor space");
    }
    prevSpace.removePlayer(currentPlayer.getPlayerName());
    newSpace.addPlayer(currentPlayer);
    currentPlayer.setPlayerSpace(spaceName, newSpace.getSpaceIndex());
    getTurn();
    movePet();
  }


  @Override
  public void pickItem(String itemName) {
    SpaceInterface space;
    boolean itemPresent = false;
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null. Enter a valid Item name");
    }
    space = world.getSpaceObject(currentPlayer.getPlayerSpaceName());
    for (String item : space.getItemNames()) {
      if (itemName.equals(item)) {
        itemPresent = true;
        currentPlayer.addItem(space.getItemObject(item));
        space.removeItem(item);
      }
    }
    if (!itemPresent) {
      throw new IllegalArgumentException("Item not present in this room");
    } else {
      getTurn();
      movePet();
    }
  }

  @Override
  public String lookAround() {
    List<String> currentSpaceNeighbors;
    StringBuilder lookAround = new StringBuilder("");
    currentSpaceNeighbors = world
            .getSpaceObject(currentPlayer.getPlayerSpaceName())
            .getNeighbors().stream()
            .filter(a -> !world.getSpaceObject(a).isPet()).collect(Collectors.toList());
    lookAround.append(currentPlayer.getPlayerName())
            .append("'s current location:\n")
            .append(world.getSpaceObject(currentPlayer.getPlayerSpaceName()).toString())
            .append("\n")
            .append("Neighbors: ")
            .append(world.getSpaceObject(currentPlayer.getPlayerSpaceName())
                    .getNeighbors().stream().filter(a -> !world.getSpaceObject(a).isPet())
                    .collect(Collectors.toList()))
            .append("\n\n")
            .append(String.format("Neighboring spaces from %s's current location:\n",
                    currentPlayer.getPlayerName()));
    for (String neighborSpace : currentSpaceNeighbors) {
      lookAround.append(world.getSpaceObject(neighborSpace).toString())
              .append("\n");
    }
    getTurn();
    movePet();
    return lookAround.toString();
  }

  @Override
  public boolean isGameOver(int turns) {
    if (turns <= 0) {
      throw new IllegalArgumentException("Turns cannot be less than equal to 0");
    }
    if (totalTurns >= turns) {
      return true;
    } else if (targetPetWrapper.getTargetHealth() <= 0
            && (winner == null || winner.isEmpty())) {
      winner = currentPlayer.getPlayerName();
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void movePet(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    if (world.getSpaceObject(spaceName) != null) {
      world.getSpaceObject(targetPetWrapper.getPetSpaceName()).setPet();
      targetPetWrapper.movePet(spaceName);
      world.getSpaceObject(spaceName).setPet();
      spaceVisitList = dfs(spaceName);
      getTurn();
    } else {
      throw new IllegalArgumentException("Space not found");
    }
  }

  private void movePet() {
    if (spaceVisitCounter >= spaceVisitList.size()) {
      spaceVisitCounter = 0;
    }
    world.getSpaceObject(targetPetWrapper.getPetSpaceName()).setPet();
    targetPetWrapper.movePet(spaceVisitList.get(spaceVisitCounter));
    world.getSpaceObject(spaceVisitList.get(spaceVisitCounter)).setPet();
    spaceVisitCounter += 1;
  }

  @Override
  public String hitTarget(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    if (!currentPlayer.getPlayerSpaceName()
            .equalsIgnoreCase(targetPetWrapper.getTargetSpaceName())) {
      getTurn();
      throw new IllegalStateException("Target not present in this room");
    } else if (currentPlayer.getPlayerItemList().contains(itemName)) {
      if (!checkPlayerVisibility()) {
        currentPlayer.removeItem(itemName);
        movePet();
        getTurn();
        throw new IllegalStateException("Hit move dodged by another player. Item dropped! ");
      } else {
        int itemDamage = currentPlayer
                .getItem(itemName)
                .getItemDamage();
        targetPetWrapper.hitTarget(itemDamage);
        currentPlayer.removeItem(itemName);
        movePet();
        getTurn();
        return String.format("Hit move successful! ");
      }
    } else if ("poke".equalsIgnoreCase(itemName)) {
      if (!checkPlayerVisibility()) {
        movePet();
        getTurn();
        throw new IllegalStateException("Poking in eye dodged by another player! ");
      } else {
        targetPetWrapper.hitTarget(1);
        movePet();
        getTurn();
        return String.format("Poked in eye! ");
      }
    } else {
      throw new IllegalArgumentException("Item not present in this space");
    }
  }

  @Override
  public Boolean checkComputerTargetHit() {
    if (currentPlayer.getPlayerType() == PlayerType.COMPUTER) {
      if (currentPlayer.getPlayerSpaceName().equals(targetPetWrapper.getTargetSpaceName())
              && (checkPlayerVisibility())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getCurrentPlayerName() {
    String currentPlayerName = currentPlayer.getPlayerName();
    return currentPlayerName;
  }

  @Override
  public PlayerType getCurrentPlayerType() {
    return currentPlayer.getPlayerType();
  }

  @Override
  public List<String> getCurrentPlayerNeighbors() {
    List<String> currentPlayerNeighbors = new ArrayList<>();
    if (world.getSpaceObject(currentPlayer.getPlayerSpaceName())
            .getNeighbors() != null) {
      for (String neighbors : world.getSpaceObject(currentPlayer
              .getPlayerSpaceName()).getNeighbors()) {
        if (!world.getSpaceObject(neighbors).isPet()) {
          currentPlayerNeighbors.add(neighbors);
        }
      }
    } else {
      throw new IllegalStateException(String.format("There are no neighbors for space %s",
              currentPlayer.getPlayerSpaceName()));
    }
    return currentPlayerNeighbors;
  }

  @Override
  public String getPlayerSpace(String playerName) {
    if (playerName == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }

    String playerSpaceName = "";
    for (PlayerInterface p : playerObjectList) {
      if (p.getPlayerName().equals(playerName)) {

        playerSpaceName = p.getPlayerSpaceName();
      }
    }
    return playerSpaceName;
  }

  @Override
  public int getCurrentPlayerItemCount() {
    return Math.toIntExact(currentPlayer.getPlayerItemList().stream().count());
  }

  @Override
  public String getPlayerDetails(String playerName) {
    if (playerName == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }
    String currentPlayerDetails = null;
    boolean isPlayerVisible = false;
    PlayerInterface p = null;

    if (!getPlayersNameList().contains(playerName)) {
      throw new IllegalArgumentException("Invalid Player name");
    }

    for (PlayerInterface x : playerObjectList) {
      if (x.getPlayerName().equals(playerName)) {
        p = x;
      }
    }


    for (String neighbors : getCurrentPlayerNeighbors()) {
      if ((world.getSpaceObject(neighbors).getPlayers().contains(playerName)
              && !world.getSpaceObject(neighbors).isPet())
              || (playerName == getCurrentPlayerName())) {
        isPlayerVisible = true;
      }
    }

    if (currentPlayer.getPlayerSpaceName().equals(p.getPlayerSpaceName())) {
      isPlayerVisible = true;
    }

    if (isPlayerVisible == false && getPlayersNameList().contains(playerName)) {
      throw new IllegalArgumentException("Player out of visibility range");
    }

    for (PlayerInterface player : playerObjectList) {
      if (playerName.equals(player.getPlayerName())) {
        currentPlayerDetails = new StringBuilder()
                .append(player)
                .toString();
      }
    }
    if (currentPlayerDetails == null) {
      throw new IllegalArgumentException("Player not found");
    }

    return currentPlayerDetails;
  }


  @Override
  public List<String> getPickableItemNames() {
    List<String> itemDetails = new ArrayList<>();
    for (String item : world.getSpaceObject(currentPlayer.getPlayerSpaceName()).getItemNames()) {
      itemDetails.add(item);
    }
    return itemDetails;
  }

  @Override
  public PlayerInterface getPlayerObject(String playerName) {
    if (playerName == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }
    for (PlayerInterface player : playerObjectList) {
      if (playerName.equals(player.getPlayerName())) {
        return player;
      }
    }
    throw new IllegalArgumentException("Player not found ");
  }

  @Override
  public String getWinner() {
    return winner;
  }

  /**
   * Checks if current player is visible by any other
   * player or not.
   *
   * @return true if player is not visible and false otherwise.
   */

  private Boolean checkPlayerVisibility() {
    if (playerObjectList
            .stream()
            .filter(a -> world.getSpaceNeighbors(currentPlayer
                    .getPlayerSpaceName()).contains(a.getPlayerSpaceName()))
            .collect(Collectors.toList()).stream().count() > 0
            || playerObjectList.stream().filter(a -> a.getPlayerSpaceName()
                    .equals(currentPlayer.getPlayerSpaceName()))
            .collect(Collectors.toList()).stream().count() > 1) {
      return false;
    }
    return true;
  }

  /**
   * Gets list of player names added in the game.
   *
   * @return list of string added in the game.
   */
  private List<String> getPlayersNameList() {
    List<String> playersNameList = new ArrayList<>();
    for (PlayerInterface playersName : playerObjectList) {
      playersNameList.add(playersName.getPlayerName());
    }
    return playersNameList;
  }


  private void getTurn() {
    currentPlayerCounter += 1;
    if (currentPlayerCounter == playerObjectList.size()) {
      currentPlayerCounter = 0;
    }
    currentPlayer = playerObjectList.get(currentPlayerCounter);
    totalTurns += 1;
    world.getSpaceObject(targetPetWrapper.getTargetSpaceName()).setTarget();
    targetPetWrapper.moveTarget();
    world.getSpaceObject(targetPetWrapper.getTargetSpaceName()).setTarget();

  }

  @Override
  public int makeMove(int bound) {
    int turn = currentPlayer.getStrategy().getTurn(bound);
    return turn;
  }

  @Override
  public StrategyInterface getCurrentPlayerStrategy() {
    return currentPlayer.getStrategy();
  }

  @Override
  public List<String> getSpaceNames() {
    return world.getSpaceNames();
  }

  @Override
  public List<String> getPlayerNames() {
    List<String> playerNames = new ArrayList<>();
    for (PlayerInterface p : playerObjectList) {
      if (!world.getSpaceObject(p.getPlayerSpaceName()).isPet()
              || p.getPlayerName().equals(currentPlayer.getPlayerName())) {
        playerNames.add(p.getPlayerName());
      }
    }
    return playerNames;
  }

  private List<String> dfs(String s) {
    List<String> outputList = new ArrayList<>();
    world.getSpaceNames().forEach(a -> spaceVisitMap.put(a, false));
    return dfsUtil(s, null, new ArrayList<>(), outputList);
  }

  private List<String> dfsUtil(String s, String parent,
                               List<List<String>> roadVisited,
                               List<String> outputList) {
    if (spaceVisitMap.entrySet().stream().filter(a -> a.getValue() == true).count()
            == world.getSpaceNames().size()) {
      return outputList;
    }
    spaceVisitMap.put(s, true);
    outputList.add(s);
    roadVisited.add(new ArrayList<>(Arrays.asList(parent, s)));

    for (String space : world.getSpaceObject(s).getNeighbors()
            .stream().sorted().collect(Collectors.toList())) {
      if (!spaceVisitMap.get(space)) {
        dfsUtil(space, s, roadVisited, outputList);
      }
    }
    for (int y = 0; y < roadVisited.size(); y++) {
      if (roadVisited.get(y).get(1) == s) {
        dfsUtil(roadVisited.get(y).get(0), s, roadVisited, outputList);
      }
    }
    return outputList;
  }


}
