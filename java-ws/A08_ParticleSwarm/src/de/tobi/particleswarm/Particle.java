package de.tobi.particleswarm;

public class Particle implements Constants{
	
	private double[] position;
	private double[] velocity;
	private double[] bestPos;
	
	public Particle(double[] position, double[] velocity) {
		this.position = new double[NUM_DIM];
		this.velocity = new double[NUM_DIM];
		this.bestPos = new double[NUM_DIM];
		
		System.arraycopy(position, 0, this.position, 0, position.length);
		System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity) {
		this.velocity = velocity;
	}

	public double[] getBestPos() {
		return bestPos;
	}

	public void setBestPos(double[] bestPos) {
		this.bestPos = bestPos;
	}
	
	public void checkBestSolution(double[] globalBest) {
		if(ParticleSwarmApp.f(this.bestPos) < ParticleSwarmApp.f(globalBest)) {
			globalBest = this.bestPos;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("Momentan beste Lösungen: ");
		boolean hasFirst = false;
		for(double pos : bestPos) {
			if(hasFirst)
				builder.append("-"+pos);
			else {
				hasFirst = true;
				builder.append(pos);
			}
		}
		return builder.toString();
	}
}
