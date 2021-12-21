package controller;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.game.Game;
import model.game.GameImpl;
import view.DungeonView;
import view.DungeonViewSwing;

/**
 * Class for implementing a graphical dungeon game using the java swing GUI library.
 */
public class DungeonGuiController implements DungeonGraphicController {

  private final int numMonsters;
  private final long randomSeed;
  private DungeonView view;
  private Game model;
  private int height;
  private int width;
  private int degreeOfConnectivity;
  private boolean isWrapping;
  private int percentTreasure;
  private String playerName;

  /**
   * Constructor for the graphical game class.
   */
  public DungeonGuiController() {
    this.height = 10;
    this.width = 10;
    this.degreeOfConnectivity = 0;
    this.isWrapping = false;
    this.percentTreasure = 25;
    this.numMonsters = 3;
    this.playerName = "New Player";
    randomSeed = 1212321313L;
  }

  @Override
  public void startDefaultGame() throws IOException {
    this.initiateDungeon();
    view.makeVisible();
  }

  @Override
  public void startGame(int height, int width, int degreeOfConnectivity,
                        boolean isWrapping, int percentTreasure, int numMonsters,
                        String playerName) throws IOException {
    if (view != null) {
      view.close();
    }
    if (playerName.equals("")) {
      this.playerName = "New Player";
    } else {
      this.playerName = playerName;
    }
    this.height = height;
    this.width = width;
    this.degreeOfConnectivity = degreeOfConnectivity;
    this.isWrapping = isWrapping;
    this.percentTreasure = percentTreasure;

    this.initiateDungeon();
    view.refresh();
    view.makeVisible();
  }

  @Override
  public void restart() throws IOException {
    view.close();
    this.startDefaultGame();
  }

  @Override
  public String handleMoveInput(int keyInt) {
    Map<Integer, String> moves = new HashMap<>();
    moves.put(KeyEvent.VK_UP, "N");
    moves.put(KeyEvent.VK_DOWN, "S");
    moves.put(KeyEvent.VK_LEFT, "W");
    moves.put(KeyEvent.VK_RIGHT, "E");
    String message = "";

    if (model != null && !model.isEndState()) {
      model.move(moves.get(keyInt));
      if (model.isEnd()) {
        message = "You've reached the end. You win!";
        view.endGame();
      } else if (!model.isPlayerAlive()) {
        message = "Otyugh killed you! Game Over.";
        view.endGame();
      } else if (!model.getMonsterSmell().isEmpty()) {
        message = model.getMonsterSmell();
      }
      view.refresh();
    }

    return message;
  }

  @Override
  public void handlePickTreasureInput() {
    if (model != null && !model.isEnd()) {
      model.pickUpTreasure();
      view.refresh();
    }
  }

  @Override
  public void handlePickWeaponInput() {
    if (model != null && !model.isEnd()) {
      model.pickupWeapon();
      view.refresh();
    }
  }

  @Override
  public String handleShoot(String direction, String distance) {
    String out = "";
    if (model != null && !model.isEnd()) {
      out = model.shootArrow(direction, distance);
      view.refresh();
    }
    return out;
  }

  @Override
  public String handleCellClick(int i, int j) {
    String message = "";

    if (model != null && !model.isEndState()) {
      message = model.move(i, j);
      view.refresh();
    }
    return message;
  }

  private void initiateDungeon() throws IOException {
    model = new GameImpl(height, width, degreeOfConnectivity, isWrapping, percentTreasure,
            playerName, randomSeed);
    model.addTreasuresToDungeon();
    model.addWeaponsToDungeon();
    model.addMonstersToDungeon(numMonsters);
    view = new DungeonViewSwing(model);
    view.addActionListener(this);
    view.addKeyListener(this);
    view.addClickListener(this);
  }
}
