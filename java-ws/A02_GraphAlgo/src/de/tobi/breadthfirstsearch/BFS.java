package de.tobi.breadthfirstsearch;

import java.util.LinkedList;
import java.util.Queue;

public class BFS<T> {

	public void bfs(Vertex<T> root) {
		Queue<Vertex<T>> queue = new LinkedList<Vertex<T>>();
		root.setVisited(true);
		queue.add(root);
		
		while(!queue.isEmpty()) {
			Vertex<T> aktVertex = queue.remove();
			System.out.println("visit -> "+aktVertex);
			aktVertex.getNeighList().stream().filter(v -> !v.isVisited()).forEach(v -> {v.setVisited(true); queue.add(v);});
		}
	}
	
	

}
