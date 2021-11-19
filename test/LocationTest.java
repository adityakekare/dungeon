import org.junit.Before;
import org.junit.Test;

import model.dungeon.Connector;
import model.game.Game;
import model.game.GameImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class for testing the Location methods.
 */
public class LocationTest {

  Game game;

  @Before
  public void setUp() throws Exception {
    game = new GameImpl(4, 4, 0, true,
            30, "Aditya");
  }

  @Test
  public void getRoutes() {
    System.out.println(game.dumpDungeon());
    boolean[] expected1 = {false, true, false, true};

    assertEquals(new Connector(expected1).toString(),
            game.getDungeon().getGrid()[0][0].getRoutes().toString());
  }

  @Test
  public void isTunnel() {
    assertTrue(game.getDungeon().getGrid()[0][0].isTunnel());
  }
}