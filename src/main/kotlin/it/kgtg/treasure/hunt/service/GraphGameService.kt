package it.kgtg.treasure.hunt.service

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.model.GameMap
import it.kgtg.treasure.hunt.model.Graph
import it.kgtg.treasure.hunt.model.Point
import it.kgtg.treasure.hunt.provider.GameMapProvider
import it.kgtg.treasure.hunt.service.graph.GraphSearchProvider
import it.kgtg.treasure.hunt.utils.Loggable
import it.kgtg.treasure.hunt.utils.MapPoint
import it.kgtg.treasure.hunt.utils.getIntAtIndex
import org.springframework.stereotype.Service

/**
 * Represents game service which uses graph representation of the map in order to find treasure path. It performs search
 * using one of implemented algorithms (BFS or DFS) based on the chosen one in [GameProperties.graphSearchAlgorithm].
 * Map is provided by [GameMapProvider]. [play] method is responsible for finding the path to treasure.
 *
 * @author Kamil Gunia
 */
@Service
class GraphGameService(
    gameMapProvider: GameMapProvider,
    private val gameProperties: GameProperties,
    private val graphSearchProvider: GraphSearchProvider
) : BaseGameService(gameMapProvider) {

    private val mapToTreasurePathsCache = mutableMapOf<String, Map<Point, List<Point>>>()

    companion object: Loggable()

    override fun findTreasure(gameMap: GameMap, mapPoint: MapPoint): String {
        log.debug("Starting point: $mapPoint")
        return findTreasure(gameMap, Point(mapPoint))
    }

    private fun findTreasure(gameMap: GameMap, point: Point): String =
        mapToTreasurePathsCache
            .getOrPut(gameMap.name) { generatePathsMap(gameMap) }[point]
            ?.formatOutput()
            ?: gameProperties.missingTreasureMessage

    private fun generatePathsMap(gameMap: GameMap): Map<Point, List<Point>> {
        val (graph: Graph, pointsWithTreasure: List<Point>) = createMapGraph(gameMap)
        return getPointsEndingWithTreasure(pointsWithTreasure, graph)
    }

    private fun createMapGraph(gameMap: GameMap): Pair<Graph, List<Point>> {
        log.debug("Generating graph for map")
        val graph = Graph()
        val pointsWithTreasure: MutableList<Point> = mutableListOf()

        gameMap.map.forEachIndexed { rowIndex: Int, columns: Array<String> ->
            columns.forEachIndexed { columnIndex, cellValue ->
                val x: Int = cellValue.getIntAtIndex(0)
                val y: Int = cellValue.getIntAtIndex(1)

                if (rowIndex == x - 1 && columnIndex == y - 1) {
                    pointsWithTreasure.add(Point(x, y))
                }

                graph.addVertexWithEdge(Point(rowIndex + 1, columnIndex + 1), cellValue)
            }
        }

        log.debug("Found following points with treasure: $pointsWithTreasure")
        return graph to pointsWithTreasure.toList()
    }

    private fun getPointsEndingWithTreasure(
        pointsWithTreasure: List<Point>,
        graph: Graph
    ): Map<Point, List<Point>> {
        val pointToTreasurePath: MutableMap<Point, MutableList<Point>> = mutableMapOf()

        pointsWithTreasure.forEach { treasureLocation: Point ->
            val path: List<Point> = findFullPathLeadingToTreasure(graph, treasureLocation)
            fillWithSubPaths(pointToTreasurePath, path)
        }

        return pointToTreasurePath.toMap()
    }

    private fun fillWithSubPaths(
        pointToPath: MutableMap<Point, MutableList<Point>>,
        path: List<Point>
    ) {
        path.forEachIndexed { index: Int, point: Point ->
            val subPath: List<Point> = path.subList(index, path.size)

            pointToPath.getOrPut(point) { mutableListOf() }
                       .addAll(subPath)
        }
    }

    private fun findFullPathLeadingToTreasure(
        graph: Graph,
        treasureLocation: Point
    ): List<Point> =
        graphSearchProvider
            .provide(gameProperties.graphSearchAlgorithm)
            .traverse(graph, treasureLocation)
            .reversed()

    private fun List<Point>.formatOutput() =
        this.joinToString(separator = gameProperties.outputDelimiter)

}