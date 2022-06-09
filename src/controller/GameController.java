package controller;

import controller.commands.AddPlayer;
import controller.commands.Attack;
import controller.commands.CommandInterface;
import controller.commands.LookAround;
import controller.commands.MovePet;
import controller.commands.MovePlayer;
import controller.commands.PickItem;
import controller.commands.SetAttributeCommand;
import java.util.HashMap;
import java.util.Map;
import model.player.CustomException;
import model.world.FacadePlayerCollectionInterface;
import model.world.Segregator;
import view.GameViewInterface;

/**
 * Class which implements the gameControllerInterface which handles the
 * user actions.
 */
public class GameController implements GameControllerInterface {
  private final GameViewInterface gameView;
  private final FacadePlayerCollectionInterface facadePlayerCollection;
  private final Map<Integer, CommandInterface> moveCommands;
  private final int totalTurns;

  /**
   * Constructor which initializes the gameController Object.
   *
   * @param gameView               reference to the gameView object
   * @param facadePlayerCollection reference to the facadePlayerCollection object
   * @param totalTurns             total number of turns allowed in the game
   */
  public GameController(GameViewInterface gameView,
                        FacadePlayerCollectionInterface facadePlayerCollection,
                        int totalTurns) {
    if (gameView == null || facadePlayerCollection == null || totalTurns == 0) {
      throw new IllegalArgumentException("GameView and FacadePlayerCollection should not be null");
    }
    if (totalTurns < 1) {
      throw new IllegalArgumentException("Total turns should be greater than zero");
    }
    this.gameView = gameView;
    this.facadePlayerCollection = facadePlayerCollection;
    this.moveCommands = new HashMap<>();
    this.gameView.setFeatures(this);
    this.totalTurns = totalTurns;
    init();
  }

  @Override
  public void playGame() {
    if (facadePlayerCollection.getPlayerNames().size() == 0) {
      gameView.displayError("No Players in the game, add players");
    } else {
      gameView.makeVisible();
      takeComputerTurn();
    }
  }

  @Override
  public void processMoves(int command) {
    if (command <= 0) {
      throw new IllegalArgumentException("Invalid command");
    }
    try {
      if (isGameOver()) {
        return;
      }
      CommandInterface cmd = moveCommands.getOrDefault(command, null);
      cmd.run();
      gameView.refresh();
      takeComputerTurn(command);
    } catch (IllegalArgumentException ex) {
      gameView.displayError(ex.getMessage());
    } catch (IllegalStateException ex) {
      gameView.displayError(ex.getMessage());
      takeComputerTurn(command);
    }
  }

  @Override
  public void processMoves(int command, String s) {
    if (command == 0 || s == null) {
      throw new IllegalArgumentException("Invalid command");
    }
    try {
      if (isGameOver()) {
        return;
      }
      SetAttributeCommand cmd = (SetAttributeCommand) moveCommands.getOrDefault(command, null);
      cmd.setAttribute(s);
      cmd.run();
      gameView.refresh();
      takeComputerTurn(command);
    } catch (IllegalArgumentException ex) {
      gameView.displayError(ex.getMessage());
    } catch (IllegalStateException ex) {
      gameView.displayError(ex.getMessage());
      takeComputerTurn(command);
    }
  }

  @Override
  public void setModel(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    Segregator segregator = new Segregator(s);
    facadePlayerCollection.initGame(segregator);
  }


  @Override
  public boolean isGameOver() {
    boolean gameOver = false;
    if (facadePlayerCollection.isGameOver(totalTurns)) {
      if (facadePlayerCollection.getWinner().equals("")) {
        gameView.displayError("Game over!\n Target Ran away!");
      } else {
        gameView.displayError(String.format("Game over!\n Player %s wins",
                facadePlayerCollection.getWinner()));
      }
      gameOver = true;
    }
    return gameOver;
  }

  /**
   * Method which initializes the commands.
   */
  private void init() {
    moveCommands.put(1, new MovePet(this.facadePlayerCollection, this.gameView));
    moveCommands.put(2, new PickItem(this.facadePlayerCollection, this.gameView));
    moveCommands.put(3, new MovePlayer(this.facadePlayerCollection, this.gameView));
    moveCommands.put(4, new LookAround(this.facadePlayerCollection, this.gameView));
    moveCommands.put(5, new Attack(this.facadePlayerCollection, this.gameView));
    moveCommands.put(6, new AddPlayer(this.facadePlayerCollection, this.gameView, false));
    moveCommands.put(7, new AddPlayer(this.facadePlayerCollection, this.gameView, true));
  }

  private void takeComputerTurn(int command) {
    if (command == 0) {
      throw new IllegalArgumentException("Invalid command");
    }
    if (command != 6 && command != 7) {
      takeComputerTurn();
    }
  }

  /**
   * Method to take the turn of the computer, it runs in recursion and if the turn is of
   * human player then throw the exception.
   */
  private void takeComputerTurn() {
    try {
      if(isGameOver()){
        return;
      }
      int i = facadePlayerCollection.makeMove(4);
      CommandInterface cmd = moveCommands.getOrDefault(i, null);
      cmd.run();
      takeComputerTurn();
    } catch (IllegalArgumentException | IllegalStateException ex) {
      takeComputerTurn();
    } catch (CustomException ex) {
      //do nothing. this customException is thrown when it is human player turn
      //so, it throws the control to the view.
    }
  }
}

