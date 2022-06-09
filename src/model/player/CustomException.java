package model.player;

/**
 * CustomException is thrown whenever a user input is needed from
 * the human player.
 */
public class CustomException extends RuntimeException {
  /**
   * The constructor will set the message from
   * and display whenever the exception is thrown.
   * @param errMsg string of error message set to display when
   *               the exception is thrown.
   */
  public CustomException(String errMsg) {
    super(errMsg);
  }
}
