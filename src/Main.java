/* NAME: Sydney Anderson and Devon Knudsen
 * DATE: 5/20/19
 * DESCRIPTION: Main class for the Campus Recycling Program assignment */

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.nanoTime;

public class Main {
    private static DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String args[]) throws IOException {
        Graph<String> campus = new Graph<>(true);
        addBuildings(campus);
        addRoads(campus);
        double[] shortest = shortestPath("Nethken Hall", campus);
        double[] longest = longestPath("Memorial Gym", campus);
        double[] prims = printMST("Nethken Hall", campus);

        System.out.println("_______________________________________________________________________________________");

        /* distance analysis */
        if(prims[0]<shortest[0]){
            System.out.println("Most efficient (least distance) for the workers is Prim-Jarnik algorithm: " + prims[0] + " miles");
        }
        else{
            System.out.println("Most efficient (least distance) for the workers is Dijkstra algorithm: " + shortest[0] + " miles");
        }

        /* time analysis */
        if(prims[1]<shortest[1]){
            System.out.println("Most efficient (time analysis) for the workers is Prim-Jarnik algorithm: " + prims[1] + " ns");
        }
        else{
            System.out.println("Most efficient (time analysis) for the workers is Dijkstra algorithm: " + shortest[1] + " ns");
        }
    }

    /* function to add the "buildings" to the "campus" graph (adding vertices)*/
    private static void addBuildings(Graph<String> campus) throws IOException {
        String path = "educationBuildings.txt";
        Scanner scanner = new Scanner(new File(path));
        while(scanner.hasNextLine()){
            campus.addVertex(scanner.nextLine());
        }

        scanner.close();
    }

    /* function to add the "roads" to the "campus" graph (adding edges) */
    private static void addRoads(Graph<String> campus) throws IOException {
        String path = "buildingConnections.txt";
        Scanner scanner = new Scanner(new File(path));
        while(scanner.hasNextLine()){
            String[] edge = scanner.nextLine().split(",");
            String origin = edge[0];
            String destination = edge[1];
            double weight = Double.parseDouble(edge[2]);
            campus.addEdge(origin, destination, weight);
        }

        scanner.close();
    }

    /* function to run DIJKSTRA'S ALGORITHM for a given graph and origin vertex to find the shortest paths
     *  returns the total length and elapsed time for the algorithm to be used for analysis */
    private static double[] shortestPath(String origin, Graph<String> graph){
        System.out.println("SHORTEST PATHS:");
        double totalLength = 0.0;
        ArrayList<Vertex<String>> adjList = graph.getAdj();
        int startIndex = graph.searchVert(origin);

        long start = nanoTime();
        ArrayList<Stack<Integer>> shortPaths = graph.constructShortestPaths(adjList.get(startIndex));
        long end = nanoTime();

        for(int i = 0; i < shortPaths.size(); i++){
            Stack<Integer> neededPath = shortPaths.get(i);
            totalLength += printPath(neededPath, graph);
        }

        long timeElapsed = end - start;
        double[] values = {totalLength, timeElapsed};
        System.out.println("TOTAL LENGTH FOR ALL SHORTEST PATHS (DIJKSTRA'S): " + df.format(totalLength) + " MILES");
        System.out.println("TOTAL TIME ELAPSED TO RUN DIJKSTRA'S ALGORITHM: " + timeElapsed + " NANOSECONDS\n");

        return values;
    }

    /* function to run a modified version of DIJKSTRA'S ALGORITHM for a given graph and origin vertex to find the longest paths
     * returns the total length and elapsed time for the algorithm to be used for analysis */
    private static double[] longestPath(String origin, Graph<String> graph){
        System.out.println("LONGEST PATHS:");
        double totalLength = 0.0;
        ArrayList<Vertex<String>> adjList = graph.getAdj();
        int startIndex = graph.searchVert(origin);

        long start = nanoTime();
        ArrayList<Stack<Integer>> longPaths = graph.constructLongestPaths(adjList.get(startIndex));
        long end = nanoTime();

        for(int i = 0; i < longPaths.size(); i++){
            Stack<Integer> neededPath = longPaths.get(i);
            totalLength += printPath(neededPath, graph);
        }

        long timeElapsed = end - start;
        double[] values = {totalLength, timeElapsed};
        System.out.println("TOTAL LENGTH FOR ALL LONGEST PATHS (MODDED DIJKSTRA'S): " + df.format(totalLength) + " MILES");
        System.out.println("TOTAL TIME ELAPSED TO RUN MODDED DIJKSTRA'S ALGORITHM: " + timeElapsed + " NANOSECONDS\n");

        return values;
    }

    /* function to print the paths for both versions of DIJKSTRA'S algorithm
     * returns the total weight of the entire stack tree */
    private static double printPath(Stack<Integer> path, Graph<String> graph){
        ArrayList<Vertex<String>> adjList = graph.getAdj();
        double pathLength = 0;
        int stackSize = path.GetSize();
        if(stackSize > 1) {
            for (int i = 0; i < stackSize; i++) {
                Vertex<String> pop = adjList.get(path.pop());
                if (path.IsEmpty())
                    System.out.print(pop.getValue() + "\n");
                else {
                    pop.getEdges().First();
                    for (int j = 0; j < pop.getEdges().GetSize(); j++) {
                        if (pop.getEdges().GetValue().opposite(pop) == adjList.get(path.peek())) {
                            pathLength += pop.getEdges().GetValue().getWeight();
                            System.out.print(pop.getValue() + " ---> ");

                            break;
                        }

                        pop.getEdges().Next();
                    }
                }
            }

            System.out.println("TRAVEL DISTANCE: " + df.format(pathLength) + " MILES\n");
        }

        return pathLength;
    }

    /* function to run and print PRIM'S ALGORITHM for a given graph and origin vertex
     * returns the total length and elapsed time for the algorithm to be used for analysis */
    public static double[] printMST(String origin, Graph<String> graph){
        System.out.println("MINIMUM SPANNING TREE:");
        ArrayList<Vertex<String>> adjList = graph.getAdj();

        long start = nanoTime();
        Graph<String> MST = graph.primjMinSpan(adjList.get(graph.searchVert(origin)));
        long end = nanoTime();

        MST.printEdges();

        long timeElapsed = end - start;

        double[] values = {MST.getTotalWeight(), timeElapsed};
        System.out.println("\nTOTAL WEIGHT FOR MST (PRIM'S): " + MST.getTotalWeight() + " MILES");
        System.out.println("TOTAL TIME TO RUN PRIM'S ALGORITHM: " + timeElapsed + " NANOSECONDS");

        return values;
    }
}
