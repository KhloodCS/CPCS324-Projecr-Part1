/* CPCS324 Project Part 1

 Group members: 
    1- Razan Alamri
    2- Khloud Alsofyani
    3- Leen Ba Galaql
    4- Shatha Binmahfouz
 */
package GraphFramework;
import java.util.LinkedList;

//
public class MHPrimAlg extends MSTAlgorithm {

    //capacity of the heap maxSize
    int capacity;

    //current  size of the heap 
    int currentSize;

    // minHeap Array 
    Edge[] MinHeapArr;

    //position of heap elements in the Heap
    int[] indexes;

    //cost of MST 
    int cost;

    // constructors
    public MHPrimAlg() {

    }

    /**
     * constructor
     *
     * @param verticesNo of a graph input
     */
    public MHPrimAlg(int verticesNum) {
        capacity = verticesNum;     //capacity of minHeap = number of vertices in graph
        MinHeapArr = new Edge[capacity + 1];   //create HeapNode for all the vertices
        indexes = new int[capacity];    //intilize the index array 
        //first element of array not used assging it to -1 and infinity
        MinHeapArr[0] = new Edge();
        MinHeapArr[0].weight = Integer.MIN_VALUE;  //minumn value to do not use it 
        MinHeapArr[0].source.label = -1;
        currentSize = 0;

    }

    /**
     * PrimMH method take the graph as its parameter to find MST of the graph
     *
     * @param graph
     */
    public void PrimMH(Graph graph) {
        MSTResultList = new Edge[capacity];

        //intillize all edges 
        for (int i = 0; i < graph.verticesNo; i++) {
            //intilize all heap nodes
            MSTResultList[i] = new Edge();
            MSTResultList[i].source.label = i; //with lable 
            MSTResultList[i].parent.label = -1; //with lable 
            MSTResultList[i].weight = Integer.MAX_VALUE; //and infinity as the key 

        }
        // the first node has weight of 0  
        MSTResultList[0].weight = 0;

        //add all vertices to the minHeap 
        for (int i = 0; i < graph.verticesNo; i++) {
            insert(MSTResultList[i]);
        }

        //while heap node is not empty 
        while (!isEmpty()) {
            //extract the min top element 
            Edge extractedMinNode = extractMin();
            cost += extractedMinNode.weight;
            //extracted vertex lable 
            int extractedVertex = extractedMinNode.source.label;
            MSTResultList[extractedVertex].source.isVisited = true; //mark the extracted vertex ad visited 

            LinkedList<Edge> list = graph.vertices[extractedVertex].adjList;//for all edges of the vertex 
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge destination doesnt visited yet
                if (!MSTResultList[edge.target.label].source.isVisited) {
                    int destination = edge.target.label;
                    int newWight = edge.weight;
                    //check if updated key 
                    if (MSTResultList[destination].weight > newWight) {
                        decreaseKey(newWight, destination);//change thee wight of the node
                        //update the wight and the parent
                        MSTResultList[destination].parent.label = extractedVertex;
                        MSTResultList[destination].weight = newWight;

                    }
                }
            }

        }
    }

    /**
     * remove the min value form the min heap
     *
     * @param newWight new wight to update
     * @param vertex need to modify
     */
    public void decreaseKey(int newWight, int vertex) {
        //get the index which key's needs a decrease;
        int index = indexes[vertex];
        //get the node and update its value
        Edge node = MinHeapArr[index];
        node.weight = newWight;
        bubbleUp(index);

    }

    /**
     * to add new node to the minHeap
     *
     * @param Node new node
     */
    public void insert(Edge Node) {
        currentSize++; //increment size of heap
        int Index = currentSize;
        MinHeapArr[Index] = Node; //update the minHeap
        indexes[Node.source.label] = Index; //update the place of the node 
        bubbleUp(Index);

    }

    /**
     * put the new value added to the correct position
     *
     * @param Number to bubbleUp index
     */
    public void bubbleUp(int Number) {

        int parentIndex = Number / 2; //find parent of node 
        int currentIndex = Number;
        //while parent is bigger than child 
        //buble the child up
        while (currentIndex > 0 && MinHeapArr[parentIndex].weight > MinHeapArr[currentIndex].weight) {

            Edge currentNode = MinHeapArr[currentIndex];
            Edge parentNode = MinHeapArr[parentIndex];
            //swap the positions
            indexes[currentNode.source.label] = parentIndex;
            indexes[parentNode.source.label] = currentIndex;
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = parentIndex / 2;
        }
        //otherwise do nothing
    }

    /**
     * extract the node with the last weight
     *
     * @return the minimum node which is the root
     */
    public Edge extractMin() {
        Edge min = MinHeapArr[1];//take the root of heap first element 
        Edge lastNode = MinHeapArr[currentSize]; //save the last element 
        indexes[lastNode.source.label] = 1; //put the last emlent as the first element in the hrap 
        MinHeapArr[1] = lastNode; //replace the first element by the root 
        MinHeapArr[currentSize] = null; //delete the last element
        sinkDown(1);
        currentSize--;//decrement the current size of heap 
        return min;  //return the deleted node 
    }

    /**
     * sinkDown method rearrange the heap again after deleting the top
     *
     * @param k to sinkDown
     */
    public void sinkDown(int k) {
        int smallest = k;
        int leftChildIndex = 2 * k;
        int rightChildIndex = 2 * k + 1;
        if (leftChildIndex < heapSize() && MinHeapArr[smallest].weight > MinHeapArr[leftChildIndex].weight) {
            smallest = leftChildIndex;
        }
        if (rightChildIndex < heapSize() && MinHeapArr[smallest].weight > MinHeapArr[rightChildIndex].weight) {
            smallest = rightChildIndex;
        }
        if (smallest != k) {

            Edge smallestNode = MinHeapArr[smallest];
            Edge kNode = MinHeapArr[k];

            //swap the positions
            indexes[smallestNode.source.label] = k;
            indexes[kNode.source.label] = smallest;
            swap(k, smallest);
            sinkDown(smallest);
        }
    }

    /**
     * method to swap the position of two nodes
     *
     * @param a index of first node
     * @param b index of second node
     */
    public void swap(int a, int b) {
        Edge temp = MinHeapArr[a];
        MinHeapArr[a] = MinHeapArr[b];
        MinHeapArr[b] = temp;
    }

    /**
     *
     * @return true if minHeap is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * @return the currentSize of minHeap
     */
    public int heapSize() {
        return currentSize;
    }

    /**
     * display all edges in the Et MST
     */
    @Override
    public void displayResultingMST() {

        for (int i = 0; i < MSTResultList.length; i++) {
            if (MSTResultList[i].parent.label != -1) {
        Edge e = null;
        e.displayInfo();            }

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
