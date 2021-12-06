package model.inventory;

import java.util.Random;

/**
 * Class for representing a monster in the game. The monster can hit the player, and receive damage.
 */
public class Monster {

  private final MonsterName name;
  private int health;

  /**
   * One-argument contructor which takes in the name of monster.
   *
   * @param name name of monster.
   */
  public Monster(MonsterName name) {
    this.name = name;
    this.health = 2;
  }

  /**
   * No-argument constructor for monster.
   */
  public Monster() {
    this.name = MonsterName.OTYUGH;
    this.health = 2;
  }

  /**
   * Method for hitting the player based on injuries received by the monster.
   */
  public boolean hitPlayer() {
    if (this.health < 2) {
      return new Random().nextBoolean();
    }
    return true;
  }

  /**
   * Method for receiving damage from weapons.
   */
  public void receiveDamage() {
    health -= 1;
  }

  /**
   * Getter for health of the monster.
   *
   * @return health of the monster.
   */
  public int getHealth() {
    return health;
  }

  /**
   * Getter for the name of the monster.
   *
   * @return name of the monster.
   */
  public MonsterName getName() {
    return name;
  }

  /**
   * Method for string representation of the monster.
   *
   * @return String representation of the monster.
   */
  @Override
  public String toString() {
    return this.getName().name();
  }
}
