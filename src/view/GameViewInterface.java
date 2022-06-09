package view;

import controller.GameControllerInterface;
import java.util.List;

/**
 * GameViewInterface handles display of the
 * game board, game state ,and errors if any.
 */
public interface GameViewInterface {

  /**
   * Makes the game board visible with all the
   * components like player, target and world outline.
   */
  void makeVisible();

  /**
   * Refreshes the board and updates the game board
   * with the latest state of the game.
   */
  void refresh();

  /**
   * Adds listeners to the game to make the game
   * interactive through keyboard and mouse.
   */
  void setFeatures(GameControllerInterface controller);

  /**
   * Shows a drop-down menu with the input
   * list as the list of string passed in parameter.
   * This will be used to get items for hitting the target
   * or move player/pet to different spaces in the world.
   *
   * @param data list of string which needs to be showed
   *             as a list of value in the panel.
   */
  String getActionInput(String title, String msg, List<String> data);

  /**
   * Displays a drop-down list for user to pick a valid item,
   * and a valid space.
   * @param data the data from which player should be allowed to
   *             select the value.
   * @return string of the value which player selected.
   */
  String getActionInput(String data);

  /**
   * Retrieves player information like player name,
   * space, type and bag count.
   * Note: using general object, as this list contains different type of
   * variables to take the input from the user.
   * @return player data transfer object.
   *
   */
  PlayerDto getPlayerDto();

  /**
   * Shows error if any with the descriptive
   * message passed in the parameter.
   *
   * @param errorMessage string which needs to be displayed
   *                     as error message.
   */
  void displayError(String errorMessage);

  /**
   * Displays an informational message to player.
   * @param message string of message which needs to be shown to player.
   */
  void showMsg(String message);
}
