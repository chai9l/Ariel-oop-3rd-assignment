package api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DW_GraphTest {

    DW_Graph graph;
    DW_Graph graph2;
    NodeData n1 = new myNode(1);
    NodeData n2 = new myNode(2);
    NodeData n3 = new myNode(3);

    @BeforeEach
    void setup() {
        graph = new DW_Graph();
        graph2 = new DW_Graph();
    }

    @Test
    void hasEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        graph.connect(1,3,10);
        assertFalse(graph.hasEdge(1,2));
        assertTrue(graph.hasEdge(1,3));
        graph.removeEdge(1, 3);
        graph.connect(1,2,15);
        assertTrue(graph.hasEdge(1,2));
        assertFalse(graph.hasEdge(1,3));
    }

    @Test
    void addNode() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        int check = 3;
        List<NodeData> list = new LinkedList<>();
        list.addAll(graph.getV());
        assertEquals(check, list.size());
    }

    @Test
    void connect() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        graph.connect(1,3,10);
        assertFalse(graph.hasEdge(1,2));
        assertTrue(graph.hasEdge(1,3));

        graph.removeEdge(1, 3);
        graph.connect(1,2,15);
        assertTrue(graph.hasEdge(1,2));
        assertFalse(graph.hasEdge(1,3));
    }

    @Test
    void removeNode() {
        int size = 3;
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        assertEquals(graph.nodeSize(), size);
        graph.removeNode(1);
        assertEquals(graph.nodeSize(), size - 1);
        graph.removeNode(2);
        assertEquals(graph.nodeSize(), size - 2);
        graph.removeNode(3);
        assertEquals(graph.nodeSize(), size - 3);
        assertNull(graph.removeNode(1));
        assertNull(graph.removeNode(2));
        assertNull(graph.removeNode(3));
    }

    @Test
    void removeEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        graph.connect(1,3,10);
        assertTrue(graph.hasEdge(1,3));
        graph.removeEdge(1,3);
        assertFalse(graph.hasEdge(1,3));
    }

    @Test
    void nodeSize() {
        for(int i=1 ; i<=5 ; i++) {
            graph.addNode(new myNode(i));
            graph2.addNode(new myNode(i+5));
        }
        assertEquals(graph2.nodeSize(), graph.nodeSize());
        graph.removeNode(5);
        assertNotEquals(graph2.nodeSize(), graph.nodeSize());
    }

    @Test
    void edgeSize() {
        for(int i=1 ; i<=3 ; i++) {
            graph.addNode(new myNode(i));
            graph2.addNode(new myNode(i+3));
        }
        graph.connect(1,3,10);
        graph.connect(1,2,15);
        graph2.connect(4,5,20);
        graph2.connect(4,6,25);

        assertEquals(graph.edgeSize(),graph2.edgeSize());
        graph.removeEdge(1,3);
        assertNotEquals(graph.edgeSize(),graph2.edgeSize());
    }

    @Test
    void getE() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        graph.connect(1,2,10);
        graph.connect(1,3,20);

        Collection<EdgeData> g = graph.getE(1);
        Iterator<EdgeData> runner = g.iterator();
        while(runner.hasNext()) {
            EdgeData check = runner.next();
            assertNotNull(check);
        }
    }

    @Test
    void getV() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);

        graph.connect(1,2,10);
        graph.connect(1,3,20);

        Collection<NodeData> g = graph.getV();
        Iterator<NodeData> runner = g.iterator();
        while(runner.hasNext()) {
            NodeData check = runner.next();
            assertNotNull(check);
        }
    }
}