package model.dungeon;

/**
 * This class encapsulates a boolean array which represents the connected paths of a location in the
 * model.dungeon.
 */
public class Connector {

  private final boolean north;
  private final boolean south;
  private final boolean west;
  private final boolean east;

  /**
   * Constructor takes in a boolean array and initializes the attributes.4
   *
   * @param isOpen boolean array where 0 is north, 1 is south, 2 is west, 3 is east.
   */
  public Connector(boolean[] isOpen) {
    this.north = isOpen[0];
    this.south = isOpen[1];
    this.west = isOpen[2];
    this.east = isOpen[3];
  }

  public boolean isNorth() {
    return north;
  }

  public boolean isSouth() {
    return south;
  }

  public boolean isWest() {
    return west;
  }

  public boolean isEast() {
    return east;
  }

  /**
   * Method to get the count of connected paths.
   *
   * @return number of connected paths.
   */
  public int getNumRoutes() {
    int count = 0;
    if (this.isEast()) {
      count += 1;
    }
    if (this.isNorth()) {
      count += 1;
    }
    if (this.isSouth()) {
      count += 1;
    }
    if (this.isWest()) {
      count += 1;
    }

    return count;
  }

  @Override
  public String toString() {
    String result = "";
    if (this.north) {
      result += "N, ";
    }
    if (this.south) {
      result += "S, ";
    }
    if (this.east) {
      result += "E, ";
    }
    if (this.west) {
      result += "W, ";
    }

    result = result.replaceAll(", $", "");

    return result;
  }
}
