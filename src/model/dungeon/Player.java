package model.dungeon;

import model.inventory.Treasure;
import model.inventory.Weapon;

/**
 * Interface for a player. Gives methods for the player to interact with the GameMap.
 */
interface Player {

  /**
   * This method allows the player to pick the given treasure.
   *
   * @param treasure treasure enum object to be picked.
   */
  void pickTreasure(Treasure treasure);

  /**
   * Getter for location of the player.
   *
   * @return Id of the location.
   */
  int getLocation();

  /**
   * Getter for the player name.
   *
   * @return Player's name.
   */
  String getName();

  /**
   * This method allows the player to move around in the model.dungeon. Player moves to the location
   * passed as a parameter.
   *
   * @param location location id.
   */
  void move(int location);

  void pickWeapon(Weapon weapon);

  public boolean isAlive();

  public void setAlive(boolean alive);

  public void shoot();

  public int getArrowCount();
}
