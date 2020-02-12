package it.kgtg.treasure.hunt.service.graph

import it.kgtg.treasure.hunt.model.Graph
import it.kgtg.treasure.hunt.model.Point

/**
 * Represents graph search algorithm.
 *
 * @author Kamil Gunia
 */
interface Search {

    /**
     * Performs graph traversal.
     *
     * @param graph the graph to traverse
     * @param root the starting vertex
     * @return path leading to the root
     */
    fun traverse(graph: Graph, root: Point): Set<Point>

    /**
     * The name of the algorithm specified by [SearchAlgorithm]
     *
     * @return the name of the algorithm
     */
    fun algorithmName(): SearchAlgorithm

}