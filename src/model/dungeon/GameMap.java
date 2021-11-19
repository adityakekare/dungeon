package model.dungeon;

/**
 * Class for representing a map in the game. A generic map with a methods to add treasures and move
 * the player can be created using this interface.
 */
public interface GameMap {

  String playerShootArrowSouth(int distance);

  String playerShootArrowWest(int distance);

  String playerShootArrowEast(int distance);

  void addWeapons();

  /**
   * Method for randomly adding treasures to the map.
   */
  void addTreasures();

  /**
   * Method for getting the possible moves from the player's location as a Connector object.
   *
   * @return a Connector object representing connected routes.
   */
  Connector getPossibleMoves();

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

  void generateMonsters(int totalNum);

  void collectWeaponForPlayer();

  boolean checkPlayerState();

  String playerShootArrowNorth(int distance);
}
