package de.tobi.qlearning;

import java.text.DecimalFormat;
import java.util.Random;

public interface Constants {
	
	//nutzen von nicht-terminierten Status (zum initialisieren von r[][])
	public static double STATE_REWARD = 0;
	//nutzen von terminierten Status
	public static double EXIT_REWARD = 100;
	//discount Faktor um mit zukünftigen Belohnungen umzugehen
	public static double GAMMA = 0.9;
	//Lernrate
	public static double ALPHA = 0.1;
	//Anzahl der Iterationen
	public static int NUM_Of_ITERATIONS = 100000;
	//Nutzen bei verwenden von nicht existierenden Kanten
	public static double MIN_VALUE = -Double.MAX_VALUE;
	//Anzahl der Knoten
	public static int NUM_STATES = 6;
	
	static Random RAND = new Random();
	
	static DecimalFormat DDF = new DecimalFormat("###.###");
}