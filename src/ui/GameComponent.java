package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

import model.Direction;
import model.Player;
import model.Wall;
import model.Zombie;
import model.GameModel;

public class GameComponent extends JComponent {

	
	private Player player = new Player(300,500,4,20,20);
	private Zombie[] zombies = {new Zombie(10, 310, Direction.RIGHT, 550), new Zombie(10, 360, Direction.RIGHT, 550), new Zombie(10, 170, Direction.RIGHT, 550), new Zombie(10, 520, Direction.RIGHT, 200), new Zombie(10, 460, Direction.RIGHT, 200), new Zombie(480, 380, Direction.DOWN, 160), new Zombie(565, 100, Direction.DOWN, 215), new Zombie(10, 230, Direction.DOWN, 250), new Zombie(250, 10, Direction.DOWN, 250)};
	private Timer timer;
	private GameModel model;

	Wall[] walls = { new Wall(0, 150, 100, 150), new Wall(100, 100, 100, 150), new Wall(100, 100, 200, 100),
			new Wall(200, 000, 200, 100), new Wall(500, 000, 500, 100), new Wall(500, 100, 600, 100),
			new Wall(000, 200, 200, 200), new Wall(100, 300, 300, 300), new Wall(300, 200, 300, 300),
			new Wall(300, 200, 550, 200), new Wall(300, 350, 600, 350), new Wall(050, 400, 250, 400),
			new Wall(250, 400, 250, 600), new Wall(000, 500, 200, 500), new Wall(350, 500, 350, 600),
			new Wall(350, 500, 450, 500), new Wall(550, 500, 600, 500), new Wall(0, 0, 600, 0), new Wall(0, 0, 0, 600),
			new Wall(600, 0, 600, 600), new Wall(0, 600, 600, 600) };

	public GameComponent(GameModel model) {
		this.model = model;

		setFocusable(true);

		timer = new Timer(50, e -> {
			player.update();
			for (int i = 0; i < walls.length; i++) {
				if (walls[i].getX1() == walls[i].getX2()) {
					if (player.getPosX() <= walls[i].getX1() && walls[i].getX1() <= player.getPosX() + player.getSizeX() && ((walls[i].getY1() <= player.getPosY() && player.getPosY() <= walls[i].getY2()) || (walls[i].getY1() <= player.getPosY() + player.getSizeY() && player.getPosY() + player.getSizeY() <= walls[i].getY2()))) {
						player.flip();
						player.update();
						player.update();
						break;
					}
				} else {
					if (player.getPosY() <= walls[i].getY1() && walls[i].getY1() <= player.getPosY() + player.getSizeY() && ((walls[i].getX1() <= player.getPosX() && player.getPosX() <= walls[i].getX2()) || (walls[i].getX1() <= player.getPosX() + player.getSizeX() && player.getPosX() + player.getSizeX() <= walls[i].getX2()))) {
						player.flip();
						player.update();
						player.update();
						break;
					}
				}
			}
			for (int i = 0; i < zombies.length; i++) {
			    for (int j = i + 1; j < zombies.length; j++) {
			        Zombie e1 = zombies[i];
			        Zombie e2 = zombies[j];

			        if (e1.getZombieBounds().intersects(e2.getZombieBounds())) {
			            e1.flip();
			            e2.flip();
			        }
			    }
			}
			
			for (Zombie zombie: zombies) {
					
				zombie.update();
				
				if (player.getPlayerBounds().intersects(zombie.getZombieBounds())) {
					if (player.getIsShoving() >= 5) {
						zombie.getShoved(player.getShovingDirection());
						player.flip();
						player.update();
						player.update();
					} else {
						player.isHit();
						zombie.flip();
						player.update();
						player.update();
					}
				}
			}
			
			repaint();
		});
		timer.start();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					player.handleInput(Direction.UP);
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					player.handleInput(Direction.DOWN);
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					player.handleInput(Direction.LEFT);
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					player.handleInput(Direction.RIGHT);
				}
			    else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				      player.shove();
				}
			  }
		});
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
	for (Zombie zombie: zombies) {
		zombie.draw(g2);
	}
	
	drawHUD(g2);

	for (int i = 0; i < walls.length; i++)
		walls[i].draw(g2);
	// TODO: draw based on model state
	}
	
	
	private void drawHUD(Graphics2D g) {
		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);
		
		Color orig = g.getColor();
		
		g.setColor(Color.WHITE);
		g.drawString("Score: " + this.model.getScore(), 10, 20);
		g.drawString("Lives: " + this.model.getLives(), 10, 45);
		
		g.setColor(orig);
	}
}
