#Project 4: Text-based Adventure Game

###Overview
The program demonstrates a game where a player can navigate their way through a dungeon, collect 
treasures, and slay monsters. The dungeon consists of caves and tunnels. The player can find and 
collect arrows in the game. The arrows can be used to kill monsters. The monster can kill the 
player, if the player visits its cave. The player starts at a random cave in the dungeon and has 
the goal of reaching a random end cave. The player also starts with 3 arrows. If the player dies, 
the game ends and player loses. When the player reaches the end cave, they have an option to end 
the game. If the player ends the game here, the player wins.

###Features
- The program provides the feature to create a dungeon of specified size.
- It allows the user to decide the density of treasures and arrows in the dungeon by taking 
percentage of caves containing the collectibles as input.
- It also asks the user to give the degree of connectivity and the name of the player as input.
- The user can provide the number of monsters to be added in the dungeon.
- The user can opt for the dungeon to be a wrapping one.
- The program creates a dungeon and allows the user to navigate as the player using command
  line inputs.
- During the game, the player can move between caves and tunnels, collect treasures, collect arrows,
and shoot arrows to kill monsters.
- The user can quit the game using "Q" as input.

###How to run:
To run the jar, use the following command on cmd:
> java -jar "path to jar file"

###How to use the program:
User can interact with the program using command line to move the player. The program accepts the
following character inputs:
- M - Move player.
  - N - Move player north of current location by 1 unit.
  - S - Move player south of current location by 1 unit.
  - W - Move player west of current location by 1 unit.
  - E - Move player east of current location by 1 unit.
- P - Pick up treasure/ arrows.
  - treasure - pick up treasure.
  - arrow - pick up arrows.
- S - Shoot arrow.
  - N - Shoot an arrow in the north direction.
  - S - Shoot an arrow in the south direction.
  - W - Shoot an arrow in the west direction.
  - E - Shoot an arrow in the east direction.
  - Numbers (1-5) - distance as measured by number of caves.
- Q - Quit the game.

**Please pay attention to the console messages which describe the possible moves from the current
position. All other moves are invalid.**

###Description of Example runs:

#####Program run 1:
In this sample run, the player navigates through the dungeon, collects treasures and arrows. The
player kills multiple Otyugh monsters. The player reaches the end location multiple times. In the
end the player comes to the end location and ends the game. The player wins!

#####Program run 2:
The player navigates the dungeon, collects treasures and arrows. The player visits a cave where
an Otyugh is present, and the player dies. The player loses!

#####Program run 3:
This sample run demonstrates the program's response to invalid inputs. The player navigates through
the dungeon, reaches the end location and ends the game.

###Design changes:
There are a few changes from the previous design. The treasure, monster and weapon components have
been reworked. Now there is an interface called Inventory which provides methods to implement
container classes for holding treasure/ monster/ weapon. All these container classes are part
of the abstract location in a map data structure. The inventory types are stored as an enum, which
is the key type of the map. The tunnel has privileges to hold only the weapon (only arrow type 
present for now), while caves can hold weapon, treasure as well as monster. Monster holder can
hold only one monster at a time.
Other than this, controller has been added to implement the game using the model.

###Assumptions:

- Whenever a player picks up treasure or arrows from a cave, he picks up all the gems/ all the 
arrows at once.
- Dungeon size has constraints. The sum of height and width of the dungeon cannot be less than
  7. This constraint is applied to eliminate the complexity in finding a MST in the dungeon.
- The player can move only in 4 directions from a location (if paths are available): north, south,
  east and west.
- Tunnels do not contain treasures or monsters.
- Tunnels cannot be start or end points of the dungeon.
- Arrows only hit the monster, if the monster lies at the exact distance away from the player 
location. If the distance isn't accurate, the arrow misses.
- Arrows can't be shot in the same cave.
- If the player is not immediately killed by an injured monster, and he/she sticks around to collect 
collectibles, the monster attempts to kill the player again.
- Game doesn't end when the player reaches the end cave. It only ends if the player selects the end
option.
- If the player quits the game at the end location, they still win.

###Limitations:

- The player can move in 4 directions only.
- Dungeon has size constraints: height + width >= 7.
- The player cannot pick up multiple treasures one by one.
- The player cannot pick up arrows one by one.

###Citations:

- Kruskal's algorithm implemented in the program to calculate the minimum spanning tree is referred
  from the following article:
  [link](https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/)
- Breadth First Search algorithm implemented in the program to calculate the shortest distance
  between 2 locations in a dungeon is referred from the following article:
  [link](https://www.geeksforgeeks.org/shortest-path-unweighted-model.graphUtils/)
