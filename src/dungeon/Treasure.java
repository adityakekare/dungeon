package dungeon;

public enum Treasure {
  DIAMOND(20), RUBY(10), SAPPHIRE(5);

  private final int points;

  Treasure(int points) {
    this.points = points;
  }

  @Override
  public String toString() {
    return String.format(this.name() + ": +" + points);
  }
}
