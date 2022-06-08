package controller.commands;

import model.player.CustomException;
import model.world.FacadePlayerCollectionInterface;
import view.GameViewInterface;

/**
 * Class which implements the Command Interface and does the job
 * of moving the pet by passing the required details to the model
 * class.
 */
public class MovePet implements CommandInterface {

  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final GameViewInterface gameView;

  /**
   * Constructor which initializes the MovePet object.
   *
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param gameView               reference to the gameView object
   */
  public MovePet(FacadePlayerCollectionInterface facadePlayerCollection,
                 GameViewInterface gameView) {
    if (facadePlayerCollection == null || gameView == null) {
      throw new IllegalArgumentException("Model or GameView cannot be null");
    }
    this.facadePlayerCollection = facadePlayerCollection;
    this.gameView = gameView;
  }

  @Override
  public void run() {
    String roomName;
    try {
      roomName = facadePlayerCollection.getRandomSpace();
    } catch (CustomException ex) {
      roomName = gameView.getActionInput("Move Pet", "Select a space to move pet",
              facadePlayerCollection.getSpaceNames());
    }
    String playerName = facadePlayerCollection.getCurrentPlayerName();
    this.facadePlayerCollection.movePet(roomName);
    String msg = new StringBuilder("Pet moved to ")
            .append(roomName)
            .append("by ")
            .append(playerName)
            .toString();
    gameView.showMsg(msg);
  }

}
