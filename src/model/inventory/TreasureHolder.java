package model.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TreasureHolder implements Inventory {

  private final List<Treasure> treasures;

  public TreasureHolder() {
    this.treasures = new ArrayList<>();
  }

  @Override
  public void add() {
    Random random = new Random();
    Map<Integer, Treasure> treasureMap = new HashMap<>();
    int i = 0;
    for (Treasure treasure : Treasure.values()) {
      treasureMap.put(i, treasure);
      i += 1;
    }

    int randomId = random.ints(0, treasureMap.size()).findAny().getAsInt();
    treasures.add(treasureMap.get(randomId));
  }

  @Override
  public void remove() {
    treasures.clear();
  }

  @Override
  public boolean contains() {
    return !treasures.isEmpty();
  }

  @Override
  public List<Treasure> get() {
    return new ArrayList<>(treasures);
  }
}
