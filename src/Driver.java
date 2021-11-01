
import java.util.Scanner;

import game.Game;
import game.GameImpl;

public class Driver {
  public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome to the dungeon!\nNotations: \nP = Player\nC = Cave\nT = Tunnel\n" +
            "S = Start\nE = End\n");
    Game game = new GameImpl(7, 7, 10, true,
            30, "Aditya");
    System.out.println(game.getStatus());
    System.out.println(game.dumpDungeon());
    System.out.println(game.getMovesForPlayer());

    while(!game.isEnd()){
      String move = scanner.nextLine();
      if(move.equalsIgnoreCase("q")){
        System.out.println("Quitting game...");
        break;
      }
      try{
        game.move(move);
      }
      catch(IllegalArgumentException exception){
        System.out.println(exception.getMessage() + ". Try again.");
        continue;
      }
      System.out.println(game.dumpDungeon());
      System.out.println(game.getStatus());
      System.out.println(game.getMovesForPlayer());
    }

    if(game.isEnd()){
      System.out.println("Player wins!");
    }
    else{
      System.out.println("Game aborted.");
    }

  }
}
