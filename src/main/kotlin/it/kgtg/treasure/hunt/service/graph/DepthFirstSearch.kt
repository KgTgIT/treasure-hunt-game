package it.kgtg.treasure.hunt.service.graph

import it.kgtg.treasure.hunt.model.Graph
import it.kgtg.treasure.hunt.model.Point
import org.springframework.stereotype.Service
import java.util.*

/**
 * Performs graph traversal according to Depth First Search (DFS) algorithm.
 *
 * @author Kamil Gunia
 */
@Service
class DepthFirstSearch : Search {

    override fun traverse(graph: Graph, root: Point): Set<Point> {
        val visited: MutableSet<Point> = LinkedHashSet()
        val stack: Stack<Point> = Stack<Point>().apply { this.push(root) }

        while (stack.isNotEmpty()) {
            val vertex: Point = stack.pop()

            if (!visited.contains(vertex)) {
                visited.add(vertex)

                for (v: Point in graph.getVertices(vertex)) {
                    stack.push(v)
                }
            }
        }

        return visited
    }

    override fun algorithmName(): SearchAlgorithm = SearchAlgorithm.DFS

}