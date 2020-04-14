package de.tobi.hillclimbing;

import java.text.DecimalFormat;

public class HillClimbing {
	private static final double START_X = -1;
	private static final double DELTA = 0.1;
	
	private final static DecimalFormat DDF = new DecimalFormat("###.###");
	
	public double f(double x) {
		return -1*(x-1)*(x-1)+2;
	}
	
	public void hillClimbingSearch() {
		double xmax = START_X;
		double ymax = f(xmax);
		
		while(f(xmax+ DELTA) > ymax) {
			System.out.print("("+DDF.format(xmax)+";"+DDF.format(ymax)+"), ");
			xmax = xmax+DELTA;
			ymax = f(xmax);
			
		}
		System.out.println("");
		System.out.println("Maximum bei ("+DDF.format(xmax)+";"+DDF.format(ymax)+").");
	}

}
