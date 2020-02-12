package it.kgtg.treasure.hunt.error

import it.kgtg.treasure.hunt.utils.MapPoint

private class CircularPathException(
    point: MapPoint,
    path: List<MapPoint>
) : RuntimeException("Point $point was already visited. Found circular path:\n${path.joinToString("\n")}")

/**
 * Helper function for throwing [CircularPathException].
 *
 * @param point the map point for which this error occurred
 * @param path the circular path
 * @throws [CircularPathException]
 */
fun circularPathError(point: MapPoint, path: List<MapPoint>): Nothing = throw CircularPathException(point, path)