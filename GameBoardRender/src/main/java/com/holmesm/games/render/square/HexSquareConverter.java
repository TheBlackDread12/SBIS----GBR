package com.holmesm.games.render.square;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.holmesm.games.board.Square;
import com.holmesm.games.pieces.GamePiece;
import com.holmesm.games.pieces.factory.ImagePieceRenderer;
import com.holmesm.games.render.canvas.Point;
import com.holmesm.games.render.canvas.RenderSpace;
import com.holmesm.games.render.renderable.PolygonRenderable;
import com.holmesm.games.render.renderable.RenderableObject;

public class HexSquareConverter implements SquareConverter{

	private static Random RANDOM = new Random();
	public static double HEIGHT = Math.sqrt(3.0);
	public static double WIDTH = 2;
	private static Color[]COLORS = {Color.BLACK,Color.BLUE,Color.GREEN,Color.YELLOW};
	Point[] HEX_POINTS = {new Point(WIDTH*-.5,HEIGHT),new Point(WIDTH*-1,0),new Point(WIDTH*-.5,-1*HEIGHT),new Point(WIDTH*.5,-1*HEIGHT),new Point(WIDTH,0),new Point(WIDTH*.5,HEIGHT), new Point(WIDTH*-.5,HEIGHT)};
	
	public RenderSpace convert(Square square) {
		List<RenderableObject> objects = Lists.newArrayList();
		ImagePieceRenderer image = new ImagePieceRenderer(WIDTH*2,HEIGHT*2);
		for(GamePiece piece: square.getContent()) {
			objects.add(image.convert(piece));
		}
		return new RenderSpace(new PolygonRenderable(HEX_POINTS,Color.WHITE,null,null),objects);
	}

	
	
	private Color getRandomColor() {
		return COLORS[RANDOM.nextInt(COLORS.length)];
	}
	
}
