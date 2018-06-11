package com.holmesm.games.render.action.selection;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.google.common.collect.Lists;
import com.holmesm.games.abilities.Ability;
import com.holmesm.games.pieces.factory.ImageRepository;

public class AbilitySelectionPanel extends JPanel{

	private List<Ability> abilities;
	private Ability selected;
	private List<AbilitySelectionListener> abilitySelectionListeners;
	
	public AbilitySelectionPanel(List<Ability> abilities) {
		super(new GridLayout(3,3));
		this.abilities = abilities;
		abilitySelectionListeners = Lists.newLinkedList();
	}
	
	public void addAbilityListener(AbilitySelectionListener listener) {
		
	}
	
	protected void abilitySelected(Ability ability) {
		this.selected = ability;
		for(AbilitySelectionListener listener:abilitySelectionListeners) {
			listener.abilitySelected(ability);
		}
		this.repaint();
	}
	
	public void paint(Graphics graphics) {
		
		for(Ability ability:abilities) {
			JButton button = new JButton(ability.getName());
			button.addActionListener(new ButtonListener(ability));
			try {
				button.setIcon( new ImageIcon(ImageRepository.getImageAt(".\\images\\"+ability.getName()+".png")));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			if(selected!=null && selected.equals(ability)){
				System.out.println(selected.getName());
			}
			this.add(button);
		}
		
	}
	
	private class ButtonListener implements ActionListener{

		private Ability ability;
		
		public ButtonListener(Ability ability) {
			this.ability = ability;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			abilitySelected(ability);
		}
		
	}
	
}
