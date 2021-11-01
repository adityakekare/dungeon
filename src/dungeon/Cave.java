package dungeon;

import java.util.ArrayList;
import java.util.List;

public class Cave extends AbstractLocation {

  private List<Treasure> treasures;

  public Cave(int id, Connector routes) {
    super(id, routes);
    this.treasures = new ArrayList<>();
  }

  @Override
  public boolean isTunnel() {
    return false;
  }

  public void setTreasures(List<Treasure> treasures) {
    this.treasures = treasures;
  }

  public List<Treasure> getTreasures() {
    return new ArrayList<>(this.treasures);
  }

  @Override
  public String toString() {
    return String.format("Location id: " + this.getId() + " Location type: Cave: "
    + this.getTreasures());
  }
}
