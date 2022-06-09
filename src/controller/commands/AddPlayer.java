package controller.commands;

import model.player.ComputerStrategy;
import model.player.HumanStrategy;
import model.player.PlayerType;
import model.player.RandomImpl;
import model.player.StrategyInterface;
import model.world.FacadePlayerCollectionInterface;
import view.GameViewInterface;
import view.PlayerDto;

/**
 * Class which implements the Command Interface and does the job
 * of adding the player by passing the required details to the model
 * class.
 */
public class AddPlayer implements CommandInterface {

  private final boolean isComputer;

  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final GameViewInterface gameView;

  /**
   * Constructor which initializes the addPlayer object.
   *
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param gameView               reference to the gameView object
   * @param isComputer             boolean value which determines the type of player
   */
  public AddPlayer(FacadePlayerCollectionInterface facadePlayerCollection,
                   GameViewInterface gameView, boolean isComputer) {
    if (facadePlayerCollection == null || gameView == null) {
      throw new IllegalArgumentException("Model or GameView cannot be null");
    }
    this.gameView = gameView;
    this.facadePlayerCollection = facadePlayerCollection;
    this.isComputer = isComputer;
  }


  @Override
  public void run() {
    execute();
  }


  /**
   * Method which executes the command with addition to
   * appending the output.
   */
  private void execute() {
    String playerName;
    String spaceName;
    if (isComputer) {
      StrategyInterface strategy = new ComputerStrategy(new RandomImpl());
      playerName = this.gameView.getActionInput("Enter the name of the computer player");
      spaceName = strategy.getCreatePlayerSpace(this.facadePlayerCollection.getSpaceNames());
      this.facadePlayerCollection.createPlayer(playerName, PlayerType.COMPUTER,
              spaceName, 5, strategy);
    } else {
      PlayerDto playerDto = this.gameView.getPlayerDto();
      playerName = playerDto.getName();
      spaceName = playerDto.getRoomName();
      this.facadePlayerCollection.createPlayer(playerName, PlayerType.HUMAN,
              spaceName, playerDto.getCarryLimit(), new HumanStrategy());
    }
    String msg = new StringBuilder()
            .append("Player - ")
            .append(playerName)
            .append(" added into ")
            .append(spaceName)
            .toString();
    gameView.showMsg(msg);
  }
}
