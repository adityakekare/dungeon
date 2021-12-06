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

  /**
   * Method for the player to pick the given weapon.
   *
   * @param weapon weapon to be picked.
   */
  void pickWeapon(Weapon weapon);

  /**
   * Method to check whether the player is alive or not.
   *
   * @return true if player is alive.
   */
  public boolean isAlive();

  /**
   * Method to set the player's state.
   *
   * @param alive boolean value for setting the state.
   */
  public void setAlive(boolean alive);

  /**
   * Method for the player to shoot the arrow. Reduces the number of arrows with the player by 1.
   */
  public void shoot();

  /**
   * Method for getting the remaining arrows with the player.
   *
   * @return number of arrows remaining with the player.
   */
  public int getArrowCount();
}
