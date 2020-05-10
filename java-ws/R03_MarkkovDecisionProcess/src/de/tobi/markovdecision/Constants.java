package de.tobi.markovdecision;

import java.text.DecimalFormat;

public interface Constants {
	
	//nutzen von nicht-terminierten Status (zum initialisieren von r[][])
	public static double STATE_REWARD = -0.1;
	//discount Faktor um mit zukünftigen Belohnungen umzugehen
	public static double GAMMA = 0.99;
	//um ein stochastisches System zu bekommen Wkeit der Hauptrichtung
	public static double ACTION_PROB = 0.8;
	//um ein stochastisches System zu bekommen Wkeit der anderen Richtungen
	public static double ACTION_MISS_PROB = 0.1;
	//Anzahl der Iterationen
	public static int NUM_Of_ITERATIONS = 100;
	//Konvergenzkriterium für die Valuefunktion
	public static double EPSILON = 1e-7;
	//Höhe des Boards
	public static int NUM_ROWS = 3;
	//Länge des Boards
	public static int NUM_COLS = 4;
	
	static DecimalFormat DDF = new DecimalFormat("###.###");
}