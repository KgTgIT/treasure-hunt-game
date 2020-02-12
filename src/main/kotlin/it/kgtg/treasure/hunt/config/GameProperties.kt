package it.kgtg.treasure.hunt.config

import it.kgtg.treasure.hunt.service.graph.SearchAlgorithm
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import kotlin.properties.Delegates

/**
 * Represents properties prefixed with "game" that are loaded from application.yml.
 *
 * @author Kamil Gunia
 */
@Component
@ConfigurationProperties("game")
class GameProperties {

    /** Represents the path to directory holding maps */
    var mapsDirectory: String by Delegates.notNull()

    /** Represents the number of rows in map */
    var mapRows: Int by Delegates.notNull()

    /** Represents the number of columns in map */
    var mapCols: Int by Delegates.notNull()

    /** Represents the column delimiter used in map file */
    var mapColumnDelimiter: String by Delegates.notNull()

    /** Represents the delimiter used for clues in output message */
    var outputDelimiter: String by Delegates.notNull()

    /** Represents the message displayed when treasure cannot be found */
    var missingTreasureMessage: String by Delegates.notNull()

    /** Represents the name of the graph search algorithm. Possible values are hold in [SearchAlgorithm] */
    var graphSearchAlgorithm: SearchAlgorithm by Delegates.notNull()

}