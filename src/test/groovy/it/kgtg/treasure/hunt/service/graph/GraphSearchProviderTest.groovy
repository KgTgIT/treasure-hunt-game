package it.kgtg.treasure.hunt.service.graph

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class GraphSearchProviderTest extends Specification {

    @Subject
    private GraphSearchProvider provider

    @Shared
    private def bfs = Stub(BreadthFirstSearch) {
        algorithmName() >> SearchAlgorithm.BFS
    }

    @Shared
    private def dfs = Stub(DepthFirstSearch){
        algorithmName() >> SearchAlgorithm.DFS
    }

    void setup() {
        provider = new GraphSearchProvider([bfs, dfs])
        provider.initialize()
    }

    @Unroll
    def "Should provide implementation for #algorithmName algorithm"() {
        expect: "Proper Implementation"
        provider.provide(algorithmName) == implementation

        where: "Choosing algorithm name"
        algorithmName       || implementation
        SearchAlgorithm.BFS || bfs
        SearchAlgorithm.DFS || dfs
    }

}
