package de.tobi.main;

import de.tobi.backprop.BackPropNetwork;
import de.tobi.backprop.Constants;

public class ClusteringApp implements Constants{

	public static void main(String[] args) {
		float[][] tData = new float[][] {
			
			//gelbe Kreise
			new float[] {0.1f, 0.2f},
			new float[] {0.3f, 0.2f},
			new float[] {0.15f, 0.58f},
			new float[] {0.45f, 0.7f},
			new float[] {0.4f, 0.9f},
			
			//grüne Kreise
			new float[] {0.4f, 1.2f},
			new float[] {0.45f, 0.95f},
			new float[] {0.42f, 1f},
			new float[] {0.5f, 1.1f},
			new float[] {0.52f, 1.45f},
			
			//blaue Kreise
			new float[] {0.6f, 0.2f},
			new float[] {0.75f, 0.7f},
			new float[] {0.9f, 0.34f},
			new float[] {0.85f, 0.76f},
			new float[] {0.8f, 0.34f}
		};
		
		//Da Neuronale Netze mit 0-1 Werten besser funktionieren, verwenden wir hier die Ergebnisse
		// (1,0,0) (0,1,0) und (0,0,1) für die einzelnen Cluster
		float[][] tRes = new float[][] {
			new float[] {0, 0},
			new float[] {0, 0},
			new float[] {0, 0},
			new float[] {0, 0},
			new float[] {0, 0},
			new float[] {0, 1},
			new float[] {0, 1},
			new float[] {0, 1},
			new float[] {0, 1},
			new float[] {0, 1},
			new float[] {1, 0},
			new float[] {1, 0},
			new float[] {1, 0},
			new float[] {1, 0},
			new float[] {1, 0},
		};
		
		BackPropNetwork network = new BackPropNetwork(2, 4, 2);
		
		//training
		for(int i = 0; i < ITERATIONS; i++) {
			for(int j =0; j < tData.length; j++) {
				network.train(tData[j], tRes[j], LEARNING_RATE, MOMENTUM);
			}
			//System.out.println("Training " + i);
		}
		
		System.out.println(network);
		
		float[] res1 = network.run(new float[] {0.05f, 0.05f});
		System.out.println(DDF.format(res1[0])+" - "+DDF.format(res1[1]));
		
		float[] res2 = network.run(new float[] {0.6f, 0.2f});
		System.out.println(DDF.format(res2[0])+" - "+DDF.format(res2[1]));
	}
}
