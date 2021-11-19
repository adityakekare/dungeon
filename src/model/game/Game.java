package model.game;

import model.dungeon.GameMap;

/**
 * Interface for implementing method associated to the model.dungeon model.game. This interface provides methods
 * to communicate with the model.dungeon.
 */
public interface Game {
  /**
   * Method which asks the player to move to the given direction.
   *
   * @param direction String input (N,S,W,E)
   */
  void move(String direction);

  /**
   * Method for dumping the model.dungeon as a string.
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
   * Checks whether model.game is at its end state.
   *
   * @return true if model.game is at the end state.
   */
  boolean isEnd();

  /**
   * Method for asking the model.dungeon to add treasures to the model.dungeon based on the input given as
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

  void addMonstersToDungeon(int num);

  void addWeaponsToDungeon();

  void pickupWeapon();

  boolean isPlayerAlive();

  String shootArrow(String direction, String distance);

  String getMonsterSmell();
}
