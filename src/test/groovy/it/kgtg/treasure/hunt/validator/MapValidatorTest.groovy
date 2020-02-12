package it.kgtg.treasure.hunt.validator

import it.kgtg.treasure.hunt.config.GameProperties
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class MapValidatorTest extends Specification {

    @Subject
    private MapValidator mapValidator

    void setup() {
        GameProperties gamePropertiesMock = new GameProperties()
        gamePropertiesMock.mapRows = 5
        gamePropertiesMock.mapCols = 5
        gamePropertiesMock.mapColumnDelimiter = " "

        mapValidator = new MapValidator(gamePropertiesMock)
    }

    def "Should pass map validation"() {
        given:
        def mapName = "testMap"
        List<String> mapRows = [
            "55 14 25 52 21",
            "44 31 11 53 43",
            "24 13 45 12 34",
            "42 22 43 32 41",
            "51 23 33 54 15"
        ]

        when:
        mapValidator.validate(mapRows, mapName)

        then:
        noExceptionThrown()
    }

    def "Should not pass map validation due to wrong number of rows"() {
        given:
        def mapName = "testMap"
        List<String> mapRows = [
            "55 14 25 52 21"
        ]

        when:
        mapValidator.validate(mapRows, mapName)

        then:
        thrown(IllegalStateException)
    }

    def "Should not pass map validation due to wrong number of columns"() {
        given:
        def mapName = "testMap"
        List<String> mapRows = [
            "55 14 25 52",
            "44 31 11 53",
            "24 13 45 12",
            "42 22 43 32",
            "51 23 33 54"
        ]

        when:
        mapValidator.validate(mapRows, mapName)

        then:
        thrown(IllegalStateException)
    }

    def "Should not pass map validation due to wrong cell value"() {
        given:
        def mapName = "testMap"
        List<String> mapRows = [
            "55 14 25 52 21",
            "44 31 11 53 43",
            "24 13 aa 12 34",
            "42 22 43 32 41",
            "51 23 33 54 15"
        ]

        when:
        mapValidator.validate(mapRows, mapName)

        then:
        thrown(IllegalStateException)
    }

    @Unroll
    def "Should return #length as length of number #number"() {
        expect:
        mapValidator.length(number) == length

        where:
        number || length
        0      || 1
        5      || 1
        -5     || 1
        10     || 2
        -10    || 2
        100    || 3
        10000  || 5
        -10000 || 5
    }

    @Unroll
    def "Should test whether #value value is a #expectedLength digit number"() {
        expect:
        mapValidator.isNumericOfLength(value, expectedLength) == result

        where:
        value | expectedLength || result
        "0"   | 2              || false
        "10"  | 2              || true
        "a"   | 1              || false
    }

}
