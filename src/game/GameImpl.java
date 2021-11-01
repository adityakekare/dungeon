package game;

import java.util.Locale;

import dungeon.Connector;
import dungeon.Dungeon;
import dungeon.GameMap;
import dungeon.Position;

public class GameImpl implements Game {

  private final GameMap dungeon;

  public GameImpl(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                  int percentTreasure, String playerName) {
    if(height + width < 7 || height <= 0 || width <= 0){
      throw new IllegalArgumentException("Please give a bigger size for the dungeon");
    }
    else if(degreeOfInterconnectivity < 0){
      throw new IllegalArgumentException("Degree of connectivity less than 0 not possible");
    }
    else if(percentTreasure < 0 || percentTreasure > 100){
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
    String result = "Possible moves (Press N, S, E, W to move. Press Q to quit.): ";
    result += dungeon.getPossibleMoves().toString();

    return result;
  }

  @Override
  public String getStatus() {
    return dungeon.getPlayerStatus();
  }

  @Override
  public boolean isEnd() {
    return dungeon.checkIfPlayerAtEndLocation();
  }
}
