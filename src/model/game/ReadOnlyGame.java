package model.game;

import java.util.Map;
import model.dungeon.Location;
import model.dungeon.Position;

/**
 * Interface for a read-only version of the model which contains only getter methods.
 */
public interface ReadOnlyGame {
  /**
   * Possible set of moves for the player from their current location is returned.
   *
   * @return set of moves as a string.
   */
  String getMovesForPlayer();

  /**
   * Get description of the location where player is positioned at.
   *
   * @return String description of the location.
   */
  String getStatusLocation();

  /**
   * Get description of the player including their collectibles.
   *
   * @return String description of the player.
   */
  String getStatusPlayer();

  /**
   * Checks whether game is at its end state.
   *
   * @return true if game is at the end state.
   */
  boolean isEnd();

  /**
   * Method for checking if the player is alive.
   *
   * @return true if player is alive.
   */
  boolean isPlayerAlive();

  /**
   * Method for getting the monster's smell at player's current location.
   *
   * @return String representing the monster's smell.
   */
  String getMonsterSmell();

  /**
   * Get grid of the dungeon.
   *
   * @return Location 2-D array of dungeon map.
   */
  Location[][] getGrid();

  /**
   * Method for getting the player's location in the grid.
   *
   * @return location of the player's current position.
   */
  Position getPlayerPosition();

  /**
   * Method for getting the player's location Id in the grid.
   *
   * @return location ID of the player's current position.
   */
  int getPlayerLocationId();

  /**
   * Get the treasures collected by the player.
   *
   * @return Map representing treasure count.
   */

  Map<String, Integer> getPlayerTreasureCount();

  /**
   * Get the weapons collected by the player.
   *
   * @return int representing arrow count.
   */
  int getPlayerWeaponCount();


  boolean[][] getVisited();

  String getName();
}
