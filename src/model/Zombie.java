package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
	private Direction traverseDirection;
	private int traverseLength;
	private int startingX;
	private int startingY;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	private Boolean onPath;

	
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
		this.onPath = true;
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
		loadSpriteOnce();
		this.onPath = true;
	}
	
	/**
	 * Moves the zombie each tick based on how the zombie was initialized
	 */
	@Override
	public void update() {
		if (onPath) {
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
		} else {
			if (traverseDirection == Direction.LEFT || traverseDirection == Direction.RIGHT) {
				if (posY > startingY) {
					posY -= 1;
				} else {
					posY += 1;
				}
				if (posY == startingY) onPath = true;
			} else {
				if (posX > startingX) {
					posX -= 1;
				} else {
					posX += 1;
				}
				if (posX == startingX) onPath = true;
			}
		}
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			sprite = ImageIO.read(Zombie.class.getResource("Zombie.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}
	
	public void draw(Graphics2D g2) {

		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, posX - 5, posY - 5, (int) (sizeX * 1.5), (int) (sizeY * 1.5), null);
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.BLUE);
			g2.fillOval(posX, posY, sizeX, sizeY);
		}
	}
	
	public Rectangle getZombieBounds() {
	    Rectangle r = new Rectangle(
	    		posX,
	    		posY,
	    		sizeX,
	    		sizeY
	    );
	    return r;
	}
	
	public void getShoved(Direction dir) {
		if (dir == Direction.UP) {
			posY -= 10;
		} else if (dir == Direction.DOWN) {
			posY += 10;
		} else if (dir == Direction.LEFT) {
			posX -= 10;
		} else if (dir == Direction.RIGHT) {
			posX += 10;
		}
		flip();
		onPath = false;
	}
	
	public void flip() {
		if (traverseDirection == Direction.UP) {
			traverseDirection = Direction.DOWN;
		} else if (traverseDirection == Direction.DOWN) {
			traverseDirection = Direction.UP;
		} else if (traverseDirection == Direction.LEFT) {
			traverseDirection = Direction.RIGHT;
		} else if (traverseDirection == Direction.RIGHT) {
			traverseDirection = Direction.LEFT;
		}
	}

}
