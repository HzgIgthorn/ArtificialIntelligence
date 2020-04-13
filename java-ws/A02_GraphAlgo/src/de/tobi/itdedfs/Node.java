package de.tobi.itdedfs;

import java.util.ArrayList;
import java.util.List;

public class Node {
	
	private String name;
	private int depthLevel = 0;
	private List<Node> adjList;
	
	public Node(String name) {
		this.name = name;
		this.adjList = new ArrayList<Node>();
	}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getDepthLevel() {
		return depthLevel;
	}



	public void setDepthLevel(int depthLevel) {
		this.depthLevel = depthLevel;
	}



	public List<Node> getAdjList() {
		return adjList;
	}



	public void setAdjList(List<Node> adjList) {
		this.adjList = adjList;
	}



	public void addNeigh(Node node) {
		this.adjList.add(node);
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	

}
