package dungeon;

import java.util.List;

public interface Location {

  int getId();
  Connector getRoutes();
  boolean isTunnel();
}
