import org.junit.Before;
import org.junit.Test;

import model.game.Game;
import model.game.GameImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Class for testing Game methods.
 */
public class GameTest {

  Game game;

  @Before
  public void setUp() throws Exception {
    game = new GameImpl(4, 4, 0, false,
            30, "Aditya");
  }

  @Test
  public void move() {
  }

  @Test
  public void dumpDungeon() {
    assertEquals("T--C--C--T \n" +
            " |  |  |  | \n" +
            " |  |  |  | \n" +
            " T  T  T  T \n" +
            " |  |  |  | \n" +
            " |  |  |  | \n" +
            " T  P  E  T \n" +
            " |  |  |  | \n" +
            " |  |  |  | \n" +
            " C  C  C  C", game.dumpDungeon());
  }

  @Test
  public void getMovesForPlayer() {
  }

  @Test
  public void getStatus() {
//    assertEquals("Player name: Aditya Location id: 9\n" +
//            "Treasures collected:None\n" +
//            "Location id: 9 Location type: Tunnel", model.game.getStatus());
  }

  @Test
  public void isEnd() {
    assertFalse(game.isEnd());
  }

}