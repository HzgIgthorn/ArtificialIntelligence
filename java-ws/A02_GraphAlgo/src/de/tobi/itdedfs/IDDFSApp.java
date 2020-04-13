package de.tobi.itdedfs;

public class IDDFSApp {

	public static void main(String[] args) {
		Node va = new Node("A");
		Node vb = new Node("B");
		Node vc = new Node("C");
		Node vd = new Node("D");
		Node ve = new Node("E");
		
		va.addNeigh(vb);
		va.addNeigh(vc);
		vb.addNeigh(vd);
		vd.addNeigh(ve);
		
		IDDFS algo = new IDDFS(ve);
		algo.runDeepeningSearch(va);

	}

}
