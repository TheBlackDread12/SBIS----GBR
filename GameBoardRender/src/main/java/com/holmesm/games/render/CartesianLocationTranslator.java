package com.holmesm.games.render;


import com.holmesm.games.board.location.CartesianLocation;
import com.holmesm.games.board.location.Location;
import com.holmesm.games.render.canvas.LocationTranslator;
import com.holmesm.games.render.canvas.Point;
import com.holmesm.games.render.square.HexSquareConverter;

public class CartesianLocationTranslator implements LocationTranslator{

	public Point translateLocation(Location location) {
		if(!(location instanceof CartesianLocation)) {
			throw new RuntimeException("Cannot translate non-cartesian locations");
		}
		
		CartesianLocation cartLoc = (CartesianLocation)location;
		double x = Math.floor(cartLoc.getX()/2)*HexSquareConverter.WIDTH*2;
		double y = cartLoc.getY()*HexSquareConverter.HEIGHT*2;
		double additionalHeight = HexSquareConverter.HEIGHT;
		double additionalWidth = Math.floor(cartLoc.getX()/2)*HexSquareConverter.WIDTH+HexSquareConverter.WIDTH;
		if(cartLoc.getX()%2==1) {
			additionalHeight+=HexSquareConverter.HEIGHT;
			additionalWidth +=HexSquareConverter.WIDTH*1.5;
		}
		else if(cartLoc.getX()>0) {
			//additionalWidth += Math.floor(cartLoc.getX()/2)*HexSquareConverter.WIDTH;
		}
		return new Point(x+additionalWidth,y+additionalHeight);
	}

}
