package player;

import dungeon.Treasure;

public interface Player {

  void pickTreasure(Treasure treasure);

  int getLocation();

  String getName();

  void move(int location);
}
