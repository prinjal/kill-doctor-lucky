package view;

/**
 * Holds information of a player like name,
 * room name and carry limit.
 */
public class PlayerDto {
  private final String name;
  private final String roomName;
  private final int carryLimit;

  /**
   * Initializes this object to set the attributes of player.
   *
   * @param name       name of the player.
   * @param roomName   name of the room which player wants to start from.
   * @param carryLimit item carrying limit of the player.
   */
  public PlayerDto(String name, String roomName, int carryLimit) {
    this.name = name;
    this.roomName = roomName;
    this.carryLimit = carryLimit;
  }

  /**
   * Method to get the name of the player.
   *
   * @return name of the player
   */
  public String getName() {
    return name;
  }

  /**
   * Method to get the name of the room.
   *
   * @return name of the room
   */
  public String getRoomName() {
    return roomName;
  }

  /**
   * Method to get the carry limit of the player.
   *
   * @return value of the carry limit
   */
  public int getCarryLimit() {
    return carryLimit;
  }
}
