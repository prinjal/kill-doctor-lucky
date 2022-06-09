package controllertest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import controller.GameController;
import controller.GameControllerInterface;
import model.world.FacadePlayerCollectionInterface;
import org.junit.Test;
import view.GameViewInterface;

/**
 * This class tests the controller class' edge cases.
 */
public class GameControllerTest {

  @Test
  public void testValidGameControllerConstructor() {
    StringBuilder log = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(log);
    FacadePlayerCollectionMockModel mockModel = new FacadePlayerCollectionMockModel(log);
    new GameController(viewMock, mockModel, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidGameControllerConstructorWhenGameViewIsNull() {
    GameViewInterface viewMock = null;
    FacadePlayerCollectionMockModel mockModel = null;
    new GameController(viewMock, mockModel, 2);
  }

  @Test
  public void testPlayGame() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.playGame();
    String expectedRes = "Make Move Called with value 4\n";
    assertEquals(expectedRes, modelLog.toString());
    expectedRes = "Make visible called\n";
    assertEquals(expectedRes, viewLog.toString());
  }

  @Test
  public void testValidMovePetAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(1);
    String expectedRes = "MovePet method called with value Armory\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "getActionInput method called with value title: Move Pet "
            + "msg: Select a space to move pet data: [Armory, Foyer]\n";
    assertEquals(expectedRes, viewLog.toString());
  }

  @Test
  public void testInvalidMovePetAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel =
            new FailingFacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(1);
    String expectedRes = "movePet method called with value Armory\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "displayError method called with value: Exception from movePet method\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testValidPickItemAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(2);
    String expectedRes = "pickItem method called with value Knife\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "getActionInput method called with value title: Pick Item "
            + "msg: Select an item data: [Knife, Pan]\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testInvalidPickItemAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel =
            new FailingFacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(2);
    String expectedRes = "pickItem method called with value Knife\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "displayError method called with value: Exception from pickItem method\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testValidLookAroundAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(4);
    String expectedRes = "lookAround method called";
    assertTrue(modelLog.toString().contains(expectedRes));
  }

  @Test
  public void testInvalidLookAroundAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel =
            new FailingFacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(4);
    String expectedRes = "lookAround method called\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "displayError method called with value: Exception from lookAround method\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testValidAttackAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(5);
    String expectedRes = "hitTarget method called with value Knife\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "getActionInput method called with value title: Attack Player "
            + "msg: Select an Item to attack data: [Knife, Pan]\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testInvalidAttackAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel =
            new FailingFacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(5);
    String expectedRes = "hitTarget method called with value Knife\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "displayError method called with value: Exception from hitTarget method\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testValidAddPlayerAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(6);
    String expectedRes = "createPlayer method called with value: Mike Human Armory 5\n";
    assertEquals(expectedRes, modelLog.toString());
    expectedRes = "PlayerName: Mike RoomName: Armory CarryLimit: 5\n";
    assertEquals(expectedRes, viewLog.toString());
  }

  @Test
  public void testInvalidAddPlayerAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel =
            new FailingFacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(6);
    String expectedRes = "createPlayer method called with value: Mike Human Armory 5\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "displayError method called with value: Exception from createPlayer method\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }

  @Test
  public void testValidMovePlayerAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel = new FacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(3, "Armory");
    String expectedRes = "movePlayer method called with value Armory\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "refresh method called\n";
    assertEquals(expectedRes, viewLog.toString());
  }

  @Test
  public void testInvalidMovePlayerAction() {
    StringBuilder modelLog = new StringBuilder();
    StringBuilder viewLog = new StringBuilder();
    GameViewInterface viewMock = new GameViewMock(viewLog);
    FacadePlayerCollectionInterface mockModel =
            new FailingFacadePlayerCollectionMockModel(modelLog);
    GameControllerInterface game = new GameController(viewMock, mockModel, 2);
    game.processMoves(3, "Armory");
    String expectedRes = "movePlayer method called with value Armory\n";
    assertTrue(modelLog.toString().contains(expectedRes));
    expectedRes = "displayError method called with value: Exception from movePlayer method\n";
    assertTrue(viewLog.toString().contains(expectedRes));
  }
}