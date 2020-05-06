package de.tobi.recurrent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.AbstractCache;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.springframework.core.io.ClassPathResource;

public class RecurrentApp {

	public static void main(String[] args) {
		
		SentenceIterator sentenceIterator = null;
		try {
			//Lade ein Trainings Datensatz mit genügend vielen Sätzen
			String filePath = new ClassPathResource("raw_sentences.txt").getFile().getAbsolutePath();
			//Leerzeichen vor und nach jeder Zeile entfernen
			sentenceIterator = new BasicLineIterator(filePath);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TokenizerFactory factory = new DefaultTokenizerFactory();
		factory.setTokenPreProcessor(new CommonPreprocessor());
		
		//Erstelle ein Alphabet aus den gelernten Buchstaben
		VocabCache<VocabWord> cache = new AbstractCache<>();
		WeightLookupTable<VocabWord> table = new InMemoryLookupTable.Builder<VocabWord>()
				//Benutze 100 Neuronen im HiddenLayer
				.vectorLength(100)
				//AdaGrad zur Optimierung anstatt SGD
				.useAdaGrad(false)
				.cache(cache).build();
		
		Word2Vec network = new Word2Vec.Builder()
				//Alle Wörter unter der Grenze werden ignoriert
				.minWordFrequency(5)
				.iterations(1)
				.epochs(10)
				//Es wird ein HiddenLayer mit 100 Neuronen gebaut
				.layerSize(100)
				.seed(42)
				//betrachte 5 Wörter vor und nach dem Aktuellen
				.windowSize(5)
				.iterate(sentenceIterator)
				.tokenizerFactory(factory)
				.lookupTable(table)
				.vocabCache(cache)
				.build();
				
		//Starte Training
		network.fit();
		
		Collection<String> nearestWords = network.wordsNearest("week", 5);
		System.out.println("Die Wörter am nächsten zu 'week' sind: "+nearestWords );
	}

}
