package de.tobi.convolutional;

import java.io.IOException;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class ConvolutionalApp {

	public static void main(String[] args) {
		int numOfChannels = 1;
		//10 Klassen von verschiedenen Outputs
		int numOfOutput = 10;
		//64 Items werden zeitgleich untersucht
		int batchSize = 64;
		int numOfEpochs = 1;
		int numOfIterations = 1;
		int seed = 123;

		//Erstelle Training und Test Datensätze
		DataSetIterator trainingIterator = null;
		DataSetIterator testIterator = null;
		try {
			trainingIterator = new MnistDataSetIterator(batchSize, true, seed);
			testIterator = new MnistDataSetIterator(batchSize, false, seed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
				.seed(seed)
				.iterations(numOfIterations)
				.regularization(true).l2(0.0005)
				.learningRate(.01)
				.weightInit(WeightInit.XAVIER)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
				.updater(Updater.NESTEROVS)
				.list()
				.layer(0, new ConvolutionLayer.Builder(5, 5)
						.nIn(numOfChannels)
						.stride(1, 1)
						.nOut(20)
						.activation(Activation.RELU)
						.build())
				.layer(1,  new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
						//Das Pooling Fenster wird auf 2x2 gesetzt
						.kernelSize(2, 2)
						//Nachdem das Maximum aus den 2x2 Fenster gewählt wurde, soll das Fenster 2 nach rechts
						//oder 2 nach unten versetzt werden 
						.stride(2, 2)
						.build())
				.layer(2, new ConvolutionLayer.Builder(5, 5)
						.stride(1, 1)
						.nOut(50)
						.activation(Activation.RELU)
						.build())
				.layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
						.kernelSize(2, 2)
						.stride(2, 2)
						.build())
				.layer(4, new DenseLayer.Builder().activation(Activation.RELU)
						.nOut(500).build())
				.layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
						.nOut(numOfOutput)
						.activation(Activation.SOFTMAX)
						.build())
				//28x28 große Bilder mit einen Chanal (nur eine Farbe)
				.setInputType(InputType.convolutionalFlat(28, 28, 1))
				.backprop(true).pretrain(false).build();
		
		MultiLayerNetwork network = new MultiLayerNetwork(configuration);
		network.init();
		
		network.setListeners(new ScoreIterationListener(1));
		
		for(int i=0; i < numOfEpochs; i++) {
			network.fit(trainingIterator);
			Evaluation evaluation = network.evaluate(testIterator);
			System.out.println(evaluation.stats());
			testIterator.reset();
		}
	}

}
