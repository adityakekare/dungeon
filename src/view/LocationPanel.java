package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.dungeon.Connector;
import model.dungeon.Location;
import model.game.ReadOnlyGame;
import model.inventory.InventoryType;

/**
 * Class for making a panel for each location in the dungeon.
 */
public class LocationPanel extends JPanel {

  private final Location location;
  private final ReadOnlyGame model;
  private final boolean isVisited;

  /**
   * Constructor which takes in read-only model, visited array and location instance
   * of the location.
   *
   * @param location location instance.
   * @param model read-only model.
   * @param isVisited array for storing visited location.
   */
  public LocationPanel(Location location, ReadOnlyGame model, boolean isVisited) {
    setSize(60, 60);
    this.location = location;
    this.model = model;
    this.isVisited = isVisited;
  }

  @Override
  protected void paintComponent(Graphics g) {

    Image locationImage = null;
    Image playerImage = null;
    Image weaponImage = null;
    Image monsterImage = null;
    Image treasureImage = null;
    Image weakSmell = null;
    Image strongSmell = null;

    String pathToImage = "res/images/";
    String pathToPlayer = "res/images/player.png";
    String pathToWeakSmell = "res/images/stench01.png";
    String pathToStrongSmell = "res/images/stench02.png";
    String pathToTreasure = "res/images/treasure.png";
    String pathToMonster = "res/images/otyugh.png";
    String pathToDiamond = "res/images/diamond.png";
    String pathToRuby = "res/images/ruby.png";
    String pathToSapphire = "res/images/emerald.png";
    String pathToArrow = "res/images/arrow-white.png";

    Connector routes = location.getRoutes();

    try {
      if (isVisited) {
        pathToImage += "color-cells/";
        if (routes.isNorth()) {
          pathToImage += "N";
        }
        if (routes.isSouth()) {
          pathToImage += "S";
        }
        if (routes.isEast()) {
          pathToImage += "E";
        }
        if (routes.isWest()) {
          pathToImage += "W";
        }
      } else {
        pathToImage += "blank";
      }

      locationImage = ImageIO.read(new File(pathToImage + ".png"));
      playerImage = ImageIO.read(new File(pathToPlayer));
      monsterImage = ImageIO.read(new File(pathToMonster));
      treasureImage = ImageIO.read(new File(pathToTreasure));
      weaponImage = ImageIO.read(new File(pathToArrow));
      strongSmell = ImageIO.read(new File(pathToStrongSmell));
      weakSmell = ImageIO.read(new File(pathToWeakSmell));

      g.drawImage(locationImage, 0, 0, this);
      if (isVisited) {
        if (model.getPlayerLocationId() == location.getId()) {
          boolean isMonsterPresent = false;
          if (location.contains(InventoryType.MONSTER)) {
            isMonsterPresent = true;
            g.drawImage(playerImage, 20, 20, 20, 20, this);
            g.drawImage(monsterImage, 40, 20, 20, 20, this);
          } else {
            g.drawImage(playerImage, 10, 20, 30, 30, this);
          }
          if (location.contains(InventoryType.WEAPON)) {
            if (isMonsterPresent) {
              g.drawImage(weaponImage, 25, 42, 8, 8, this);
            } else {
              g.drawImage(weaponImage, 15, 42, 8, 8, this);
            }
          }
          if (location.contains(InventoryType.TREASURE)) {
            if (isMonsterPresent) {
              g.drawImage(treasureImage, 25, 10, 10, 10, this);
            } else {
              g.drawImage(treasureImage, 15, 10, 10, 10, this);
            }

          }
          if (model.getMonsterSmell().contains("unbearably")) {
            g.drawImage(strongSmell, 5, 5, 50, 50, this);
          } else if (model.getMonsterSmell().contains("filthy")) {
            g.drawImage(weakSmell, 5, 5, 50, 50, this);
          }
        } else {
          if (location.contains(InventoryType.WEAPON)) {
            g.drawImage(weaponImage, 15, 10, 15, 15, this);
          }
          if (location.contains(InventoryType.TREASURE)) {
            g.drawImage(treasureImage, 15, 30, 15, 15, this);
          }
          if (location.contains(InventoryType.MONSTER)) {
            g.drawImage(monsterImage, 40, 20, 20, 20, this);
          }
        }
      }
    } catch (IOException e) {
      System.out.print("Can't read image: " + pathToImage + ".png\n");
    }

  }
}
