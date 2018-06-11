package com.holmesm.games.pieces.factory;

import com.holmesm.games.pieces.GamePiece;
import com.holmesm.games.render.renderable.RenderableObject;

public interface GamePieceRenderer<E extends GamePiece> {

	
	public RenderableObject convert(E piece);
}
