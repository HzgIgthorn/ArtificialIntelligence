package de.tobi.tabusearch;

public interface Constants {
	
	/**
	 * because the interval is [-10,10] with 0.1 steps
	 */
	static int NUM_VALUES = 200;
	
	/**
	 * Number of iterations in the search
	 */
	static int NUM_ITER = 100000;
	
	/**
	 * Tabu tenure is the size of the tabu list or queue
	 */
	static int TABU_TENURE = 400;
}
