package it.kgtg.treasure.hunt.model

import it.kgtg.treasure.hunt.utils.getIntAtIndex

/**
 * Represents a point on the map. It is also used as vertex in graph representing the map.
 *
 * @author Kamil Gunia
 */
data class Point(
    val x: Int,
    val y: Int
) {

    /**
     * Creates a point based on the clue in the cell.
     *
     * @param value the cell value from which the point will be created
     */
    constructor(value: String) : this(
        x = value.getIntAtIndex(0),
        y = value.getIntAtIndex(1)
    )

    override fun toString(): String = "$x$y"

}