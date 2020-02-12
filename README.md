# Treasure Hunt Game

## Specification

|  34 |  21 |  32 |  41 |  25 |
|-----|-----|-----|-----|-----|
|  14 |  42 |  43 |  14 |  31 |
|  54 |  45 |  52 |  42 |  23 |
|  33 |  15 |  51 |  31 |  35 |
|  21 |  52 |  33 |  13 |  23 |

You are going to write a program to explore the above table for a treasure.
The values in the table are clues. Each cell contains a number between 11 and 55,
where the ten’s digit represents the row number and the unit’s digit represents
the column number of the cell containing the next clue. Starting with the upper
left corner (at 1,1), use the clues to guide your search through the
table - (the first three clues are 34, 42, 15). The treasure is a cell whose
value is the same as its coordinates. Your program must first read in the
treasure map data into a 5 by 5 array.

### Preferred Languages
Python, Groovy, Scala, Ruby, (Vanilla) Javascript

### Input Format
Input contains five lines each containing five space separated integers.

### Output Format
If the treasure is found, your program should output the index (row, column)
of cells it visits during its search for treasure (separated by a single space).
Cells must be separated by a newline “\n”. If there is no treasure, print “NO TREASURE”

### Implementation
Write two different implementations. The first should use a functional programming
approach (closures, native data structures). The second implementation should
be implemented in an object oriented way (object models, simple oo patterns).
One of the implementations should be coded with recursion, the other without recursion.

For non javascript:

Read input from STDIN. Print output to STDOUT. Do not use external libraries.

For Javascript:

Input is a list of tuples (array of arrays), while the output can be logged to the console.

### Sample Input
    55 14 25 52 21
    44 31 11 53 43
    24 13 45 12 34
    42 22 43 32 41
    51 23 33 54 15

### Sample Output
    11
    55
    15
    21
    44
    32
    13
    25
    43

## Additional Requirements
Expose the implementation with an endpoint that accepts the start coordinate as an HTTP GET parameter.
Underneath it should use the provided "sample input" (treasure map) to compute and return the solution as a JSON response.
Please also write tests for your implementation(s).

Use following technologies:
- Micronaut
- Groovy

# Solution

In my solution I chosen Kotlin instead of Groovy. I did that since it has similar syntax,
far better API, provides null safe fields (you decide whether it can be nullable) and
has coroutines which I planned to use. Finally, GraalVM supports only java and Kotlin.
It is also very well supported by Micronaut, Spring or Gradle. Nevertheless, Groovy is
present and is used in tests that are written in Spock.

There are some points that still can be improved (see [Further work](#future-work)).

## Recursive solution
It is not an optimal one. Although it has proper braking point and works even with circular paths
that are being detected properly. In such situations "NO TREASURE" message is being returned.

## Optimal solution

The optimal solution use graph processing. I created two classes:
* Point - that represents a point on map (and vertex of the graph)
* Graph - that represent graph of paths between map points

The map is being loaded to the Graph structure. During this process treasure points
are being found. Later on a graph traversal algorithms run on points with treasure
to find the path leading to them. Next the path is being split to sub paths in
order to create a map of all possible starting points to paths with treasure.
Lastly the map is being checked if it contains the value provided by player.
The map is generated just once for each map and give us O(1) response times.

I implemented just two common graph search algorithms BFS and DFS. But there are
far better ones like Dijkstra's and A* algorithms that can further improve the search.

## Running

Simply execute the **run.sh** file. Once application is up, open in browser following URL:

Recursive version:

[http://localhost:8080/games/treasure-hunt/v1/play?startingPoint=11]()

Graph version:

[http://localhost:8080/games/treasure-hunt/v2/play?startingPoint=11]()

And play the game.

Project is integrated with Swagger. Documentation of services can be found under:

[http://localhost:8080/swagger/treasure-hunt-game-1.0.yml]()

## Stack

* Kotlin
* Micronaut
* Spring Boot (Micronaut reads all Spring annotations properly and by not polluting source code with its
 annotation we have an option to use something else, like Quarkus)
* Groovy (for tests)
* Spock (with Spock Reports available under build/spock-reports/index.html)
* Gradle (with Kotlin DSL)
* Swagger (for documentation of the endpoint)

## Configuration

All configuration was externalized to application.yml (read by **GameProperties** class). Default values:
```yml
game:
  maps-directory: "maps"
  map-rows: 5
  map-cols: 5
  map-column-delimiter: " "
  output-delimiter: "\n"
  missing-treasure-message: "NO TREASURE"
  graph-search-algorithm: "BFS"
```
Where:
* **game.maps-directory** is the location of folder holding maps. When it cannot be found, a
fallback to classpath is used.
* **game.map-rows** specifies how many rows should a map have and is being used for validation
* **game.map-cols** specifies how many cols should a map have and is being used for validation
* **game.map-column-delimiter** specifies the separator between columns values
* **game.output-delimiter** specifies the separator used in response
* **game.missing-treasure-message** specifies the message returned to the player when path cannot
be found
* **graph-search-algorithm** represents the graph search algorithm. Supported algorithms are being
stored in **SearchAlgorithm** enum.

## Package Structure

The main package (it.kgtg.treasure.hunt) holds following classes:

- **Application.kt** which represents the starting point of this application

It also have following subpackages:

- **config**

This package stores configuration. Currently it holds only **GameProperties** that read properties.
- **controller**

This package holds two controllers. **GraphController** represents the solution that uses graphs.
The **RecursionController** represents the solution that uses recursion.
- **error**

Holds **GameExceptions.kt** file that contain error classes and utilities.
- **model**

Holds two data classes - **Point** (which is represents point of map and is also treated as vertex) and **Graph**.
- **provider**

Holds **GameMapProvider** that provide maps (currently loaded from directory specified in properties).
- **service**

Stores two implementations of services that use graph or recursion. The subpackage graph holds
two implementations of graph search algorithms (BFS and DFS).
- **utils**

Some utilities. The **TypeAliases.kt** file has configuration of type aliases for
better readability of variables. Thanks to this syntactic sugar wrapping classes were not needed.
The **Loggable** represents base class of companion objects that enable logging support.
The **GameExtensions.kt** is used to keep common extensions.

- **validator**

Stores a simple validator for game maps. It verifies the content of cells and number of
rows and columns.

## Future work

- make use of coroutines
- support map of any size
- support multiple maps (simple change in endpoint is required), e.g:
`http://localhost:8080/games/treasure-hunt/v1/maps/map1/play?startingPoint=11`
- implement other graph search algorithms (A*, Dijkstra's)

## Known Issues

Test do not run during build. Lately there is some kind of bug in micronaut
that prevents spock tests from running. Therefore you need to run them from Intellij.