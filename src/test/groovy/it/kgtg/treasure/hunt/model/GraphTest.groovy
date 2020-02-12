package it.kgtg.treasure.hunt.model

import spock.lang.Specification
import spock.lang.Subject

class GraphTest extends Specification {

    @Subject
    private Graph graph

    def "Should construct graph"() {
        given: "Empty graph"
        graph = new Graph()

        when: "Adding edges from 1st row of the map"
        graph.addVertexWithEdge(new Point(1, 1), "55")
        graph.addVertexWithEdge(new Point(1, 2), "14")
        graph.addVertexWithEdge(new Point(1, 3), "25")
        graph.addVertexWithEdge(new Point(1, 4), "52")
        graph.addVertexWithEdge(new Point(1, 5), "21")
        and: "Adding edges from 2nd row of the map"
        graph.addVertexWithEdge(new Point(2, 1), "44")
        graph.addVertexWithEdge(new Point(2, 2), "31")
        graph.addVertexWithEdge(new Point(2, 3), "11")
        graph.addVertexWithEdge(new Point(2, 4), "53")
        graph.addVertexWithEdge(new Point(2, 5), "43")
        and: "Adding edges from 3rd row of the map"
        graph.addVertexWithEdge(new Point(3, 1), "24")
        graph.addVertexWithEdge(new Point(3, 2), "13")
        graph.addVertexWithEdge(new Point(3, 3), "45")
        graph.addVertexWithEdge(new Point(3, 4), "12")
        graph.addVertexWithEdge(new Point(3, 5), "34")
        and: "Adding edges from 4th row of the map"
        graph.addVertexWithEdge(new Point(4, 1), "42")
        graph.addVertexWithEdge(new Point(4, 2), "22")
        graph.addVertexWithEdge(new Point(4, 3), "43")
        graph.addVertexWithEdge(new Point(4, 4), "32")
        graph.addVertexWithEdge(new Point(4, 5), "41")
        and: "Adding edges from 5th row of the map"
        graph.addVertexWithEdge(new Point(5, 1), "51")
        graph.addVertexWithEdge(new Point(5, 2), "23")
        graph.addVertexWithEdge(new Point(5, 3), "33")
        graph.addVertexWithEdge(new Point(5, 4), "54")
        graph.addVertexWithEdge(new Point(5, 5), "15")

        then: "No exception was thrown"
        noExceptionThrown()

        and: "Graph has two vertices at vertex represented by point(1,1)"
        with(graph.getVertices(new Point(1, 1))) {
            it.size() == 2
        }
    }
}
