package de.tobi.deepneural;

import java.util.Random;

import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.Layer;
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
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class DeepIrisApp {

	public static void main(String[] args) {
		
		int numOutput = 3;
		int numSamples = 150;
		int batchSize = 150;
		int splitTrainNum = (int)(batchSize * .8);
		int seed = 123;
		DataSetIterator iter = new IrisDataSetIterator(batchSize, numSamples);
		DataSet next = iter.next();
		next.shuffle();
		next.normalize();
		
		SplitTestAndTrain testAndTrain = next.splitTestAndTrain(splitTrainNum, new Random(seed));
		DataSet train = testAndTrain.getTrain();
		DataSet test = testAndTrain.getTest();
		
		//Erzeuge einen Builder für ein Neurales Nets
		NeuralNetConfiguration.Builder nnBuilder = new NeuralNetConfiguration.Builder();
		nnBuilder.iterations(5000);
		nnBuilder.learningRate(0.01);
		nnBuilder.seed(123);
		nnBuilder.useDropConnect(false);
		nnBuilder.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT);
		nnBuilder.biasInit(0);
		
		//Baue den ersten Hidden Layer ein
		//In einem DenseLayer zeigt jeder Knoten des vorigen Layers auf jeden Knoten des Folgenden
		DenseLayer.Builder h1Builder = new DenseLayer.Builder();
		h1Builder.nIn(4);
		h1Builder.nOut(5);
		h1Builder.activation(Activation.RELU);
		h1Builder.weightInit(WeightInit.DISTRIBUTION);
		h1Builder.dist(new UniformDistribution(0, 1));
		
		//Baue den zweiten Hidden Layer ein
		DenseLayer.Builder h2Builder = new DenseLayer.Builder();
		h2Builder.nIn(5);
		h2Builder.nOut(5);
		h2Builder.activation(Activation.RELU);
		h2Builder.weightInit(WeightInit.DISTRIBUTION);
		h2Builder.dist(new UniformDistribution(0, 1));
		
		//Baue den Output Layer ein
		OutputLayer.Builder opBuilder = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD);
		opBuilder.nIn(5);
		opBuilder.nOut(3);
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
		neuralN.fit(train);
		
		//Gebe Statistiken für das Ergebnis aus
		Evaluation evaluation = new Evaluation(numOutput);
		evaluation.eval(test.getLabels(), neuralN.output(test.getFeatureMatrix(), Layer.TrainingMode.TEST));
		System.out.println(evaluation.stats());

	}

}
