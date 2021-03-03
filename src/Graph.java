/* NAME: Sydney Anderson and Devon Knudsen
 * DATE: 5/20/19
 * DESCRIPTION: Class that is utilized to create graph data structures */

import java.util.ArrayList;

public class Graph<Type> {

    private int numV;
    private ArrayList<Vertex<Type>> adj;
    private boolean directed;
    private double INF;
    private ArrayList<Vertex<Type>> vertexList;
    private double totalWeight;

    public Graph(boolean isDirected){
        numV = 0;
        adj = new ArrayList<>();
        directed = isDirected;
        INF = 99999999.9;
        vertexList = new ArrayList<>();
        totalWeight = 0.0;
    }

    /* function that returns the number of vertices */
    public int getNumV(){
        return numV;
    }

    /* function that returns the adjacency list */
    public ArrayList<Vertex<Type>> getAdj(){
        return adj;
    }

    /* function that returns the total weight of the entire graph */
    public double getTotalWeight(){
        return totalWeight;
    }

    /* function that searches for a given vertex value within the adjacency list
    *  returns the index if found OR -1 if not found */
    public int searchVert(Type value){
        int index = -1;
        for(int i = 0; i < adj.size(); i++){
            if(adj.get(i).getValue().equals(value)) {
                index = i;
                return index;
            }
        }

        return index;
    }

    /* function that adds a vertex into the adjacency list
     *  returns true if inserted OR false if already within the adjacency list */
    public boolean addVertex(Type value){
        if(searchVert(value) == -1){
            Vertex<Type> newVertex = new Vertex<>(value, adj.size());
            adj.add(newVertex);
            vertexList.add(newVertex);
            numV++;

            return true;
        }

        return false;
    }

    /* function that removes a vertex into the adjacency list
     * also removes all edges containing the removed vertex from the list
     * returns true if removed OR false not already within the adjacency list */
    public boolean removeVertex(Type value){
        int toRemove = searchVert(value);
        if(toRemove > -1){
            for(int j = toRemove + 1; j < adj.size(); j++){
                adj.get(j).setIndex(adj.get(j).getIndex() - 1);
            }

            adj.remove(toRemove);

            for(int k = 0; k < adj.size(); k++){
                removeEdge(adj.get(k).getValue(), value);
            }

            numV--;
            return true;
        }

        return false;
    }

    /* function that adds an edge into a given origin vertex's linked list of edges
     * returns true if inserted OR false if the origin does not exist within the adjacency list AND/OR the destination
     * does not exist within the adjacency list */
    public boolean addEdge(Type source, Type destination, double weight) {
        int sourceExists = searchVert(source);
        int destExists = searchVert(destination);

        if (sourceExists > -1 && destExists > -1) {
            Edge newEdge = new Edge(adj.get(sourceExists), adj.get(destExists), weight);
            adj.get(sourceExists).getEdges().InsertAfter(newEdge);
            totalWeight += weight;

            if(!directed){
                Edge undirectedEdge = new Edge(adj.get(destExists), adj.get(sourceExists), weight);
                adj.get(destExists).getEdges().InsertAfter(undirectedEdge);
                totalWeight += weight;
            }

            return true;
        }

        return false;
    }

    /* function that removes an edge into a given origin vertex's linked list of edges
     * returns true if removed OR false if the origin does not exist within the adjacency list AND/OR the origin does not have any edges */
    public boolean removeEdge(Type source, Type destination) {
        List<Edge> tempEdges = incidentEdges(source);
        if (tempEdges != null) {
            Edge sToD = getEdge(adj.get(searchVert(source)), adj.get(searchVert(source)));
            for (int i = 0; i < tempEdges.GetSize(); i++) {
                tempEdges.SetPos(i);
                if (tempEdges.GetValue() == sToD) {
                    totalWeight -= sToD.getWeight();
                    tempEdges.Remove();

                    if(!directed){
                        List<Edge> unDirTempEdges = incidentEdges(destination);
                        Edge dToS = getEdge(adj.get(searchVert(destination)), adj.get(searchVert(source)));
                        for (int j = 0; j < unDirTempEdges.GetSize(); j++) {
                            unDirTempEdges.SetPos(j);
                            if (unDirTempEdges.GetValue() == dToS) {
                                totalWeight -= dToS.getWeight();
                                unDirTempEdges.Remove();

                                break;
                            }
                        }
                    }

                    return true;
                }
            }
        }

        return false;
    }

    /* function that returns the edge between two given vertices OR null if there is none */
    public Edge getEdge(Vertex<Type> origin, Vertex<Type> dest){
        origin.getEdges().First();
        for(int i = 0; i < degree(origin); i++){
            Edge currEdge = origin.getEdges().GetValue();
            if(currEdge.opposite(origin) == dest){
                return currEdge;
            }

            origin.getEdges().Next();
        }

        return null;
    }

