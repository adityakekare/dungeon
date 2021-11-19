package model.dungeon;

/**
 * Class for representing a position on a 2D plane.
 */
public class Position {

  private final int xpos;
  private final int ypos;

  /**
   * Takes in x and y co-ordinates of the position.
   *
   * @param x x-coordinate.
   * @param y y-coordinate.
   */
  public Position(int x, int y) {
    this.xpos = x;
    this.ypos = y;
  }

  /**
   * Getter for x-coordinate.
   *
   * @return x-coordinate.
   */
  public int getXpos() {
    return xpos;
  }

  /**
   * Getter for y-coordinate.
   *
   * @return y-coordinate.
   */
  public int getYpos() {
    return ypos;
  }

  @Override
  public String toString() {
    return xpos + " " + ypos;
  }
}
