import controller.DungeonConsoleController;
import controller.DungeonController;
import java.io.InputStreamReader;
import java.util.Scanner;
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
  }
}
