import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing the Dungeon methods.
 */
public class DungeonTest {


  @Test
  public void testLogic() {
    int height = 3;
    int width = 4;
    int id = 1;
    int row = (id - (id % width)) / width;
    int col = id % width;

    assertEquals(row, 0);
    assertEquals(col, 1);
  }
}