package model.dungeon;

import java.util.Map;

/**
 * Class for representing a map in the game. A generic map with a methods to add treasures and move
 * the player can be created using this interface.
 */
public interface GameMap {

  /**
   * Method for shooting the arrow to the south at a specific distance.
   *
   * @param distance distance as a measure of number of caves.
   * @return String representing whether the arrow missed or hit.
   */
  String playerShootArrowSouth(int distance);

  /**
   * Method for shooting the arrow to the west at a specific distance.
   *
   * @param distance distance as a measure of number of caves.
   * @return String representing whether the arrow missed or hit.
   */
  String playerShootArrowWest(int distance);

  /**
   * Method for shooting the arrow to the east at a specific distance.
   *
   * @param distance distance as a measure of number of caves.
   * @return String representing whether the arrow missed or hit.
   */
  String playerShootArrowEast(int distance);

  /**
   * Method for adding weapons (arrows) to the dungeon.
   */
  void addWeapons();

  /**
   * Method for randomly adding treasures to the map.
   */
  void addTreasures();

  /**
   * Method for getting the coordinates of location in the dungeon using its id.
   *
   * @param id Id of the location.
   * @return Position instance of the location.
   */
  Position getPosition(int id);

  /**
   * Method for getting the possible moves from the player's location as a Connector object.
   *
   * @return a Connector object representing connected routes.
   */
  Connector getPossibleMoves();

  /**
   * Method for calculating dynamically the smell of monster at the player location.
   *
   * @return String representing the smell.
   */
  String getSmellAtPlayerLocation();

  /**
   * Method for moving the player to the north of the current location.
   */
  void playerMoveNorth();

  /**
   * Method for moving the player to the south of the current location.
   */
  void playerMoveSouth();

  /**
   * Method for moving the player to the east of the current location.
   */
  void playerMoveEast();

  /**
   * Method for moving the player to the west of the current location.
   */
  void playerMoveWest();

  /**
   * Method for getting the current status of the player as a string. Status represents treasures
   * collected and current location.
   *
   * @return String status.
   */
  String getPlayerStatus();

  /**
   * Method for getting the status of the location where player is present currently as a string.
   * Status represents treasures present at the location and location id.
   *
   * @return String status.
   */
  String getLocationStatus();

  /**
   * Checks if the player location and the end location are the same.
   *
   * @return true if player is at end location.
   */
  boolean checkIfPlayerAtEndLocation();

  /**
   * This method allows the player to collect treasure from the current location.
   */
  void collectTreasureForPlayer();

  /**
   * Getter for grid.
   *
   * @return 2-D array of the map.
   */
  Location[][] getGrid();

  /**
   * Getter for the player object.
   *
   * @return Player object.
   */
  Player getPlayer();

  /**
   * Method for generating the given number of monsters in the dungeon.
   *
   * @param totalNum number of monsters to be created.
   */
  void generateMonsters(int totalNum);

  /**
   * Method for the player to collect weapons from the current location.
   */
  void collectWeaponForPlayer();

  /**
   * Method to check whether the player is alive or dead.
   *
   * @return true if alive.
   */
  boolean checkPlayerState();

  /**
   * Method for shooting the arrow to the north at a specific distance.
   *
   * @param distance distance as a measure of number of caves.
   * @return String representing whether the arrow missed or hit.
   */
  String playerShootArrowNorth(int distance);

  String getPlayerName();

  /**
   * Method for getting the player's location.
   *
   * @return location id of the player's current lcoation.
   */
  int getPlayerLocation();

  /**
   * Method for getting the player's location in the grid.
   *
   * @return location of the player's current position.
   */
  Position getPlayerGridLocation();

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

  String getMove(int id);
}
