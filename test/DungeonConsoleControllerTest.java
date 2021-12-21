import controller.DungeonConsoleController;
import controller.DungeonController;

import java.io.InputStreamReader;
import java.io.StringReader;

import model.game.Game;
import model.game.GameImpl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing the methods of the dungeon console controller. The controller uses a
 * deterministic model to perform the tests.
 */
public class DungeonConsoleControllerTest {

  Game model;
  Game model2;

  @Before
  public void setUp() throws Exception {
    long seed = 3123124134141L;
    model = new GameImpl(4, 4, 0, false,
            5, "Aditya", seed);
    long seed1 = 214143241151431L;
    model2 = new GameImpl(4, 4, 0, false,
            50, "Aditya", seed);
    model.addTreasuresToDungeon();
    model.addWeaponsToDungeon();
    model.addMonstersToDungeon(1);

    model2.addTreasuresToDungeon();
    model2.addWeaponsToDungeon();
    model2.addMonstersToDungeon(1);
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    DungeonController c = new DungeonConsoleController(input, gameLog);
    c.startGame(model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(null);
  }

  @Test
  public void testInvalidMpsInput() {
    StringReader input = new StringReader("sdfsf\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Please enter a valid input. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testInvalidMoveDirectionInput() {
    StringReader input = new StringReader("m\ndawd\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "Invalid Input. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testCannotMoveDirectionInput() {
    StringReader input = new StringReader("m\ne\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No such path exists. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testInvalidShootDirectionInput() {
    StringReader input = new StringReader("s\nfafasda\n3\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "Invalid Input. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testInvalidShootDistanceInput() {
    StringReader input = new StringReader("s\ne\n6\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "Please enter an integer between 1-5. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testInvalidShootDistanceInput2() {
    StringReader input = new StringReader("s\ne\n-1\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "Please enter an integer between 1-5. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testInvalidPickInput() {
    StringReader input = new StringReader("p\nfsfasfds\n6\nq");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "What?(treasure or arrow) \n" +
            "Please enter a valid input. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Please enter a valid input. Try again.\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Quitting game...\n" +
            "\n" +
            "Game aborted.\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testCompleteGameValidInputsPlayerLose() {
    StringReader input = new StringReader("m\ns\nm\ns\nm\ns\nm\nw\nm\nw\nm\nn\nm\nn"
            + "\nm\nw\nm\nn\ne\n");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);
    model.dumpDungeon();

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, S, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "You smell something filthy nearby...\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, S, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "You smell something unbearably filthy nearby...\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "\n" +
            "Otyugh ate you for dinner tonight.\n" +
            "Better luck next time!\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testCompleteGameValidInputsPlayerWin() {
    StringReader input = new StringReader("m\ns\nm\ns\nm\ns\nm\nw\nm\nw\nm\nn\nm\nn"
            + "\nm\nw\ns\nn\n1\ns\nn\n1\nm\nn\ne\n");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model);
    model.dumpDungeon();

    String expected = "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, S, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "You smell something filthy nearby...\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, S, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "You smell something unbearably filthy nearby...\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "You hear a great howl in the distance\n" +
            "\n" +
            "You smell something unbearably filthy nearby...\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 2\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "You hear a great howl in the distance\n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 1\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected: None\n" +
            "Arrows remaining: 1\n" +
            "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, Shoot, or End (M-P-S-E)? \n" +
            "\n" +
            "Player wins!\n";

    assertEquals(expected, output.toString());
  }

  @Test
  public void testPickUpTreasureAndWeapons() {
    StringReader input = new StringReader("p\ntreasure\np\narrow\nm\ns\nm\ns\nm\ns\nm\nw\np\n" +
            "arrow\nm\nw\np\ntreasure\nm\nn\nm\nn\nm\nw\ns\nn\n1\ns\nn\n1\nm\nn\ne\n");
    Appendable output = new StringBuilder();
    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(model2);
    model2.dumpDungeon();

    String expected = "You are in a cave.\n" +
            "Collectibles - Treasure: SAPPHIRE, DIAMOND  Arrows: 1\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "What?(treasure or arrow) \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Collectibles - Arrows: 1\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "What?(treasure or arrow) \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 4\n" +
            "You are in a cave.\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 4\n" +
            "You are in a tunnel which continues from N, S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 4\n" +
            "You are in a tunnel which continues from N, S\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 4\n" +
            "You are in a tunnel which continues from N, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 4\n" +
            "You are in a tunnel.\n" +
            "Collectibles - Arrows: 1\n" +
            "The tunnel continues from E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "What?(treasure or arrow) \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 5\n" +
            "You are in a tunnel which continues from E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1\n" +
            "Arrows remaining: 5\n" +
            "You are in a cave.\n" +
            "Collectibles - Treasure: RUBY\n" +
            "Doors lead to the N, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "What?(treasure or arrow) \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 5\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 5\n" +
            "You are in a cave.\n" +
            "Collectibles - Treasure: SAPPHIRE\n" +
            "Doors lead to the N, S, E, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "You smell something filthy nearby...\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 5\n" +
            "You are in a cave.\n" +
            "Doors lead to the N, S, W\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "You smell something unbearably filthy nearby...\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 5\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "You hear a great howl in the distance\n" +
            "\n" +
            "You smell something unbearably filthy nearby...\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 4\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "No. of caves (1-5)? \n" +
            "You hear a great howl in the distance\n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 3\n" +
            "You are in a tunnel which continues from N, E\n" +
            "Move, Pickup, or Shoot (M-P-S)? \n" +
            "Where? \n" +
            "\n" +
            "Treasures collected:  SAPPHIRE: 1 DIAMOND: 1 RUBY: 1\n" +
            "Arrows remaining: 3\n" +
            "You are in a cave.\n" +
            "Collectibles - Treasure: RUBY  Arrows: 1\n" +
            "Doors lead to the S\n" +
            "Move, Pickup, Shoot, or End (M-P-S-E)? \n" +
            "\n" +
            "Player wins!\n";

    assertEquals(expected, output.toString());
  }
}