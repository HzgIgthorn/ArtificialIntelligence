package de.tobi.hopfield;

public class Utils {
	
	public static double[] transform(double[] pattern) {
		double[] res = new double[pattern.length];
		for(int i = 0; i < pattern.length; i++) {
			if(pattern[i] == 0)
				res[i] = -1;
			else
				res[i] = pattern[i];
		}
		return res;
	}

}
