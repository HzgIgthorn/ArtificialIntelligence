package de.tobi.perceptron;

public class Perceptron {
	
	private float[] w;
	private float[][] input;
	private float[] output;
	private int numW;
	
	public Perceptron(float[][] inp, float[] out) {
		super();
		this.input = inp;
		this.output = out;
		this.numW = input[0].length;
		this.w = new float[numW];
		
		initW();
	}

	private void initW() {
		for(int i=0; i<numW; i++) {
			w[i]=0;
		}
	}
	
	public void train(float lernRate) {
		float totalE = 1;
		while( totalE != 0) {
			totalE = 0;
			for(int i=0; i < output.length; i++) {
				float calcOut = calcOutput(input[i]);
				float error = Math.abs(output[i]-calcOut);
				totalE += error;
				
				for(int j=0;j<numW; j++) {
					w[j] = w[j] + lernRate*input[i][j]*error;
				}
			}
			System.out.println("Während des Trainings ist der totale Fehler: "+ totalE);
		}
	}
	
	public float calcOutput(float[] input){
		float sum = 0f;
		for(int i = 0; i < input.length; i++) {
			sum = sum + w[i] * input[i];
		}
		return ActivationFkt.stepFkt(sum);
	}

	public float[] getW() {
		return w;
	}
	
	
}
