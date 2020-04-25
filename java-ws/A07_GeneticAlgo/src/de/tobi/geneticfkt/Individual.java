package de.tobi.geneticfkt;

import de.tobi.geneticalgo.Constants;

public class Individual implements Constants{
	
	private int[] genes;
	@SuppressWarnings("unused")
	private int fitness;
	
	public Individual() {
		super();
		this.genes = new int[CHROMO_LENGTH];
	}
	
	public void generateIndividual() {
		for(int i=0; i<CHROMO_LENGTH; ++i) {
			int gene = RAND.nextInt(2);
			genes[i] = gene;
		}
	}
	
	public double f(double x) {
		return Math.sin(x)*((x-2)*(x-2))+3;
	}
	
	public double genesToDouble() {
		int base = 1;
		
		double geneInDouble =0;
		
		for(int i = 0; i < Constants.GENE_LENGTH; i++) {
			if(this.genes[i] == 1) {
				geneInDouble += base;
			}
			base = base * 2;
		}
		
		geneInDouble = geneInDouble / 102.4f;
		return geneInDouble;
	}

	public double getFitness() {
		double geneInDouble = genesToDouble();
		return f(geneInDouble);
	}

	public int getGene(int i) {
		return this.genes[i];
	}

	public void setGene(int i, int val) {
		this.genes[i] = val;
		this.fitness = 0;
	}

	@Override
	public String toString() {
		StringBuilder builder= new StringBuilder(""+getGene(0));
		for(int i=1; i<CHROMO_LENGTH; i++) {
			builder.append(", "+getGene(i));
		}
		return builder.toString();
	}
}
