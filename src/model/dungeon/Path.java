package model.dungeon;

import java.util.Objects;

/**
 * Class for representing a path in the model.dungeon which connects 2 locations.
 */
public class Path implements Comparable<Path> {

  private final int source;
  private final int destination;

  /**
   * Constructor which takes in the source and destination location ID.
   *
   * @param source      source location id.
   * @param destination destination location id.
   */
  public Path(int source, int destination) {
    this.source = Math.min(source, destination);
    this.destination = Math.max(source, destination);
  }

  /**
   * Getter for source id.
   *
   * @return source location id.
   */
  public int getSource() {
    return source;
  }

  /**
   * Getter for destination location id.
   *
   * @return destination location id.
   */
  public int getDestination() {
    return destination;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Path)) {
      return false;
    }

    Path otherPath = (Path) o;

    if (otherPath.getSource() == this.getSource() && otherPath.getDestination()
            == this.getDestination()) {
      return true;
    } else if (otherPath.getSource() == this.getDestination() && otherPath.getDestination()
            == this.getSource()) {
      return true;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(source + destination);
  }

  @Override
  public int compareTo(Path o) {
    if (this.source < o.source) {
      return -1;
    } else if (this.source > o.source) {
      return 1;
    } else {
      return Integer.compare(this.destination, o.destination);
    }
  }

  @Override
  public String toString() {
    return String.format(source + " => " + destination);
  }
}
