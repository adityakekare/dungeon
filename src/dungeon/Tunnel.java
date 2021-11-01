package dungeon;

public class Tunnel extends AbstractLocation{

  protected Tunnel(int id, Connector routes) {
    super(id, routes);
  }

  @Override
  public boolean isTunnel() {
    return true;
  }

  @Override
  public String toString() {
    return String.format("Location id: " + this.getId() + " Location type: Tunnel");
  }
}
