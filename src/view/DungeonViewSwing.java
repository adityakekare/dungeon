package view;

import controller.DungeonGraphicController;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import model.game.ReadOnlyGame;

/**
 * Class which represents the JFrame (main frame) of the game. It contains all the sub-panels
 * inside it.
 */
public class DungeonViewSwing extends JFrame implements DungeonView {

  private final JMenuItem newGame;
  private final JMenuItem restart;
  private final JMenuItem exit;
  private final JMenuItem settings;
  private final JSlider extentSlider;
  private final JSlider depthSlider;
  private final JSlider percentSlider;
  private final JSpinner docField, monsterField;
  private final JRadioButton yes, no;
  private final JTextField playerName;
  private final DungeonMapPanel mapPanel;
  private final DescriptionPanel descriptionPanel;
  private final ReadOnlyGame model;
  private boolean isGameActive;


  /**
   * Constructor for contructing the jframe.
   *
   * @param model read-only model.
   *
   * @throws IOException If the image is not readable.
   */
  public DungeonViewSwing(ReadOnlyGame model) throws IOException {
    super("Dungeons and Dragons");
    isGameActive = true;
    this.model = model;
    this.setSize(900, 600);
    this.setMaximumSize(new Dimension(600, 600));
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new FlowLayout(FlowLayout.LEFT));

    JMenu jMenu = new JMenu("Game Menu");
    jMenu.setMnemonic(KeyEvent.VK_F);
    JMenuBar jMenuBar = new JMenuBar();

    SpinnerNumberModel docModel = new SpinnerNumberModel(0, 0, 20, 1);
    SpinnerNumberModel monsterModel = new SpinnerNumberModel(3, 0, 20, 1);

    String yesString = "Yes";
    String noString = "No";

    JDialog settingsPopUp = new JDialog(this, "Settings");
    extentSlider = new JSlider(4, 30, 10);
    extentSlider.setMajorTickSpacing(4);
    extentSlider.setMinorTickSpacing(1);
    extentSlider.setPaintTicks(true);
    extentSlider.setPaintLabels(true);
    depthSlider = new JSlider(3, 30, 10);
    depthSlider.setMajorTickSpacing(4);
    depthSlider.setMinorTickSpacing(1);
    depthSlider.setPaintTicks(true);
    depthSlider.setPaintLabels(true);
    docField = new JSpinner(docModel);
    docField.setValue(0);
    yes = new JRadioButton(yesString);
    yes.setActionCommand(yesString);
    no = new JRadioButton(noString);
    no.setActionCommand(noString);
    ButtonGroup group = new ButtonGroup();
    group.add(yes);
    group.add(no);
    no.setSelected(true);
    percentSlider = new JSlider(0, 100, 25);
    percentSlider.setMajorTickSpacing(4);
    percentSlider.setMinorTickSpacing(1);
    percentSlider.setPaintTicks(true);
    percentSlider.setPaintLabels(false);
    monsterField = new JSpinner(monsterModel);
    monsterField.setValue(3);
    playerName = new JTextField("New Player");
    playerName.setActionCommand("name");

    settings = new JMenuItem("Settings");
    restart = new JMenuItem("Restart");
    newGame = new JMenuItem("Start new game");
    exit = new JMenuItem("Exit");

    jMenu.add(newGame);
    jMenu.add(restart);
    jMenu.add(settings);
    jMenu.add(exit);

    jMenuBar.add(jMenu);

    this.setJMenuBar(jMenuBar);
    this.mapPanel = new DungeonMapPanel(model);
    descriptionPanel = new DescriptionPanel(model);
    GameDescriptionPanel gameDescriptionPanel = new GameDescriptionPanel(model);

