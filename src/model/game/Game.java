package model.game;

import model.dungeon.GameMap;


/**
 * Interface for implementing method associated to the dungeon game. This interface provides methods
 * to communicate with the dungeon.
 */
public interface Game extends ReadOnlyGame {
  /**
   * Method which asks the player to move to the given direction.
   *
   * @param direction String input (N,S,W,E)
   */
  void move(String direction);

  /**
   * Method which asks the player to move to the given direction.
   *
   * @param row of location.
   * @param col of location.
   */
  String move(int row, int col);

  /**
   * Method for dumping the dungeon as a string.
   *
   * @return String representation of the model.dungeon.
   */
  String dumpDungeon();


  boolean isEndState();

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
   * Method for player to shoot the arrow in the given direction and at given distance.
   *
   * @param direction direction to shoot.
   * @param distance  distance to shoot at in terms of number of caves.
   * @return String representing whether the arrow hit or missed.
   */
  String shootArrow(String direction, String distance);

  /**
   * Getter for the dungeon grid in the Game.
   *
   * @return GameMap object (Dungeon).
   */
  GameMap getDungeon();

}
