package de.tobi.breadthfirstsearch;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
	
	private T data;
	private boolean visited;
	private List<Vertex<T>> neighList;
	
	public Vertex(T data) {
		super();
		this.data = data;
		this.visited = false;
		this.neighList = new ArrayList<Vertex<T>>();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public List<Vertex<T>> getNeighList() {
		return neighList;
	}

	public void setNeighList(List<Vertex<T>> neighList) {
		this.neighList = neighList;
	}

	public void addNeigh(Vertex<T> vertex) {
		this.neighList.add(vertex);
	}

	@Override
	public String toString() {
		return ""+data.toString();
	}
}
