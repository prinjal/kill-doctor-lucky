package controllertest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.player.CustomException;
import model.player.PlayerType;
import model.player.StrategyInterface;
import model.world.FacadePlayerCollectionInterface;
import model.world.Segregator;

/**
 * Failing mock model to test the controller for failing states.
 */
public class FailingFacadePlayerCollectionMockModel implements FacadePlayerCollectionInterface {

  private final StringBuilder log;

  /**
   * Initializes this object to create a log of output data.
   * @param log to assert the output with actual value.
   */
  public FailingFacadePlayerCollectionMockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void createPlayer(String name, PlayerType playerType, String spaceName,
                           int itemCapacity, StrategyInterface strategy) {
    log.append("createPlayer method called with value: ").append(name).append(" ")
            .append(playerType).append(" ").append(spaceName).append(" ").append(itemCapacity)
            .append("\n");
    throw new IllegalArgumentException("Exception from createPlayer method");
  }

  @Override
  public void movePlayer(String spaceName) {
    log.append("movePlayer method called with value ").append(spaceName).append("\n");
    throw new IllegalArgumentException("Exception from movePlayer method");
  }

  @Override
  public void pickItem(String itemName) {
    log.append("pickItem method called with value ").append(itemName).append("\n");
    throw new IllegalArgumentException("Exception from pickItem method");
  }

  @Override
  public String lookAround() {
    log.append("lookAround method called").append("\n");
    throw new IllegalArgumentException("Exception from lookAround method");
  }

  @Override
  public boolean isGameOver(int turns) {
    return false;
  }

  @Override
  public void drawWorld() {

  }

  @Override
  public void movePet(String spaceName) {
    log.append("movePet method called with value ").append(spaceName).append("\n");
    throw new IllegalArgumentException("Exception from movePet method");
  }

  @Override
  public String hitTarget(String itemName) {
    log.append("hitTarget method called with value ").append(itemName).append("\n");
    throw new IllegalArgumentException("Exception from hitTarget method");
  }

  @Override
  public int makeMove(int bound) {
    log.append(new StringBuilder().append("Make Move Called with value ")
            .append(bound)).append("\n");
    throw new CustomException("Custom exception called");
  }

  @Override
  public void initGame(Segregator segregator) {

  }

  @Override
  public List<String> getPlayerNames() {
    return null;
  }

  @Override
  public String getCurrentPlayerName() {
    return null;
  }

  @Override
  public PlayerType getCurrentPlayerType() {
    return null;
  }

  @Override
  public List<String> getCurrentPlayerNeighbors() {
    return null;
  }

  @Override
  public String getPlayerSpaceName(String playerName) {
    return null;
  }

  @Override
  public int getCurrentPlayerItemCount() {
    return 0;
  }

  @Override
  public String getPlayerDetails(String playerName) {
    return null;
  }

  @Override
  public List<String> getPickableItemNames() {
    List<String> itemNames = new ArrayList<>();
    itemNames.add("Knife");
    itemNames.add("Pan");
    return itemNames;
  }

  @Override
  public String getWorldName() {
    return null;
  }

  @Override
  public List<Integer> getWorldSize() {
    return null;
  }

  @Override
  public List<String> getSpaceNames() {
    List<String> spaceNames = new ArrayList<>();
    spaceNames.add("Armory");
    spaceNames.add("Foyer");
    return spaceNames;
  }

  @Override
  public String getSpaceDetails(String spaceName) {
    return null;
  }

  @Override
  public Map<String, List<String>> getSpaceCoordinates() {
    return null;
  }

  @Override
  public String getTargetDetails() {
    return null;
  }

  @Override
  public List<String> getCurrentPlayerItemList() {
    List<String> itemNames = new ArrayList<>();
    itemNames.add("Knife");
    itemNames.add("Pan");
    return itemNames;
  }

  @Override
  public boolean checkComputerTargetHit() {
    return false;
  }

  @Override
  public String getWinner() {
    return null;
  }

  @Override
  public String getRandomSpace() {
    throw new CustomException("Custom exception called");
  }

  @Override
  public String getHitItem() {
    throw new CustomException("Custom exception called");
  }

  @Override
  public String getMovePlayer() {
    throw new CustomException("Custom exception called");
  }

  @Override
  public String getPickItem() {
    throw new CustomException("Custom exception called");
  }

  @Override
  public String getTargetSpace() {
    throw new CustomException("Custom exception called");
  }

  @Override
  public String getPetSpaceName() {
    return null;
  }
}
