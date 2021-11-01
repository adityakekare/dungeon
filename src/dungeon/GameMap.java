package dungeon;

public interface GameMap {

  Connector getPossibleMoves();
  void playerMoveNorth();
  void playerMoveSouth();
  void playerMoveEast();
  void playerMoveWest();
  String getPlayerStatus();
  boolean checkIfPlayerAtEndLocation();
}
