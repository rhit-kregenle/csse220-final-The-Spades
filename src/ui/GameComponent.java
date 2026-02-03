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

import model.Direction;
import model.Player;
import model.Wall;
import model.Zombie;
import model.GameModel;

public class GameComponent extends JComponent {

	
	private Player player = new Player(300,500,4,20,20);
	private Zombie zombie = new Zombie(10, 310, Direction.RIGHT, 550);
	private Timer timer;
	private GameModel model;
	
	Wall[] walls = {new Wall(0,150,100,150), new Wall(100,100,100,150), new Wall(100,100,200,100), new Wall(200,000,200,100), new Wall(500,000,500,100), new Wall(500,100,600,100), new Wall(000,200,200,200), new Wall(100,300,300,300), new Wall(300,200,300,300), new Wall(300,200,550,200), new Wall(300,350,600,350), new Wall(050,400,250,400), new Wall(250,400,250,600), new Wall(000,500,200,500), new Wall(350,500,350,600),new Wall(350,500,450,500), new Wall(550,500,600,500),new Wall(0,0,600,0),new Wall(0,0,0,600),new Wall(600,0,600,600),new Wall(0,600,600,600)};

	


	public GameComponent(GameModel model) {

		setFocusable(true);
		
		timer = new Timer(50, e -> {
			player.update();
			for (int i = 0; i < walls.length; i++) {
				if (walls[i].getX1() == walls[i].getX2()) {
					if (player.getPosX() <= walls[i].getX1() && walls[i].getX1() <= player.getPosX() + 20 && ((walls[i].getY1() <= player.getPosY() && player.getPosY() <= walls[i].getY2()) || (walls[i].getY1() <= player.getPosY() + 20 && player.getPosY() + 20 <= walls[i].getY2()))) {
						player.flip();
						player.update();
						player.update();
						break;
					}
				} else {
					if (player.getPosY() <= walls[i].getY1() && walls[i].getY1() <= player.getPosY() + 20 && ((walls[i].getX1() <= player.getPosX() && player.getPosX() <= walls[i].getX2()) || (walls[i].getX1() <= player.getPosX() + 20 && player.getPosX() + 20 <= walls[i].getX2()))) {
						player.flip();
						player.update();
						player.update();
						break;
					}
				}
			}
			zombie.update();
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
	zombie.draw(g2);
	
	for (int i = 0; i < walls.length; i++) walls[i].draw(g2);

	// TODO: draw based on model state
	}
}
