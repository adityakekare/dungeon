/**
 * Class for testing the GameMap methods.
 */
public class GameMapTest {

//  Game model.game;
//  GameMap model.dungeon;
//  Location[][] loc;
//
//  @Before
//  public void setUp() throws Exception {
//    model.game = new GameImpl(4, 4, 0, false,
//            25, "Aditya");
//  }
//
//  @Test
//  public void addTreasures() {
//    model.game.addTreasuresToDungeon();
//    model.dungeon = model.game.getDungeon();
//    loc = model.dungeon.getGrid();
//    int count = 0;
//    for (int i = 0; i < 4; i++) {
//      for (int j = 0; j < 4; j++) {
//        if (!loc[i][j].isTunnel()) {
//          if (!(((Cave) loc[i][j]).getTreasures().isEmpty())) {
//            count += 1;
//          }
//        }
//      }
//    }
//
//    assertEquals(4, count);
//  }
//
//  @Test
//  public void playerMoveNorth() {
//    Game model.game = new GameImpl(4, 4, 0, false,
//            30, "Aditya");
//    model.game.addTreasuresToDungeon();
//    model.game.getStatus();
//    model.game.dumpDungeon();
//    model.game.getMovesForPlayer();
//    int loc = model.game.getDungeon().getPlayer().getLocation();
//    model.game.move("n");
//
//    assertEquals(loc - 4, model.game.getDungeon().getPlayer().getLocation());
//  }
//
//  @Test
//  public void playerMoveSouth() {
//    Game model.game = new GameImpl(4, 4, 0, false,
//            30, "Aditya");
//    model.game.addTreasuresToDungeon();
//    model.game.getStatus();
//    model.game.dumpDungeon();
//    model.game.getMovesForPlayer();
//    int loc = model.game.getDungeon().getPlayer().getLocation();
//    model.game.move("s");
//
//    assertEquals(loc + 4, model.game.getDungeon().getPlayer().getLocation());
//  }
//
//  @Test
//  public void playerMoveEast() {
//    Game model.game = new GameImpl(4, 4, 0, false,
//            30, "Aditya");
//    model.game.addTreasuresToDungeon();
//    model.game.getStatus();
//    model.game.dumpDungeon();
//    model.game.getMovesForPlayer();
//    int loc = model.game.getDungeon().getPlayer().getLocation();
//    model.game.move("e");
//
//    assertEquals(loc + 1, model.game.getDungeon().getPlayer().getLocation());
//  }
//
//  @Test
//  public void playerMoveWest() {
//    Game model.game = new GameImpl(4, 4, 0, false,
//            30, "Aditya");
//    model.game.addTreasuresToDungeon();
//    model.game.getStatus();
//    model.game.dumpDungeon();
//    model.game.getMovesForPlayer();
//    int loc = model.game.getDungeon().getPlayer().getLocation();
//    model.game.move("w");
//
//    assertEquals(loc - 1, model.game.getDungeon().getPlayer().getLocation());
//  }
//
//  @Test
//  public void getPlayerStatus() {
//    assertEquals("Player name: Aditya Location id: 4\n" +
//            "Treasures collected:None", model.game.getDungeon().getPlayer().toString());
//  }
//
//  @Test
//  public void getLocationStatus() {
//    assertEquals("Location id: 3 Location type: Tunnel",
//            model.game.getDungeon().getLocationStatus());
//  }
//
//  @Test
//  public void checkIfPlayerAtEndLocation() {
//    assertFalse(model.game.getDungeon().checkIfPlayerAtEndLocation());
//  }

}