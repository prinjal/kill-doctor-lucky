package controller;

import model.world.FacadePlayerCollection;
import model.world.FacadePlayerCollectionInterface;
import view.GameView;
import view.GameViewInterface;

/**
 * Method which initiates the game, acts as driver for the project.
 */
public class Driver {

  /**
   * Main method of the project.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    try {
      int totalTurns = Integer.parseInt(args[0]);
      FacadePlayerCollectionInterface model =
              new FacadePlayerCollection();
      GameViewInterface view = new GameView(model);
      GameControllerInterface controller = new GameController(view, model, totalTurns);
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      System.out.printf("Enter valid number of total turns");
    }
  }
}
