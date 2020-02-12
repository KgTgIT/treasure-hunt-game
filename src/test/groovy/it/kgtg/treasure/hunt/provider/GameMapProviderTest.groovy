package it.kgtg.treasure.hunt.provider

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.validator.MapValidator
import spock.lang.Specification
import spock.lang.Subject

class GameMapProviderTest extends Specification {

    @Subject
    private GameMapProvider gameMapProvider

    void setup() {
        def propertiesMock = new GameProperties().tap {
            it.mapsDirectory = "test-maps"
            it.mapRows = 5
            it.mapCols = 5
            it.mapColumnDelimiter = " "
        }

        MapValidator validatorStub = Stub(MapValidator, constructorArgs: [
            propertiesMock
        ]) as MapValidator

        gameMapProvider = new GameMapProvider(
            propertiesMock,
            validatorStub
        )
        gameMapProvider.initialize()
    }

    def "Should throw exception due to missing map file"() {
        given: "Path to a file instead of map directory"
        def propertiesMock = new GameProperties().tap {
            it.mapsDirectory = "test-maps/testMap.txt"
        }

        MapValidator validatorStub = Stub(MapValidator, constructorArgs: [
            propertiesMock
        ]) as MapValidator

        def gameMapProvider = new GameMapProvider(
            propertiesMock,
            validatorStub
        )

        when: "Initializing provider object"
        gameMapProvider.initialize()

        then: "An exception is being thrown"
        thrown(IllegalStateException)
    }

    def "Should provide 2D array loaded from specified map file"() {
        given: "Specified map file"
        String mapName = "testMap.txt"

        when: "Obtaining 2D array from map"
        def gameMap = gameMapProvider.provide(mapName)

        then: "Array has 5 rows and each row contains proper data"
        with(gameMap.map as String[][]) {
            it.length == 5

            it[0] == ["55", "14", "25", "52", "21"] as String[]
            it[1] == ["44", "31", "11", "53", "43"] as String[]
            it[2] == ["24", "13", "45", "12", "34"] as String[]
            it[3] == ["42", "22", "43", "32", "41"] as String[]
            it[4] == ["51", "23", "33", "54", "15"] as String[]
        }
    }

    def "Should provide 2D array loaded from any existing map file"() {
        given: "Unspecified map file name"
        String mapName = null

        when: "Obtaining 2D array"
        def gameMap = gameMapProvider.provide(mapName)

        then: "Array has 5 rows and each row contains proper data"
        with(gameMap.map as String[][]) {
            it.length == 5

            it[0] == ["55", "14", "25", "52", "21"] as String[]
            it[1] == ["44", "31", "11", "53", "43"] as String[]
            it[2] == ["24", "13", "45", "12", "34"] as String[]
            it[3] == ["42", "22", "43", "32", "41"] as String[]
            it[4] == ["51", "23", "33", "54", "15"] as String[]
        }
    }
}
