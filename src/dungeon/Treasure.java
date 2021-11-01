package dungeon;

public enum Treasure {
  DIAMOND, RUBY, SAPPHIRE;

  @Override
  public String toString() {
    return String.format(this.name());
  }
}
