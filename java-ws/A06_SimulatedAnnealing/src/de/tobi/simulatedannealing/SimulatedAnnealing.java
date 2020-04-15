package de.tobi.simulatedannealing;

import java.util.Random;

public class SimulatedAnnealing implements Constants{
	
	private Random random;
	private double currCoord;
	private double nextCoord;
	private double bestCoord;
	
	public void findOpt() {
		double temp = MAX_TEMP;
		while(temp > MIN_TEMP) {
			nextCoord = random.nextDouble()*(MAX_COORD-MIN_COORD)+MIN_COORD;
			
			double aktEnergy = getEnergy(currCoord);
			double newEnergy = getEnergy(nextCoord);
			if(acceptaceProb(aktEnergy, newEnergy, temp)>Math.random()) {
				currCoord=nextCoord;
			}
			if(f(currCoord) < f(bestCoord)) {
				bestCoord = currCoord;
				System.out.println("Aktueller Wert ist bei ("+DDF.format(currCoord)+"/"+DDF.format(f(currCoord))+") und temp ist "+DDF.format(temp));
			}
			temp *= 1 - COOLING;
			
		}
		
		System.out.println("Geschätztes Optimum ist bei ("+DDF.format(bestCoord)+"/"+DDF.format(f(bestCoord))+").");
	}
	
	public SimulatedAnnealing() {
		super();
		this.random = new Random();
	}

	public double getEnergy(double x) {
		return f(x);
	}
	
	public double f(double x) {
		return (x-0.3)*(x-0.3)*(x-0.3)-5*x+x*x-2;
	}
	
	public double acceptaceProb(double energy, double newEnergy, double temp) {
		
		if(newEnergy < energy)
			return 1;
		return Math.exp((energy-newEnergy)/temp);
	}

}
