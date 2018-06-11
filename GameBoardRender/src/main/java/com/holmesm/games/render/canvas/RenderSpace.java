package com.holmesm.games.render.canvas;

import java.awt.Color;
import java.util.List;

import com.holmesm.games.render.renderable.PolygonRenderable;
import com.holmesm.games.render.renderable.RenderableObject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RenderSpace {
	private PolygonRenderable space;
	private List<RenderableObject>contents;
	
	
	
}
