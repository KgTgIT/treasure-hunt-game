package it.kgtg.treasure.hunt.service

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.model.GameMap
import it.kgtg.treasure.hunt.provider.GameMapProvider
import it.kgtg.treasure.hunt.validator.MapValidator
import spock.lang.Specification
import spock.lang.Subject

class RecursionGameServiceTest extends Specification {

    @Subject
    private RecursionGameService gameService

    private GameProperties gamePropertiesMock

    void setup() {
        String[][] map = [
            ["55", "14", "25", "52", "21"] as String[],
            ["44", "31", "11", "53", "43"] as String[],
            ["24", "13", "45", "12", "34"] as String[],
            ["42", "22", "43", "32", "41"] as String[],
            ["51", "23", "33", "54", "15"] as String[]
        ]

        gamePropertiesMock = new GameProperties()
        gamePropertiesMock.missingTreasureMessage = "MISSING TREASURE"
        gamePropertiesMock.outputDelimiter = "\n"

        def mapValidatorStub = Stub(MapValidator, constructorArgs: [
            gamePropertiesMock
        ])

        def gameMapProviderStub = Stub(GameMapProvider, constructorArgs: [
            gamePropertiesMock,
            mapValidatorStub
        ]) {
            provide(_ as String) >> new GameMap(map, "testMap")
            provide(null) >> new GameMap(map, "defaultMap")
        } as GameMapProvider

        gameService = new RecursionGameService(gameMapProviderStub, gamePropertiesMock)
    }

    def "Should find treasure at position 43 when starting point is 11"() {
        given: "A starting point at 11"
        String startingPoint = "11"

        when: "Finding treasure"
        def result = gameService.play(startingPoint)

        then: "Proper path to treasure is being returned"
        result == "11\n" +
                  "55\n" +
                  "15\n" +
                  "21\n" +
                  "44\n" +
                  "32\n" +
                  "13\n" +
                  "25\n" +
                  "43"
    }

    def "Should not find treasure when starting point is 42 due to circular path"() {
        given: "Starting point at 42"
        String startingPoint = "42"

        when: "Finding treasure"
        def result = gameService.play(startingPoint)

        then: "Missing treasure message is being returned"
        result == gamePropertiesMock.missingTreasureMessage
    }

}
