package controllertest;

import controller.GameControllerInterface;
import java.util.List;
import view.GameViewInterface;
import view.PlayerDto;

/**
 * Creates a mock class for view to test the controller.
 */
public class GameViewMock implements GameViewInterface {

  private final StringBuilder log;

  /**
   * Initializes this object to test the controller class.
   * @param log output to assert with the actual value.
   */
  public GameViewMock(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void makeVisible() {
    log.append(new StringBuilder().append("Make visible called")).append("\n");
  }

  @Override
  public void refresh() {
    log.append("refresh method called").append("\n");
  }

  @Override
  public void setFeatures(GameControllerInterface controller) {

  }

  @Override
  public String getActionInput(String title, String msg, List<String> data) {
    log.append("getActionInput method called with value title: ").append(title).append(" msg: ")
            .append(msg).append(" data: ").append(data).append("\n");
    return data.get(0);
  }

  @Override
  public String getActionInput(String data) {
    log.append("getActionInput method called with value ").append(data).append("\n");
    return "Armory";
  }

  @Override
  public PlayerDto getPlayerDto() {
    log.append("PlayerName: ").append("Mike").append(" RoomName: ").append("Armory")
            .append(" CarryLimit: ").append(5).append("\n");
    return new PlayerDto("Mike", "Armory", 5);
  }

  @Override
  public void displayError(String errorMessage) {
    log.append("displayError method called with value: ").append(errorMessage).append("\n");
  }

  @Override
  public void showMsg(String message) {

  }
}
