package com.holmesm.games.render.square;

import java.util.List;

import com.holmesm.games.board.Square;
import com.holmesm.games.render.canvas.RenderSpace;
import com.holmesm.games.render.renderable.RenderableObject;

public interface SquareConverter {

	/**
	 * Will draw the objects returned in order. 
	 * @param square
	 * @return
	 */
	public RenderSpace convert(Square square);
}
