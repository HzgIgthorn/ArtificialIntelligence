package de.tobi.breadthfirstsearch;

public class BFSApp {

	public static void main(String[] args) {
		Graph graph = new Graph();
		
		BFS<String> bfs = new BFS<>();
		bfs.bfs(graph.getVertexes().get(0));

	}

}
