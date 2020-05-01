package de.tobi.backprop;

/**
 * @author TCarl
 *
 */
public class BackPropNetwork implements Constants{
	
	private Layer[] layers;
	
	int inputSize;
	int hiddenSize;
	int outputSize;

	public BackPropNetwork(int inputSize, int hiddenSize, int outputSize) {
		this.layers = new Layer[2];
		layers[0] = new Layer(inputSize, hiddenSize);
		layers[1] = new Layer(hiddenSize, outputSize);
		
		this.inputSize = inputSize;
		this.hiddenSize = hiddenSize;
		this.outputSize = outputSize;
	}
	
	public Layer getLayer(int index) {
		return layers[index];
	}
	
	public int size() {
		return layers.length;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		float[] fir = layers[0].getWeights();
		int off = 0;
		for(int i = 0; i < inputSize; i++) {
			for(int h = 0; h < hiddenSize; h++) {
				builder.append("w[i" + i + "/h"+h+"] = " + DDF.format(fir[off++])+"; ");
			}
			builder.append("\r\n");
		}
		off = 0;
		float[] sec = layers[1].getWeights();
		for(int h = 0; h < hiddenSize; h++) {
			for(int o = 0; o < outputSize; o++) {
				builder.append("w[h" + h + "/o"+o+"] = " + DDF.format(sec[off++])+"; ");
			}
			builder.append("\r\n");
		}
		return builder.toString();
	}
}
