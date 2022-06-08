package model.player;

import java.util.List;

/**
 * HumanStrategy handles the algorithm for human player
 * and throws exception with descriptive messages
 * to ask the user for input.
 */
public class HumanStrategy implements StrategyInterface {

  private PlayerWrapperInterface playerWrapper;

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
    throw new CustomException("Enter valid data");
  }


  @Override
  public String getRandomSpace() {
    throw new CustomException("Enter space name");
  }

  @Override
  public String getHitItem() {
    throw new CustomException("Enter item name");
  }

  @Override
  public String getMovePlayer() {
    throw new CustomException("Enter space name");
  }

  @Override
  public String getPickItem() {
    throw new CustomException("Enter item name");
  }

  @Override
  public String getCreatePlayerSpace(List<String> spaceNames) {
    if (spaceNames == null) {
      throw new IllegalArgumentException("Space names cannot be null");
    }
    throw new CustomException("Enter space name");
  }
}
