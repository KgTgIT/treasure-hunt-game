package it.kgtg.treasure.hunt.service

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.error.circularPathError
import it.kgtg.treasure.hunt.model.GameMap
import it.kgtg.treasure.hunt.provider.GameMapProvider
import it.kgtg.treasure.hunt.utils.Loggable
import it.kgtg.treasure.hunt.utils.MapPoint
import org.springframework.stereotype.Service

/**
 * Represents game service. It performs search for a path to the treasure based on map that is provided by [GameMapProvider].
 * [play] method is responsible for finding the path to treasure.
 *
 * @author Kamil Gunia
 */
@Service
class RecursionGameService(
    gameMapProvider: GameMapProvider,
    private val gameProperties: GameProperties
) : BaseGameService(gameMapProvider) {

    companion object : Loggable()

    override fun findTreasure(gameMap: GameMap, mapPoint: MapPoint): String =
        try {
            log.debug("Starting point: $mapPoint")
            val movesHistory = mutableSetOf<MapPoint>()

            findPathToTreasure(gameMap, mapPoint, movesHistory)
                .joinToString(separator = gameProperties.outputDelimiter)
        } catch (ex: Exception) {
            log.error("Failed to find the path to treasure due to ${ex.message}")
            gameProperties.missingTreasureMessage
        }

    private fun findPathToTreasure(
        gameMap: GameMap,
        point: MapPoint,
        movesHistory: MutableSet<MapPoint>
    ): MutableList<MapPoint> {
        if (movesHistory.contains(point)) {
            circularPathError(point, movesHistory.toList())
        } else {
            movesHistory.add(point)
        }

        val pathToTreasure = mutableListOf<MapPoint>()
        pathToTreasure.add(point)

        val nextPoint: MapPoint = gameMap.getNextMapPoint(point)
        log.debug("Point $point is leading to $nextPoint")

        if (nextPoint != point) {
            findPathToTreasure(gameMap, nextPoint, movesHistory)
                .also { pathToTreasure.addAll(it) }
        } else {
            log.debug("Found treasure path. It's Payday :)")
        }

        return pathToTreasure
    }

}