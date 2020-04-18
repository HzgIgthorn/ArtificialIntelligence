package de.tobi.particleswarm;

public class ParticleSwarmApp {

	public static void main(String[] args) {
		SwarmOpt opt = new SwarmOpt();
		opt.solve(false);
		opt.showSolution("Lösung");
	}
	
	public static double f(double[] xy) {
		return Math.exp(-xy[0]*xy[0]-xy[1]*xy[1])*Math.sin(xy[0] );
	}

}
