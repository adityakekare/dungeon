package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import model.game.ReadOnlyGame;

/**
 * Panel for describing the game controls to the user.
 */
public class GameDescriptionPanel extends JPanel {

  private final ReadOnlyGame model;

  /**
   * Takes in read-only model to construct the panel.
   *
   * @param model read-only model.
   */
  public GameDescriptionPanel(ReadOnlyGame model) {
    this.model = model;
    this.setBackground(Color.LIGHT_GRAY);
    this.setLayout(null);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }
}
