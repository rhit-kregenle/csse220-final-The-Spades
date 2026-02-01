package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.Timer;

import app.Direction;
import app.Player;
import model.GameModel;

public class GameComponent extends JComponent {

	
	private Player player = new Player(300,500,4,20,20);
	private Timer timer;
	private GameModel model;


	public GameComponent(GameModel model) {

		setFocusable(true);
		
		timer = new Timer(50, e -> {
			player.update();
			repaint();
			});
		timer.start();
		
		addKeyListener(new KeyAdapter() {
			  @Override
			  public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_W) {
			      player.handleInput(Direction.UP);
			    }
			    else if (e.getKeyCode() == KeyEvent.VK_S) {
				      player.handleInput(Direction.DOWN);
				}
			    else if (e.getKeyCode() == KeyEvent.VK_A) {
				      player.handleInput(Direction.LEFT);
				}
			    else if (e.getKeyCode() == KeyEvent.VK_D) {
				      player.handleInput(Direction.RIGHT);
				}
			  }
		});
		
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
	
	player.draw(g2);
	

	// TODO: draw based on model state
	}
}
