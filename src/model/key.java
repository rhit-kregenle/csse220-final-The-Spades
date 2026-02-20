package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class key implements Interactable {
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	int x;
	int y;
	boolean beenCollected = false;

	public key(int locX, int locY, boolean collected) {
		
		this.x = locX;
		this.y = locY;
		this.beenCollected = collected;
		loadSpriteOnce();
	}

	@Override
	public void whenInteract(Player player, GameModel model) {
		beenCollected = true;
		player.setHasKey();
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			sprite = ImageIO.read(Player.class.getResource("Key.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public void draw(Graphics2D g2) {
		if (!beenCollected) {
			if (sprite != null) {
				// sprite replaces the circle
				g2.drawImage(sprite, x - 2, y - 2, 15, 30, null);
			} else {
			Rectangle2D rect = new Rectangle2D.Double(x, y, 10, 20);
			Color orig = g2.getColor();
			g2.setColor(Color.YELLOW);
			g2.fill(rect);
			g2.setColor(orig);
			}
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean getKeyCollected() {
		return beenCollected;
	}
	
	public Rectangle2D getBounds() {
		return new Rectangle2D.Double(x, y, 5, 10);
	}
}
