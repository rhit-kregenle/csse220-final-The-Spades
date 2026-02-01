package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import model.GameModel;

public class GameComponent extends JComponent {

	
	
	private GameModel model;


	public GameComponent(GameModel model) {
	this.model = model;
	}


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;

	Color origColor = g2.getColor();
	
	// Minimal placeholder to test  it’s running
	Rectangle2D.Double rect = new Rectangle2D.Double(0,0,600,600);
	g2.setColor(Color.GREEN);
	g2.fill(rect);
	
	g2.setColor(origColor);
	

	// TODO: draw based on model state
	}
}
