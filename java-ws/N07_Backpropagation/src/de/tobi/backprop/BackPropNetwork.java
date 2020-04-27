package de.tobi.backprop;

public class BackPropNetwork {
	
	private Layer[] layers;

	public BackPropNetwork(int inputSize, int hiddenSize, int outputSize) {
		this.layers = new Layer[2];
		layers[0] = new Layer(inputSize, hiddenSize);
		layers[1] = new Layer(hiddenSize, outputSize);
	}
	
	public Layer getLayer(int index) {
		return layers[index];
	}
	
	public float[] run(float[] input) {
		float[] activations = input;
		
		for(int i=0; i<layers.length; i++) {
			activations = layers[i].run(activations);
		}
		return activations;
	}
	
	public void train(float[] input, float[] targetOutput, float learningRate, float momentum) {
		float[] calOut = run(input);
		float[] error = new float[calOut.length];
		for(int i=0; i<error.length; i++) {
			error[i] = targetOutput[i] - calOut[i];
		}
		
		for(int i = layers.length-1; i>=0; i--) {
			error = layers[i].train(error, learningRate, momentum);
		}
	}
}