    /* function that returns the amount of edges going out of a given vertex */
    public int degree(Vertex<Type> vert){
        return vert.getEdges().GetSize();
    }

    /* function that returns the list of the edges of a given vertex */
    public List<Edge> incidentEdges(Type source){
        int index = searchVert(source);
        if(index > -1 && degree(adj.get(index)) > 0 ){
            return adj.get(index).getEdges();
        }

        return null;
    }

    /* function that prints every edge within the graph */
    public void printEdges(){
        int lastNum = -1;
        for(int i = 0; i < adj.size(); i++){
            if(adj.get(i).getEdges().GetSize() < 1)
                continue;

            if(lastNum != i) {
                adj.get(i).getEdges().First();
                lastNum = i;
            }

            System.out.println(adj.get(i).getEdges().GetValue().getOrigin().getValue() + " ---> " + adj.get(i).getEdges().GetValue().getDestination().getValue());

            if(adj.get(i).getEdges().GetPos() != adj.get(i).getEdges().GetSize() - 1){
                adj.get(i).getEdges().Next();
                i--;
            }
        }
    }

    /* function that prints every vertex within the graph */
    public void printVertices(){
        for(int i = 0; i < adj.size(); i++){
            System.out.println("Vertex: " + adj.get(i).getValue() + " & Edge Sum: " + edgeSum(adj.get(i)));
        }
    }

    /* function that returns the sum of all of the weights of a given vertex's edges*/
    public double edgeSum(Vertex<Type> vert){
        double sum = 0;
        if(degree(vert) > 0) {
            vert.getEdges().First();
            for (int i = 0; i < degree(vert); i++) {
                double currWeight = vert.getEdges().GetValue().getWeight();
                sum += currWeight;

                vert.getEdges().Next();
            }
        }

        return sum;
    }

    /* function that utilizes Dijkstra's algorithm to construct a list of shortest paths from a given starting vertex
    *  to every other vertex within the graph
    *  returns a list of stacks (each index within the list of stacks is the shortest path to that corresponding index
    *  within the adjacency list) */
    public ArrayList<Stack<Integer>> constructShortestPaths(Vertex<Type> start){
        boolean[] known = new boolean[adj.size()];
        double[] cost = new double[adj.size()];
        int[] path = new int[adj.size()];
        ArrayList<Stack<Integer>> shortPaths = new ArrayList<>();

        for(int i = 0; i < adj.size(); i++){
            known[i] = false;
            cost[i] = INF;
            path[i] = -1;
        }

        known[start.getIndex()] = true;
        cost[start.getIndex()] = 0;
        Vertex<Type> tempSource = start;

        if(degree(tempSource) != 0) {
            while (true) {
                if (tempSource.getEdges().GetSize() != 0) {
                    tempSource.getEdges().First();

                    for (int i = 0; i < degree(tempSource); i++) {
                        int oppositeIndex = tempSource.getEdges().GetValue().getDestination().getIndex();
                        if (cost[oppositeIndex] > tempSource.getEdges().GetValue().getWeight() + cost[tempSource.getIndex()] && !known[oppositeIndex]) {
                            cost[oppositeIndex] = tempSource.getEdges().GetValue().getWeight() + cost[tempSource.getIndex()];
                            path[oppositeIndex] = tempSource.getIndex();
                        }

                        tempSource.getEdges().Next();
                    }
                }

                // find the cheapest unknown index
                int cheapestIndex = -1;
                for (int j = 0; j < known.length; j++) {
                    if (!known[j]) { // if known = false
                        if (cheapestIndex != -1) {
                            if (cost[j] < cost[cheapestIndex]) {
                                cheapestIndex = j;
                            }
                        } else {
                            cheapestIndex = j;
                        }
                    }
                }

                if (cheapestIndex == -1) {
                    break;
                }

                tempSource = adj.get(cheapestIndex);
                known[cheapestIndex] = true;
            }

            //CREATES STACK FOR EACH INDEX, if known = false... then path == null
            Stack<Integer> buildPath = new Stack<>();
            for (int i = 0; i < adj.size(); i++) {
                if (known[i]) { //while path is true
                    buildPath = new Stack<>();
                    buildPath.push(i); //push index
                    int tempPath = i;
                    while (path[tempPath] != -1) {
                        buildPath.push(path[tempPath]); //push index's path
                        tempPath = path[tempPath]; //go to this index
                    }
                }

                shortPaths.add(buildPath);
            }

            return shortPaths;
        }
        else
            return null;
    }

