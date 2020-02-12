package it.kgtg.treasure.hunt.service

import it.kgtg.treasure.hunt.config.GameProperties
import it.kgtg.treasure.hunt.model.GameMap
import it.kgtg.treasure.hunt.model.Graph
import it.kgtg.treasure.hunt.model.Point
import it.kgtg.treasure.hunt.provider.GameMapProvider
import it.kgtg.treasure.hunt.service.graph.BreadthFirstSearch
import it.kgtg.treasure.hunt.service.graph.GraphSearchProvider
import it.kgtg.treasure.hunt.service.graph.SearchAlgorithm
import it.kgtg.treasure.hunt.validator.MapValidator
import spock.lang.Specification
import spock.lang.Subject

class GraphGameServiceTest extends Specification {

    @Subject
    private GraphGameService gameService

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
        gamePropertiesMock.graphSearchAlgorithm = SearchAlgorithm.BFS

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

        def bfs = Stub(BreadthFirstSearch) {
            traverse(_ as Graph, new Point(4,3)) >> [
                new Point(4, 3),
                new Point(2, 5),
                new Point(1, 3),
                new Point(3, 2),
                new Point(4, 4),
                new Point(2, 1),
                new Point(1, 5),
                new Point(5, 5),
                new Point(1, 1),
                new Point(2, 3),
                new Point(5, 2),
                new Point(1, 4),
                new Point(1, 2),
                new Point(3, 4),
                new Point(3, 5),
            ]
        }
        def searchAlgorithms = [bfs]

        def graphSearchProvider = Stub(GraphSearchProvider, constructorArgs: [
            searchAlgorithms
        ]) {
            provide(_ as SearchAlgorithm) >> bfs
        } as GraphSearchProvider
        graphSearchProvider.initialize()

        gameService = new GraphGameService(
            gameMapProviderStub,
            gamePropertiesMock,
            graphSearchProvider
        )
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

    def "Should not find treasure when starting point is 42"() {
        given: "Starting point at 42"
        String startingPoint = "42"

        when: "Finding treasure"
        def result = gameService.play(startingPoint)

        then: "Missing treasure message is being returned"
        result == gamePropertiesMock.missingTreasureMessage
    }

}
