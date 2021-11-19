package model.dungeon;

import java.util.HashMap;
import java.util.Map;

import model.inventory.Treasure;
import model.inventory.Weapon;

/**
 * This class implements the player interface as represents a player in the model.dungeon. The player can
 * move in the model.dungeon and collect treasures.
 */
class PlayerImpl implements Player {

  private final String name;
  private final Map<String, Integer> treasureCount;
  private final Map<String, Integer> weaponCount;
  private int location;
  private boolean alive;

  /**
   * Constructor for player object. Takes name and location id as parameters.
   *
   * @param name     player name.
   * @param location location id.
   */
  public PlayerImpl(String name, int location) {
    this.name = name;
    this.location = location;
    treasureCount = new HashMap<>();
    weaponCount = new HashMap<>();
    weaponCount.put(Weapon.ARROW.toString(), 3);
    alive = true;
  }

  @Override
  public void pickTreasure(Treasure treasure) {
    treasureCount.put(treasure.toString(),
            treasureCount.getOrDefault(treasure.toString(), 0) + 1);
  }

  @Override
  public String toString() {
    String treasures = "";
    for (Map.Entry<String, Integer> entry : this.treasureCount.entrySet()) {
      treasures += " " + entry.getKey() + ": " + entry.getValue();
    }
    if (treasures.equals("")) {
      treasures += "None";
    }
//    return "Player name: " + this.name + " Location id: " + this.location
//            + "\n" + "Treasures collected:" + treasures + ", Arrows remaining: "
//            + weaponCount.get(Weapon.ARROW.toString());
    return "Treasures collected: " + treasures + "\nArrows remaining: "
            + weaponCount.get(Weapon.ARROW.toString());
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
  public void pickWeapon(Weapon weapon) {
    weaponCount.put(weapon.toString(),
            weaponCount.getOrDefault(weapon.toString(), 0) + 1);
  }

  @Override
  public int getLocation() {
    return location;
  }

  @Override
  public boolean isAlive() {
    return alive;
  }

  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  @Override
  public void shoot() {
    if (this.getArrowCount() <= 0) {
      throw new IllegalArgumentException("No more arrows left");
    }
    weaponCount.put(Weapon.ARROW.name(), weaponCount.get(Weapon.ARROW.name()) - 1);
  }

  public int getArrowCount() {
    return weaponCount.get(Weapon.ARROW.name());
  }
}
