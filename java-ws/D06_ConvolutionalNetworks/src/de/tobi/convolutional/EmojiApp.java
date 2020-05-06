package de.tobi.convolutional;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
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
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;

public class EmojiApp{
	
	static final Random RAND = new Random();

	public static void main(String[] args) {
		//Anzahl der RGB Channels
		int numOfChannels = 1;
		int width = 28;
		int height = 28;
		//Es gibt zwei Outputklassen (fröhlich und traurig)
		int numOfOutput = 2;
		//Anzahl der Trainingsdaten
		int batchSize = 16;
		//Anzahl der Wiederholung mir dem Traingsdaten
		int numOfEpochs = 200;
		int numOfIterations = 1;
		int seed = 123;
		
		File trainData = new File("C:\\udemy\\JavaUdemyAI\\git-ws\\java-ws\\D06_ConvolutionalNetworks\\Resources\\UdemyDataset");
		File testData = new File("C:\\udemy\\JavaUdemyAI\\git-ws\\java-ws\\D06_ConvolutionalNetworks\\Resources");
		
		//Das Random wird übergeben, damit die Trainingsdaten zufällig angeordnet werden,
		//und nicht in der Reihenfolge, in der sie eingelesen werde  n.
		FileSplit train = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS, RAND);
		FileSplit test = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS, RAND);
		
		ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();
		ImageRecordReader recordReader = new ImageRecordReader(height, width, numOfChannels, labelMaker);
		try {
			recordReader.initialize(train);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataSetIterator dataIter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, numOfOutput);
		
		DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
		scaler.fit(dataIter);
		dataIter.setPreProcessor(scaler);
		
		DataSet ds = dataIter.next();
		System.out.println(ds);
		System.out.println(dataIter.getLabels());
		
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
						.stride(1,1)
						.nOut(20)
						.activation(Activation.RELU)
						.build())
				.layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
						.kernelSize(2,2)
						.stride(2,2)
						.build())
				.layer(2, new DenseLayer.Builder().activation(Activation.RELU)
						.nOut(500).build())
				.layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
						.nOut(numOfOutput)
						.activation(Activation.SOFTMAX)
						.build())
				.setInputType(InputType.convolutionalFlat(28, 28, 1))
				.backprop(true).pretrain(false).build();
		
		MultiLayerNetwork network = new MultiLayerNetwork(configuration);
		network.init();
		
		network.setListeners(new ScoreIterationListener(1));
		
		for(int i=0; i<numOfEpochs; i++) {
			network.fit(dataIter);
			Evaluation evaluation = network.evaluate(dataIter);
			System.out.println(evaluation.stats());
			dataIter.reset();
		}
		
		ParentPathLabelGenerator labelMakerTest = new ParentPathLabelGenerator();
		ImageRecordReader recordReaderTest = new ImageRecordReader(height, width, numOfChannels, labelMakerTest);
		try {
			recordReaderTest.initialize(test);
			//recordReaderTest.setLabels(Arrays.asList("traurig", "fröhlich"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataSetIterator dataIterTest = new RecordReaderDataSetIterator(recordReaderTest, batchSize, 1, numOfOutput);
		
		DataNormalization scalerTest = new ImagePreProcessingScaler(0, 1);
		scalerTest.fit(dataIterTest);
		dataIterTest.setPreProcessor(scalerTest);
		
		System.out.println("New prediction: " + network.output(dataIterTest));
	}
}
