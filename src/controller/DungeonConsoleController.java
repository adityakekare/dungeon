package controller;

import java.io.IOException;
import java.util.Scanner;

import model.game.Game;

/**
 * This class implements the DungeonController interface. It is a console controller which
 * implements the model game using the console as output and input.
 */
public class DungeonConsoleController implements DungeonController {

  private final Appendable out;
  private final Scanner scanner;

  /**
   * Constructor to initialize the controller with input stream and output stream.
   *
   * @param in  input stream.
   * @param out output stream.
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scanner = new Scanner(in);
  }

  @Override
  public void startGame(Game game) {
    if (game == null) {
      throw new IllegalArgumentException("Invalid model passed");
    }

    try {
      String smell = game.getMonsterSmell();
      if (!smell.isEmpty()) {
        out.append(smell).append("\n");
      }
      out.append(game.getStatusLocation());
      out.append(game.getMovesForPlayer()).append("\n");

      while (game.isPlayerAlive()) {
        out.append(game.isEnd() ? "Move, Pickup, Shoot, or End (M-P-S-E)? \n" :
                "Move, Pickup, or Shoot (M-P-S)? \n");
        String move = scanner.nextLine();
        if (move.equalsIgnoreCase("m") || move.equalsIgnoreCase("s")) {
          out.append("Where? \n");
          String direction = scanner.nextLine();
          if (move.equalsIgnoreCase("m")) {
            try {
              game.move(direction);
            } catch (IllegalArgumentException exception) {
              out.append(exception.getMessage()).append(". Try again.\n");
              continue;
            }
          } else {
            out.append("No. of caves (1-5)? \n");
            String distance = scanner.nextLine();
            try {
              out.append(game.shootArrow(direction, distance)).append("\n");
            } catch (IllegalArgumentException iae) {
              out.append(iae.getMessage()).append(". Try again.\n");
              continue;
            }
          }
        } else if (move.equalsIgnoreCase("p")) {
          out.append("What?(treasure or arrow) \n");
          String item = scanner.nextLine();
          if (item.equalsIgnoreCase("treasure")) {
            game.pickUpTreasure();
          } else if (item.equalsIgnoreCase("arrow")) {
            game.pickupWeapon();
          } else {
            out.append("Please enter a valid input" + ". Try again.\n");
            continue;
          }
        } else if (move.equalsIgnoreCase("e")) {
          try {
            if (game.isEnd()) {
              break;
            } else {
              throw new IllegalArgumentException("Invalid Input");
            }
          } catch (IllegalArgumentException iae) {
            out.append(iae.getMessage()).append("\n");
            continue;
          }
        } else if (move.equalsIgnoreCase("q")) {
          out.append("Quitting game...\n");
          break;
        } else {
          out.append("Please enter a valid input" + ". Try again.\n");
          continue;
        }
        smell = game.getMonsterSmell();
        out.append("\n");
        if (!smell.isEmpty()) {
          out.append(smell).append("\n");
        }
        out.append(game.getStatusPlayer()).append("\n");
        out.append(game.getStatusLocation());
        out.append(game.getMovesForPlayer()).append("\n");
      }

      if (game.isEnd()) {
        out.append("\nPlayer wins!\n");
      } else if (!game.isPlayerAlive()) {
        out.append("\nOtyugh ate you for dinner tonight.\nBetter luck next time!\n");
      } else {
        out.append("\nGame aborted.\n");
      }

    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }

  }
}
