package it.kgtg.treasure.hunt.controller

import io.micronaut.http.annotation.Controller
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.validation.Valid
import javax.validation.constraints.Pattern
import io.micronaut.validation.Validated
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import it.kgtg.treasure.hunt.service.GraphGameService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Represents REST controller for Treasure Hunt game that uses graph processing.
 *
 * @author Kamil Gunia
 */
@Validated
@RestController
@Controller("games/treasure-hunt/v2")
class GraphController(
    private val gameService: GraphGameService
) {

    /**
     * Returns path to the treasure or 'NO TREASURE'. It uses graph processing in order to find the path.
     *
     * @param startingPoint represents the coordinates on the map, e.g 11 means x=1, y=1 which is the default value
     * @return path to treasure or 'NO TREASURE'
     */
    @ApiResponses(
        ApiResponse(content = [Content(mediaType = "application/json", schema = Schema(type = "string"))]),
        ApiResponse(responseCode = "400", description = "Bad request (e.g. invalid with regexp d{2})"),
        ApiResponse(responseCode = "200", description = "Successful request")
    )
    @GetMapping("/play")
    fun play(
        @Valid
        @Pattern(regexp = "\\d{2}", message = "Both values of starting point (xy) must be a number between 1 and 5!")
        @RequestParam(value = "startingPoint", defaultValue = "11")
        startingPoint: String
    ): ResponseEntity<String> {
        val response = gameService.play(startingPoint)
        return ResponseEntity(response, HttpStatus.OK)
    }

}