package de.tobi.geneticfkt;

import de.tobi.geneticalgo.Constants;
import de.tobi.geneticfkt.Population;

public class GeneticAlgo implements Constants{
	
	public Population evolvePopulation(Population population) {
		Population res = new Population(population.size());
		
		for(int i = 0; i < population.size(); i++) {
			Individual indi1 = randomSelection(population);
			Individual indi2 = randomSelection(population);
			
			Individual IndiC = crossover(indi1, indi2);
			res.saveIndividual(i, IndiC);
		}
		
		for(int i = 0; i < res.size(); i++) {
			mutate(res.getIndividual(i));
		}
		return res;
	}

	private void mutate(Individual individual) {
		for(int i=0; i < CHROMO_LENGTH; i++) {
			if(RAND.nextDouble() < MUTA_RATE) {
				int gene = RAND.nextInt(2);
				individual.setGene(i, gene);
			}
		}
	}

	private Individual crossover(Individual indi1, Individual indi2) {
		Individual res = new Individual();
		for(int i= 0; i < CHROMO_LENGTH; i++) {
			if(RAND.nextDouble() < CROSS_RATE) {
				res.setGene(i, indi1.getGene(i));
			}else {
				res.setGene(i, indi2.getGene(i));
			}
		}
		return res;
	}

	private Individual randomSelection(Population population) {
		Population newPop = new Population(TOURN_SIZE);
		
		for(int i=0; i<TOURN_SIZE; i++) {
			int index = RAND.nextInt(population.size());
			newPop.saveIndividual(i, population.getIndividual(index));
		}
		
		Individual fittest = newPop.getFittestIndividual();
		return fittest;
	}
}
