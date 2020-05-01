package de.tobi.reader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class CharacterReader {
	
	Path path;
	
	public CharacterReader(String base, String image) {
		super();
		this.path = Paths.get(base, image);
	}

	public String readImage() throws IOException{
		StringBuilder builder = new StringBuilder("new float[]{");
		
		BufferedImage image = ImageIO.read(path.toFile());
		byte[][] pixels = new byte[image.getWidth()][];
		
		for(int x = 0; x < image.getWidth(); x++) {
			pixels[x] = new byte[image.getHeight()];
			
			for(int y = 0; y < image.getHeight(); y++) {
				pixels[x][y] = (byte) (image.getRGB(x, y) == 0xFFFFFFFF ? 0 : 1);
				builder.append(pixels[x][y]+",");
			}
		}
		builder.append("}");
		return builder.toString();
	}
}
