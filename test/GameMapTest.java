import org.junit.Before;
import org.junit.Test;

import model.dungeon.Connector;
import model.dungeon.GameMap;
import model.dungeon.Location;
import model.game.Game;
import model.game.GameImpl;
import model.inventory.InventoryType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Class for testing the GameMap methods.
 */
public class GameMapTest {

  Game game;
  Game game2;
  GameMap dungeon;
  Location[][] loc;

  @Before
  public void setUp() throws Exception {
    long seed = 3123124134141L;
    game2 = new GameImpl(4, 4, 0, false,
            50, "Aditya", seed);
  }

  @Test
  public void testAddTreasures() {
    game = new GameImpl(5, 6, 0, false,
            20, "Aditya");
    game.addTreasuresToDungeon();
    dungeon = game.getDungeon();
    loc = dungeon.getGrid();
    int width = loc[0].length;

    int count = 0;
    for (Location[] locations : loc) {
      for (int j = 0; j < width; j++) {
        if (locations[j].contains(InventoryType.TREASURE)) {
          count += 1;
        }
      }
    }
    assertEquals(6, count);
  }

  @Test
  public void testAddWeapons() {
    game = new GameImpl(5, 6, 0, false,
            40, "Aditya");
    game.addWeaponsToDungeon();
    dungeon = game.getDungeon();
    loc = dungeon.getGrid();
    int height = loc.length;
    int width = loc[0].length;

    int count = 0;
    for (Location[] locations : loc) {
      for (int j = 0; j < width; j++) {
        if (locations[j].contains(InventoryType.WEAPON)) {
          count += 1;
        }
      }
    }
    assertEquals(12, count);
  }

  @Test
  public void testGetPossibleMoves() {
    game = new GameImpl(8, 9, 0, true,
            40, "Aditya");

    String expected = "";
    Location[][] locations = game.getDungeon().getGrid();
    int x = game.getDungeon().getPosition(game.getDungeon().getPlayerLocation()).getXpos();
    int y = game.getDungeon().getPosition(game.getDungeon().getPlayerLocation()).getYpos();
    Connector routes = locations[x][y].getRoutes();
    if (routes.isNorth()) {
      expected += "N, ";
    }
    if (routes.isSouth()) {
      expected += "S, ";
    }
    if (routes.isEast()) {
      expected += "E, ";
    }
    if (routes.isWest()) {
      expected += "W, ";
    }

    expected = expected.replaceAll(", ", "");

    assertEquals(expected, game.getMovesForPlayer());
  }

  @Test
  public void playerMoveNorth() {
    int width = 4;
    int height = 5;
    int loc = 0;
    Game game = null;

    while (true) {
      game = new GameImpl(height, width, 0, false,
              30, "Aditya");
      game.addTreasuresToDungeon();
      loc = game.getDungeon().getPlayerLocation();
      try {
        game.move("n");
        break;
      } catch (IllegalArgumentException iae) {
        continue;
      }
    }

    assertEquals(loc - width, game.getDungeon().getPlayerLocation());
  }

  @Test
  public void playerMoveSouth() {
    int width = 7;
    int height = 7;
    int loc = 0;
    Game game = null;

    while (true) {
      game = new GameImpl(height, width, 0, false,
              30, "Aditya");
      game.addTreasuresToDungeon();
      loc = game.getDungeon().getPlayerLocation();
      try {
        game.move("s");
        break;
      } catch (IllegalArgumentException iae) {
        continue;
      }
    }


    assertEquals(loc + width, game.getDungeon().getPlayerLocation());
  }

  @Test
  public void playerMoveEast() {
    int width = 7;
    int height = 7;
    int loc = 0;
    Game game = null;

    while (true) {
      game = new GameImpl(height, width, 0, false,
              30, "Aditya");
      game.addTreasuresToDungeon();
      loc = game.getDungeon().getPlayerLocation();
      try {
        game.move("e");
        break;
      } catch (IllegalArgumentException iae) {
        continue;
      }
    }

    assertEquals(loc + 1, game.getDungeon().getPlayerLocation());
  }

  @Test
  public void playerMoveWest() {
    int width = 8;
    int height = 7;
    int loc = 0;
    Game game = null;

    while (true) {
      game = new GameImpl(height, width, 0, false,
              30, "Aditya");
      game.addTreasuresToDungeon();
      loc = game.getDungeon().getPlayerLocation();
      try {
        game.move("w");
        break;
      } catch (IllegalArgumentException iae) {
        continue;
      }
    }

    assertEquals(loc - 1, game.getDungeon().getPlayerLocation());
  }

  @Test
  public void getPlayerStatus() {
    int width = 8;
    int height = 7;
    Game game = new GameImpl(height, width, 0, false,
            30, "Aditya");
    assertEquals("Treasures collected: None\n" +
            "Arrows remaining: 3", game.getStatusPlayer());
  }

  @Test
  public void getLocationStatus() {
    int width = 8;
    int height = 7;
    Game game = new GameImpl(height, width, 0, false,
            30, "Aditya");
    assertEquals("You are in a cave.\n" +
                    "Doors lead to the ",
            game.getStatusLocation());
  }

  @Test
  public void checkIfPlayerAtEndLocation() {
    int width = 8;
    int height = 7;
    Game game = new GameImpl(height, width, 0, false,
            30, "Aditya");
    assertFalse(game.getDungeon().checkIfPlayerAtEndLocation());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testShoot() {
    game2.addMonstersToDungeon(5);
    assertEquals("You shoot an arrow into the darkness",
            game2.shootArrow("s", "1"));

    assertEquals("You shoot an arrow into the darkness",
            game2.shootArrow("w", "2"));
  }

}