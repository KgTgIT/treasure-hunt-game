package it.kgtg.treasure.hunt.model

import it.kgtg.treasure.hunt.utils.MapPoint
import it.kgtg.treasure.hunt.utils.getIntAtIndex

/**
 * Represents game map.
 *
 * @author Kamil Gunia
 */
data class GameMap(
    val map: Array<Array<String>>,
    val name: String
) {

    /**
     * Returns next point on the path for specified map point.
     *
     * @param point the point on the map
     * @return next point on the path
     */
    fun getNextMapPoint(point: MapPoint): MapPoint {
        val row: Int = point.getIntAtIndex(0) - 1
        val column: Int = point.getIntAtIndex(1) - 1
        return map[row][column]
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameMap

        if (!map.contentDeepEquals(other.map)) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = map.contentDeepHashCode()
        result = 31 * result + name.hashCode()
        return result
    }

}