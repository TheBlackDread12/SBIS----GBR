package com.holmesm.games.render.canvas;

import com.holmesm.games.board.location.Location;

public interface LocationSelectionListener {

	public void notifyLocationSelected(Location location,Canvas happenedOn) ;
}
