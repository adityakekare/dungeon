import org.junit.Before;
import org.junit.Test;

import model.dungeon.Location;
import model.game.Game;
import model.game.GameImpl;
import model.inventory.InventoryType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing Game methods.
 */
public class GameTest {

  Game game;

  @Before
  public void setUp() throws Exception {
    long seed = 3123124134141L;
    game = new GameImpl(4, 4, 0, false,
            50, "Aditya", seed);
  }

  @Test
  public void testMove() {
    game.move("s");
    game.move("s");
    game.move("s");
    game.move("w");
    game.move("w");
    game.move("n");
    game.move("e");

    assertEquals(10, game.getDungeon().getPlayerLocation());
  }

  @Test
  public void testDumpDungeon() {
    assertEquals("\n" +
            "            \n" +
            " E  C  C  P \n" +
            " |  |  |  | \n" +
            " |  |  |  | \n" +
            " T--C  T  T \n" +
            "    |  |  | \n" +
            "    |  |  | \n" +
            " C--C--T  T \n" +
            "    |     | \n" +
            "    |     | \n" +
            " C--C--T--T \n" +
            "            \n", game.dumpDungeon());
  }

  @Test
  public void testGetMovesForPlayer() {
    assertEquals("S", game.getMovesForPlayer());

    game.move("S");

    assertEquals("N, S", game.getMovesForPlayer());

    game.move("s");
    game.move("s");
    game.move("w");
    game.move("w");
    game.move("n");

    assertEquals("N, S, E, W", game.getMovesForPlayer());

  }

  @Test
  public void testGetStatusPlayer() {
    assertEquals("Treasures collected: None\n" +
            "Arrows remaining: 3", game.getStatusPlayer());

    game.addTreasuresToDungeon();
    game.addWeaponsToDungeon();
    game.move("S");
    game.move("S");
    game.move("S");
    game.move("W");
    game.move("W");
    game.move("N");
    game.move("N");
    game.move("W");
    game.move("N");
    game.pickupWeapon();
    game.pickUpTreasure();

    assertEquals("Treasures collected:  RUBY: 1\n" +
            "Arrows remaining: 4", game.getStatusPlayer());
  }

  @Test
  public void getStatusLocation() {
    assertEquals("You are in a cave.\n" +
            "Doors lead to the ", game.getStatusLocation());
    game.move("s");
    assertEquals("You are in a tunnel which continues from ", game.getStatusLocation());
  }

  @Test
  public void isEnd() {
    assertFalse(game.isEnd());

    game.move("S");
    game.move("S");
    game.move("S");
    game.move("W");
    game.move("W");
    game.move("N");
    game.move("N");
    game.move("W");
    game.move("N");

    assertTrue(game.isEnd());
  }

  @Test
  public void testGetMonsterSmell() {
    game.addMonstersToDungeon(8);
    assertEquals("", game.getMonsterSmell());

    game.move("S");
    game.move("S");
    game.move("S");

    assertEquals("You smell something filthy nearby...", game.getMonsterSmell());

    game.move("W");

    assertEquals("You smell something unbearably filthy nearby...", game.getMonsterSmell());
  }

  @Test
  public void testShootArrow() {
    game.addMonstersToDungeon(8);
    assertEquals("You hear a great howl in the distance",
            game.shootArrow("S", "2"));
    assertEquals("You hear a great howl in the distance",
            game.shootArrow("S", "2"));

    game.move("S");
    game.move("S");
    game.move("S");
    game.move("w");

    assertEquals("You shoot an arrow into the darkness",
            game.shootArrow("E", "2"));

    game.move("w");
    game.move("n");
  }

  @Test
  public void testPlayerAlive() {
    game.addMonstersToDungeon(8);

    assertTrue(game.isPlayerAlive());

    game.move("S");
    game.move("S");
    game.move("S");
    game.move("W");
    game.move("W");

    assertFalse(game.isPlayerAlive());
  }

  @Test
  public void testPickupTreasure() {
    game.addTreasuresToDungeon();
    game.move("s");
    game.pickUpTreasure();
    assertEquals("Treasures collected: None\n" +
            "Arrows remaining: 3", game.getStatusPlayer());
  }

  @Test
  public void testPickupWeapon() {
    game.addWeaponsToDungeon();
    game.move("s");
    game.pickupWeapon();
    assertEquals("Treasures collected: None\n" +
            "Arrows remaining: 3", game.getStatusPlayer());

    game.move("S");
    game.move("S");
    game.move("W");
    game.move("W");
    game.move("N");
    game.move("N");
    game.move("W");
    game.move("N");
    game.pickupWeapon();

    assertEquals("Treasures collected: None\n" +
            "Arrows remaining: 4", game.getStatusPlayer());
  }

  @Test
  public void testAddTreasures() {
    game.addTreasuresToDungeon();
    Location[][] loc = game.getDungeon().getGrid();
    int count = 0;

    for (Location[] locations : loc) {
      for (int j = 0; j < 4; j++) {
        if (locations[j].contains(InventoryType.TREASURE)) {
          count += 1;
        }
      }
    }
    assertEquals(8, count);
  }

  @Test
  public void testAddMonsters() {
    game.addMonstersToDungeon(6);
    Location[][] loc = game.getDungeon().getGrid();
    int count = 0;

    for (Location[] locations : loc) {
      for (int j = 0; j < 4; j++) {
        if (locations[j].contains(InventoryType.MONSTER)) {
          count += 1;
        }
      }
    }
    assertEquals(6, count);
  }

  @Test
  public void testAddWeapons() {
    game.addWeaponsToDungeon();
    Location[][] loc = game.getDungeon().getGrid();
    int count = 0;

    for (Location[] locations : loc) {
      for (int j = 0; j < 4; j++) {
        if (locations[j].contains(InventoryType.WEAPON)) {
          count += 1;
        }
      }
    }
    assertEquals(8, count);
  }

}