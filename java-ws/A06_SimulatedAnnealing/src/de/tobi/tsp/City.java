package de.tobi.tsp;

import java.util.Random;

public class City {
	
	private int x;
	private int y;
	
	private final static Random RAND = new Random();
	
	public City() {
		super();
		this.x = RAND.nextInt(100)+1;
		this.y = RAND.nextInt(100)+1;
	}
	
	public City(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public double distTo(City city) {
		int distX = Math.abs(city.getX()-this.getX());
		int distY = Math.abs(city.getY()-this.getY());
		
		return Math.sqrt(distX*distX +distY*distY);
	}

	@Override
	public String toString() {
		return "("+this.x+"/"+this.y+")";
	}
}
