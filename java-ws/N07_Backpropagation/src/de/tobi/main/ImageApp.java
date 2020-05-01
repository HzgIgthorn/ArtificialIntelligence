package de.tobi.main;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.tobi.reader.CharacterReader;

public class ImageApp {

	public static final String BASE = "C:\\\\udemy\\\\JavaUdemyAI\\\\git-ws\\\\java-ws\\\\N07_Backpropagation\\\\resources";
	
	public static void main(String[] args) throws Exception {
		List<String> images;
		try(Stream<Path> stream = Files.walk(Paths.get(BASE), 1)){
			images = stream.filter(f -> !Files.isDirectory(f)).map(Path::getFileName).map(Path::toString).collect(Collectors.toList());
		}

		StringBuilder builder = new StringBuilder("float[][] tData = new float[][]{\r\n");
		boolean hasFirst = false;
		for(String image : images) {
			CharacterReader reader = new CharacterReader(BASE,image);
			if(hasFirst)
				builder.append(",\r\n"+reader.readImage());
			else {
				hasFirst = true;
				builder.append(reader.readImage());
			}
		}
		builder.append("\r\n};");
		System.out.println(builder.toString());
	}
}
