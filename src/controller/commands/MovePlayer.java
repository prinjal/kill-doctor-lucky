package controller.commands;

import model.player.CustomException;
import model.world.FacadePlayerCollectionInterface;
import view.GameViewInterface;

/**
 * Class which implements the Command Interface and does the job
 * of moving the player by passing the required details to the model
 * class.
 */
public class MovePlayer implements SetAttributeCommand {
  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final GameViewInterface gameView;
  private String spaceName;

  /**
   * Constructor which initializes the MovePlayer object.
   *
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param gameView               reference to the gameView object
   */
  public MovePlayer(FacadePlayerCollectionInterface facadePlayerCollection,
                    GameViewInterface gameView) {
    if (facadePlayerCollection == null || gameView == null) {
      throw new IllegalArgumentException("Model or GameView cannot be null");
    }
    this.facadePlayerCollection = facadePlayerCollection;
    this.gameView = gameView;
    this.spaceName = null;
  }


  @Override
  public void run() {
    String playerName = facadePlayerCollection.getCurrentPlayerName();
    try {
      String spaceName = facadePlayerCollection.getMovePlayer();
      facadePlayerCollection.movePlayer(spaceName);
    } catch (CustomException e) {
      facadePlayerCollection.movePlayer(spaceName);
    }
    String msg = new StringBuilder("Player ")
            .append(playerName)
            .append(" moved to ")
            .append(spaceName)
            .toString();
    gameView.showMsg(msg);
    gameView.refresh();
  }

  @Override
  public void setAttribute(String attribute) {
    if (attribute == null) {
      throw new IllegalArgumentException("Attribute cannot be null");
    }
    this.spaceName = attribute;
  }
}
