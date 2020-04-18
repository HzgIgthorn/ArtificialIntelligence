package de.tobi.particleswarm;

public class SwarmOpt implements Constants {

	private double[] globalBest;
	private Particle[] swarm;
	private int epoche;

	public SwarmOpt() {
		super();
		this.globalBest = new double[NUM_DIM];
		this.swarm = new Particle[NUM_PART];
		generateRandomSolution();
	}

	public void solve(boolean min) {
		for (int i = 0; i < NUM_PART; i++) {
			double[] locations = initLocation();
			double[] velocities = initVelocity();
			this.swarm[i] = new Particle(locations, velocities);
		}

		while (epoche < MAX_ITER) {
			for (Particle particle : this.swarm) {

				// update der Geschwindigkeiten
				for (int i = 0; i < particle.getVelocity().length; i++) {
					double rp = RAND.nextDouble();
					double rg = RAND.nextDouble();

					particle.getVelocity()[i] = W * particle.getVelocity()[i]
							+ C1 * rp * (particle.getBestPos()[i] - particle.getPosition()[i])
							+ C2 * rg * (this.globalBest[i] - particle.getPosition()[i]);
				}

				// update der Positionen
				for (int i = 0; i < particle.getPosition().length; i++) {
					particle.getPosition()[i] += particle.getVelocity()[i];
					if (particle.getPosition()[i] < MIN)
						particle.getPosition()[i] = MIN;
					if (particle.getPosition()[i] > MAX)
						particle.getPosition()[i] = MAX;
				}

				if(min) {
					if (ParticleSwarmApp.f(particle.getPosition()) < ParticleSwarmApp.f(particle.getBestPos()))
						particle.setBestPos(particle.getPosition());
					if (ParticleSwarmApp.f(particle.getPosition()) < ParticleSwarmApp.f(globalBest)) {
						
						System.arraycopy(particle.getBestPos(), 0, globalBest, 0, particle.getBestPos().length);
					}
						
				}else {
					if (ParticleSwarmApp.f(particle.getPosition()) > ParticleSwarmApp.f(particle.getBestPos()))
						particle.setBestPos(particle.getPosition());
					if (ParticleSwarmApp.f(particle.getPosition()) > ParticleSwarmApp.f(globalBest))
						System.arraycopy(particle.getBestPos(), 0, globalBest, 0, particle.getBestPos().length);
				}
				
			}
			epoche++;
		}
	}

	public void showSolution(String info) {
		System.out.println(info+" für das PSO Problem!");
		StringBuilder builder = new StringBuilder(info +" ist f(");
		boolean hasFirst = false;
		for (double best : globalBest) {
			if (hasFirst)
				builder.append("/" + DDF.format(best));
			else {
				hasFirst = true;
				builder.append("" + DDF.format(best));
			}
		}
		builder.append(") = "+DDF.format(ParticleSwarmApp.f(globalBest)));
		System.out.println(builder.toString());
	}

	private void generateRandomSolution() {
		for (int i = 0; i < NUM_DIM; i++) {
			double random = random(MIN, MAX);
			this.globalBest[i] = random;
		}
	}

	private double random(double min, double max) {
		return RAND.nextDouble() * Math.abs(MAX - MIN) + MIN;
	}

	private double[] initLocation() {
		double x = random(MIN, MAX);
		double y = random(MIN, MAX);

		double[] location = new double[] { x, y };
		return location;
	}

	private double[] initVelocity() {
		double vmin = Math.abs(MAX - MIN);
		double vx = random(-vmin, vmin);
		double vy = random(-vmin, vmin);

		double[] velocity = new double[] { vx, vy };
		return velocity;
	}
}
