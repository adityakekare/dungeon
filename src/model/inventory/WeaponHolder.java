package model.inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Class acts as a wrapper for holding a collection of weapons. We can add, remove, get and check
 * whether the weapon is present in the container.
 */
public class WeaponHolder implements Inventory {

  private final List<Weapon> weapons;

  /**
   * Constructor for initializing the weapon container.
   */
  public WeaponHolder() {
    this.weapons = new ArrayList<>();
  }

  @Override
  public void add() {
    weapons.add(Weapon.ARROW);
  }

  @Override
  public void remove() {
    this.weapons.clear();
  }

  @Override
  public boolean contains() {
    return !weapons.isEmpty();
  }

  @Override
  public List<Weapon> get() {
    return new ArrayList<>(weapons);
  }
}
