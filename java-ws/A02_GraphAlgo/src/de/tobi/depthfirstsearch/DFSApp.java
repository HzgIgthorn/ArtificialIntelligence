package de.tobi.depthfirstsearch;

import de.tobi.breadthfirstsearch.Graph;

public class DFSApp {

	public static void main(String[] args) {
		Graph graph = new Graph();
		
		DFS<String> dfs = new DFS<String>(graph.getVertexes());
		
		getLs(10);
		
		dfs.dfsRec(new Graph().getVertexes().get(1));

	}

	private static void getLs(int max) {
		for(int i = 0; i < max; i++) {
			System.out.print("-");
		}
		System.out.println("-");
	}
}
