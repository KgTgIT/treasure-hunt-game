package it.kgtg.treasure.hunt.service.graph

import it.kgtg.treasure.hunt.model.Graph
import it.kgtg.treasure.hunt.model.Point
import org.springframework.stereotype.Service
import java.util.*

/**
 * Performs graph traversal according to Breadth First Search (BFS) algorithm.
 *
 * @author Kamil Gunia
 */
@Service
class BreadthFirstSearch : Search {

    override fun traverse(graph: Graph, root: Point): Set<Point> {
        val visited: MutableSet<Point> = mutableSetOf<Point>().apply { this.add(root) }
        val queue: Queue<Point> = LinkedList<Point>().apply { this.add(root) }

        while (queue.isNotEmpty()) {
            val vertex: Point = queue.poll()

            for (v: Point in graph.getVertices(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v)
                    queue.add(v)
                }
            }
        }

        return visited
    }

    override fun algorithmName(): SearchAlgorithm = SearchAlgorithm.BFS

}