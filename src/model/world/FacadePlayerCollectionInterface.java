package model.world;


import model.player.PlayerType;
import model.player.StrategyInterface;

/**
 * Variables in the controller class will be of
 * FacadePlayerCollectionInterface which is implemented
 * by FacadePlayerCollection. It returns all the necessary
 * information needed to drive the program through controller.
 */
public interface FacadePlayerCollectionInterface extends ReadOnlyGameViewInterface {

  /**
   * Create players in the game at a particular space
   * and with attributes like name, type and bag capacity.
   *
   * @param name         name of the player which need to be created.
   * @param playerType   type of the player(Human/Computer) enum.
   * @param spaceName    name of the space that player wants to
   *                     enter in.
   * @param itemCapacity bag capacity of the player to hold
   *                     number of items.
   */
  void createPlayer(String name, PlayerType playerType, String spaceName, int itemCapacity,
                    StrategyInterface strategy);

  /**
   * Moves player from one space to another.
   * This method gets called by the command class.
   * This action counts as a turn.
   *
   * @param spaceName name of the space player wants to move in
   */
  void movePlayer(String spaceName);

  /**
   * Picks the item which is located in the current space.
   * This method gets called by the command class
   * and counts as a turn.
   *
   * @param itemName name of the item that the player
   *                 want to pick.
   */
  void pickItem(String itemName);

  /**
   * Gives detailed information of the neighboring spaces
   * from the current space of the current player. This counts
   * as a turn.
   *
   * @return string of details of neighboring spaces.
   */
  String lookAround();

  /**
   * The number of turns in the game is compared with
   * the turns provided in the parameter and the method
   * returns true if the turns of game exceeds number
   * of turns provided in parameter and false otherwise.
   *
   * @param turns total turns allowed for the game
   * @return true if game is over. false if game is not over.
   */
  boolean isGameOver(int turns);


  /**
   * Draws graphical representation of the
   * world and saves it in the current location.
   */
  void drawWorld();


  /**
   * Moves pet to a specified space provided
   * by the player.
   *
   * @param spaceName name of the space
   *                  where pet needs to be moved.
   */
  void movePet(String spaceName);

  /**
   * Hits target with the item name specified in the
   * parameter.
   *
   * @param itemName name of the item to hit the target.
   * @return String of status of message for hitting target
   */
  String hitTarget(String itemName);


  /**
   * Takes a move for a computer player and asks for
   * a move for Human player.
   *
   * @param bound limits the move that computer player can make.
   * @return integer value of move that should be taken.
   */
  int makeMove(int bound);

  /**
   * Initializes the game for a custom specification of world
   * format.
   * @param segregator segregator object to create world with custom
   *            specification.
   */
  void initGame(Segregator segregator);

}
