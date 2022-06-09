package modeltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.player.HumanStrategy;
import model.player.PlayerType;
import model.player.PlayerWrapper;
import model.player.PlayerWrapperInterface;
import model.targetpet.TargetPetWrapper;
import model.targetpet.TargetPetWrapperInterface;
import model.world.Segregator;
import model.world.World;
import model.world.WorldInterface;
import org.junit.Before;
import org.junit.Test;

/**
 * PlayerWrapperTest class tests the PlayerWrapper class
 * by validating the functionalities of moving a player,
 * picking an item, looking around and displaying information
 * of space and person.
 */
public class PlayerWrapperTest {

  private static WorldInterface world;
  private static PlayerWrapperInterface playerWrapper;
  private static TargetPetWrapperInterface targetPetWrapper;

  /**
   * Before the tests are performed the setup
   * class will create a world and create spaces
   * and items so that player wrapper can be
   * tested with a particular player.
   */
  @Before
  public void setup() {
    List<String> inputList = new ArrayList<>();
    inputList.add("36 30 Doctor Lucky's Mansion");
    world = World.createWorld(inputList);
    List<String> spaceInput = new ArrayList<>();
    spaceInput.add("22 19 23 26 Armory");
    spaceInput.add("12 11 21 20 Dining Hall");
    spaceInput.add("16 21 21 28 Billiard Room");
    spaceInput.add("16  3 21 10 Kitchen");
    spaceInput.add("8 11 11 20 Tennessee Room");
    Map<Integer, List<String>> itemsMap = new HashMap<>();
    itemsMap.put(0, List.of("3", "Revolver"));
    itemsMap.put(1, List.of("4", "Chain Saw"));
    itemsMap.put(2, List.of("2", "Billiard Cue"));
    itemsMap.put(3, List.of("3", "Knife"));
    world.createSpacesItems(spaceInput, itemsMap);
    targetPetWrapper = new TargetPetWrapper(List.of("50 Doctor Lucky"),
            List.of("Fortune the Cat"), world.getSpaceNames());
    playerWrapper = new PlayerWrapper(world, targetPetWrapper);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidPlayerWrapper() {
    PlayerWrapperInterface playerWrapper;
    playerWrapper = new PlayerWrapper(null, null);
  }


  @Test(expected = IllegalArgumentException.class)
  public void createNullPlayerName() {

    //Null player name
    playerWrapper.createPlayer(null, PlayerType.HUMAN, "Armory", 10,
            new HumanStrategy());

  }


  @Test(expected = IllegalArgumentException.class)
  public void createNullPlayerType() {

    //Null player type
    playerWrapper.createPlayer("Jack", null, "Armory", 5, new HumanStrategy());


  }

  @Test(expected = IllegalArgumentException.class)
  public void createNullSpaceName() {

    //Null space name
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, null, 5,
            new HumanStrategy());

  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidSpaceName() {

    //Space not found
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "InvalidSpace", 5,
            new HumanStrategy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createPlayerInvalidItemCapacity() {
    //Invalid Item capacity
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "InvalidSpace", -2,
            new HumanStrategy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void createDuplicatePlayerName() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Kitchen", 2,
            new HumanStrategy());
  }

  @Test
  public void createPlayer() {
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory",
            5, new HumanStrategy());
  }

