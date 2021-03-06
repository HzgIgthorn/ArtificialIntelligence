package de.tobi.depthfirstsearch;

import java.util.List;
import java.util.Stack;

import de.tobi.breadthfirstsearch.Vertex;

public class DFS<T> {
	
	public DFS(List<Vertex<T>> vertexes) {
		vertexes.stream().filter(v -> !v.isVisited()).forEach(v -> dfs(v));
	}
	
	public DFS() {}

	public void dfs(Vertex<T> root) {
		Stack<Vertex<T>> stack= new Stack<Vertex<T>>();
		root.setVisited(true);
		stack.add(root);
		
		while(!stack.isEmpty()) {
			Vertex<T> aktVertex = stack.pop();
			System.out.println("visit -> "+aktVertex);
			aktVertex.getNeighList().stream().filter(v -> !v.isVisited()).forEach(v -> {v.setVisited(true); stack.push(v);});
		}
	}
	
	public void dfsRec(Vertex<T> root) {
		System.out.println("visit -> " + root);
		root.setVisited(true);
		
		for(Vertex<T> vertex : root.getNeighList()) {
			if(!vertex.isVisited()) {
				dfsRec(vertex);
			}
		}
	}

}
