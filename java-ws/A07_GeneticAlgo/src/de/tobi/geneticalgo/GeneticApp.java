package de.tobi.geneticalgo;

public class GeneticApp implements Constants{

	public static void main(String[] args) {
		GeneticAlgo algo = new GeneticAlgo();
		Population population = new Population(100);
		population.init();
		
		int count = 0;
		
		while(population.getFittestIndividual().getFitness() != MAX_FITNESS) {
			count++;
			System.out.println("Generation: "+ count + " fittest is: " + population.getFittestIndividual().getFitness());
			System.out.println(population.getFittestIndividual()+"\n");
			population = algo.evolvePopulation(population);
		}
		
		System.out.println("Lösung gefunden "+population.getFittestIndividual());

	}

}
