package app;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Mobile {
	private int maxLives;
	private int currentLives;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	Direction direction;

	public Player(int startingX, int startingY, int maxLives, int sizeX, int sizeY) {
		this.posX = startingX;
		this.posY = startingY;
		this.maxLives = maxLives;
		this.currentLives = maxLives;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.direction = Direction.UP;
		loadSpriteOnce();
	}

	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			// tennis.png must be in the SAME package as Ball.java
			sprite = ImageIO.read(Player.class.getResource("tennis.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public void handleInput(Direction dir) {
		direction = dir;
	}

	public void draw(Graphics2D g2) {

		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, posX, posY, sizeX, sizeY, null);
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.RED);
			g2.fillOval(posX, posY, sizeX, sizeY);
		}
	}

	public void update() {
		if (direction == Direction.UP) {
			posY += delta;
		} else if (direction == Direction.DOWN) {
			posY -= delta;
		} else if (direction == Direction.LEFT) {
			posX -= delta;
		} else if (direction == Direction.RIGHT) {
			posX += delta;
		}
	}
}
