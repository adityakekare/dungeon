package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit;

import model.game.ReadOnlyGame;
import model.inventory.Treasure;

/**
 * Class for making a panel for describing player's status.
 */
public class DescriptionPanel extends JPanel {

  private final ReadOnlyGame model;
  private String message;

  /**
   * Constructor that takes in a read-only model.
   *
   * @param model read-only model.
   */
  public DescriptionPanel(ReadOnlyGame model) {
    this.setPreferredSize(new Dimension(275, 600));
    this.message = "";
    this.model = model;
    this.setBackground(new Color(0, 102, 102));
    this.setLayout(null);
    this.setVisible(true);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g.setColor(Color.white);
    g.setFont(new Font("default", Font.BOLD, 18));
    String rubyPath = "res/images/ruby.png";
    String diamondPath = "res/images/diamond.png";
    String sapphirePath = "res/images/emerald.png";
    String arrowPath = "res/images/arrow-black.png";
    Image ruby = null;
    Image sapphire = null;
    Image diamond = null;
    Image arrow = null;

    g.drawString(model.getName() + "'s score:\n", 10, 20);
    g.setFont(new Font("default", Font.BOLD, 14));
    try {
      ruby = ImageIO.read(new File(rubyPath));
      diamond = ImageIO.read(new File(diamondPath));
      sapphire = ImageIO.read(new File(sapphirePath));
      arrow = ImageIO.read(new File(arrowPath));

    } catch (IOException e) {
      System.out.print("Can't read image");
    }
    g.drawImage(ruby, 10, 40, this);
    g.drawImage(diamond, 10, 110, this);
    g.drawImage(sapphire, 10, 180, this);
    g.drawImage(arrow, 10, 250, this);

    Map<String, Integer> treasures = model.getPlayerTreasureCount();
    int weaponCount = model.getPlayerWeaponCount();

    g.drawString(String.valueOf(treasures.getOrDefault(Treasure.RUBY.toString(), 0)),
            70, 55);
    g.drawString(String.valueOf(treasures.getOrDefault(Treasure.DIAMOND.toString(), 0)),
            70, 125);
    g.drawString(String.valueOf(treasures.getOrDefault(Treasure.SAPPHIRE.toString(), 0)),
            70, 195);
    g.drawString(String.valueOf(weaponCount), 70, 260);

    g.drawString(message, 10, 325);

  }

  public void display(String message) {
    this.message = message;
  }
}


