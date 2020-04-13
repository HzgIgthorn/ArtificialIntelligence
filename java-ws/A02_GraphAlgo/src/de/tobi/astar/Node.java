package de.tobi.astar;

public class Node {
	
	private int g;
	private int h;
	private int rI;
	private int cI;
	private Node parent;
	private boolean blocked;
	
	public Node(int rI, int cI) {
		this.rI = rI;
		this.cI = cI;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
	public int getF() {
		return g + h;
	}
	
	public int getrI() {
		return rI;
	}

	public int getcI() {
		return cI;
	}

	@Override
	public String toString() {
		return "Node [g=" + g + ", h=" + h + ", rI=" + rI + ", cI=" + cI + ", parent=" + parent + ", blocked=" + blocked
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (cI != other.cI)
			return false;
		if (rI != other.rI)
			return false;
		return true;
	}
}
