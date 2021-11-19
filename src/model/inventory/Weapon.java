package model.inventory;

public enum Weapon {
  ARROW;

  @Override
  public String toString() {
    return this.name();
  }
}
