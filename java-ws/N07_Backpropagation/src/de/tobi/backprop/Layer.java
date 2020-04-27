package de.tobi.backprop;

import java.util.Arrays;

public class Layer implements Constants{
	
	private float[] output;
	private float[] input;
	private float[] weights;
	private float[] dWeights;
	
	public Layer(int inputSize, int outputSize) {
		this.output = new float[outputSize];
		this.input = new float[inputSize + 1];
		this.weights = new float[(inputSize + 1) * outputSize];
		this.dWeights = new float[weights.length];
		
		init();
	}

	private void init() {
		for(int i = 0; i < weights.length; i++) {
			weights[i] = (RAND.nextFloat()-SHIFT)*FAK;
		}
	}
	
	public float[] run(float[] inputArray) {
		System.arraycopy(inputArray, 0, input, 0, inputArray.length);
		
		input[input.length - 1] = 1;
		int offset = 0;
		
		for(int i=0; i<output.length; i++) {
			for(int j=0; j<input.length; j++) {
				//wir müssen den Index für das Array um ein Offset shiften, weil alle Knoten in einem Array stehen.
				//Jeder folgende Knoten hat den Index um die der vorigen Knoten vershoben
				output[i] += weights[offset + j]*input[j];
			}
			//output wird erst summiert und dann durch die sigmoid Funktion geschickt
			output[i] = ActivationFkt.sigmoid(output[i]);
			offset += input.length;
		}
		
		return Arrays.copyOf(output, output.length);
	}
	
	public float[] train(float[] error, float learningRate, float momentum) {
		int offset = 0;
		float[] nextError = new float[input.length];
		for(int i=0; i < output.length; i++) {
			float delta = error[i]*ActivationFkt.dSigmoid(output[i]);
			
			for(int j=0; j<input.length; j++) {
				int weightIndex = offset + j;
				nextError[j] = nextError[j]+weights[weightIndex]*delta;
				//input[j] * delta ist der Gradient
				float dw = input[j] * delta * learningRate;
				//dWeights[weightIndex] ind die Gewichte aus der vorigen Iteration
				weights[weightIndex] += dWeights[weightIndex] * momentum + dw;
				
				dWeights[weightIndex] = dw;
			}
		}
		return nextError;
	}
}
