package de.tobi.tsp;

public class TSPApp {

	public static void main(String[] args) {
		for(int i = 0; i < 100; i++) {
			City city = new City();
			Repository.addCity(city);
		}
		
		SimulatedAnnealing search = new SimulatedAnnealing();
		search.simulation();
		
		System.out.println("Approximativ beste Lösung ist: "+ search.getBestSol());

	}

}
