package de.tobi.tabusearch;

public class CostFunktion {
	
	public static double f(double x, double y) {
		return Math.exp(-x*x-y*y)*Math.sin(x);
	}

}
