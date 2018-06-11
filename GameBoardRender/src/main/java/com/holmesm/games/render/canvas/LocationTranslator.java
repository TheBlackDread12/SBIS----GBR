package com.holmesm.games.render.canvas;

import com.holmesm.games.board.location.Location;

public interface LocationTranslator {

	public Point translateLocation(Location location);
}
