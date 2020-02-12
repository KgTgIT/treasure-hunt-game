package it.kgtg.treasure.hunt.provider

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.model.GameMap
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import it.kgtg.treasure.hunt.validator.MapValidator
import java.io.File
import javax.annotation.PostConstruct

/**
 * Represents provider for game maps. Currently it loads them from filesystem. Probably maps should be moved to some DB.
 *
 * @author Kamil Gunia
 */
@Service
class GameMapProvider(
    private val properties: GameProperties,
    private val mapValidator: MapValidator
) {

    private lateinit var fileNameToFile: Map<String, File>
    private lateinit var defaultMap: String

    @PostConstruct
    fun initialize() {
        fileNameToFile = getDirectoryWithMaps().listFiles()
                                              ?.associateBy { it.name }
                                              ?: error("Could not find any map. The path in game.maps-directory property should lead to directory!")

        defaultMap = fileNameToFile.entries
                                   .first()
                                   .key
    }

    private fun getDirectoryWithMaps(): File =
        File(properties.mapsDirectory).takeIf { it.exists() }
                                     ?: ClassPathResource(properties.mapsDirectory).file

    /**
     * Provides map with specified name. It tries to load files from [GameProperties.mapsDirectory] with a fallback to
     * classpath. If requested map does not exist it loads first available.
     *
     * @param mapName the name of requested map
     * @return the game map as 2D Array of Strings
     */
    fun provide(mapName: String? = null): GameMap {
        val mapFileName = mapName ?: defaultMap
        return readMapRows(mapFileName)
            .map { columnValues: String -> splitToArray(columnValues) }
            .toTypedArray()
            .let { GameMap(it, mapFileName) }
    }

    private fun readMapRows(mapFileName: String): List<String> =
        fileNameToFile.getValue(mapFileName)
                      .readLines()
                      .also { mapValidator.validate(it, mapFileName) }

    private fun splitToArray(columns: String): Array<String> =
        columns.split(delimiters = *arrayOf(properties.mapColumnDelimiter), limit = properties.mapCols)
               .toTypedArray()

}