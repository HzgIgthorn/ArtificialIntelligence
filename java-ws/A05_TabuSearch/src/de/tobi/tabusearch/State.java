package de.tobi.tabusearch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class State {
	final static DecimalFormat DDF = new DecimalFormat("###.###");
	
	private double x;
	private double y;
	private double z;
	private List<State> neigh;
	
	public State(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.neigh = new ArrayList<State>();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public List<State> getNeigh() {
		return neigh;
	}

	public void addNeigh(State neigh) {
		this.neigh.add(neigh);
	}

	@Override
	public String toString() {
		return "State [x=" + DDF.format(x) + ", y=" + DDF.format(y) + ", z=" + DDF.format(z) + "]";
	}
}
