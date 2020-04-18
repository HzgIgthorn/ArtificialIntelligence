package de.tobi.particleswarm;

import java.text.DecimalFormat;
import java.util.Random;

public interface Constants {
	
	static DecimalFormat DDF = new DecimalFormat("###.###");
	
	static int NUM_DIM = 2;
	static int NUM_PART = 10;
	static double MIN = -2;
	static double MAX = 2;
	static double W=0.729;
	static double C1=1.49;
	static double C2=1.49;
	
	static double MAX_ITER = 10000;
	
	static Random RAND = new Random();
}
