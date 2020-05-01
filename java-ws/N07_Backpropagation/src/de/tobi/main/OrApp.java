package de.tobi.main;

import de.tobi.backprop.BackPropNetwork;
import de.tobi.backprop.Constants;
import de.tobi.backprop.Layer;

public class OrApp implements Constants{

	public static void main(String[] args) {
		float[][] tData = new float[][] {
			new float[] {0,0},
			new float[] {0,1},
			new float[] {1,0},
			new float[] {1,1}
		};
		
		float[][] tRes = new float[][] {
				new float[] {0},
				new float[] {1},
				new float[] {1},
				new float[] {1}
		};
		
		BackPropNetwork network = new BackPropNetwork(2, 3, 1);
		
		for(int i = 0; i < ITERATIONS; i++) {
			for(int j = 0; j < tRes.length; j++) {
				network.train(tData[j], tRes[j], LEARNING_RATE, MOMENTUM);
			}
			System.out.println();
			for(int j = 0; j < tRes.length; j++) {
				float[] t = tData[j];
				System.out.println("Anzahl der Iterationen: " + (i + 1));
				System.out.printf("%.1f, %.1f --> %.3f\n", t[0], t[1], network.run(t)[0]);
			}
		}
		for(int i = 0; i < network.size(); i++) {
			Layer layer = network.getLayer(i);
			int j=0;
			for(Float w : layer.getWeights()) {
				//System.out.print("w"+i+""+j++ +" ist "+DDF.format(w)+" ");
			}
		}
		System.out.println(network);
	}
}
