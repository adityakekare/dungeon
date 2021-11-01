package dungeon;

public abstract class AbstractLocation implements Location{

  private final int id;
  private final Connector routes;

  protected AbstractLocation(int id, Connector routes) {
    this.id = id;
    this.routes = routes;
  }

  public int getId(){
    return id;
  }

  public Connector getRoutes(){
    return routes;
  }
}