    /*  function that utilizes Dijkstra's algorithm to construct a list of longest paths from a given starting vertex
     *  to every other vertex within the graph
     *  returns a list of stacks (each index within the list of stacks is the longest path to that corresponding index
     *  within the adjacency list) */
    public ArrayList<Stack<Integer>> constructLongestPaths(Vertex<Type> start) {
        boolean[] known = new boolean[adj.size()];
        double[] cost = new double[adj.size()];
        int[] path = new int[adj.size()];
        ArrayList<Stack<Integer>> longPaths = new ArrayList<>();

        for (int i = 0; i < adj.size(); i++) {
            known[i] = false;
            cost[i] = 0;
            path[i] = -1;
        }

        known[start.getIndex()] = true;
        cost[start.getIndex()] = 0;
        Vertex<Type> tempSource = start;

        if (degree(tempSource) != 0) {
            while (true) {
                if (tempSource.getEdges().GetSize() != 0) {
                    tempSource.getEdges().First();

                    for (int i = 0; i < degree(tempSource); i++) {
                        int oppositeIndex = tempSource.getEdges().GetValue().getDestination().getIndex();
                        if (cost[oppositeIndex] < tempSource.getEdges().GetValue().getWeight() + cost[tempSource.getIndex()] && !known[oppositeIndex]) {
                            cost[oppositeIndex] = tempSource.getEdges().GetValue().getWeight() + cost[tempSource.getIndex()];
                            path[oppositeIndex] = tempSource.getIndex();
                        }

                        tempSource.getEdges().Next();
                    }
                }

                //go to the cheapest unknown index
                int expensiveIndex = -1;
                for (int j = 0; j < known.length; j++) {
                    if (!known[j]) { // if known = false
                        if (expensiveIndex != -1) {
                            if (cost[j] > cost[expensiveIndex]) {
                                expensiveIndex = j;
                            }
                        } else {
                            expensiveIndex = j;
                        }
                    }
                }

                if (expensiveIndex == -1) {
                    break;
                }

                tempSource = adj.get(expensiveIndex);
                known[expensiveIndex] = true;
            }

            //CREATES STACK FOR EACH INDEX, if known = false... then path == null
            Stack<Integer> buildPath = new Stack<>();
            for (int i = 0; i < adj.size(); i++) {
                if (known[i]) { //while path is true
                    buildPath = new Stack<>();
                    buildPath.push(i);//push index
                    int tempPath = i;
                    while (path[tempPath] != -1) {
                        buildPath.push(path[tempPath]); //push index's path
                        tempPath = path[tempPath]; //go to this index
                    }
                }

                longPaths.add(buildPath);
            }

            return longPaths;
        }
        else
            return null;
    }

    /*  function that utilizes Prim's algorithm to construct a minimum spanning tree (subgraph) from a given starting vertex
     *  returns the created minimum spanning tree */
    public Graph<Type> primjMinSpan(Vertex start){
        boolean[] known = new boolean[adj.size()];
        double[] cost = new double[adj.size()];
        int[] path = new int[adj.size()];
        Graph<Type> MST = new Graph<>(true);

        for(int i = 0; i < adj.size(); i++){
            known[i] = false;
            cost[i] = INF;
            path[i] = -1;
            MST.addVertex(vertexList.get(i).getValue());
        }

        known[start.getIndex()] = true;
        cost[start.getIndex()] = 0;
        Vertex<Type> tempSource = start;
        if(degree(tempSource) != 0) {

            while (true) {
                if (tempSource.getEdges().GetSize() != 0) { //work through kids of vertex
                    tempSource.getEdges().First();

                    for (int i = 0; i < degree(tempSource); i++) {
                        int oppositeIndex = tempSource.getEdges().GetValue().getDestination().getIndex();
                        if (cost[oppositeIndex] > tempSource.getEdges().GetValue().getWeight() && !known[oppositeIndex]) {
                            cost[oppositeIndex] = tempSource.getEdges().GetValue().getWeight();
                            path[oppositeIndex] = tempSource.getIndex();
                        }

                        tempSource.getEdges().Next();
                    }
                }

                //go to the cheapest unknown index
                int cheapestIndex = -1;
                for (int j = 0; j < known.length; j++) {
                    if (!known[j]) { // if known = false
                        if (cheapestIndex != -1) {
                            if (cost[j] < cost[cheapestIndex]) {
                                cheapestIndex = j;
                            }
                        } else {
                            cheapestIndex = j;
                        }
                    }
                }

                if (cheapestIndex == -1) {
                    break;
                }

                tempSource = adj.get(cheapestIndex);
                known[cheapestIndex] = true;
            }

            // adds the edges to the MST based on the path array
            for(int i = 0; i < adj.size(); i++){
                if(path[i] != -1) {
                    Edge iE = getEdge(adj.get(path[i]), adj.get(i));
                    double edgeW = iE.getWeight();

                    MST.addEdge(adj.get(path[i]).getValue(), adj.get(i).getValue(), edgeW);
                }
            }

        }

        return MST;
    }

}
