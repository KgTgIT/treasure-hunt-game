package it.kgtg.treasure.hunt.service.graph

import it.kgtg.treasure.hunt.model.Graph
import it.kgtg.treasure.hunt.model.Point
import spock.lang.Specification
import spock.lang.Subject

class DepthFirstSearchTest extends Specification {

    @Subject
    private DepthFirstSearch search = new DepthFirstSearch()

    private Graph graph

    void setup() {
        graph = new Graph().tap {
            it.addVertexWithEdge(new Point(1, 1), "55")
            it.addVertexWithEdge(new Point(1, 2), "14")
            it.addVertexWithEdge(new Point(1, 3), "25")
            it.addVertexWithEdge(new Point(1, 4), "52")
            it.addVertexWithEdge(new Point(1, 5), "21")
            it.addVertexWithEdge(new Point(2, 1), "44")
            it.addVertexWithEdge(new Point(2, 2), "31")
            it.addVertexWithEdge(new Point(2, 3), "11")
            it.addVertexWithEdge(new Point(2, 4), "53")
            it.addVertexWithEdge(new Point(2, 5), "43")
            it.addVertexWithEdge(new Point(3, 1), "24")
            it.addVertexWithEdge(new Point(3, 2), "13")
            it.addVertexWithEdge(new Point(3, 3), "45")
            it.addVertexWithEdge(new Point(3, 4), "12")
            it.addVertexWithEdge(new Point(3, 5), "34")
            it.addVertexWithEdge(new Point(4, 1), "42")
            it.addVertexWithEdge(new Point(4, 2), "22")
            it.addVertexWithEdge(new Point(4, 3), "43")
            it.addVertexWithEdge(new Point(4, 4), "32")
            it.addVertexWithEdge(new Point(4, 5), "41")
            it.addVertexWithEdge(new Point(5, 1), "51")
            it.addVertexWithEdge(new Point(5, 2), "23")
            it.addVertexWithEdge(new Point(5, 3), "33")
            it.addVertexWithEdge(new Point(5, 4), "54")
            it.addVertexWithEdge(new Point(5, 5), "15")
        }
    }

    def "Should perform Depth First Traversal"() {
        given: "Vertex of the graph representing a point on map with treasure"
        def point = new Point(4, 3)

        when: "Traversing the graph"
        def points = search.traverse(graph, point)

        then: "Proper path to treasure is being returned"
        points.size() == 15
    }

    def "Should return proper algorithm type"() {
        when: "Creating an instance"
        search = new DepthFirstSearch()

        then: "Proper algorithm type is being returned"
        search.algorithmName() == SearchAlgorithm.DFS
    }

}
