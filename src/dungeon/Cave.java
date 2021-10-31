package dungeon;

import java.util.ArrayList;
import java.util.List;

public class Cave {

  private final int id;
  private final Connector routes;
  private final List<Treasure> treasures;

  public Cave(int id, Connector routes) {
    this.id = id;
    this.routes = routes;
    this.treasures = new ArrayList<>();
  }

  @Override
  public String toString() {
    return "[" + id + "]";
  }
}
