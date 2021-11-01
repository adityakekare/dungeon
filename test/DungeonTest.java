import org.junit.Test;

import dungeon.Dungeon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DungeonTest {

  @Test
  public void testToString() {
//    Treasure treasure = Treasure.DIAMOND;
//    System.out.println(treasure.toString());
//
    Dungeon dungeon = new Dungeon(4, 4, 0, true,
            40, "Aditya");
    System.out.println(dungeon.toString());
//    System.out.println(dungeon.toString());

//    Path path1 = new Path(1, 2);
//    Path path2 = new Path(2, 1);
//
//    assertTrue(path1.equals(path2));
  }

  @Test
  public void testLogic(){
    int height = 3;
    int width = 4;
    int id = 1;
    int row = (id - (id % width))/ width;
    int col = id % width;

    assertEquals(row, 0);
    assertEquals(col, 1);
  }
}