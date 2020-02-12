package it.kgtg.treasure.hunt.validator

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.utils.Loggable
import org.springframework.stereotype.Service
import kotlin.math.abs
import kotlin.math.log10

/**
 * A simple validator of game map structure.
 *
 * @author Kamil Gunia
 */
@Service
class MapValidator(
    private val properties: GameProperties
) {

    companion object : Loggable()

    /**
     * Performs game map validation. When any attribute of the map is invalid it throws IllegalStateException.
     *
     * @param mapRows the loaded lines of the map
     * @param mapName the name of the map
     * @throws IllegalStateException when the amount of columns and rows is incorrect or when map cell holds incorrect value
     */
    fun validate(mapRows: List<String>, mapName: String) {
        log.debug("Validating $mapName map")

        if (mapRows.size != properties.mapRows) {
            error("Map $mapName does not have ${properties.mapRows} rows!")
        }

        mapRows.forEachIndexed { index: Int, row: String ->
            val rowIndex = index + 1
            val columnValues: List<String> = row.split(properties.mapColumnDelimiter)

            if (columnValues.size != properties.mapCols) {
                error("Map $mapName does not have ${properties.mapCols} columns in $rowIndex row!")
            }

            val incorrectColumnValues = columnValues
                .filterNot { it.isNumericOfLength(2) }
                .toList()

            if (incorrectColumnValues.isNotEmpty()) {
                error("Map $mapName has following incorrect values in $rowIndex row: $incorrectColumnValues")
            }
        }
    }

    // Instead of extension functions, pattern match would be better (but less readable)
    private fun String.isNumericOfLength(expectedLength: Int): Boolean =
        try {
            val asNumber = this.toIntOrNull()
            asNumber != null && asNumber.length() == expectedLength
        } catch (ex: Exception) {
            log.error("Could not parse [$this] to Int")
            false
        }

    private fun Int.length() =
        when (this) {
            0 -> 1
            else -> log10(abs(toDouble())).toInt() + 1
        }

}