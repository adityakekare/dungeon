import java.io.InputStreamReader;
import java.util.Scanner;

import controller.DungeonConsoleController;
import controller.DungeonController;
import model.game.Game;
import model.game.GameImpl;

/**
 * Driver class to demonstrate the runs of the program.
 */
public class Driver {
  /**
   * Main method of the program to demonstrate the runs.
   *
   * @param args cmd arguments.
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = null;

    while (true) {
      System.out.println("Please enter the size of the dungeon");
      String row = scanner.nextLine();
      String col = scanner.nextLine();

      try {
        int r = Integer.parseInt(row);
        int c = Integer.parseInt(col);

        System.out.println("Please enter the degree of interconnectivity");
        int doc = Integer.parseInt(scanner.nextLine());

        System.out.println("Is the dungeon wrapping? (Y or N)");
        String wrap = scanner.nextLine();
        boolean isWrapping;
        if (wrap.equalsIgnoreCase("y")) {
          isWrapping = true;
        } else if (wrap.equalsIgnoreCase("n")) {
          isWrapping = false;
        } else {
          throw new IllegalArgumentException("Please enter valid input");
        }

        System.out.println("Please enter the percentage of treasure & arrows");
        int percentage = Integer.parseInt(scanner.nextLine());

        System.out.println("Please enter the name of the player");
        String name = scanner.nextLine();

        System.out.println("Please enter the number of monsters in the dungeon: \n");
        int monsters = Integer.parseInt(scanner.nextLine());

        System.out.println("\nWelcome to the dungeon " + name + "!");
        game = new GameImpl(r, c, doc, isWrapping,
                percentage, name);
        game.addTreasuresToDungeon();
        game.addWeaponsToDungeon();
        game.addMonstersToDungeon(monsters);
        break;

      } catch (NumberFormatException nfe) {
        System.out.println("Please enter integer inputs");
      } catch (IllegalArgumentException iae) {
        System.out.println(iae.getMessage());
      }
    }

    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    DungeonController controller = new DungeonConsoleController(input, output);
    controller.startGame(game);
////    System.out.println(model.game.dumpDungeon());
//    System.out.print(game.getStatusLocation());
////    System.out.println(model.game.dumpDungeon());
//    System.out.println(game.getMovesForPlayer());
//
//    while (game.isPlayerAlive()) {
//      System.out.println(game.isEnd() ? "Move, Pickup, Shoot, or End (M-P-S-E)? ":
//              "Move, Pickup, or Shoot (M-P-S)? ");
//      String move = scanner.nextLine();
//      if(move.equalsIgnoreCase("m") || move.equalsIgnoreCase("s")){
//        System.out.println("Where? ");
//        String direction = scanner.nextLine();
//        if(move.equalsIgnoreCase("m")){
//          try {
//            game.move(direction);
//          } catch (IllegalArgumentException exception) {
//            System.out.println(exception.getMessage() + ". Try again.");
//            continue;
//          }
//        }
//        else{
//          System.out.println("No. of caves (1-5)? ");
//          String distance = scanner.nextLine();
//          try {
//            System.out.println(game.shootArrow(direction, distance));
//          }catch (IllegalArgumentException iae){
//            System.out.println(iae.getMessage() + ". Try again.");
//            continue;
//          }
//        }
//      }
//      else if(move.equalsIgnoreCase("p")){
//        System.out.println("What?(treasure or arrow) ");
//        String item = scanner.nextLine();
//        if(item.equalsIgnoreCase("treasure")){
//          game.pickUpTreasure();
//        }
//        else if(item.equalsIgnoreCase("arrow")){
//          game.pickupWeapon();
//        }
//        else{
//          System.out.println("Please enter a valid input" + ". Try again.");
//          continue;
//        }
//      }
//      else if(move.equalsIgnoreCase("e")){
//        try{
//          if(game.isEnd()){
//            break;
//          }
//          else{
//            throw new IllegalArgumentException("Invalid Input");
//          }
//        }catch(IllegalArgumentException iae){
//          System.out.println(iae.getMessage());
//          continue;
//        }
//      }
//      else if(move.equalsIgnoreCase("q")) {
//        System.out.println("Quitting model.game...");
//        break;
//      }
//      else{
//        System.out.println("Please enter a valid input" + ". Try again.");
//        continue;
//      }
////      System.out.println(model.game.dumpDungeon());
//      String smell = game.getMonsterSmell();
//      if(!smell.isEmpty()){
//        System.out.println(smell);
//      }
//      System.out.println(game.getStatusPlayer());
//      System.out.print(game.getStatusLocation());
//      System.out.println(game.getMovesForPlayer());
//    }
//
//    if (game.isEnd()) {
//      System.out.println("\nPlayer wins!");
//    }
//    else if(!game.isPlayerAlive()){
//      System.out.println("\nOtyugh ate you for dinner tonight.\nBetter luck next time!");
//    }
//      else {
//      System.out.println("\nGame aborted.");
//    }
  }
}
