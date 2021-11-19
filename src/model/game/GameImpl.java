package model.game;

import model.dungeon.Dungeon;
import model.dungeon.GameMap;

/**
 * Class for implementing methods in the Game interface. provides methods to interact with the
 * model.dungeon and mutate its state according to the input.
 */
public class GameImpl implements Game {

  private final GameMap dungeon;

  /**
   * Constructor which takes in all the attributes required to create the Dungeon object.
   *
   * @param height                    height of the model.dungeon.
   * @param width                     width of the model.dungeon.
   * @param degreeOfInterconnectivity degree of interconnectivity.
   * @param isWrapping                whether the model.dungeon is wrapping or not.
   * @param percentTreasure           %age of caves which contain treasure.
   * @param playerName                Name of the Player.
   */
  public GameImpl(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                  int percentTreasure, String playerName) {
    if (height + width < 7 || height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Please give a bigger size for the model.dungeon");
    } else if (degreeOfInterconnectivity < 0) {
      throw new IllegalArgumentException("Degree of connectivity less than 0 not possible");
    } else if (percentTreasure < 0 || percentTreasure > 100) {
      throw new IllegalArgumentException("Percentage of treasure should be between 0 and 100");
    }

    this.dungeon = new Dungeon(height, width, degreeOfInterconnectivity,
            isWrapping, percentTreasure, playerName);
  }

  @Override
  public void move(String direction) {
    switch (direction.toLowerCase()) {
      case "n":
        if (dungeon.getPossibleMoves().isNorth()) {
          dungeon.playerMoveNorth();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      case "s":
        if (dungeon.getPossibleMoves().isSouth()) {
          dungeon.playerMoveSouth();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      case "w":
        if (dungeon.getPossibleMoves().isWest()) {
          dungeon.playerMoveWest();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      case "e":
        if (dungeon.getPossibleMoves().isEast()) {
          dungeon.playerMoveEast();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }

  @Override
  public String dumpDungeon() {
    return dungeon.toString();
  }

  @Override
  public String getMovesForPlayer() {
//    String result = "Possible moves (Press N, S, E, W to move. Press Q to quit.): ";
//    result += model.dungeon.getPossibleMoves().toString();

    return dungeon.getPossibleMoves().toString();
  }

  @Override
  public String getStatusLocation() {
    return dungeon.getLocationStatus();
  }

  @Override
  public String getStatusPlayer() {
    return dungeon.getPlayerStatus();
  }

  @Override
  public boolean isEnd() {
    return dungeon.checkIfPlayerAtEndLocation() && dungeon.checkPlayerState();
  }

  @Override
  public void addTreasuresToDungeon() {
    this.dungeon.addTreasures();
  }

  @Override
  public void pickUpTreasure() {
    this.dungeon.collectTreasureForPlayer();
  }

  @Override
  public GameMap getDungeon() {
    return dungeon;
  }

  @Override
  public void addMonstersToDungeon(int num) {
    this.dungeon.generateMonsters(num);
  }

  @Override
  public void addWeaponsToDungeon() {
    this.dungeon.addWeapons();
  }

  @Override
  public void pickupWeapon() {
    this.dungeon.collectWeaponForPlayer();
  }

  @Override
  public boolean isPlayerAlive() {
    return this.dungeon.checkPlayerState();
  }

  @Override
  public String shootArrow(String direction, String distance) {
    int dist = 0;
    try {
      dist = Integer.parseInt(distance);
      if (dist <= 0 || dist > 5) {
        throw new IllegalArgumentException("Please enter an integer between 1-5");
      }
    } catch (NumberFormatException nfe) {
      System.out.println("Please enter an integer between 1-5");
    }
    switch (direction.toLowerCase()) {
      case "n":
        if (dungeon.getPossibleMoves().isNorth()) {
          return dungeon.playerShootArrowNorth(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      case "s":
        if (dungeon.getPossibleMoves().isSouth()) {
          return dungeon.playerShootArrowSouth(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      case "w":
        if (dungeon.getPossibleMoves().isWest()) {
          return dungeon.playerShootArrowWest(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      case "e":
        if (dungeon.getPossibleMoves().isEast()) {
          return dungeon.playerShootArrowEast(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }

  @Override
  public String getMonsterSmell() {
    return this.dungeon.getSmellAtPlayerLocation();
  }
}
