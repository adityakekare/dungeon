package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import controller.DungeonGraphicController;
import model.dungeon.Location;
import model.dungeon.Position;
import model.game.ReadOnlyGame;

/**
 * Class for making a super-panel for all the sub-panels of the locations in the grid.
 */
public class DungeonMapPanel extends JPanel {

  private final ReadOnlyGame model;
  private final LocationPanel[][] panels;
  private final int lengthDungeon;
  private final int widthDungeon;

  /**
   * Constructor that takes in a read-only model.
   *
   * @param model read-only model.
   *
   * @throws IOException when image can't be read properly.
   */
  public DungeonMapPanel(ReadOnlyGame model) throws IOException {
    try{
      Image playerImage = ImageIO.read(new File("res/images/player.png"));
    } catch (IOException e){
      System.out.println(e.getMessage());
    }

    this.model = model;
    Location[][] dungeonMap = model.getGrid();
    lengthDungeon = dungeonMap.length;
    widthDungeon = dungeonMap[0].length;
    panels = new LocationPanel[lengthDungeon][widthDungeon];
    GridLayout cellLayout = new GridLayout(lengthDungeon, widthDungeon, 0, 0);
    GridBagLayout gridLayout = new GridBagLayout();
    GridBagConstraints constraints = new GridBagConstraints();
    this.setLayout(cellLayout);
    this.setPreferredSize(new Dimension(lengthDungeon * 60, widthDungeon * 60));
    this.addPanels();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
  }

  private void addPanels() {
    for (int i = 0; i < lengthDungeon; i++) {
      for (int j = 0; j < widthDungeon; j++) {
        LocationPanel locationPanel = new LocationPanel(model.getGrid()[i][j], model,
                model.getVisited()[i][j]);
        locationPanel.setFocusable(false);
        this.add(locationPanel);
        panels[i][j] = locationPanel;
      }
    }
  }

  /**
   * Refresh all the sub-components and the panel itself.
   */
  public void refresh() {
    this.removeAll();
    this.addPanels();
    for (int i = 0; i < lengthDungeon; i++) {
      for (int j = 0; j < widthDungeon; j++) {
        panels[i][j].repaint();
      }
    }
  }

  /**
   * Add a click listener to each of the sub-panels.
   *
   * @param listener controller as listener.
   */
  public void addClickListener(DungeonGraphicController listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
//        System.out.println("out");
        for (int i = 0; i < panels.length; i++) {
          for (int j = 0; j < panels[0].length; j++) {
            Point click = e.getLocationOnScreen();
            Point origin = panels[i][j].getLocationOnScreen();
            Point dest = new Point((int) (panels[i][j].getWidth() + origin.getX()),
                    (int) (panels[i][j].getHeight() + origin.getY()));
            if (click.getX() > origin.getX() && click.getX() <= dest.getX() &&
                    click.getY() > origin.getY() && click.getY() <= dest.getY()) {
//              System.out.println("in");
              listener.handleCellClick(i, j);
            }
          }
        }
      }
    };
//    for (int i = 0; i < panels.length; i++) {
//      for (int j = 0; j < panels[0].length; j++) {
//        panels[i][j].addMouseListener(clickAdapter);
//      }
//    }
    this.addMouseListener(clickAdapter);
  }

  public JPanel getGridData() {
    JPanel panel = new JPanel();
    int rows = model.getGrid().length;
    int cols = model.getGrid()[0].length;
    GridBagLayout panelLayout = new GridBagLayout();
    panel.setLayout(panelLayout);
    GridBagConstraints c = new GridBagConstraints();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        JPanel pictureComponent = panels[i][j];
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = i;
        c.gridx = j;
        panel.add(pictureComponent, c);
      }
    }
    return panel;
  }
}

