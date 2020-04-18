package de.tobi.geneticfkt;

public class Population {
	
	private Individual[] individuals;

	public Population(int size) {
		super();
		this.individuals = new Individual[size];
	}
	
	public void init() {
		for(int i = 0; i < individuals.length; ++i) {
			Individual individual = new Individual();
			individual.generateIndividual();
			saveIndividual(i, individual);
		}
	}

	public Individual getIndividual(int i) {
		return this.individuals[i];
	}
	
	public Individual getFittestIndividual() {
		Individual res = individuals[0];
		
		for(int i = 1; i < individuals.length; ++i) {
			if(getIndividual(i).getFitness() <= res.getFitness())
				res = individuals[i];
		}
		return res;
	}
	
	public int size() {
		return this.individuals.length;
	}
	
	public void saveIndividual(int i, Individual individual) {
		this.individuals[i] = individual;		
	}
}
