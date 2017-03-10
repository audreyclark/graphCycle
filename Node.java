/*Audrey Clark January 2017
 * CS 405 Assign 2
 * Node class to hold information from nodes in given graph
 * */
import java.util.*;
public class Node {
	//queue of edges
	LinkedList<Node> adjList = new LinkedList<Node>();
	//number of edges pointing in
	int in = 0;
	//number of edges pointing out
	int out = 0;
	private int id;
	
	//constructor
	public Node(int id){
		this.id = id;
	}
	public int getId(){
		return this.id;
	}
	public int getIn(){
		return this.in;
	}
	public int getOut(){
		return this.out;
	}
	public void setIn(int n){
		this.in = n;
	}
	public void setOut(int n){
		this.out = n;
	}
	public Node next(){
		if(this.adjList.isEmpty()){
			return null;
		}else{
			Node next = adjList.remove();
			return next;
		}
	}
}
