package com.holmesm.games.pieces.factory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.common.collect.Maps;

public class ImageRepository {

	private static Map<String,Image> cachedImages = Maps.newHashMap();
	
	public static Image getImageAt(String fileLocation) throws IOException {
		if(cachedImages.containsKey(fileLocation)) {
			return cachedImages.get(fileLocation);
		}
		
		BufferedImage img = ImageIO.read(new File(fileLocation));
		cachedImages.put(fileLocation, img);
		return img;
	}
	
}
