package de.tobi.tictactoe;

public class Cell {
	
	private int x;
	private int y;
	private int minmax;
	
	public Cell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getMinmax() {
		return minmax;
	}
	
	public void setMinmax(int minmax) {
		this.minmax = minmax;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "("+this.x+"/"+this.y+")";
	}
}
