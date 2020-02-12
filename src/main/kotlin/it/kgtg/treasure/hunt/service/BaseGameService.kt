package it.kgtg.treasure.hunt.service

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.model.GameMap
import it.kgtg.treasure.hunt.provider.GameMapProvider
import it.kgtg.treasure.hunt.utils.MapPoint

/**
 * Represents base class of game services.
 *
 * @author Kamil Gunia
 */
abstract class BaseGameService(
    private val gameMapProvider: GameMapProvider
) {

    /**
     * Responsible for finding the path to treasure. When a path does not exist it returns message taken from
     * [GameProperties.missingTreasureMessage] otherwise it returns moves needed to find treasure chest. The output
     * (following map points) are separated by [GameProperties.outputDelimiter] delimiter.
     *
     * @param startingPoint the starting point on map
     */
    fun play(startingPoint: MapPoint): String =
        gameMapProvider
            .provide()
            .let { gameMap: GameMap -> findTreasure(gameMap, startingPoint) }

    /**
     * Method responsible for finding treasure path.
     *
     * @param gameMap the game map
     * @param mapPoint the point on map chosen by player
     */
    abstract fun findTreasure(gameMap: GameMap, mapPoint: MapPoint): String

}