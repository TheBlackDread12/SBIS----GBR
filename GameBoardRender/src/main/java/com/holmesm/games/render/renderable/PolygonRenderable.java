package com.holmesm.games.render.renderable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import com.holmesm.games.render.canvas.Point;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PolygonRenderable implements RenderableObject{

	private Point[] shape;
	private Color lineColor;
	private Color fillColor;
	
	private Polygon lastDrawn;
	
	public void render(Graphics g,Point center, double zoom) {
		// TODO Auto-generated method stub
		g.setColor(lineColor);
		Polygon p = new Polygon();
		for(Point point:shape) {

			 p.addPoint((int)(point.getX()*zoom+center.getX()),(int)(point.getY()*zoom+center.getY()));
		 }
		 g.drawPolygon(p);
		 if(fillColor!=null) {
			 g.setColor(fillColor);
			 g.fillPolygon(p);
		 }
		 lastDrawn = p;
		
	}
	
	public Polygon getLastDrawn() {
		return lastDrawn;
	}

}
