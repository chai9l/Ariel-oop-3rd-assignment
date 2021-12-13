package api;

import org.w3c.dom.Node;

import java.util.*;

public class DW_Graph implements DirectedWeightedGraph{

    private HashMap<Integer,NodeData> graph = new HashMap<>();
    private HashMap<String,EdgeData> edges = new HashMap<>();
    private int mc;
    double factor_x;
    double factor_y;
    double offset_x;
    double offset_y;

    public DW_Graph() {

    }

    public class nodeIterator implements Iterator<NodeData> {
        private int i_mc;
        private Iterator<NodeData> iter;
        private NodeData current;

        public nodeIterator() {
            i_mc = mc;
            iter = graph.values().iterator();
        }

        private void checkRuntimeException() {
            if (i_mc != mc) {
                throw new RuntimeException("Graph has changed in the middle of an iteration");
            }
        }

        @Override
        public boolean hasNext() {
            checkRuntimeException();
            return iter.hasNext();
        }

        @Override
        public void remove() {
            checkRuntimeException();
            ++i_mc;
            iter.remove();
            removeNode(current.getKey());
        }

        @Override
        public NodeData next() {
            checkRuntimeException();
            if (!hasNext()) {
                return null;
            }
            current = iter.next();
            return current;
        }
    }

    public class edgeIterator implements Iterator<EdgeData> {
        private int e_mc;
        private Iterator<EdgeData> iter;
        private EdgeData current;

        public edgeIterator() {
            e_mc = mc;
            iter = edges.values().iterator();
        }

        private void checkRuntimeException() {
            if (e_mc != mc) {
                throw new RuntimeException("Graph has changed in the middle of an iteration");
            }
        }

        @Override
        public boolean hasNext() {
            checkRuntimeException();
            return iter.hasNext();
        }

        @Override
        public void remove() {
            checkRuntimeException();
            ++e_mc;
            iter.remove();
            removeEdge(current.getSrc(), current.getDest());
        }

        @Override
        public EdgeData next() {
            checkRuntimeException();
            if (!hasNext()) {
                return null;
            }
            current = iter.next();
            return current;
        }
    }

    public String generateKey(int node1, int node2) {
        String s = node1 + "/" + node2;
        return s;
    }

    public NodeData getFirst() {
        return graph.values().stream().findFirst().get();
    }

    public boolean hasEdge(int node1, int node2) {
        if(graph == null) { return false; }
        if(!graph.containsKey(node1) && !graph.containsKey(node2)) {
            return false;
        }
        myNode n1 = (myNode) graph.get(node1);
        String s = generateKey(node1,node2);
        EdgeData ret = edges.get(s);
        if(ret == null) {
            return false;
        }
        return true;
    }

    @Override
    public NodeData getNode(int key) {
        return this.graph.get(key);
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        String temp = generateKey(src,dest);
        return edges.get(temp);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        if (!this.graph.containsKey(n.getKey())) {
            this.graph.put(n.getKey(), n);
            this.mc++;
        }
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) {
        if (!this.graph.containsKey(src) || !this.graph.containsKey(dest) || src == dest || w<=0)
            return;
        myNode n1 = (myNode) graph.get(src);
        myNode n2 = (myNode) graph.get(dest);
        String key = generateKey(src,dest);
        EdgeData e = new myEdge(src,dest,w);

        if (!this.hasEdge(src, dest)){
            this.edges.put(key,e);
            n1.addNi(n2);
            this.mc++;
        }
        if(this.hasEdge(src,dest) && this.getEdge(src, dest).getWeight() != w) {
            this.edges.put(key,e);
            this.mc++;
        }
    }

    public Collection<EdgeData>getEdges(){
        return edges.values();
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        nodeIterator ni = new nodeIterator();
        return ni;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        edgeIterator ei = new edgeIterator();
        return ei;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        myNode ret = (myNode) graph.get(key);
        LinkedList<NodeData> l = new LinkedList<>();
        if (ret != null) {
            for (NodeData i : ret.getNi()) {
                l.add(i);
            }

            for(NodeData i : l) {
                removeEdge(i.getKey(),key);
                removeEdge(key,i.getKey());
                // mc--;
            }

            for(NodeData i : graph.values()){
                removeEdge(i.getKey(),key);
            }
            mc++;

            return graph.remove(ret.getKey());
        }
        return null;
    }


    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(graph==null) { return null; }
        if(graph.containsKey(src) && graph.containsKey(dest)) {
            String s = generateKey(src, dest);
            String s2 = generateKey(dest, src);
            EdgeData ret = edges.get(s);
            if (hasEdge(src, dest)) {
                edges.remove(s);
                myNode nodeX = (myNode)graph.get(src);
                myNode nodeY = (myNode)graph.get(dest);
                nodeX.removeNi(nodeY);
                nodeY.removeNi(nodeX);
                mc++;
                return ret;
            }
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return graph.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public int getMC() {
        return mc;
    }

    public void setMC(int m) {
        this.mc = m;
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges in the graph.
     * @return Collection<EdgeData>
     */
    public Collection<EdgeData> getE(int node_id) {
        if(graph == null || edges.size() == 0) { return null; }
        myNode node = (myNode) getNode(node_id);
        HashSet<EdgeData> ret = new HashSet<>();
        for(NodeData curr : node.getNi()) {
            String temp = generateKey(node.getKey(),curr.getKey());
            ret.add(edges.get(temp));
        }
        return ret;
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * @return Collection<NodeData>
     */
    public Collection<NodeData> getV() {
        if(graph == null) { return null; }
        return graph.values();
    }

    public void getmaxMin() {
        Iterator <NodeData> nodes = this.nodeIter();
        double min_x = Double.MAX_VALUE;
        double min_y = Double.MAX_VALUE;
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        while(nodes.hasNext()){
            myNode p = (myNode) nodes.next();
            min_x = Math.min(min_x,p.getLocation().x());
            min_y = Math.min(min_y,p.getLocation().y());
            max_x = Math.max(max_x,p.getLocation().x());
            max_y = Math.max(max_y,p.getLocation().y());
        }
        offset_x = min_x;
        factor_x = 700/(max_x - min_x);
        offset_y = min_y;
        factor_y = 700/(max_y - min_y);
    }
}
