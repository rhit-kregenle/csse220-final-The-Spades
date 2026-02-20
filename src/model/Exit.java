package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Class: Exit
 * 
 * Purpose: Complete the level when the player collides with it.
 * 
 * @author Manas Paranjape
 */

public class Exit extends JComponent implements Interactable {
	int x;
	int y;
	int sizeX = 50;
	int sizeY = 50;
	boolean exitable = false;

	// Sprite related fields.
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	public Exit(int x, int y) {
		this.x = x;
		this.y = y;
		loadSpriteOnce();
	}

	public void makeExitable(Player player) {
		if (player.hasKey()) {
			exitable = true;
		}
	}

	public void draw(Graphics2D g2) {
		if (exitable) {
			if (sprite != null) {
				// sprite replaces the circle
				g2.drawImage(sprite, x, y, sizeX, sizeY, null);
			} else {
				// fallback if sprite failed to load
				g2.setColor(Color.BLUE);
				g2.fillRect(x, y, sizeX, sizeY);
			}
		}
	}

	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			sprite = ImageIO.read(Zombie.class.getResource("exit.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	@Override
	public void whenInteract(Player player, GameModel model) {
		model.scoreIncrease();
		makeExitable(player);
	}

	public Rectangle getBounds() {
		if (exitable) {
			Rectangle r = new Rectangle(x, y, sizeX, sizeY);
			return r;
		} else {
			return new Rectangle(-5, -5, 2, 2);
		}
	}

}
