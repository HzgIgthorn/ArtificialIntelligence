package de.tobi.perceptron;

public class ActivationFkt {
	
	public static int stepFkt(float x) {
		final int TRESHOLD = 1;
		if(x >= TRESHOLD)
			return 1;
		return 0;
	}
}
