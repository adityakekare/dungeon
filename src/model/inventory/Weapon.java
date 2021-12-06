package model.inventory;

/**
 * Enum for types of weapons.
 */
public enum Weapon {
  ARROW;

  @Override
  public String toString() {
    return this.name();
  }
}
