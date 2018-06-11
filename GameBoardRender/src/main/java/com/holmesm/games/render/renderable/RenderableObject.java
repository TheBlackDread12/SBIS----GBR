package com.holmesm.games.render.renderable;

import java.awt.Graphics;

import com.holmesm.games.render.canvas.Point;

public interface RenderableObject {

	public void render(Graphics g,Point center,double zoom);
	
}
