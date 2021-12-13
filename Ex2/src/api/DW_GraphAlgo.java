package api;

import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class DW_GraphAlgo implements DirectedWeightedGraphAlgorithms{

    DW_Graph graph = new DW_Graph();
    private Queue<NodeData> que;                           //isConnected Method
    private HashSet<NodeData> checkNodes;                  //isConnected
    private HashSet<NodeData> transposeCheckNodes;                  //isConnected

    private Map<NodeData, Boolean> vis;                    //visited
    private Map<NodeData, NodeData> prev;                 //parents
    private PriorityQueue<NodeData> pQue;                  //for shortest path as list


    public static class CompareDis implements Comparator<NodeData> {
        @Override
        public int compare(NodeData node1, NodeData node2) {
            return Double.compare(node1.getWeight(), node2.getWeight());
        }
    }

    public  static class compareLocation implements Comparator<NodeData> {
        public int compare (NodeData node1, NodeData node2) {
            // node1
            double x1 = node1.getLocation().x();
            double y1 = node1.getLocation().y();
            double z1 = node1.getLocation().z();
            // node2
            double x2 = node2.getLocation().x();
            double y2 = node2.getLocation().y();
            double z2 = node2.getLocation().z();
            // distance = sqrt( x^2 + y^2 + z^2 )
            double d1 = Math.sqrt((x1*x1) + (y1*y1) + (z1*z1));
            double d2 = Math.sqrt((x2*x2) + (y2*y2) + (z2*z2));
            return Double.compare(d1, d2);
        }
    }

    @Override
    public void init(DirectedWeightedGraph g) {
        graph = (DW_Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    /**
     * Reset all graph nodes tag to zero
     */
    public void reset(DW_Graph g) {
        for (NodeData i : g.getV()) {
            i.setTag(0);
            i.setInfo("");
        }
    }

    private DW_Graph graphTranspose(DW_Graph g) {
        DW_Graph ret = new DW_Graph();
        for (NodeData runner : g.getV()) {
            myNode temp = new myNode(runner.getKey());
            temp.setTag(0);
            ret.addNode(temp);
        }

        for (EdgeData edge : graph.getEdges()) {
            ret.connect(edge.getDest(), edge.getSrc(), edge.getWeight());
        }

        return ret;
    }

    @Override
    public DirectedWeightedGraph copy() {

        DirectedWeightedGraph copyFrom = graph;
        DirectedWeightedGraph copyTo = new DW_Graph();
        HashSet<NodeData> ver = new HashSet<>(graph.getV());       //all vertices as set

        while (!ver.isEmpty()) {
            if (ver.iterator().hasNext()) {
                NodeData node = ver.iterator().next();
                LinkedList<NodeData> que = new LinkedList<>();
                que.add(node);
                while (!que.isEmpty()) {
                    node = que.poll();
                    copyTo.addNode(node);

                    for (EdgeData curNei : copyFrom.getE(node.getKey())) {     //iterate on node neighbors
                        double edgeWeight = curNei.getWeight();   //edge weight
                        NodeData destNode = copyFrom.getNode(curNei.getDest());
                        if (ver.contains(destNode)) {
                            copyTo.addNode(destNode);                    //add node to copied graph
                            que.add(destNode);
                            copyTo.connect(node.getKey(), destNode.getKey(), edgeWeight); // add edge(mark as neighbor)
                            ver.remove(destNode);
                        } else if (!(copyTo.getE(node.getKey()).contains(destNode))) {   //if dest and node are not connected yet
                            copyTo.connect(node.getKey(), destNode.getKey(), edgeWeight);
                        }
                    }
                    ver.remove(node);
                }
            }
        }
        ((DW_Graph)copyTo).setMC(copyFrom.getMC());       //set tha same mc
        return copyTo;
    }


    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1)
            return true;
        NodeData start = graph.getV().stream().iterator().next();
        this.que = new LinkedList<NodeData>();
        this.checkNodes = new HashSet<NodeData>();
        this.transposeCheckNodes = new HashSet<NodeData>();
        boolean gIsConnected = false;
        boolean gtIsConnected = false;

        for (NodeData cur : graph.getV())
            cur.setTag(0);

        start.setTag(1);
        que.add(start);
        while (!que.isEmpty()) {
            NodeData at = que.poll();
            checkNodes.add(at);
            for (EdgeData cur : graph.getE(at.getKey())) {
                NodeData dest = graph.getNode(cur.getDest());
                if (dest.getTag() == 0) {
                    if (graph.getE(dest.getKey()).size() != 0) {
                        dest.setTag(1);
                        que.add(dest);
                    }
                }
            }
        }

        for (NodeData cur : graph.getV())
            cur.setTag(0);
        if (checkNodes.size() == graph.nodeSize())
            gIsConnected = true;

        // start transposing and check
        DW_Graph transposeGraph = new DW_Graph();
        transposeGraph = (DW_Graph) graphTranspose(graph);

        for (NodeData cur : transposeGraph.getV())
            cur.setTag(0);

        NodeData start2 = transposeGraph.getV().stream().iterator().next();
        start2.setTag(1);
        que.add(start2);
        while (!que.isEmpty()) {
            NodeData at = que.poll();
            transposeCheckNodes.add(at);
            for (EdgeData cur : transposeGraph.getE(at.getKey())) {
                NodeData dest = transposeGraph.getNode(cur.getDest());
                if (dest.getTag() == 0) {
                    if (transposeGraph.getE(dest.getKey()).size() != 0) {
                        dest.setTag(1);
                        que.add(dest);
                    }
                }
            }
        }
        for (NodeData cur : transposeGraph.getV())
            cur.setTag(0);

        if (transposeCheckNodes.size() == transposeGraph.nodeSize())
            gtIsConnected = true;


        if (gIsConnected && gtIsConnected) {
            return true;
        }
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        NodeData start = graph.getNode(src);                                    //int to node
        NodeData finish = graph.getNode(dest);                                  //int to node
        this.prev = new HashMap<NodeData, NodeData>();                         //parents
        this.pQue = new PriorityQueue<NodeData>(graph.nodeSize(), new CompareDis());     //priority queue by distance
        Map<NodeData, Boolean> vis1 = new HashMap<NodeData, Boolean>();        //visited

        if (src == dest)
            return 0.0;

        if (start == null || finish == null)
            return -1;

        for (NodeData set : graph.getV())
            set.setWeight(-1);                   //set each node distance "unreachable"

        pQue.add(start);
        vis1.put(start, true);
        start.setWeight(0);
        while (!pQue.isEmpty()) {
            NodeData current = pQue.poll();

            for (EdgeData edge : graph.getE(current.getKey())) {        //current node neighbors checking and update
                int d = edge.getDest();
                NodeData nodeDest = graph.getNode(d);
                if (!vis1.containsKey(nodeDest.getKey())) {
                    if (nodeDest.getWeight() == -1)                       //if "unreachable" tag it reach but max distance
                        nodeDest.setWeight(Double.MAX_VALUE);

                    double newDis = current.getWeight() + edge.getWeight(); //the new distance
                    if (newDis < nodeDest.getWeight()) {                    //choose the shortest
                        nodeDest.setWeight(newDis);
                        prev.put(nodeDest, current);
                        pQue.add(nodeDest);
                    }
                }
            }
            vis1.put(current, true);
        }
        return graph.getNode(dest).getWeight();
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        NodeData source = graph.getNode(src);                                          //int to node
        NodeData finish = graph.getNode(dest);                                         //int to node
        this.prev = new HashMap<NodeData, NodeData>();                                //parents
        this.pQue = new PriorityQueue<>(graph.nodeSize(), new CompareDis());            //queue
        Map<NodeData, Boolean> vis = new HashMap<NodeData, Boolean>();
        LinkedList<NodeData> directions = new LinkedList<NodeData>();

        if (src == dest) {
            directions.add(source);
            return directions;
        }

        if (source == null || finish == null)
            return null;

        for (NodeData set : graph.getV())
            set.setWeight(-1);                                       //set each node distance "unreachable"

        pQue.add(source);
        vis.put(source, true);
        source.setWeight(0);
        NodeData current = source;
        while (!pQue.isEmpty()) {
            current = pQue.poll();

            if (current.getKey()==finish.getKey())
                break;
            for (EdgeData edge : graph.getE(current.getKey())) {        //current node neighbors checking and update
                int d = edge.getDest();
                NodeData nodeDest = graph.getNode(d);
                if (!vis.containsKey(nodeDest)) {
                    if (nodeDest.getWeight() == -1)                       //if "unreachable" tag it reach but max distance
                        nodeDest.setWeight(Integer.MAX_VALUE);

                    double newDis = current.getWeight() + edge.getWeight(); //the new distance
                    if (newDis < nodeDest.getWeight()) {                    //choose the shortest
                        nodeDest.setWeight(newDis);
                        prev.put(nodeDest, current);
                        pQue.add(nodeDest);
                    }
                }
            }
            vis.put(current, true);
        }

        if(!(current.getKey()==finish.getKey()))
            return null;

        for (NodeData node = finish; node != null; node = prev.get(node)) {    //adding nodes directions in reverse order
            directions.add(node);
        }
        Collections.reverse(directions);
        return directions;
    }

    public int sort_queue(Queue<NodeData> q) {
        int ret = 0;
        double min_dist = Double.MAX_VALUE;
        for (NodeData n : q) {
            myNode node = (myNode) n;
            if (node.getDistance() < min_dist) {
                min_dist = node.getDistance();
                ret = node.getKey();
            }
        }
        return ret;
    }

    public void dijkstra(myNode source) {
        Queue<NodeData> vq = new LinkedList<>();
        for (NodeData v : graph.getV()) {
            ((myNode) v).setDistance(Double.MAX_VALUE);
            vq.add(v);
        }
        source.setDistance(0);
        while(!vq.isEmpty()) {
            int temp_min = sort_queue(vq);
            myNode temp_min_node = (myNode) graph.getNode(temp_min);
            vq.remove(temp_min_node);
            for (myNode ni : temp_min_node.getNi()) {
                double edge_weight = graph.getEdge(temp_min_node.getKey(), ni.getKey()).getWeight();
                double dist_from_src = temp_min_node.getDistance() + edge_weight;
                if (dist_from_src < ni.getDistance()) {
                    ni.setDistance(dist_from_src);
                }
            }
        }
    }

    @Override
    public NodeData center() {
        if (!isConnected()) {
           return null;
        }
        NodeData ret = null;
        double shortest_dist = Double.MAX_VALUE;
        for (NodeData v : graph.getV()) {
            double max = -1;
            dijkstra((myNode) v);
            for ( NodeData vi : graph.getV()) {
                if (((myNode) vi).getDistance() > max) {
                    max = ((myNode) vi).getDistance();
                }
            }
            if (max < shortest_dist) {
                shortest_dist = max;
                ret = v;
            }
        }
        return ret;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        reset(graph);
        Comparator<NodeData> comp = new compareLocation();
        List<NodeData> ret = new LinkedList<>();
        PriorityQueue<NodeData> pq = new PriorityQueue<>(comp);
        for (NodeData c : cities) { // sorts cities into a prio que sorted by location.
            pq.add(c);
        }
        while(!pq.isEmpty()) {
            if(pq.peek().getInfo() == "v") {
                pq.poll();
            }
            NodeData poll = pq.poll();
            NodeData peek = pq.peek();
            if (peek == null) {
                ret.add(poll);
                break;
            }
            List<NodeData> sp = shortestPath(poll.getKey(), peek.getKey());
            for(NodeData on_path : sp) {
                if (pq.contains(on_path)) {
                    on_path.setInfo("v");
                }
                if (on_path != peek) {
                    ret.add(on_path);
                }
            }
        }
        return ret;
    }


    @Override
    public boolean save(String file) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject jsonGraph = new JsonObject();
        JsonArray jsonNodes = new JsonArray();
        JsonArray jsonEdges = new JsonArray();
        for (NodeData node : graph.getV()) {
            JsonObject json = new JsonObject();
            String loc = node.getLocation().x() + "," + node.getLocation().y() + "," + node.getLocation().z();
            json.addProperty("pos", loc);
            json.addProperty("id", node.getKey());
            jsonNodes.add(json);
        }
        for (EdgeData edge : ((DW_Graph) graph).getEdges()) {
            JsonObject json = new JsonObject();
            json.addProperty("src", edge.getSrc());
            json.addProperty("w", edge.getWeight());
            json.addProperty("dest", edge.getDest());
            jsonEdges.add(json);
        }
        jsonGraph.add("Edges", jsonEdges);
        jsonGraph.add("Nodes", jsonNodes);

        String fullGraphJson = gson.toJson(jsonGraph);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.write(fullGraphJson);
            pw.close();
            return true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean load(String file) {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DirectedWeightedGraph.class, new DwGraphJsonDeserializer());
            Gson gson = builder.create();
            FileReader reader = new FileReader(file);
            DirectedWeightedGraph toInit = gson.fromJson(reader, DirectedWeightedGraph.class);
            this.init(toInit);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
