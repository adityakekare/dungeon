package view;

import controller.DungeonGraphicController;

/**
 * Interface for Swing GUI type view for the dungeon game. All the methods required for the view
 * are present here.
 */
public interface DungeonView {

  /**
   * Method for repainting the screen.
   */
  void refresh();

  /**
   * Make the view visible.
   */
  void makeVisible();

  /**
   * Add controller as the action listener to the view.
   *
   * @param listener controller as listener.
   */
  void addActionListener(DungeonGraphicController listener);

  /**
   * Add controller as the Key listener to the view.
   *
   * @param listener controller as listener.
   */
  void addKeyListener(DungeonGraphicController listener);

  /**
   * Method for displaying feeback to the user on the screen.
   *
   * @param message Feedback string.
   */
  void displayFeedback(String message);

  /**
   * Dispose the view.
   */
  void close();

  /**
   * Set flag for indicating end of game.
   */
  void endGame();

  /**
   * Add a click listener to the view.
   *
   * @param listener controller as the listener.
   */
  void addClickListener(DungeonGraphicController listener);

}
