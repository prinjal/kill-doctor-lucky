package controller.commands;

import java.util.List;
import model.player.CustomException;
import model.world.FacadePlayerCollectionInterface;
import view.GameViewInterface;

/**
 * Class which implements the Command Interface and does the job
 * of Attacking the target by passing the required details to the model
 * class.
 */
public class Attack implements CommandInterface {

  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final GameViewInterface gameView;

  /**
   * Constructor which initializes the Attack object.
   *
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param gameView               reference to the gameView object
   */
  public Attack(FacadePlayerCollectionInterface facadePlayerCollection,
                GameViewInterface gameView) {
    if (facadePlayerCollection == null || gameView == null) {
      throw new IllegalArgumentException("Model or GameView cannot be null");
    }
    this.gameView = gameView;
    this.facadePlayerCollection = facadePlayerCollection;
  }

  @Override
  public void run() {
    String itemName;
    try {
      itemName = facadePlayerCollection.getHitItem();
    } catch (CustomException ex) {
      itemName = gameView.getActionInput("Attack Player", "Select an Item to attack",
              facadePlayerCollection.getCurrentPlayerItemList()
                      .size() > 0 ? facadePlayerCollection.getCurrentPlayerItemList()
                      : List.of("Poke"));
    }
    String playerName = facadePlayerCollection.getCurrentPlayerName();
    String hitMsg=this.facadePlayerCollection.hitTarget(itemName);
    String msg = new StringBuilder()
            .append("Hit Successful with ")
            .append(itemName)
            .append(" ")
            .append(playerName)
            .toString();
    gameView.showMsg(msg);
    gameView.refresh();
  }

}
