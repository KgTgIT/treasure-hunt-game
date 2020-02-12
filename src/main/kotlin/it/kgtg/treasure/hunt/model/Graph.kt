package it.kgtg.treasure.hunt.model

import it.kgtg.treasure.hunt.utils.getIntAtIndex

/**
 * Represents a graph that is constructed from the game map. Each map point represents a vertex.
 *
 * @author Kamil Gunia
 */
data class Graph(
    private val adjacencyVertices: MutableMap<Point, MutableList<Point>> = mutableMapOf()
) {

    /**
     * Adds specified point as vertex of the graph. It also adds the edges specified by clue of the map cell.
     *
     * @param point the vertex to add
     * @param clue the vertex that can be reached from specified point
     */
    fun addVertexWithEdge(point: Point, clue: String) {
        addVertex(point)

        val x: Int = clue.getIntAtIndex(0)
        val y: Int = clue.getIntAtIndex(1)
        addEdge(point, Point(x, y))
    }

    private fun addVertex(point: Point) {
        adjacencyVertices.getOrPut(point) { mutableListOf() }
    }

    private fun addEdge(from: Point, to: Point) {
        adjacencyVertices.getValue(from).add(to)
        adjacencyVertices.getOrPut(to) { mutableListOf() }.add(from)
    }

    /**
     * Return the vertices for specified point (vertex)
     *
     * @param point the vertex
     * @return the vertices of specified point (vertex)
     */
    fun getVertices(point: Point): List<Point> =
        adjacencyVertices.getValue(point).toList()

    override fun toString(): String {
        val stringBuffer = StringBuffer()

        for (point: Point in adjacencyVertices.keys) {
            stringBuffer.append(point)
            stringBuffer.append(adjacencyVertices.getValue(point))
        }

        return stringBuffer.toString()
    }
}
