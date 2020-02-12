package it.kgtg.treasure.hunt.service.graph

import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

/**
 * Provider for graph search algorithms.
 *
 * @author Kamil Gunia
 */
@Service
class GraphSearchProvider(
    private val searchAlgorithms: List<Search>
) {

    private lateinit var algorithmNameToSearchClass: Map<SearchAlgorithm, Search>

    @PostConstruct
    fun initialize() {
        algorithmNameToSearchClass = searchAlgorithms
            .map { it.algorithmName() to it }
            .toMap()
    }

    /**
     * Provides implementation of the graph search algorithm for specified name.
     *
     * @param searchAlgorithm the name of the search algorithm
     */
    fun provide(searchAlgorithm: SearchAlgorithm): Search =
        algorithmNameToSearchClass[searchAlgorithm]
            ?: error("Could not find implementation of specified algorithm: $searchAlgorithm")

}