package de.tobi.breadthfirstsearch;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	List<Vertex<String>> vertexes;

	public Graph() {
		super();
		Vertex<String> va = new Vertex<>("A");
		Vertex<String> vb = new Vertex<>("B");
		Vertex<String> vc = new Vertex<>("C");
		Vertex<String> vd = new Vertex<>("D");
		Vertex<String> ve = new Vertex<>("E");
		Vertex<String> vf = new Vertex<>("F");
		Vertex<String> vg = new Vertex<>("G");
		Vertex<String> vh = new Vertex<>("H");
		
		va.addNeigh(vb);
		va.addNeigh(vf);
		va.addNeigh(vg);
		vb.addNeigh(vc);
		vb.addNeigh(vd);
		vd.addNeigh(ve);
		vg.addNeigh(vh);
		ve.addNeigh(va);
		
		vertexes = new ArrayList<Vertex<String>>();
		vertexes.add(va);
		vertexes.add(vb);
		vertexes.add(vc);
		vertexes.add(vd);
		vertexes.add(ve);
		vertexes.add(vf);
		vertexes.add(vg);
		vertexes.add(vh);
	}

	public List<Vertex<String>> getVertexes() {
		return vertexes;
	}
}
