package model.world;

import java.util.ArrayList;
import java.util.List;
import model.player.PlayerType;
import model.player.PlayerWrapperInterface;
import model.player.StrategyInterface;
import model.targetpet.TargetPetWrapper;
import model.targetpet.TargetPetWrapperInterface;

/**
 * FacadePlayerCollection is a subclass of FacadeModelCollection
 * which inherits all the methods of the parent class plus
 * have its own methods which are specific to player like getting
 * current player information, moving player, looking around from
 * a player's view,etc.
 */
public class FacadePlayerCollection extends FacadeModelCollection
        implements FacadePlayerCollectionInterface {

  private PlayerWrapperInterface playerWrapper;
  private TargetPetWrapperInterface targetPetWrapper;
  private Segregator seg;

  /**
   * Constructor method to set all the lists
   * fetched from Segregator class.
   */
  public FacadePlayerCollection() {
    super();
    this.playerWrapper = null;
    this.targetPetWrapper = null;
    this.seg = new Segregator();
    initGame(seg);
  }

  @Override
  public void initGame(Segregator s) {
    this.seg = s;
    super.initGame(s);
    this.targetPetWrapper = new TargetPetWrapper(seg.getTarget(), seg.getPet(), getSpaceNames());
    this.playerWrapper = getPlayerWrapper(targetPetWrapper);
  }

  @Override
  public List<String> getPlayerNames() {
    return playerWrapper.getPlayerNames();
  }

  @Override
  public void createPlayer(String name, PlayerType playerType, String spaceName, int itemCapacity,
                           StrategyInterface strategy) {
    if (name == null || playerType == null || spaceName == null || itemCapacity < 0) {
      throw new IllegalArgumentException("Invalid details of player");
    }
    playerWrapper.createPlayer(name, playerType, spaceName, itemCapacity, strategy);
  }

  @Override
  public void movePlayer(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    playerWrapper.movePlayer(spaceName);
  }


  @Override
  public void pickItem(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }
    playerWrapper.pickItem(itemName);
  }

  @Override
  public String lookAround() {
    return playerWrapper.lookAround();
  }


  @Override
  public boolean isGameOver(int turns) {
    if (turns < 0) {
      throw new IllegalArgumentException("Turns should be positive");
    }
    return playerWrapper.isGameOver(turns);
  }

  @Override
  public String getCurrentPlayerName() {
    return playerWrapper.getCurrentPlayerName();
  }

  @Override
  public PlayerType getCurrentPlayerType() {
    return playerWrapper.getCurrentPlayerType();
  }

  @Override
  public List<String> getCurrentPlayerNeighbors() {
    List<String> neighbors = new ArrayList<>();
    for (String item : playerWrapper.getCurrentPlayerNeighbors()) {
      neighbors.add(item);
    }
    return neighbors;
  }

  @Override
  public String getPlayerSpaceName(String s) {
    return playerWrapper.getPlayerSpace(s);
  }

  @Override
  public int getCurrentPlayerItemCount() {
    return playerWrapper.getCurrentPlayerItemCount();
  }

  @Override
  public String getPlayerDetails(String playerName) {
    if (playerName == null) {
      throw new IllegalArgumentException("Player name cannot be null");
    }
    return playerWrapper.getPlayerDetails(playerName);
  }

  @Override
  public List<String> getPickableItemNames() {
    List<String> itemDetails = new ArrayList<>();
    for (String item : playerWrapper.getPickableItemNames()) {
      itemDetails.add(item);
    }
    return itemDetails;
  }

  @Override
  public String getTargetDetails() {
    return targetPetWrapper.getTargetDetails();
  }

  @Override
  public void movePet(String spaceName) {
    if (spaceName == null) {
      throw new IllegalArgumentException("Space name cannot be null");
    }
    playerWrapper.movePet(spaceName);
  }

  @Override
  public String hitTarget(String itemName) {
    if (itemName == null) {
      throw new IllegalArgumentException("Item name cannot be null");
    }
    return playerWrapper.hitTarget(itemName);
  }


  @Override
  public List<String> getCurrentPlayerItemList() {
    List<String> itemList = new ArrayList<>();
    for (String s : playerWrapper.getPlayerObject(getCurrentPlayerName()).getPlayerItemList()) {
      itemList.add(s);
    }
    return itemList;
  }

  @Override
  public boolean checkComputerTargetHit() {
    return playerWrapper.checkComputerTargetHit();
  }

  @Override
  public String getWinner() {
    return playerWrapper.getWinner();
  }

  @Override
  public int makeMove(int bound) {
    return playerWrapper.makeMove(bound);
  }

  @Override
  public String getRandomSpace() {
    return playerWrapper.getCurrentPlayerStrategy().getRandomSpace();
  }

  @Override
  public String getHitItem() {
    return playerWrapper.getCurrentPlayerStrategy().getHitItem();
  }

  @Override
  public String getMovePlayer() {
    return playerWrapper.getCurrentPlayerStrategy().getMovePlayer();
  }

  @Override
  public String getPickItem() {
    return playerWrapper.getCurrentPlayerStrategy().getPickItem();
  }

  @Override
  public String getTargetSpace() {
    return targetPetWrapper.getTargetSpaceName();
  }

  @Override
  public String getPetSpaceName() {
    return targetPetWrapper.getPetSpaceName();
  }


}
