package com.holmesm.games.render.canvas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.holmesm.games.board.Board;
import com.holmesm.games.board.Square;
import com.holmesm.games.board.location.Location;
import com.holmesm.games.pieces.factory.ImageRepository;
import com.holmesm.games.render.CartesianLocationTranslator;
import com.holmesm.games.render.renderable.RenderableObject;
import com.holmesm.games.render.square.HexSquareConverter;
import com.holmesm.games.render.square.SquareConverter;

import lombok.AllArgsConstructor;

public class Canvas extends JPanel implements MouseListener,MouseWheelListener{

	private double zoomFactor;
	
	private Map<Location,List<RenderableObject>> decorations;
	private boolean hasFocus;
	private int componentHeight;
	private int componentWidth;
	private Board board;
	private Point viewCenter;
	private Image background;
	private LocationTranslator translator;
	private SquareConverter converter;
	private Map<Polygon,Location> currentlyRendered;
	List<LocationSelectionListener> listeners;
	
	
	
	public Canvas(Board board) {
        setBorder(BorderFactory.createLineBorder(Color.black));
        zoomFactor = 20.0;
        this.board = board;
        this.translator = new CartesianLocationTranslator();
        this.converter = new HexSquareConverter();
        this.listeners = Lists.newArrayList();
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        this.hasFocus = false;
        try {
			this.background = ImageRepository.getImageAt(".\\images\\NebulaBackground.jpg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.viewCenter = new Point(0,0);
        this.componentHeight = 900;
        this.componentWidth = 900;
        this.decorations = Maps.newHashMap();
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),"RightArrow");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),"LeftArrow");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),"DownArrow");
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),"UpArrow");
        
        this.getActionMap().put("RightArrow", new ArrowAction(this,-1,0));
        this.getActionMap().put("LeftArrow", new ArrowAction(this,1,0));
        this.getActionMap().put("UpArrow", new ArrowAction(this,0,1));
        this.getActionMap().put("DownArrow", new ArrowAction(this,0,-1));
    }

	public void addDecoration(Location location, RenderableObject decoration) {
		List<RenderableObject> inLocation = decorations.get(location);
		if(inLocation==null) {
			inLocation = new LinkedList<RenderableObject>();
			decorations.put(location, inLocation);
		}
		inLocation.add(decoration);
		
	}
	
	public void addDecoration(Square square,RenderableObject decoration) {
		addDecoration(board.getLocationForSquare(square),decoration);
	}
	
	public Square getSquareForLocation(Location loc){
		return board.getLocation(loc);
	}
	
	public void removeDecoration(Location location,RenderableObject decoration) {
		List<RenderableObject> inLocation = decorations.get(location);
		if(inLocation==null) {
			return;
		}
		inLocation.remove(decoration);
	}
	
	public void removeDecoration(Square square, RenderableObject decoration) {
		removeDecoration(board.getLocationForSquare(square),decoration);
	}
	
	public void addSelectionListener(LocationSelectionListener listener) {
		listeners.add(listener);
	}
	
    public Dimension getPreferredSize() {
        return new Dimension(componentHeight,componentWidth);
    }

    public void paintComponent(Graphics g) {
    	 super.paintComponent(g);
    	 g.drawImage(background, 0, 0, componentWidth, componentHeight, null);
    	 currentlyRendered = Maps.newHashMap();
    	 Set<Entry<Location,Square>> allSquares = board.getAllSquares();
    	 for(Entry<Location,Square> entry:allSquares) {
    		 Point center = translator.translateLocation(entry.getKey());
    		 center = new Point((center.getX()+viewCenter.getX())*zoomFactor,(center.getY()+viewCenter.getY())*zoomFactor);
    		 RenderSpace renderSpace = converter.convert(entry.getValue());
    		 renderSpace.getSpace().render(g, center, zoomFactor);
    		 currentlyRendered.put(renderSpace.getSpace().getLastDrawn(), entry.getKey());
    		 for(RenderableObject render:renderSpace.getContents()) {
    			render.render(g,center,zoomFactor);
    		 }
    	 }
        for(Entry<Location,List<RenderableObject>> decoration:decorations.entrySet()) {
        	 Point center = translator.translateLocation(decoration.getKey());
    		 center = new Point((center.getX()+viewCenter.getX())*zoomFactor,(center.getY()+viewCenter.getY())*zoomFactor);
    		 for(RenderableObject render:decoration.getValue()) {
     			render.render(g,center,zoomFactor);
     		 }
        }
    		
    		//Need to draw the contents of the shape still
    	
    }

	public void mouseClicked(MouseEvent e) {
		java.awt.Point clicked = new java.awt.Point(e.getX(),e.getY());
		for(Polygon p:currentlyRendered.keySet()) {
			if(p.contains(clicked)) {
				notifySelection(currentlyRendered.get(p));
				return;
			}
		}
		
	}

	private void notifySelection(Location location) {
		for(LocationSelectionListener listener:listeners) {
			listener.notifyLocationSelected(location,this);
		}
		this.repaint();
		
	}
	
	public void adjustCenter(Point byAmount) {
		viewCenter = new Point(viewCenter.getX()+byAmount.getX(),viewCenter.getY()+byAmount.getY());
		this.repaint();
	}

	public void mouseEntered(MouseEvent arg0) {
		hasFocus = true;
		
	}

	public void mouseExited(MouseEvent arg0) {
		hasFocus = false;
		
	}

	public void mousePressed(MouseEvent arg0) {
		//noop
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// noop
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if(!hasFocus) {
			return;
		}
		this.zoomFactor -= arg0.getPreciseWheelRotation();
		if(this.zoomFactor<1.0) {
			this.zoomFactor = 1.0;
		}
		if(this.zoomFactor>100) {
			this.zoomFactor = 100;
		}
		this.repaint();
		
	}

	
	@AllArgsConstructor
	private class ArrowAction extends AbstractAction {

		private Canvas attachedTo;
		private int xChange;
		private int yChange;
		
		
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			attachedTo.adjustCenter(new Point(xChange,yChange));
		}
		
	}
    
    
}
