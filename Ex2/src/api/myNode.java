package api;

import java.util.Collection;
import java.util.HashSet;

public class myNode implements NodeData{

    private int key;
    myLocation location;
    double weight;
    String info;
    int tag;
    HashSet<myNode> neighbors;
    double distance;

    public myNode(int key) {
        this.key = key;
        this.tag = -1;
        this.info = " ";
        this.neighbors = new HashSet<>();
    }

    public myNode(double x, double y, double z, int key, String info, int tag, double weight) {
        this.location = new myLocation(x, y, z);
        this.key = key;
        this.info = info;
        this.tag = tag;
        this.weight = weight;
        this.neighbors = new HashSet<>();
    }

    public myNode(double x, double y, double z, int key) {
        this.key = key;
        this.location = new myLocation(x,y,z);
        this.weight=-1;
        this.info="";
        this.tag=-1;
        this.neighbors = new HashSet<>();
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double d) {
        this.distance = d;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.location;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = info;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.location = (myLocation) p;
    }

    /**
     * Adding node as neighbor of other node
     * @param t - the added neighbor
     */
    public void addNi(myNode t) {
        if (!neighbors.contains(t)) {
            neighbors.add(t);
        }
    }

    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the node neighbors.
     * @return Collection<node_data>
     */
    public Collection<myNode> getNi() {
        return neighbors;
    }

    /**
     * Removing node from being a neighbor of other node
     * @param t - the removed neighbor
     */
    public void removeNi(myNode t) {
        if (!neighbors.contains(t)) {
            return;
        }
        neighbors.remove(t);
    }

    /**
     * Removing all nodes from being a neighbors of other node
     */
    public void removeAllNi(){
        this.neighbors.clear();
    }

    public String toString() {
        return ""+this.key;
    }
}
