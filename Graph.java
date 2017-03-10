/* Audrey Clark January 2017
 * CS 405 Assign 2
 * Read in a graph with a given adjacency list, determine if
 * the graph has a cycle that runs through every edge.
 * If so, return the path that this cycle takes.
 * */
import java.util.*;
import java.io.*;
public class Graph {
	
	static ArrayList<Node> result = new ArrayList<Node>();
	private static HashMap<Integer, Node> graphList = new HashMap<Integer, Node>();
	
	public static void main(String[] args) {
		// Read in graph via adjacency matrix
		try{
			buildGraph();
		}catch(FileNotFoundException e){
			System.out.println("File was not found.");
			System.exit(1);
		}
		if(!runGraph()){
			System.out.println("The given graph does not cycle through all vertices");
		}else{
			System.out.println("The given graph does cycle through all vertices");
			if(result.size() > 20){
				for(int i = 1; i <= result.size(); i++){
					System.out.print(result.get(i-1).getId() + " ");
					if(i % 20 == 0){
						System.out.println();
					}
				}
			}else{
				for(int i = 0; i < result.size(); i++){
					System.out.print(result.get(i).getId() + " ");
				}
			}
		}
		
	}

	/*Function used to read through given adj. matrix
	 * Input: filename given by user
	 * Output: no output, but graph is built*/
	public static void buildGraph() throws FileNotFoundException{
		System.out.println("Please enter the graph you would like to read.");
		Scanner in = new Scanner(System.in);
		String filename = in.nextLine();
		in.close();
		Scanner sc = new Scanner(new File(filename));
		String line = "";
		//iterate over file to obtain nodes and their adj. nodes
		while(sc.hasNextLine()){
			Node node;
			line = sc.nextLine();
			String[] nodeAdjS = line.split(" "); //list of nodes on a given line
			ArrayList<Integer> nodeAdj = new ArrayList<Integer>();
			//turn elements of nodeAdjS to integers
			for(int i = 0; i < nodeAdjS.length; i++){
				if(!nodeAdjS[i].equals("")){
					nodeAdj.add(Integer.parseInt(nodeAdjS[i]));
				}
			}
			//Create nodes from the given line if not already initialized
			for(Integer i : nodeAdj){
				if(!graphList.containsKey(i)) { //test if node is already initialized
					node = new Node(i);
					graphList.put(i, node);
					//System.out.println("Added node " + i);
				}
			}
			//set out degree of first node to the number of nodes on its adj. list
			node = graphList.get(nodeAdj.get(0));
			node.setOut(nodeAdj.size() - 1);
			Node adj;
			//Create adj. List for first node on the line, add to adj. in degrees
			for(Integer j = 1; j < nodeAdj.size(); j++){
				adj = graphList.get(nodeAdj.get(j));
				node.adjList.add(adj);
				adj.setIn(adj.getIn() + 1);
				//System.out.println("Node: " + adj.getId() + " in " + adj.getIn() + " out " + adj.getOut());
			}
			
		}
		/*for(Integer i : graphList.keySet()){
			System.out.println("Node: " + i + " (in, out) "+ graphList.get(i).getIn() + ", "+ graphList.get(i).getOut());
		}*/
		sc.close();
	}
	
	/*Function used to run through graph using depth first search
	 * Input: NA
	 * Output: true if the graph has a cycle through all nodes,
	 * 			false if the graph does not have this cycle
	 * */
	public static boolean runGraph(){
		//based on graph theory, if there is a node with an in degree
		//not equal to its out degree, the graph cannot run through a cycle
		//touching every edge
		for(Integer n : graphList.keySet()){
			if(graphList.get(n).getIn() != graphList.get(n).getOut()){
				return false;
			}
		}
		//take beginning node from graphList
		Node node = graphList.get(0);
		result.addAll(visit(node));
		return true;
	}
	public static ArrayList<Node> visit(Node node){
		ArrayList<Node> tempResult = new ArrayList<Node>();
		//Go through longest string of nodes possible
		Node next = node;
		while(next.getOut() > 0){
			//System.out.println("Node: " + next.getId() + ", Out: " + next.getOut());
			tempResult.add(next);
			next.setOut(next.getOut() - 1);
			next = next.next();
			next.setIn(next.getIn() - 1);
		}
		//If you hit a point where the last node to be reached had no out
		//options, then go through the graph to make sure all edges have been accounted for
		Integer index = Integer.MAX_VALUE;
		for(Node n : tempResult){
			//System.out.println("Node: " + n.getId() + ", Out: " + n.getOut());
			if(n.getOut() > 0 && n.getId() < index){
				index = n.getId();
				next = n;
			}
		}
		if(index == Integer.MAX_VALUE){
			return tempResult;
		}else{
			tempResult.addAll(tempResult.indexOf(next), visit(next));
			//System.out.println("Entered else");
			return tempResult;
		}
	}
}
