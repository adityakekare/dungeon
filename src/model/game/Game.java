package model.game;

import model.dungeon.GameMap;

/**
 * Interface for implementing method associated to the dungeon game. This interface provides methods
 * to communicate with the dungeon.
 */
public interface Game {
  /**
   * Method which asks the player to move to the given direction.
   *
   * @param direction String input (N,S,W,E)
   */
  void move(String direction);

  /**
   * Method for dumping the dungeon as a string.
   *
   * @return String representation of the model.dungeon.
   */
  String dumpDungeon();

  /**
   * Possible set of moves for the player from their current location is returned.
   *
   * @return set of moves as a string.
   */
  String getMovesForPlayer();

  String getStatusLocation();

  String getStatusPlayer();

  /**
   * Checks whether game is at its end state.
   *
   * @return true if game is at the end state.
   */
  boolean isEnd();

  /**
   * Method for asking the dungeon to add treasures to the dungeon based on the input given as
   * a percentage.
   */
  void addTreasuresToDungeon();

  /**
   * Method for asking the player to pick treasures from the current location.
   */
  void pickUpTreasure();

  /**
   * Getter for the gameMap object in the Game.
   *
   * @return GameMap object (Dungeon).
   */
  GameMap getDungeon();

  /**
   * Method for adding monsters to the dungeon.
   *
   * @param num number of monsters to be added.
   */
  void addMonstersToDungeon(int num);

  /**
   * Method for adding weapons in the dungeon.
   */
  void addWeaponsToDungeon();

  /**
   * Method for player to pick weapon from current location.
   */
  void pickupWeapon();

  /**
   * Method for checking if the player is alive.
   *
   * @return true if player is alive.
   */
  boolean isPlayerAlive();

  /**
   * Method for player to shoot the arrow in the given direction and at given distance.
   *
   * @param direction direction to shoot.
   * @param distance  distance to shoot at in terms of number of caves.
   * @return String representing whether the arrow hit or missed.
   */
  String shootArrow(String direction, String distance);

  /**
   * Method for getting the monster's smell at player's current location.
   *
   * @return String representing the monster's smell.
   */
  String getMonsterSmell();
}
