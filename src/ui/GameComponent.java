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
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.Timer;

import model.Direction;
import model.Exit;
import model.Player;
import model.Wall;
import model.Zombie;
import model.GameModel;
import model.Gem;

public class GameComponent extends JComponent {

	private Player player = new Player(300, 500, 4, 20, 20);
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	private Timer timer;
	private GameModel model;
	private GameWindow window;

	// gems declaration
	private ArrayList<Gem> gems = new ArrayList<Gem>();

	private ArrayList<Gem> walls = new ArrayList<Wall>();

	private Exit exit = new Exit(500, 100);

	public GameComponent(GameModel model, GameWindow window) {
		this.model = model;
		this.window = window;

		setFocusable(true);
		requestFocus();

		loadLevel(model.getLevel());

		timer = new Timer(50, e -> {
			player.update();
			requestFocus();

			wallBounce();

			zombieFlip();

			zombieShove();

			gemCollect(model);

			// This is the win condition, will be changed to reaching the exit.
			if (player.getPlayerBounds().intersects(exit.getBounds())) {
				this.window.showStart();
			}

			repaint();
		});

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
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					player.shove();
				}
			}
		});
	}

	private void wallBounce() {
		for (int i = 0; i < walls.length; i++) {
			if (walls[i].getX1() == walls[i].getX2()) {
				if (player.getPosX() <= walls[i].getX1() && walls[i].getX1() <= player.getPosX() + player.getSizeX()
						&& ((walls[i].getY1() <= player.getPosY() && player.getPosY() <= walls[i].getY2())
								|| (walls[i].getY1() <= player.getPosY() + player.getSizeY()
										&& player.getPosY() + player.getSizeY() <= walls[i].getY2()))) {
					player.flip();
					player.update();
					player.update();
					break;
				}
			} else {
				if (player.getPosY() <= walls[i].getY1() && walls[i].getY1() <= player.getPosY() + player.getSizeY()
						&& ((walls[i].getX1() <= player.getPosX() && player.getPosX() <= walls[i].getX2())
								|| (walls[i].getX1() <= player.getPosX() + player.getSizeX()
										&& player.getPosX() + player.getSizeX() <= walls[i].getX2()))) {
					player.flip();
					player.update();
					player.update();
					break;
				}
			}
		}
	}

	private void zombieShove() {
		for (Zombie zombie : zombies) {

			zombie.update();

			if (player.getPlayerBounds().intersects(zombie.getZombieBounds())) {
				if (player.getIsShoving() >= 5) {
					zombie.getShoved(player.getShovingDirection());
					player.flip();
					player.update();
					player.update();
				} else {
					this.model.livesDecrease();
					player.flip();
					zombie.flip();
					player.update();
					player.update();
				}
			}
		}
	}

	private void zombieFlip() {
		for (int i = 0; i < zombies.size(); i++) {
			for (int j = i + 1; j < zombies.size(); j++) {
				Zombie e1 = zombies.get(i);
				Zombie e2 = zombies.get(j);

				if (e1.getZombieBounds().intersects(e2.getZombieBounds())) {
					e1.flip();
					e2.flip();
				}
			}
		}
	}

	private void gemCollect(GameModel model) {
		if (player.getPlayerBounds().intersects(gem1.getBounds())) {
			gem1.whenInteract(player, model);
		}
		if (player.getPlayerBounds().intersects(gem2.getBounds())) {
			gem2.whenInteract(player, model);

		}
		if (player.getPlayerBounds().intersects(gem3.getBounds())) {
			gem3.whenInteract(player, model);

		}
		if (player.getPlayerBounds().intersects(gem4.getBounds())) {
			gem4.whenInteract(player, model);

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Color origColor = g2.getColor();

		// Minimal placeholder to test it’s running
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 600, 600);
		g2.setColor(Color.GREEN);
		g2.fill(rect);

		g2.setColor(origColor);

		player.draw(g2);
		for (Zombie zombie : zombies) {
			zombie.draw(g2);
		}

		exit.draw(g2);

		drawHUD(g2);

		for (int i = 0; i < walls.length; i++)
			walls[i].draw(g2);
		// TODO: draw based on model state
		for (Gem g : gems) {
			g.draw(g2);
		}
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

	private int getScore() {
		return model.getScore();
	}

	public void startTimer() {
		timer.start();
	}

	private void loadLevel() {

	}
}
