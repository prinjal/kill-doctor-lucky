package controller.commands;

import java.util.List;
import model.player.CustomException;
import model.world.FacadePlayerCollectionInterface;
import view.GameViewInterface;

/**
 * Class which implements the Command Interface and does the job
 * of picking the item from the room by passing the required details to the model
 * class.
 */
public class PickItem implements CommandInterface {

  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final GameViewInterface gameView;

  /**
   * Constructor which initializes the PickItem object.
   *
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param gameView               reference to the gameView object
   */
  public PickItem(FacadePlayerCollectionInterface facadePlayerCollection,
                  GameViewInterface gameView) {
    if (facadePlayerCollection == null || gameView == null) {
      throw new IllegalArgumentException("Model or GameView cannot be null");
    }
    this.facadePlayerCollection = facadePlayerCollection;
    this.gameView = gameView;
  }

  @Override
  public void run() {
    String itemName;
    try {
      itemName = facadePlayerCollection.getPickItem();
    } catch (CustomException e) {
      List<String> itemLst = facadePlayerCollection.getPickableItemNames();
      if (itemLst.size() > 0) {
        itemName = gameView.getActionInput("Pick Item", "Select an item", itemLst);
      } else {
        gameView.displayError("No items in the room");
        return;
      }
    }
    String playerName = facadePlayerCollection.getCurrentPlayerName();
    facadePlayerCollection.pickItem(itemName);
    String msg = new StringBuilder("Item ")
            .append(itemName)
            .append(" picked successfully ")
            .append("by ")
            .append(playerName)
            .toString();
    gameView.showMsg(msg);
    gameView.refresh();
  }

}
