package de.tobi.stochasticsearch;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class RandomSearch {
	
	private static final double START_X = -1;
	private static final double END_X = 2;
	private static final int ITER = 1000;
	
	private final static DecimalFormat DDF = new DecimalFormat("###.###");
	
	public double f(double x) {
		return -1*(x-1)*(x-1)+2;
	}
	
	public void randomSearch() {
		double xmax = START_X;
		double ymax = f(xmax);
		
		for(int i = 0; i < ITER; ++i) {
			double x = ThreadLocalRandom.current().nextDouble(START_X, END_X);
			double y = f(x);
			if(y > ymax) {
				xmax = x;
				ymax = y;
			}
		}
		System.out.println("Maximum bei ("+DDF.format(xmax)+";"+DDF.format(ymax)+").");
	}

}
