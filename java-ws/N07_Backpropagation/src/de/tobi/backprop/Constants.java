package de.tobi.backprop;

import java.util.Random;

public interface Constants {
	
	static float LEARNING_RATE = 0.3f;
	static float MOMENTUM = 0.6f;
	static int ITERATIONS = 10000;
	
	static Random RAND = new Random();
	static float SHIFT = 0.5f;
	static float FAK = 4f;
}
