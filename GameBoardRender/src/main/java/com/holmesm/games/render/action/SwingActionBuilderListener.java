package com.holmesm.games.render.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.holmesm.games.actions.Action;
import com.holmesm.games.actions.ActionBuilder;
import com.holmesm.games.actions.ActionBuilderListener;
import com.holmesm.games.board.Square;
import com.holmesm.games.board.location.Location;
import com.holmesm.games.board.path.Path;
import com.holmesm.games.pieces.GamePiece;
import com.holmesm.games.pieces.factory.ImageRepository;
import com.holmesm.games.render.canvas.Canvas;
import com.holmesm.games.render.renderable.ImageRenderable;
import com.holmesm.games.render.square.HexSquareConverter;

/**
 * Single use class, meant to be paired with a single ActionBuilder. do not multi-thread or attach to multiple ActionBuilders.
 * @author Balerion
 *
 */
public class SwingActionBuilderListener implements ActionBuilderListener{

	
	private Canvas canvas;
	
	private ImageRenderable moveHighlight;
	private Map<Square,Path> alreadyHighlighted;
	private ActionBuilder callback;
	
	public SwingActionBuilderListener(Canvas canvas) {
		alreadyHighlighted = Maps.newHashMap();
		this.canvas = canvas;
	}
	
	public void selectGamePeice(List<GamePiece> possiblePieces, ActionBuilder callBack) {
		if(possiblePieces.isEmpty()) {
			callBack.targetsSelected(possiblePieces);
		}
		callBack.targetsSelected(possiblePieces);
		
	}

	public void selectPath(List<Path> possiblePaths, ActionBuilder callBack) {
		this.callback = callBack;
		try {
			moveHighlight =  new ImageRenderable(ImageRepository.getImageAt(".\\images\\MovementCircle.png"),HexSquareConverter.HEIGHT*2,HexSquareConverter.WIDTH*2);
			for(Path path:possiblePaths) {
				for(Square square:path.getDestinations())
				{
					if(alreadyHighlighted.containsKey(square)) {
						continue;
					}
					canvas.addDecoration(square, moveHighlight);
					alreadyHighlighted.put(square,path);
				}
			}
		
		} catch (IOException e) {
			throw new RuntimeException("welp that sucks",e);
		}
		canvas.repaint();
    }
    	
   public boolean destinationSelected(Location loc) {
	  Path path = alreadyHighlighted.get(canvas.getSquareForLocation(loc));
	  if(path==null) {
		  return false;
	  }
	  for(Square s : alreadyHighlighted.keySet()) {
		  canvas.removeDecoration(s, moveHighlight);
	  }
	  callback.pathSelected(path);
	  return true;
   }
		
	

	public void actionComplete(Action action) {
		action.enact();
	}

}
