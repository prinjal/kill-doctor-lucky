package controller.commands;

import model.world.FacadePlayerCollectionInterface;
import view.GameViewInterface;

/**
 * Class which implements the Command Interface and does the job
 * of LookAround by passing the required details to the model
 * class.
 */
public class LookAround implements CommandInterface {

  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final GameViewInterface gameView;

  /**
   * Constructor which initializes the LookAround object.
   *
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param gameView               reference to the gameView object
   */
  public LookAround(FacadePlayerCollectionInterface facadePlayerCollection,
                    GameViewInterface gameView) {
    if (facadePlayerCollection == null || gameView == null) {
      throw new IllegalArgumentException("Model or GameView cannot be null");
    }
    this.facadePlayerCollection = facadePlayerCollection;
    this.gameView = gameView;
  }

  @Override
  public void run() {
    String currPlayerName = this.facadePlayerCollection.getCurrentPlayerName();
    this.facadePlayerCollection.lookAround();
    String msg = new StringBuilder(currPlayerName)
            .append(" used look around").toString();
    this.gameView.showMsg(msg);
  }
}
