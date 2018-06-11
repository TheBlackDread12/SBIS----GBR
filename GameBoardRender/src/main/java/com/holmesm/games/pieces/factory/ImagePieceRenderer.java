package com.holmesm.games.pieces.factory;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.holmesm.games.pieces.GamePiece;
import com.holmesm.games.render.renderable.ImageRenderable;
import com.holmesm.games.render.renderable.RenderableObject;

public class ImagePieceRenderer implements GamePieceRenderer<GamePiece>{

	private static String IMG_PATH = ".\\images\\";
	private double height,width;
	
	public ImagePieceRenderer(double width,double height) {
		this.width = width;
		this.height = height;
	}
	
	
	public RenderableObject convert(GamePiece piece) {
		try {
			Image img = ImageRepository.getImageAt(IMG_PATH+piece.getClass().getSimpleName()+".png");
			return new ImageRenderable(img,height,width);
		} catch (IOException e) {
			throw new RuntimeException("failed to find image for game piece",e);
		}
	}

}
