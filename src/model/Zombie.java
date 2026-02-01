package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * An enemy in the form of a Zombie. As a moving object, it is a child of
 * Mobile.
 * 
 * Zombies move back and forth on either the X or Y axis, moving a certain
 * distance away from their starting location and then back again.
 * 
 * @author Manas Paranjape
 */

public class Zombie extends Mobile {
	Direction traverseDirection;
	int traverseLength;
	int startingX;
	int startingY;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	
	/**
	 * Zombie constructor
	 * 
	 * @param startingX - the starting X of the zombie
	 * @param startingY - the starting Y of the zombie
	 * @param traverseDirection - the axis the zombie moves in, determined by
	 *                            the axis the provided direction corresponds
	 *                            to.
	 * @param traverseLength - how far the zombie should move
	 * @param speed - (optional) how many pixels the zombie should move per tick.
	 * 
	 * @return Zombie
	 */
	public Zombie(int startingX, int startingY, Direction traverseDirection, int traverseLength, int speed, int sizeX, int sizeY) {
		this.posX = startingX;
		this.posY = startingY;
		this.startingX = startingX;
		this.startingY = startingY;
		this.traverseDirection = traverseDirection;
		this.traverseLength = traverseLength;
		this.delta = speed;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		loadSpriteOnce();
	}
	
	public Zombie(int startingX, int startingY, Direction traverseDirection, int traverseLength) {
		this.posX = startingX;
		this.posY = startingY;
		this.startingX = startingX;
		this.startingY = startingY;
		this.traverseDirection = traverseDirection;
		this.traverseLength = traverseLength;
		this.delta = 5;
		this.sizeX = 20;
		this.sizeY = 20;
	}
	
	/**
	 * Moves the zombie each tick based on how the zombie was initialized
	 */
	@Override
	public void update() {
		if (traverseDirection == Direction.DOWN) {
			if (startingY <= posY & posY < traverseLength + startingY) {
				posY += delta;
			} else {
				traverseDirection = Direction.UP;
			}
		}
		
		if (traverseDirection == Direction.UP) {
			if (startingY != posY) {
				posY -= delta;
			} else {
				traverseDirection = Direction.DOWN;
			}
		}
		
		if (traverseDirection == Direction.RIGHT) {
			if (startingX <= posX & posX < traverseLength + startingX) {
				posX += delta;
			} else {
				traverseDirection = Direction.LEFT;
			}
		}
		
		if (traverseDirection == Direction.LEFT) {
			if (startingX != posX) {
				posX -= delta;
			} else {
				traverseDirection = Direction.RIGHT;
			}
		}
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			// tennis.png must be in the SAME package as Ball.java
			sprite = ImageIO.read(Zombie.class.getResource("tennis.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}
	
	public void draw(Graphics2D g2) {

		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, posX, posY, sizeX, sizeY, null);
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.BLUE);
			g2.fillOval(posX, posY, sizeX, sizeY);
		}
	}

}
