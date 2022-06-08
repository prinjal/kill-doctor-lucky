package model.player;

import java.util.List;
import java.util.Random;

/**
 * This class dynamically functions from providing random
 * values for computer and getting input from the user.
 * This gets called by the controller class which gets user input
 * and random values for the computer player.
 */
public class RandomImpl {

  private Random random;
  private int[] val;
  private int valCounter;

  /**
   * This constructor gets called by the controller
   * when random values need to be generated for the
   * computer player.
   */
  public RandomImpl() {
    this.random = new Random();
  }

  /**
   * This constructor gets called for testing the controller
   * class the variable argument values will be
   * considered instead of random values for the
   * computer player to test the computer player.
   *
   * @param val variable argument which replaces
   *            random values of computer player.
   */
  public RandomImpl(int... val) {
    this.val = val;
    this.valCounter = 0;
  }


  /**
   * getValue method will be called by the controller
   * class and it will run dynamically according to the player
   * type. In case of a computer player type it will
   * generate a random value within the bound.
   *
   * @param bound bounds the random number generator within a limit.
   * @return string value of user input or random value for computer player.
   */
  public int getValue(int bound) {
    if (random != null) {
      return random.nextInt(bound) + 1;
    } else {
      int value;
      value = val[valCounter];
      valCounter += 1;
      return value;
    }
  }

  /**
   * getValue method will be called when a list is sent as
   * an input which will run dynamically according to the
   * player type. In case of human player it will as for
   * user input and for computer it will randomly pick
   * an item or space according to the list provided in
   * input.
   *
   * @param inputList list of spaces or items for computer player
   *                  to randomly pick one of the values.
   * @return string value of user input or random value for computer player.
   */

  public int getValue(List<String> inputList) {
    if (random != null) {
      if (inputList.size() == 0) {
        throw new IllegalArgumentException("Invalid Move! ");
      }
      int pickNeighbor = random.nextInt(inputList.size());
      return pickNeighbor;
    } else {
      int value;
      value = val[valCounter];
      int pickNeighbor = value;
      valCounter += 1;
      return pickNeighbor;
    }
  }

}
