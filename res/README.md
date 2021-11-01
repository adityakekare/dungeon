Project 3: Dungeon

The program demonstrates a game where a player can navigate their way through the dungeon and
collect treasures. The dungeon consists of caves and tunnels. The player starts at a random
cav in the tunnel and has the goal of reaching a random end cave. When the player reaches the
end cave, the game ends.

Features:
- The program provides the feature to create a dungeon of specified size. 
- It allows us to decide the density of treasures in the dungeon by taking percentage 
of caves containing treasures as input.
- It also allows us to input the degree of connectivity and the name of the player.
- The program creates a dungeon and allows the user to navigate as the player using command
line input.

How to run:
To run the jar, use the following command on cmd:
java -jar "path to jar file"

How to use the program:
User can interact with the program using command line to move the player. The program accepts
the following character inputs:
N -> Move player north of current location by 1 unit.
S -> Move player south of current location by 1 unit.
W -> Move player west of current location by 1 unit.
E -> Move player east of current location by 1 unit.
Q -> Quit the game.

Please pay attention to the console messages which describe the possible moves from the current
position. All other moves are invalid.

Description of Example runs provided in the "Example runs.txt" file:

Program run 1:
Dungeon type: non-wrapping
Dungeon size: 4 x 5
Degree of interconnectivity: 0
Run description: In the following run, the player traverses from start to the end of the dungeon.
The player collects treasures on the way. The player's description is displayed at every move.
The player's location is displayed in the dungeon with the notation "P".

Program run 2:
Dungeon type: wrapping
Dungeon size: 4 x 5
Degree of interconnectivity: 4
Run description: In the following run, the player traverses to each and every location in
the dungeon. The player collects treasures on the way. The player's description is displayed at
every move. The player's location is displayed in the dungeon with the notation "P".
This is a special program run where game doesn't end at End node because I wanted to demonstrate
the player visiting every location in the dungeon. The game ends with user Quitting.

Program run 3:
Dungeon type: wrapping
Dungeon size: 7 x 7
Degree of interconnectivity: 7
Run description: In the following run, the player traverses from start to the end of a
wrapping dungeon. The player collects treasures on the way. The player's description is
displayed at every move. The player's location is displayed in the dungeon with the notation "P".


Assumptions:
- Every time a player arrives at any cave which has treasures, he must pick all the treasures.
- Dungeon size has constraints. The sum of height and width of the dungeon cannot be less than 7.
This constraint is applied to eliminate the complexity in finding a MST in a dungeon graph.
- The player can move only in 4 directions from a location (if paths are available): north, south,
east and west.
- Tunnels do not contain treasures.
- Tunnels cannot be start or end points of the dungeon.

Limitations:
- The player can move in 4 directions only.
- Dungeon has size constraints: height + width >= 7.

Citations:
- Kruskal's algorithm implemented in the program to calculate the minimum spanning tree
is referred from the following article:
  https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
- Breadth First Search algorithm implemented in the program to calculate the shortest distance
between 2 locations in a dungeon is referred from the following article:
  https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
