![](https://i.imgur.com/wTqLlcY.jpg)

**This project is the 2nd assignment given in the Object Oriented course, Ariel University.**
**The project revolves around the implmentation of Directed Weighted graphs and contains the following:**

 - Directed Weighted Graph, which represent the graph we'll work on.
 - Directed Weighted Graph algo, which represents the algorithms at work on the given graph.

## ***Run-time***

# *shortestPath*
|Number of Nodes|Run-time|
|--|--|
|1k |458 ms|
|10k |1 sec 182 ms|
|100k|11 sec|
|1mil|Unknown|

# *center* 
|Number of Nodes|Run-time|
|--|--|
|1k |2 secs 737 ms|
|10k |22 mins|
|100k|Time-out|
|1mil|Unknown|

# *tsp*
|Number of Nodes|Run-time|
|--|--|
|1k |500 ms|
|10k |1 sec 500 ms|
|100k|18 sec|
|1mil|Unknown|

# **Classes**
In this section you can find a variety of classes and their methods with a brief explanation on what the method do.

## **myNode** - implements **NodeData**

 
***Fields:***
key  - Integer
tag - Integer
weight  - Double
info - String
neighbors - List
location - geoLocation

|Method|Description|
|--|--|
|getKey|Returns the key (id) associated with this node |
|getLocation|Returns the location of this node|
|getWeight|Returns the weight associated with this node|
|setWeight|Sets the weight associated with this node|
|getTag and setTag|Returns or Sets the tag associated with this node|
|addNi|adds a new node to the associated node's neigbour list.|
|getNi|Returns the list of neighbours associated with this node |

## **myEdge** - implements **EdgeData**

 
 ***Fields:***
src - Integer
dest - Integer
weight - Double
info - String
tag - Integer

|Method|Description|
|--|--|
|getSrc|Returns The id of the source node of this edge|
|getDest|Returns The id of the destination node of this edge|
|getWeight|Returns the weight of this edge|

## **DW_Graph** - implements **DirectedWeightedGraph**

 ***Fields:***
graph - HashMap
edges -HashMap
mc - Integer

|Method|Description|
|--|--|
|generateKey|Generates a key via string by recieving 2 different nodes|
|hasEdge|Checks if 2 nodes has an edge between them|
|getNode|returns the wanted node via given interger|
|getEdge|returns the wanted edge via given source and destination|
|addNode|adds a new node to the graph by recieving an interger|
|connect|attempts to connect two given nodes with a given integer that represents the edge's weight|
|getV|returns a pointer (shallow copy) for the collection representing all the nodes in the graph|
|getE|Returns a pointer (shallow copy) for the collection representing all the edges|
|removeNode|Deletes the node (with the given ID) from the graph|
|removeEdge|Deletes the edge from the graph|
|nodeSize|Returns the number of vertices (nodes) in the graph|
|edgeSize|Returns the number of edges (assume directional graph)|
|getmaxMin |Restricts the dimension of the graph|

## **DW_GraphAlgo** - implements **DirectedWeightedGraphAlgorithms**

 
  ***Fields:***
que - Queue 
checkNodes - HashSet  
transposeCheckNodes - HashSet
vis - Map 
prev - Map  
pQue - PriorityQueue

|Method|Description|
|--|--|
|init|Initialize the graph on which this set of algorithms operates on|
|getGraph|Return the underlying graph of which this class works|
|graphTranspose | Transposes the directions of all of the graph's edges |
|copy|Compute a deep copy of this weighted graph|
|isConnected|Returns true if and only if (iff) there is a valid path from each node to each other node in the graph|
|shortestPathDist|returns the length of the shortest path between src to dest|
|shortestPath|returns the the shortest path between src to dest - as an ordered List of nodes|
|center |Attempts to find the center node of the given graph, this function returns the node that is found as center |
|tsp |Implmentation to the "Traveling Salesman Problem", this function goes through a given node list in the most efficient way  |
|save|Saves this weighted (directed) graph to the given file name - in JSON format|
|load|Loads a graph from a given file name in JSON format|

## **DW_GraphGUI & DW_GraphPanel** 
# ***GUI Methods*** :
|Method|Description|
|--|--|
|actionPerformed|This functions takes an event and implments buttons to press according to the recieved event|

|Method|Description|
|--|--|
|drawArrowLine| This function draws an arrow from node to node via given location using x,y,z|
|paint|Paints the graph|

## **UML **
![bash](https://i.imgur.com/DcaWh39.png)

## **How to run :** 
- Download the files.
- Copy the path to the jar file location.
- Pick either one of the exsisting graphs inside the data file (G1, G2, G3).
- Open cmd and type the following :
```console
cd path
```
- Now type in the console :
```console
java -jar Ex2.jar G1.json
```
![bash](https://i.imgur.com/de6d3Ci.png)

***the example given is used upon the G1 graph file, you can pick either one of the existing graphs over the output folder.***

# How to use :
**Pick what action you'd like to use on the graph opened :**

![bash](https://i.imgur.com/mqtJ0BO.png)

![bash](https://i.imgur.com/tN6QjUS.png)

***Example :***

![bash](https://i.imgur.com/z6Wf8OS.png)



