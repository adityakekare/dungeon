package game;

public interface Game {
  void move(String direction);
  String dumpDungeon();
  String getMovesForPlayer();
  String getStatus();
  boolean isEnd();
}
