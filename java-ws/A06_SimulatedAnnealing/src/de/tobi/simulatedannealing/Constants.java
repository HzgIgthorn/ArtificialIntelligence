package de.tobi.simulatedannealing;

import java.text.DecimalFormat;

public interface Constants {

	static double MIN_COORD = -2.55;
	static double MAX_COORD = 2.55;
	static double MAX_TEMP = 100;
	static double MIN_TEMP = 1;
	static double COOLING = 0.02;
	
	static DecimalFormat DDF = new DecimalFormat("###.###");
}
