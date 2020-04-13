package de.tobi.bruteforce;

import java.text.DecimalFormat;

public class BruteForce {
	
	private static final double START_X = -1;
	private static final double END_X = 2;
	private static final double DELTA = 0.1;
	
	private final static DecimalFormat DDF = new DecimalFormat("###.###");
	
	public double f(double x) {
		return -1*(x-1)*(x-1)+2;
	}
	
	public void bruteForceSearch() {
		double xmax = START_X;
		double ymax = f(xmax);
		
		for(double x = START_X; x < END_X; x=x+DELTA) {
			double y = f(x);
			if(y < ymax) {
				xmax = x;
				ymax = y;
			}
		}
		System.out.println("Maximum bei ("+DDF.format(xmax)+";"+DDF.format(ymax)+").");
	}

}
