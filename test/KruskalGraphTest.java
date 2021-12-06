import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.dungeon.Path;
import model.graphutils.KruskalGraph;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Class for testing the Kruskal algorithm methods.
 */
public class KruskalGraphTest {

  List<Path> paths;
  List<Path> paths1;
  List<Path> paths2;

  @Before
  public void setUp() throws Exception {
    paths = new ArrayList<>();
    paths1 = new ArrayList<>();
    paths2 = new ArrayList<>();
  }

  @Test
  public void testGenerateMst() {
    paths.add(new Path(0, 1));
    paths.add(new Path(0, 2));
    paths.add(new Path(1, 3));
    paths.add(new Path(2, 3));

    paths2.add(new Path(0, 1));
    paths2.add(new Path(0, 2));
    paths2.add(new Path(1, 3));

    int expectedSize = 3;

    assertEquals(expectedSize,
            KruskalGraph.generateMst(paths, 4, 0).size());
    assertArrayEquals(paths2.toArray(),
            KruskalGraph.generateMst(paths, 4, 0).toArray());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGenerateMstException() {
    paths.add(new Path(0, 1));
    paths.add(new Path(2, 3));
    paths.add(new Path(0, 2));
    paths.add(new Path(1, 3));

    KruskalGraph.generateMst(paths, 4, 4);
  }

}