package de.tobi.perceptron;

public class PerceptronApp {

	public static void main(String[] args) {
		float[][] input = {{0,0},{0,1},{1,0},{1,1}};
		float[] output = {0,1,1,1};
		
		Perceptron perceptron = new Perceptron(input, output);
		perceptron.train(0.1f);
		
		System.out.print("Der Feler ist 0, damit ist das neurale Netzwerk fertig");
		
		System.out.println("Kantengewichte sind (" +perceptron.getW()[0]+"/"+perceptron.getW()[1]+")");
	}

}
