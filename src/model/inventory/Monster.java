package model.inventory;

import java.util.Random;

public class Monster {

  private final MonsterName name;
  private int health;

  public Monster(MonsterName name) {
    this.name = name;
    this.health = 2;
  }

  public Monster() {
    this.name = MonsterName.OTYUGH;
    this.health = 2;
  }

  public boolean hitPlayer() {
    if (this.health < 2) {
      return new Random().nextBoolean();
    }
    return true;
  }

  public void receiveDamage() {
    health -= 1;
  }

  public int getHealth() {
    return health;
  }

  public MonsterName getName() {
    return name;
  }

  @Override
  public String toString() {
    return this.getName().name();
  }
}
