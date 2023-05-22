/* CPCS324 Project Part 1

 Group members: 
    1- Razan Alamri
    2- Khloud Alsofyani
    3- Leen Ba Galaql
    4- Shatha Binmahfouz
 */
package GraphFramework;
import java.util.*;

//
public class KruskalAlg extends MSTAlgorithm {

    /**
     * MST cost
     */
    int cost;

    /**
     * @param input graph
     *
     */
    public void Kruskal(Graph graph) {

        int verticesNum = graph.verticesNo;//calculate number of vertices from the input graph 
        MSTResultList = new Edge[verticesNum - 1];// V-1 edges needed to find the MST 
        //create a PriorityQueue to store edges beased on their wight 
        //Edge class implment the interface compareable and ovierride the compareTo method 
        ArrayList<Edge> result = new ArrayList<>();       //add all the edges in the adjacency list to the priorityQueue
        //add all the edges in the adjacency list to the priorityQueue
        for (int i = 0; i < verticesNum; i++) {//for all vertices
            for (int j = 0; j < graph.vertices[i].adjList.size(); j++) //for all edges of verices 
            {
                result.add(graph.vertices[i].adjList.get(j)); //add to the priorityQueue
            }
                            Collections.sort(result);

        }
        //create an array for subsets 
        Edge[] subsets = new Edge[verticesNum];
        makeSet(subsets);//make sets of each vertex in the graph 
        int MSTedges = 0;//to indicate the number of edges in the graph Et
        int n = 0;
        while (MSTedges < verticesNum && !result.isEmpty()) {
            Edge edge = result.remove(MSTedges); //dequeue the edge which has the most priority (who has minimum weight )
            int source_subset = find(subsets, edge.source.label); //find the subset of the source 
            int target_subset = find(subsets, edge.target.label); //find the subset of the targe 
            //if they form diffrent subsets that imples this edge will not create a cycle 
            if (source_subset != target_subset) {
                MSTResultList[MSTedges] = edge;//add the edge to the edges result
                cost += edge.weight; //add the cost two 
                union(subsets, source_subset, target_subset);// union the disjoint subset 

            }
        }

        //***************************
    }//end of kruskal method 
    //*******************************

    /**
     * makeSet method used to create one-element set{x} for all the V in the
     * graph
     *
     * @param edges all edges of graph
     */
    public void makeSet(Edge[] edges) {
        //for every vertex in the graph
        //create new set 
        for (int i = 0; i < edges.length; i++) {
            //set the parent and source as a i 
            edges[i] = new Edge();
            edges[i].source.label = i;
            edges[i].parent.label = i;

        }
    }

    /**
     * find method used to find the subset containing x
     *
     *
     * @param edges all edges in the graph
     * @param vertex that need to find its parent(subset)
     * @return parent represent the representatives of the subsets
     */
    public int find(Edge[] edges, int vertex) {
        //if the parent of the vertic != the same label of vertex 
        if (edges[vertex].parent.label != vertex) {
            //recursivly call find the set of its parent 
            return find(edges, edges[vertex].parent.label);
        }
        //return the set  
        return vertex;
    }

    /**
     * union the disjoint subset of source_vertex and target_vertex
     *
     * @param edges all edges
     * @param source_vertex source vertex to be union
     * @param target_vertex target vertex to be union
     */
    public void union(Edge[] edges, int source_vertex, int target_vertex) {
        //find the subset of source_vertex and subset of target_vertex 
        int x_set_parent = find(edges, source_vertex);
        int y_set_parent = find(edges, target_vertex);
        //make source_vertex as the target_vertex subset  
        edges[y_set_parent].parent.label = x_set_parent;
    }

    /**
     * display all edges in the Et MST
     */
    @Override
    public void displayResultingMST() {
        for (int i = 0; i < MSTResultList.length; i++) {
    Edge e = null;
        e.displayInfo();
        }
    
    }

    /**
     *
     * @return cost of MST
     */
    public int getMSTCost() {
        return cost;
    }
}
