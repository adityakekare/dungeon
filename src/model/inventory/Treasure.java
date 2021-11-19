package model.inventory;

/**
 * Enum for encapsulating types of treasures.
 */
public enum Treasure {
  DIAMOND, RUBY, SAPPHIRE;

  @Override
  public String toString() {
    return String.format(this.name());
  }
}
