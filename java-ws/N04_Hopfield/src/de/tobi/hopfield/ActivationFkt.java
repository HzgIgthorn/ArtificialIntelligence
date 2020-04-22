package de.tobi.hopfield;

public class ActivationFkt {
	
	public static int stepFkt(double x) {
		if(x >= 0)
			return 1;
		else
			return -1;
	}

}
