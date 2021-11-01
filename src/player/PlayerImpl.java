package player;

import java.util.HashMap;
import java.util.Map;

import dungeon.Treasure;

public class PlayerImpl implements Player{

  private final String name;
  private int location;
  private final Map<String, Integer> treasureCount;

  public PlayerImpl(String name, int location) {
    this.name = name;
    this.location = location;
    treasureCount = new HashMap<>();
  }

  @Override
  public void pickTreasure(Treasure treasure) {
    treasureCount.put(treasure.toString(),
            treasureCount.getOrDefault(treasure.toString(), 0) + 1);
  }

  @Override
  public String toString() {
    String treasures = "";
    for(Map.Entry<String, Integer> entry: this.treasureCount.entrySet()){
        treasures += " " + entry.getKey() + ": " + entry.getValue();
    }
    if(treasures.equals("")){
      treasures += "None";
    }
    return "Player name: " + this.name + "\n" + "Treasures collected:" + treasures;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void move(int location) {
    this.location = location;
  }

  @Override
  public int getLocation() {
    return location;
  }
}
