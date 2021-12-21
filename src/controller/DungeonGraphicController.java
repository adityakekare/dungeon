package controller;

import java.io.IOException;

/**
 * Controller for Graphical game.
 */
public interface DungeonGraphicController {
  /**
   * Start the game with default settings.
   *
   * @throws IOException when image is not read properly.
   */
  void startDefaultGame() throws IOException;

  /**
   * Start game with given settings.
   *
   * @param height                    height of the dungeon.
   * @param width                     width of the dungeon.
   * @param degreeOfConnectivity      degree of interconnectivity.
   * @param isWrapping                whether the dungeon is wrapping or not.
   * @param percentTreasure           %age of caves which contain treasure.
   * @param playerName                Name of the Player.
   *
   * @throws IOException              when image is not read properly.
   */
  void startGame(int height, int width, int degreeOfConnectivity,
                 boolean isWrapping, int percentTreasure, int numMonsters,
                 String playerName) throws IOException;

  /**
   * Restart the game with the same dungeon map.
   *
   * @throws IOException when image is not read properly.
   */
  void restart() throws IOException;

  /**
   * Handle the move function of the model using inputs from the view.
   *
   * @param keyInt String input.
   *
   * @return String feedback from model.
   */
  String handleMoveInput(int keyInt);

  /**
   * Handle the pick treasure functionality.
   */
  void handlePickTreasureInput();

  /**
   * handle the pick weapon functionality.
   */
  void handlePickWeaponInput();

  /**
   * Handle the shoot arrow functionality.
   *
   * @param direction String direction.
   * @param distance String distance.
   *
   * @return feedback from model.
   */
  String handleShoot(String direction, String distance);

  /**
   * Handle the click function on the view using the model.
   *
   * @param i row index of location.
   * @param j column index of location.
   *
   * @return String feedback.
   */
  String handleCellClick(int i, int j);
}
