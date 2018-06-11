package com.holmesm.games.render.renderable;

import java.awt.Graphics;
import java.awt.Image;

import com.holmesm.games.render.canvas.Point;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ImageRenderable implements RenderableObject{

	private Image image;
	private double height,width;
	
	public void render(Graphics g,Point center, double zoom) {
		// TODO Auto-generated method stub
		 double renderHeight = zoom*height;
		 double renderWidth = zoom*width;
		 g.drawImage(image,(int)(center.getX()-renderWidth/2),(int)(center.getY()-renderHeight/2),(int)renderWidth,(int)renderHeight,null);
	}

}
