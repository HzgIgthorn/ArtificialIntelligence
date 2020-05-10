package de.tobi.recurrent;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import de.tobi.utils.DataUtilities;
import de.tobi.utils.SentimentExampleIterator;

public class SentimentApp {
	
	//wir benötigen zunächst ein Testexample mit 25k Trainingsdatensätzen
	//wir haben 2 Klassen von Filmen: [0,1] für negative und [1,0] für positive reviews
	public static final String DATASET_URL = "https://ai.stanford.edu/~amaas/data/sentiment/aclImdb_v1.tar.gz";
	//wir legen die Daten im tmpdir Verzeichnis ab
	public static final String DATASET_PATH = FilenameUtils.concat(System.getProperty("java.io.tmpdir"), "dl4j_w2vSentiment/");
	//GoogleNewsVector enthält ein Vector für jedes Wort, die wie am Ende vergleichen können
	public static final String WORD_VECTORS_PATH = "C:\\Users\\tcarl\\Downloads\\GoogleNews-vectors-negative300.bin.gz";

	public static void main(String[] args) {

		//als erstes Laden wie den Datensatz herunter
		downloadData();
		//Anzahl der Beispiele in der Batch
		int batchSize = 64;
		//size of the word vectors (300 im Google News Model)
		int vectorSize = 300;
		//wie oft trainieren wir mit den Trainingsdaten
		int numOfEpochs = 1;
		//verwende nur revie mit weniger als 256 Wörtern
		int truncateLength = 256;
		int seed = 0;
		
		//Wir erlauben keine Garbage Collection für 10000 Millisekunden
		Nd4j.getMemoryManager().setAutoGcWindow(10000);

		MultiLayerConfiguration networkConfig = new NeuralNetConfiguration.Builder()
				.seed(seed)
				//Funktion ADAM um Gewichte zu berechnen
				.updater(Updater.ADAM)
				//benutze L2 Methode um Overfitting zu vermeiden
				.regularization(true).l2(1e-5)
				//Xavier Initialisierung hilft dabei das Verschwinden der Gradienten zu verhindern
				.weightInit(WeightInit.XAVIER)
				//Setze den maximalen Gradient auf 1.0 fest, damit Gradienten nicht zu groß werden können
				.gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue)
				.gradientNormalizationThreshold(1.0)
				.learningRate(2e-2)
				//Trainingsmethode SEPARTE (etwas langsamer, aber verschwendet weniger Sepeicher)
				.trainingWorkspaceMode(WorkspaceMode.SEPARATE)
				.inferenceWorkspaceMode(WorkspaceMode.SEPARATE)
				.list()
				//InputLayer mit vectorSize Neuronen und HiddenLayer mit 256 Neuronen
				.layer(0, new GravesLSTM.Builder().nIn(vectorSize).nOut(256)
						.activation(Activation.TANH).build())
				//Wir nutzen einen Output Layer mit softmax Activierungsfunktion
				.layer(1, new RnnOutputLayer.Builder().activation(Activation.SOFTMAX)
						//Wir benutzen MCXENT LossFunktion und einen Output mit 2 Neuronen
						.lossFunction(LossFunctions.LossFunction.MCXENT).nIn(256).nOut(2).build())
				.pretrain(false).backprop(true).build();
		
		//Wir erstellen und initialisieren ein Recurrent Neurales Netzwerk 
		MultiLayerNetwork network = new MultiLayerNetwork(networkConfig);
		network.init();
		network.setListeners(new ScoreIterationListener(1));
		
		//Wähle Datensätze für Training und Testing aus
		WordVectors wordVectors = WordVectorSerializer.loadStaticModel(new File(WORD_VECTORS_PATH));
		SentimentExampleIterator trainDS = null;
		SentimentExampleIterator testDS = null;
		try {
			trainDS = new SentimentExampleIterator(DATASET_PATH, wordVectors, batchSize, truncateLength, true);
			testDS = new SentimentExampleIterator(DATASET_PATH, wordVectors, batchSize, truncateLength, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Training des Neuronalen Netzwerkes
		for(int i = 0; i < numOfEpochs; i++) {
			network.fit(trainDS);
			trainDS.reset();
			
			Evaluation evaluation = network.evaluate(testDS);
			System.out.println(evaluation.stats());
		}
		
		//Erstelle ein review um zu testen ob es als positiv oder negativ erkannt wird
		String firstPositiveReview = "To be honest, we liked it! This movie was great, and I suggest that you go see it before you judge";
		INDArray features = testDS.loadFeaturesFromString(firstPositiveReview, truncateLength);
		System.out.println("Erkannt Merkmal des Satzes: " + features);
		
		INDArray networkOutput = network.output(features);
		int numOfOutputs = networkOutput.size(2);
		INDArray sentimentProbabilities = networkOutput.get(NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.point(numOfOutputs - 1));
		System.out.println("\n\nOur review: " + firstPositiveReview);
		System.out.println("Result prediction: " + networkOutput);
		System.out.println("p(positive): " + sentimentProbabilities.getDouble(0));
		System.out.println("n(negative). " + sentimentProbabilities.getDouble(1));
		
	}
	
    public static void downloadData() {
        //Create directory if required
        File directory = new File(DATASET_PATH);
        if(!directory.exists()) directory.mkdir();

        //Download file:
        String archizePath = DATASET_PATH + "aclImdb_v1.tar.gz";
        File archiveFile = new File(archizePath);
        String extractedPath = DATASET_PATH + "aclImdb";
        File extractedFile = new File(extractedPath);

        try {
        if( !archiveFile.exists() ){
            System.out.println("Starting data download (80MB)...");
            
				FileUtils.copyURLToFile(new URL(DATASET_URL), archiveFile);
			
            System.out.println("Data (.tar.gz file) downloaded to " + archiveFile.getAbsolutePath());
            //Extract tar.gz file to output directory
            DataUtilities.extractTarGz(archizePath, DATASET_PATH);
        } else {
            //Assume if archive (.tar.gz) exists, then data has already been extracted
            System.out.println("Data (.tar.gz file) already exists at " + archiveFile.getAbsolutePath());
            if( !extractedFile.exists()){
            	//Extract tar.gz file to output directory
            	DataUtilities.extractTarGz(archizePath, DATASET_PATH);
            } else {
            	System.out.println("Data (extracted) already exists at " + extractedFile.getAbsolutePath());
            }
        }
        } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