  @Test
  public void testTurnChangeRepeat() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Billiard Room", 3,
            new HumanStrategy());
    playerWrapper.createPlayer("Mike", PlayerType.HUMAN, "Dining Hall", 3,
            new HumanStrategy());

    //Verify that the first Player to take turn is Jhon
    String expected = "Jhon";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

    //Take a turn
    playerWrapper.lookAround();

    //Verify that the Secong Player to take turn is Jack
    expected = "Jack";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

    //Take a turn
    playerWrapper.lookAround();

    //Verify that the Secong Player to take turn is Mike
    expected = "Mike";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

    //Take a turn
    playerWrapper.lookAround();

    //Verify that the counter should move back to Jhon
    expected = "Jhon";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMove() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Verify the neighbors of the current player
    List<String> neighbors = new ArrayList<>();
    Collections.addAll(neighbors, "Dining Hall", "Billiard Room");
    assertEquals(neighbors, playerWrapper.getCurrentPlayerNeighbors());

    //There is only one neighbor for the current player (i.e. Dining Hall)
    //Try to move player to different location than neighbor
    playerWrapper.movePlayer("Carriage House");
  }

  @Test
  public void testValidMove() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Verify the neighbors of the current player
    List<String> neighbors = new ArrayList<>();
    Collections.addAll(neighbors, "Dining Hall", "Billiard Room");
    assertEquals(neighbors, playerWrapper.getCurrentPlayerNeighbors());

    playerWrapper.movePlayer("Dining Hall");
    String expected = "Space name: Dining Hall";
    assertTrue(playerWrapper.getPlayerDetails(playerWrapper.getCurrentPlayerName())
            .contains(expected));
  }

  @Test
  public void testSpaceRemPlayerAfterValidMove() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Verify that Jhon is in Armory
    assertTrue(world.getSpaceObject("Armory").getPlayers().contains("Jhon"));

    //Moves to its neighbor
    playerWrapper.movePlayer("Dining Hall");

    //Verify that Jhon has been removed from Armory
    assertTrue(!world.getSpaceObject("Armory").getPlayers().contains("Jhon"));

    //Verifyt hat Jhon has been moved to Dining Hall
    assertTrue(world.getSpaceObject("Dining Hall").getPlayers().contains("Jhon"));
  }

  @Test
  public void testTurnChangeAfterValidMove() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Billiard Room", 3,
            new HumanStrategy());

    //Jhon's turn
    playerWrapper.movePlayer("Dining Hall");

    //Turn should change to Jack
    String expected = "Jack";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());
  }

  @Test
  public void testTargetMoveAfterValidMove() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Check target details before move
    //Initial location of target is 3 due to other tests
    String expected = "Armory";
    assertTrue(targetPetWrapper.getTargetDetails().contains(expected));

    //Jhon's turn
    playerWrapper.movePlayer("Dining Hall");


    //Check target details after move (Goes back to 0 since there are only 4 spaces in the world)
    //Target sets space location as 0th indexed list of spaces
    expected = "Dining Hall";
    assertTrue(targetPetWrapper.getTargetDetails().contains(expected));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testPickInvalidItem() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Verify the item list in the player's space
    List<String> expected = List.of("Revolver");
    assertEquals(expected, playerWrapper.getPickableItemNames());

    //Pick an invalid item
    playerWrapper.pickItem("Chain Saw");
  }


  @Test
  public void testPickValidItem() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Verify the item list in the player's space
    List<String> expected = List.of("Revolver");
    assertEquals(expected, playerWrapper.getPickableItemNames());

    //Pick an invalid item
    playerWrapper.pickItem("Revolver");

    String expectedValue = "Item name: 'Revolver'";
    assertTrue(playerWrapper.getPlayerDetails(playerWrapper
            .getCurrentPlayerName()).contains(expectedValue));
  }

  @Test
  public void testSpaceRemItemAfterValidPick() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Dining Hall", 3,
            new HumanStrategy());

    //Verify that Space Armory has Revolver
    assertTrue(world.getSpaceObject("Dining Hall")
            .getItemNames().contains("Chain Saw"));

    //Player picks item
    playerWrapper.pickItem("Chain Saw");

    //Verify that player picks item
    assertTrue(playerWrapper.getPlayerDetails("Jhon").contains("Chain Saw"));

    //Verify that item has been removed from the Space Armory
    assertTrue(!world.getSpaceObject("Dining Hall").getItemNames().contains("Chain Saw"));

  }

  @Test
  public void testTurnChangePickItem() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Billiard Room", 3,
            new HumanStrategy());
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Verify move before pick item
    String expected = "Jhon";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

    //Pick an invalid item
    playerWrapper.pickItem("Billiard Cue");

    //Verify move after pick item
    expected = "Jack";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());
  }


  @Test
  public void testTargetMovePickItem() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Kitchen", 3,
            new HumanStrategy());

    //Check target details before move
    //Initial location of target is 3 due to other tests
    String expected = "Armory";
    assertTrue(targetPetWrapper.getTargetSpaceName().contains(expected));

    //Take turn pick item
    playerWrapper.pickItem("Knife");


    //Check target details after move (Goes back to 0 since there are only 4 spaces in the world)
    //Target sets space location as 0th indexed list of spaces
    expected = "Dining Hall";
    assertTrue(targetPetWrapper.getTargetSpaceName().contains(expected));
  }

  @Test
  public void targetDetailsToString() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    String expected = "Target name: 'Doctor Lucky' Health: '50' Space Location: Armory";
    String actual = targetPetWrapper.getTargetDetails();
    assertEquals(expected, actual);
  }

  @Test
  public void testLookAround() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    String expected = "Jhon's current location:\n"
            + "Armory, Space Index: '0',[Revolver],Player(s): [Jhon],Pet Present?: false\n"
            + "Neighbors: [Dining Hall, Billiard Room]\n"
            + "\n"
            + "Neighboring spaces from Jhon's current location:\n"
            + "Dining Hall, Space Index: '1',[Chain Saw],Player(s): [],Pet Present?: false\n"
            + "Billiard Room, Space Index: '2',[Billiard Cue],Player(s): [],Pet Present?: false\n";
    String actual = playerWrapper.lookAround();
    assertEquals(expected, actual);


  }

  @Test
  public void testTurnChangeLookAround() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Billiard Room", 3,
            new HumanStrategy());

    //Verify move before pick item
    String expected = "Jhon";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

    //Take turn look around
    playerWrapper.lookAround();

    //Verify move after pick item
    expected = "Jack";
    assertEquals(expected, playerWrapper.getCurrentPlayerName());

  }

  @Test
  public void testTargetMoveLookAround() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Check target details before move
    String expected = "Armory";
    assertTrue(targetPetWrapper.getTargetDetails().contains(expected));

    //Take turn look Around
    playerWrapper.lookAround();


    //Check target details after move
    expected = "Dining Hall";
    assertTrue(targetPetWrapper.getTargetDetails().contains(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetInvalidPlayerDetails() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    //Invalid player name
    playerWrapper.getPlayerDetails("Invalid Player");

    //Null player name
    playerWrapper.getPlayerDetails(null);
  }


  @Test
  public void testGetPlayerDetails() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    String expected = "Name: Jhon\n"
            + "Type: Human\n"
            + "Space name: Armory, Space Index: 0\n"
            + "Item list: []\n"
            + "Bag capacity: 3";
    String actual = playerWrapper.getPlayerDetails("Jhon");

    assertEquals(expected, actual);
  }

  @Test
  public void testIsGameOver() {
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jhon", PlayerType.HUMAN, "Armory", 3,
            new HumanStrategy());

    int totalTurns = 3;

    //Check isGameOver should be false
    assertEquals(false, playerWrapper.isGameOver(totalTurns));

    //Take 3 turns
    playerWrapper.lookAround();
    playerWrapper.lookAround();
    playerWrapper.lookAround();

    //Check isGameOver
    assertEquals(true, playerWrapper.isGameOver(totalTurns));
  }


  @Test
  public void targetMoveDetails() {
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    //Details that will be displayed to user
    String expected = "Target name: 'Doctor Lucky' Health: '50' Space Location: Armory";
    String actual = targetPetWrapper.getTargetDetails();
    assertEquals(expected, actual);

    //Player takes turn and target changes its position
    playerWrapper.lookAround();

    //Details to be displayed after each turn
    expected = "Target name: 'Doctor Lucky' Health: '50' Space Location: Dining Hall";
    actual = targetPetWrapper.getTargetDetails();
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerSeeInvalidNeighborSpace() {
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    playerWrapper.createPlayer("John", PlayerType.HUMAN, "Kitchen", 4,
            new HumanStrategy());

    //Current Player is Jack who is in Armory
    //and players in Kitchen should not be seen from Armory
    //since it is not a neighboring space
    playerWrapper.getPlayerDetails("John");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayerWithPet() {
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Billiard Room", 4,
            new HumanStrategy());
    playerWrapper.createPlayer("John", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    //Since this is the first chance pet will be
    //in Armory and hence Jack who is in Billiard Room should
    //not be able to see John who is in Armory with the pet.

    playerWrapper.getPlayerDetails("John");
  }

  @Test
  public void testPlayerSeeValid() {
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    playerWrapper.createPlayer("John", PlayerType.HUMAN, "Billiard Room", 4,
            new HumanStrategy());

    String expected = "Name: John\n"
            + "Type: Human\n"
            + "Space name: Billiard Room, Space Index: 2\n"
            + "Item list: []\n"
            + "Bag capacity: 4";
    String actual = playerWrapper.getPlayerDetails("John");
    assertEquals(expected, actual);
  }

  @Test
  public void createPetFromFileParsing() {
    Segregator seg = new Segregator("./res/test.txt");
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(seg.getTarget(), seg.getPet(), world.getSpaceNames());

    //Since this is the first turn pet will be in Armory

    String expected = "Armory";
    String actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createInvalidPet() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"), null, world.getSpaceNames());
  }

  @Test
  public void createValidPet() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    String expected = "Armory";
    String actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);
  }

  @Test
  public void checkPetEntry() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    //Pet should enter in Armory with the Target since Armory
    //is at 0th location
    String expected = "Armory";
    String actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);
  }

  @Test
  public void checkPetNeighborInvisible() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    //Currently, the pet is at Armory and Jack is also at Armory,
    //so he is able to see all his neighbors
    String expected = "[Dining Hall, Billiard Room]";
    String actual = playerWrapper.getCurrentPlayerNeighbors().toString();
    assertEquals(expected, actual);

    playerWrapper.lookAround();
    //After the above turn pet should move to Billiard Room according to the DFS
    //Hence, now from the current space of player i.e. Armory only Dining Hall should be visible
    expected = "[Dining Hall]";
    actual = playerWrapper.getCurrentPlayerNeighbors().toString();
    assertEquals(expected, actual);

    playerWrapper.lookAround();
    //Verifying one more time, after the above turn pet will move to Dining Hall according to
    //the DFS algorithm and hence now only Billiard Room should be visible from armory.
    expected = "[Billiard Room]";
    actual = playerWrapper.getCurrentPlayerNeighbors().toString();
    assertEquals(expected, actual);
  }

  @Test
  public void checkPetMoveTurn() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    String expected = "Armory";
    String actual = targetPetWrapper.getTargetSpaceName();
    assertEquals(expected, actual);

    playerWrapper.movePet("Billiard Room");
    //Since the movePet move is called Target should move one more space further and
    //turn should be considered.
    expected = "Dining Hall";
    actual = targetPetWrapper.getTargetSpaceName();
    assertEquals(expected, actual);
  }


  @Test
  public void movePet() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    String expected = "Armory";
    String actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);

    playerWrapper.movePet("Billiard Room");
    expected = "Billiard Room";
    actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);
  }

  @Test(expected = IllegalArgumentException.class)
  public void movePetInvalidSpace() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    playerWrapper.movePet("Invalid Space");
  }

  @Test
  public void petMoveDfs() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    //After each turn pet should move in DFS
    //Starts with Armory
    String expected = "Armory";
    String actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);

    playerWrapper.lookAround();
    expected = "Billiard Room";
    actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);


    playerWrapper.lookAround();
    expected = "Dining Hall";
    actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);

    playerWrapper.lookAround();
    expected = "Kitchen";
    actual = targetPetWrapper.getPetSpaceName();
    assertEquals(expected, actual);

  }

  @Test(expected = IllegalStateException.class)
  public void targetHitInvalidTargetSpace() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Billiard Room", 4,
            new HumanStrategy());
    playerWrapper.hitTarget("poke");
  }

  @Test(expected = IllegalArgumentException.class)
  public void targetHitInvalidItem() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    playerWrapper.hitTarget("Invalid Item");
  }

  @Test(expected = IllegalArgumentException.class)
  public void targetHitNullItem() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    playerWrapper.hitTarget(null);
  }

  @Test
  public void targetHitPokingInEyeDodged() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    playerWrapper.createPlayer("John", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());
    String expected = "Poking in eye dodged by another player! ";
    String actual = playerWrapper.hitTarget("poke");
    assertEquals(expected, actual);
  }

  @Test
  public void targetHitItemDodged() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Kitchen", 4,
            new HumanStrategy());
    playerWrapper.createPlayer("John", PlayerType.HUMAN, "Kitchen", 4,
            new HumanStrategy());

    playerWrapper.lookAround();

    playerWrapper.pickItem("Knife");

    String expected = "Item name: 'Knife'";
    String actual = playerWrapper.getPlayerDetails("John");
    assertTrue(actual.contains(expected));

    playerWrapper.lookAround();


    expected = "Hit move dodged by another player. Item dropped! ";
    actual = playerWrapper.hitTarget("Knife");
    assertEquals(expected, actual);

    //Item Dropped
    expected = "Item list: []";
    actual = playerWrapper.getPlayerDetails("John");
    assertTrue(actual.contains(expected));

  }

  @Test
  public void validTargetHit() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Dining Hall", 4,
            new HumanStrategy());

    playerWrapper.pickItem("Chain Saw");
    String expectedString = "Item name: 'Chain Saw'";
    String actualString = playerWrapper.getPlayerDetails("Jack");
    assertTrue(actualString.contains(expectedString));
    int expected = 50;
    int actual = targetPetWrapper.getTargetHealth();
    assertEquals(expected, actual);
    expectedString = "Hit move successful! ";
    actualString = playerWrapper.hitTarget("Chain Saw");
    assertEquals(expectedString, actualString);
    expected = 46;
    actual = targetPetWrapper.getTargetHealth();
    assertEquals(expected, actual);
    //Item dropped from player
    expectedString = "Item list: []";
    actualString = playerWrapper.getPlayerDetails("Jack");
    assertTrue(actualString.contains(expectedString));

  }


  @Test
  public void validTargetHitPokeEye() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("50 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    int expected = 50;
    int actual = targetPetWrapper.getTargetHealth();
    assertEquals(expected, actual);
    String expectedString = "Poked in eye! ";
    String actualString = playerWrapper.hitTarget("poke");
    assertEquals(expectedString, actualString);
    expected = 49;
    actual = targetPetWrapper.getTargetHealth();
    assertEquals(expected, actual);

  }

  @Test
  public void gameOverHumanPlayerWin() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("3 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Dining Hall", 4,
            new HumanStrategy());

    Boolean expected = false;
    Boolean actual = playerWrapper.isGameOver(10);
    assertEquals(expected, actual);

    playerWrapper.pickItem("Chain Saw");
    //Health of target is set to 3 and player is hitting with damage 4
    //Hence player will win.
    playerWrapper.hitTarget("Chain Saw");
    expected = true;
    actual = playerWrapper.isGameOver(10);
    assertEquals(expected, actual);

  }

  @Test
  public void gameOverComputerPlayerWin() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("3 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.COMPUTER, "Dining Hall", 4,
            new HumanStrategy());

    Boolean expected = false;
    Boolean actual = playerWrapper.isGameOver(10);
    assertEquals(expected, actual);

    playerWrapper.pickItem("Chain Saw");
    //Health of target is set to 3 and player is hitting with damage 4
    //Hence player will win.
    playerWrapper.hitTarget("Chain Saw");
    expected = true;
    actual = playerWrapper.isGameOver(10);
    assertEquals(expected, actual);
  }

  @Test
  public void getPlayerItemListDamageSort() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("3 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    playerWrapper.pickItem("Revolver");
    playerWrapper.movePlayer("Dining Hall");
    playerWrapper.pickItem("Chain Saw");
    //The list will be fetched sorted in descending manner
    //as per the item damage from which computer player
    //will always pick the first item (0th index)
    // to hit the target with maximum damage.
    String expected = "[Chain Saw, Revolver]";
    String actual = playerWrapper.getPlayerObject(playerWrapper.getCurrentPlayerName())
            .getPlayerItemList().toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testComputerPlayerHitTarget() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("3 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.COMPUTER, "Armory", 4,
            new HumanStrategy());

    //Computer player will pick the Hit Target move when target is in same room
    assertEquals(true, playerWrapper.checkComputerTargetHit());
  }

  @Test
  public void testComputerPlayerHitTargetInvalid() {
    TargetPetWrapperInterface targetPetWrapper =
            new TargetPetWrapper(Arrays.asList("3 Doctor Lucky"),
                    Arrays.asList("Fortune the Cat"), world.getSpaceNames());
    PlayerWrapperInterface playerWrapper = new PlayerWrapper(world, targetPetWrapper);
    playerWrapper.createPlayer("Jack", PlayerType.COMPUTER, "Armory", 4,
            new HumanStrategy());
    playerWrapper.createPlayer("Mike", PlayerType.HUMAN, "Armory", 4,
            new HumanStrategy());

    //Computer player will not pick the Hit Target move when target is in same room
    //since there is another player in the room and hit move will be dodged.
    assertEquals(false, playerWrapper.checkComputerTargetHit());
  }
}