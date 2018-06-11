package com.holmesm.games.render;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.holmesm.games.abilities.Ability;
import com.holmesm.games.abilities.Move;
import com.holmesm.games.actions.ActionBuilder;
import com.holmesm.games.board.Board;
import com.holmesm.games.board.Square;
import com.holmesm.games.board.location.CartesianLocation;
import com.holmesm.games.board.location.Location;
import com.holmesm.games.movement.MovementPoints;
import com.holmesm.games.movement.MovementType;
import com.holmesm.games.pieces.GamePiece;
import com.holmesm.games.pieces.factory.ImageRepository;
import com.holmesm.games.pieces.terrain.SpaceStart;
import com.holmesm.games.pieces.terrain.Terrain;
import com.holmesm.games.render.action.SwingActionBuilderListener;
import com.holmesm.games.render.action.selection.AbilitySelectionListener;
import com.holmesm.games.render.action.selection.AbilitySelectionPanel;
import com.holmesm.games.render.canvas.Canvas;
import com.holmesm.games.render.canvas.LocationSelectionListener;
import com.holmesm.games.render.renderable.ImageRenderable;
import com.holmesm.games.render.square.HexSquareConverter;
import com.holmesm.spaceboats.boats.TestBoat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	 //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        Board board = Board.createHexBoard(50, 50);
        board.getLocation(new CartesianLocation(1,1)).addContent(new TestBoat());
        board.getLocation(new CartesianLocation(3,3)).addContent(new TestBoat());
        Canvas canvas = new Canvas(board);
        canvas.addSelectionListener(new Listener());
        frame.getContentPane().add(canvas);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
    /*
     * Need to figure out how the current click intent is being communicated with the ability selector. Kinda need to know the current action builder still, probably just need to make this the ability listener as well
     */
    
    
    public static class Listener implements LocationSelectionListener{

    	private ImageRenderable circle;
    	private Location lastSelected;
    	private SwingActionBuilderListener actionBuilder;
    	
    	public Listener() throws IOException {
    		circle =  new ImageRenderable(ImageRepository.getImageAt(".\\images\\SelectionCircle.png"),HexSquareConverter.HEIGHT*2,HexSquareConverter.WIDTH*2);
    	}
    	
    	
		public void notifyLocationSelected(Location location,Canvas happenedOn) {
			
			if(actionBuilder==null) {
				if(lastSelected!=null) {
					happenedOn.removeDecoration(lastSelected, circle);
				}
				happenedOn.addDecoration(location, circle);
				Square s = happenedOn.getSquareForLocation(location);
				for(GamePiece gp:s.getContent()) {
					if(gp.getAbilities().isEmpty()) {
						continue;
					}
					AbilitySelectionPanel panel = new AbilitySelectionPanel(gp.getAbilities());
					happenedOn.add(panel);
					Ability ability = gp.getAbilities().get(0);
					actionBuilder = new SwingActionBuilderListener(happenedOn);
					new ActionBuilder(ability,gp,actionBuilder);
				}
			}
			else {
				if( actionBuilder.destinationSelected(location)) {
					actionBuilder = null;
				}
				
			}
			
			
		}
    	
    }
    
    public static class SelectionCircle extends GamePiece{
    	
    }
    
    public static class AbilityListener implements AbilitySelectionListener{

    	private Canvas canvas;
    	private GamePiece user;
    	
    	public AbilityListener(Canvas canvas,GamePiece user) {
    		this.canvas = canvas;
    		this.user = user;
    	}
    	
		public void abilitySelected(Ability ability) {
			new ActionBuilder(ability,user,new SwingActionBuilderListener(canvas));
			
		}
    	
    }
    
public static class ScoutShip extends GamePiece{
	
	
    	public ScoutShip() {
    		super();
    		MovementPoints movement = new MovementPoints();
    		movement.addMovementTypeCost(MovementType.GENERIC, 4.0);
    		addAbility(new Move(movement));
    	}
    }
}
