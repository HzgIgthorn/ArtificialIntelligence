package de.tobi.geneticalgo;


public class Individual implements Constants{
	
	private int[] genes;
	private int fitness;
	
	public Individual() {
		super();
		this.genes = new int[CHROMO_LENGTH];
	}
	
	public void generateIndividual() {
		for(int i=0; i<CHROMO_LENGTH; ++i) {
			int gene = RAND.nextInt(CHROMO_LENGTH);
			genes[i] = gene;
		}
	}

	public int getFitness() {
		if(fitness == 0) {
			for(int i=0; i < CHROMO_LENGTH; ++i) {
				if(genes[i] == SOL_SEQUENCE[i]) {
					this.fitness++;
				}
			}
		}
		return this.fitness;
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
