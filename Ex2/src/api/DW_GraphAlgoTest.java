package api;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DW_GraphAlgoTest {
    DW_GraphAlgo algo;
    DW_Graph graph;
    NodeData n1 = new myNode(1);
    NodeData n2 = new myNode(2);
    NodeData n3 = new myNode(3);

    String str = "data/G1.json";

    @BeforeEach
    void setup() {
            algo = new DW_GraphAlgo();
            graph = new DW_Graph();
            algo.init(graph);
    }

    @org.junit.jupiter.api.Test
    void copy() {
        DirectedWeightedGraph copied;
        copied = algo.copy();
        assertNotSame(graph,copied);
    }

    @org.junit.jupiter.api.Test
    void isConnected() {
        graph.addNode(n1);
        assertTrue(algo.isConnected());

        graph.addNode(n2);
        graph.addNode(n3);
        graph.connect(1,2,10);
        graph.connect(2,3,10);
        assertFalse(algo.isConnected());

        graph.connect(3,1,10);
        assertTrue(algo.isConnected());

        graph.removeEdge(1,2);
        assertFalse(algo.isConnected());

        graph.connect(1,3,10);
        graph.connect(3,2,10);
        assertTrue(algo.isConnected());
    }

    @org.junit.jupiter.api.Test
    void shortestPathDist() {
        double check;

        for(int i = 1; i<=10; i++) {
            NodeData temp = new myNode(i);
            graph.addNode(temp);
        }
        graph.connect(1,2,1);
        graph.connect(2,3, 1);
        graph.connect(3,4,1);
        graph.connect(4,5,1);
        graph.connect(5,10,1);

        graph.connect(1,6,1);
        graph.connect(6,7,1);
        graph.connect(7,8,1);
        graph.connect(8,9,1);
        graph.connect(9,10,2);

        check = 5;
        double ret = algo.shortestPathDist(1,10);
        assertEquals(check, ret);
        graph.removeNode(5);
        assertEquals(check, ret);
    }

    @org.junit.jupiter.api.Test
    void shortestPath() {
        for(int i = 1; i<=10; i++) {
            NodeData temp = new myNode(i);
            graph.addNode(temp);
        }
        graph.connect(1,2,1);
        graph.connect(2,3, 1);
        graph.connect(3,4,1);
        graph.connect(4,5,1);
        graph.connect(5,10,1);

        graph.connect(1,6,10);
        graph.connect(6,7,20);
        graph.connect(7,8,30);
        graph.connect(8,9,40);
        graph.connect(9,10,50);

        LinkedList<NodeData> list = new LinkedList<>();
        for(int i = 1; i<=5; i++ ) {
            NodeData temp = graph.getNode(i);
            list.add(temp);
        }
        NodeData last = graph.getNode(10);
        list.add(last);
        assertEquals(list,algo.shortestPath(1,10));
        list.pollLast();
        assertNotEquals(list,algo.shortestPath(1,10));
    }

    @org.junit.jupiter.api.Test
    void center() {
        algo = new DW_GraphAlgo();
        algo.load(str);
        int ans = algo.center().getKey();
        assertEquals(8,ans);
        str = "data/G2.json";
        algo.load(str);
        ans = algo.center().getKey();
        assertEquals(0,ans);
        str = "data/G3.json";
        algo.load(str);
        ans = algo.center().getKey();
        assertEquals(40,ans);
    }

    @org.junit.jupiter.api.Test
    void tsp() {
        algo = new DW_GraphAlgo();
        algo.load(str);
        List<NodeData> cities = new LinkedList<>();
        cities.add(algo.getGraph().getNode(3));
        cities.add(algo.getGraph().getNode(5));
        cities.add(algo.getGraph().getNode(9));
        List<NodeData> check = new LinkedList<>();
        check.add(algo.getGraph().getNode(9));
        check.add(algo.getGraph().getNode(8));
        check.add(algo.getGraph().getNode(7));
        check.add(algo.getGraph().getNode(6));
        check.add(algo.getGraph().getNode(2));
        check.add(algo.getGraph().getNode(5));
        List<NodeData> ret = algo.tsp(cities);
        assertEquals(ret, check);

    }

    @org.junit.jupiter.api.Test
    void save() {
        algo = new DW_GraphAlgo();
        DW_Graph g  = new DW_Graph();
        myNode n1 = new myNode(1);
        myNode n2 = new myNode(2);
        myNode n3 = new myNode(3);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.connect(n1.getKey(),n3.getKey(), 1);
        algo.save("data/check");
    }

    @org.junit.jupiter.api.Test
    void load() {
        algo.load("data/G1.json");
        if (algo.getGraph().getNode(10).getWeight() == 1.5815006562559664) {
            System.out.println(algo.getGraph().getNode(10).getWeight());
            System.out.println("load complete!");
        }
    }

}