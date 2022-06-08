package model.player;

import java.util.List;

/**
 * Computer Strategy handles the random generation
 * algorithm for taking valid moves and turns.
 */
public class ComputerStrategy implements StrategyInterface {

  private RandomImpl random;
  private PlayerWrapperInterface playerWrapper;

  /**
   * ComputerStrategy constructor sets the random class
   * as class variable.
   *
   * @param random random number generator class.
   */
  public ComputerStrategy(RandomImpl random) {
    if (random == null) {
      throw new IllegalArgumentException("Random cannot be null");
    }
    this.random = random;
  }

  @Override
  public void setPlayerWrapper(PlayerWrapperInterface playerWrapper) {
    if (playerWrapper == null) {
      throw new IllegalArgumentException("Player wrapper cannot be null");
    }
    this.playerWrapper = playerWrapper;
  }

  @Override
  public int getTurn(int bound) {
    if (bound <= 0) {
      throw new IllegalArgumentException("Bound cannot be less than 1");
    }
    if (playerWrapper.checkComputerTargetHit()) {
      return 5;
    }
    return random.getValue(bound);
  }


  @Override
  public String getRandomSpace() {
    List<String> spaceNames = playerWrapper.getSpaceNames();
    int randValue = random.getValue(spaceNames);
    return spaceNames.get(randValue);
  }

  @Override
  public String getHitItem() {
    List<String> itemNames = playerWrapper
            .getPlayerObject(playerWrapper.getCurrentPlayerName()).getPlayerItemList();
    if (itemNames.size() == 0) {
      return "poke";
    }
    ;
    return itemNames.get(0);
  }

  @Override
  public String getMovePlayer() {
    List<String> neighborSpaces = playerWrapper.getCurrentPlayerNeighbors();
    int randValue = random.getValue(neighborSpaces);
    return neighborSpaces.get(randValue);
  }

  @Override
  public String getPickItem() {
    List<String> pickItem = playerWrapper.getPickableItemNames();
    int randValue = random.getValue(pickItem);
    return pickItem.get(randValue);
  }

  @Override
  public String getCreatePlayerSpace(List<String> spaceNames) {
    int randValue = random.getValue(spaceNames);
    return spaceNames.get(randValue);
  }


}
