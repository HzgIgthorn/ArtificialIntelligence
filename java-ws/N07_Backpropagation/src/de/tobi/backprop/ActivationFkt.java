package de.tobi.backprop;

public class ActivationFkt {
	
	public static float sigmoid(float x) {
		return (float) (1/(1+Math.exp(-x)));
	}
	
	public static float dSigmoid(float s) {
		return s*(1-s);
	}
}