    if (model.getGrid().length > 8 || model.getGrid()[0].length > 8) {
      this.add(getScrollablePanel(mapPanel));
    } else {
      this.add(mapPanel);
    }
    this.add(descriptionPanel);
  }

  @Override
  public void refresh() {
    this.mapPanel.refresh();
    this.repaint();
    this.revalidate();
    this.requestFocus();
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }


  @Override
  public void addActionListener(DungeonGraphicController listener) {
    ActionListener action = e -> {
      if (e.getActionCommand().equals(settings.getText()) ||
              e.getActionCommand().equals(newGame.getText())) {

        int input = createSettingsPane();

        if (input == JOptionPane.OK_OPTION) {
          try {
            int height = extentSlider.getValue();
            int width = depthSlider.getValue();
            int doc = (int) docField.getValue();
            boolean isYesSelected = yes.isSelected();
            int percent = percentSlider.getValue();
            int monsters = (int) monsterField.getValue();
            String name = playerName.getText();
            listener.startGame(height, width, doc, isYesSelected, percent, monsters,
                    name);
          } catch (Exception ignored) {

          }
        }
      } else if (e.getActionCommand().equals(restart.getText())) {
        try {
          listener.restart();
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      } else if (e.getActionCommand().equals(exit.getText())) {
        close();
      }
    };
    settings.addActionListener(action);
    restart.addActionListener(action);
    newGame.addActionListener(action);
    exit.addActionListener(action);
  }

  @Override
  public void addKeyListener(DungeonGraphicController listener) {
    KeyAdapter keyAdapter = new KeyAdapter() {
      private boolean isShootPressed = false;

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
          isShootPressed = true;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        String feedback = "";
        if (isShootPressed) {
          if (e.getKeyCode() == KeyEvent.VK_UP) {
            String distance = JOptionPane.showInputDialog("Distance? (1-5)");
            try {
              if(distance != null){
                feedback = listener.handleShoot("N", distance);
              }
            } catch (IllegalArgumentException iae) {
              feedback = iae.getMessage();
            }
//            System.out.println(distance);
            isShootPressed = false;
          } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            String distance = JOptionPane.showInputDialog("Distance? (1-5)");
            try {
              if (distance != null) {
                feedback = listener.handleShoot("S", distance);
              }
            } catch (IllegalArgumentException iae) {
              feedback = iae.getMessage();
            }
//            System.out.println(feedback);
            isShootPressed = false;
          } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            String distance = JOptionPane.showInputDialog("Distance? (1-5)");
            try {
              if (distance != null) {
                feedback = listener.handleShoot("W", distance);
              }
            } catch (IllegalArgumentException iae) {
              feedback = iae.getMessage();
            }
//            System.out.println(feedback);
            isShootPressed = false;
          } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            String distance = JOptionPane.showInputDialog("Distance? (1-5)");
            try {
              if (distance != null) {
                feedback = listener.handleShoot("E", distance);
              }
            } catch (IllegalArgumentException iae) {
              feedback = iae.getMessage();
            }
//            System.out.println(feedback);
            isShootPressed = false;
          }
        } else {
          if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT
                  || e.getKeyCode() == KeyEvent.VK_LEFT ||
                  e.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
              feedback = listener.handleMoveInput(e.getKeyCode());
            } catch (IllegalArgumentException iae) {
              feedback = iae.getMessage();
            }
//            System.out.println(feedback);
          } else if (e.getKeyCode() == KeyEvent.VK_T) {
            listener.handlePickTreasureInput();
          } else if (e.getKeyCode() == KeyEvent.VK_W) {
            listener.handlePickWeaponInput();
          }
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
          isShootPressed = false;
        }
        displayFeedback(feedback);
      }
    };
    this.addKeyListener(keyAdapter);
  }

  @Override
  public void displayFeedback(String message) {
    this.descriptionPanel.display(message);
  }

  @Override
  public void close() {
    this.dispose();
  }

  private int createSettingsPane() {
    ImageIcon logoImage = new ImageIcon("res/images/gameIcon.png");
    Object[] options = {
      new JLabel("Extent of Dungeon: "),
      extentSlider,
      new JLabel("Depth of Dungeon: "),
      depthSlider,
      new JLabel("Degree of interconnectivity: "),
      docField,
      new JLabel("Wrapping dungeon? "),
      yes,
      no,
      new JLabel("Percentage of Treasure/ Weapons: "),
      percentSlider,
      new JLabel("Number of Monsters: "),
      monsterField,
      new JLabel("Player Name: "),
      playerName,
    };

    return JOptionPane.showConfirmDialog(this, options, "Settings",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, logoImage);
  }


  @Override
  public void endGame() {
    if (model.isEnd()) {
      isGameActive = false;
    }
  }

  private Component getScrollablePanel(JPanel panel) {

    JScrollPane scroller = new JScrollPane(panel);
    scroller.setPreferredSize(new Dimension(600, 600));
    scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroller.getVerticalScrollBar().setUnitIncrement(20);
    scroller.getHorizontalScrollBar().setUnitIncrement(20);

    return scroller;
  }

  @Override
  public void addClickListener(DungeonGraphicController listener) {
    this.mapPanel.addClickListener(listener);
  }
}