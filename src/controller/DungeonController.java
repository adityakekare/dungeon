package controller;

import model.game.Game;

/**
 * Interface to implement the controller for the dungeon game. The dungeon model will be used to
 * play the game.
 */
public interface DungeonController {

  /**
   * Method for implementing the game using dungeon game model.
   *
   * @param game The model for the dungeon game.
   */
  void startGame(Game game);
}
