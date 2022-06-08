package model.player;

/**
 * PlayerType enum class has two values that
 * a Player object can set as its player type.
 * It differentiates between Human and Computer.
 * The segregation is handled in the controller
 * class.
 */
public enum PlayerType {
  HUMAN("Human"), COMPUTER("Computer");

  private final String disp;

  private PlayerType(String disp) {
    this.disp = disp;
  }


  @Override
  public String toString() {
    return disp;
  }
}
