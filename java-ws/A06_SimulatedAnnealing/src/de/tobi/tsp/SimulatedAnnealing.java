package de.tobi.tsp;

import java.util.Random;

public class SimulatedAnnealing {
	
	private final static Random RAND = new Random();
	
	private SingleTour bestSol;
	
	public void simulation() {
		
		double temp = 10000;
		double cooling = 0.003;
		
		SingleTour currSol = new SingleTour();
		currSol.generateIndividual();
		
		System.out.println("Initial beste Lösung: " + currSol);
		
		bestSol = new SingleTour(currSol.getTour());
		
		while(temp > 1) {
			SingleTour nextSol = new SingleTour(currSol.getTour());
			int random1 = RAND.nextInt(nextSol.getTourSize());
			City city1 = nextSol.getCity(random1);
			
			int random2 = RAND.nextInt(nextSol.getTourSize());
			City city2 = nextSol.getCity(random2);
			
			nextSol.setCity(random2, city1);
			nextSol.setCity(random1, city2);
			
			double currEnergy = currSol.getDistance();
			double nextEnergy = nextSol.getDistance();
			
			if(acceptaceProb(currEnergy, nextEnergy,temp) > RAND.nextDouble()) {
				currSol = new SingleTour(nextSol.getTour());
				System.out.println("Aktuell untersuchte Tour: " + currSol);
			}
			
			if(currSol.getDistance() < bestSol.getDistance()) {
				bestSol = new SingleTour(currSol.getTour());
			}
			
			temp *= 1-cooling;
		}
	}
	
	public SingleTour getBestSol() {
		return bestSol;
	}

	private double acceptaceProb(double currEnergy, double nextEnergy, double temp) {
		if(nextEnergy < currEnergy)
			return 1;
		return Math.exp((currEnergy-nextEnergy)/temp);
	}

}
