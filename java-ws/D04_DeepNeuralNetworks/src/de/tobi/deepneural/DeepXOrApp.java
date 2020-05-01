package de.tobi.deepneural;

import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class DeepXOrApp {

	public static void main(String[] args) {
		
		//Der Input hat 2 Dimensionen: x und < (beide nur mit den Werten 0 oder 1)
		//Daraus basteln wir eine Matrix mit 4 Zeilen und 2 Spalten
		INDArray input = Nd4j.zeros(4,2);
		INDArray labels = Nd4j.zeros(4,2);
		
		//Die Matrix hat in Zeile 0 Spalte 0 eine 0, sowie in Zeile 0 Spalte 1 eine 0
		//Das entspricht dem Inputwert {0,0} 
		input.putScalar(new int[] {0, 0}, 0);
		input.putScalar(new int[] {0, 1}, 0);
		input.putScalar(new int[] {1, 0}, 1);
		input.putScalar(new int[] {1, 1}, 0);
		input.putScalar(new int[] {2, 0}, 0);
		input.putScalar(new int[] {2, 1}, 1);
		input.putScalar(new int[] {3, 0}, 1);
		input.putScalar(new int[] {3, 1}, 1);
		
		//Wir haben zwei Werte für das Ergebnis 10 (beschreibt die Ausgaben von 0) und 01 
		//(beschreibt die Ausgabe von 1); jeder Ergebniswert wird durch zwei Zeilen dargestellt
		labels.putScalar(new int[] {0, 0}, 1);
		labels.putScalar(new int[] {0, 1}, 0);
		labels.putScalar(new int[] {1, 0}, 0);
		labels.putScalar(new int[] {1, 1}, 1);
		labels.putScalar(new int[] {2, 0}, 0);
		labels.putScalar(new int[] {2, 1}, 1);
		labels.putScalar(new int[] {3, 0}, 1);
		labels.putScalar(new int[] {3, 1}, 0);
		
		//Wir stellen eine Kombination von input und labels her
		DataSet dataset = new DataSet(input, labels);
		
		//Erzeuge einen Builder für ein Neurales Nets
		NeuralNetConfiguration.Builder nnBuilder = new NeuralNetConfiguration.Builder();
		nnBuilder.iterations(5000);
		nnBuilder.learningRate(0.1);
		nnBuilder.seed(123);
		nnBuilder.useDropConnect(false);
		nnBuilder.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT);
		nnBuilder.biasInit(0);
		nnBuilder.miniBatch(false);
		
		//Baue den ersten Hidden Layer ein
		//In einem DenseLayer zeigt jeder Knoten des vorigen Layers auf jeden Knoten des Folgenden
		DenseLayer.Builder h1Builder = new DenseLayer.Builder();
		h1Builder.nIn(2);
		h1Builder.nOut(4);
		h1Builder.activation(Activation.RELU);
		h1Builder.weightInit(WeightInit.DISTRIBUTION);
		h1Builder.dist(new UniformDistribution(0, 1));
		
		//Baue den zweiten Hidden Layer ein
		DenseLayer.Builder h2Builder = new DenseLayer.Builder();
		h2Builder.nIn(4);
		h2Builder.nOut(4);
		h2Builder.activation(Activation.RELU);
		h2Builder.weightInit(WeightInit.DISTRIBUTION);
		h2Builder.dist(new UniformDistribution(0, 1));
		
		//Baue den Output Layer ein
		OutputLayer.Builder opBuilder = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD);
		opBuilder.nIn(4);
		opBuilder.nOut(2);
		opBuilder.activation(Activation.SOFTMAX);
		opBuilder.weightInit(WeightInit.DISTRIBUTION);
		opBuilder.dist(new UniformDistribution(0, 1));
		
		//Baue einen ListBuilder zum Sortieren der HiddenLayer und Outputlayer
		ListBuilder liBuilder = nnBuilder.list();
		liBuilder.layer(0, h1Builder.build());
		liBuilder.layer(1, h2Builder.build());
		liBuilder.layer(2, opBuilder.build());
		liBuilder.pretrain(false);
		
		//initialisiere das Neurale Netswerk
		MultiLayerConfiguration networkConf = liBuilder.build();
		MultiLayerNetwork neuralN = new MultiLayerNetwork(networkConf);
		neuralN.init();
		
		//Gebe die Fehler alle 100 Iterationen aus
		neuralN.setListeners(new ScoreIterationListener(100));
		
		//lerne die Eingabe von XOR
		neuralN.fit(dataset);
		
		//erstelle einen Output für jedes Trainingsbeispiel
		INDArray output = neuralN.output(dataset.getFeatureMatrix());
		
		//Gebe Statistiken für das Ergebnis aus
		Evaluation evaluation = new Evaluation(2);
		evaluation.eval(dataset.getLabels(), output);
		System.out.println(evaluation.stats());
		
		System.out.println("NEW PREDICTION");
		System.out.println(neuralN.output(input));
	}

}
