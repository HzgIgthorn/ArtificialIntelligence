package de.tobi.geneticalgo;

import java.util.Random;

public interface Constants {
	
	static int[] SOL_SEQUENCE = {0, 1,2,3,4,5,6,7,8,9};
	static double CROSS_RATE = 0.5;
	static double MUTA_RATE = 0.005;
	static int TOURN_SIZE = 5;
	static int CHROMO_LENGTH = 10;
	static int MAX_FITNESS = 10;
	static int SIM_LENGTH = 100;
	static int GENE_LENGTH = 10;
	
	static Random RAND = new Random();
}
