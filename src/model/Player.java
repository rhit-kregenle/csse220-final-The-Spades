package model;

/**
 * As a moving object, it is a child of Mobile.
 * 
 * Player moves and changes direction based on inputs.
 * 
 * @author Leif Kregenow
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Mobile {
	private int maxLives;
	private int currentLives;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	Direction direction;
	private int isShoving;
	private Direction shovingDirection;

	public Player(int startingX, int startingY, int maxLives, int sizeX, int sizeY) {
		this.posX = startingX;
		this.posY = startingY;
		this.maxLives = maxLives;
		this.currentLives = maxLives;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.direction = Direction.UP;
		loadSpriteOnce();
		this.delta = 5;
		this.isShoving = 0;
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
			posY -= delta;
		} else if (direction == Direction.DOWN) {
			posY += delta;
		} else if (direction == Direction.LEFT) {
			posX -= delta;
		} else if (direction == Direction.RIGHT) {
			posX += delta;
		}
		
		if (isShoving > 0) {
			isShoving--;
			if (isShoving == 5) {
				if (shovingDirection == Direction.UP) {
					posY += sizeY;
					sizeY /= 2;
				} else if (shovingDirection == Direction.DOWN) {
					sizeY /= 2;
				} else if (shovingDirection == Direction.LEFT) {
					posX += sizeX;
					sizeX /= 2;
				} else if (shovingDirection == Direction.RIGHT) {
					sizeX /= 2;
				}
			}
		}
	}
	
	public void flip() {
		if (direction == Direction.UP) {
			direction = Direction.DOWN;
		} else if (direction == Direction.DOWN) {
			direction = Direction.UP;
		} else if (direction == Direction.LEFT) {
			direction = Direction.RIGHT;
		} else if (direction == Direction.RIGHT) {
			direction = Direction.LEFT;
		}
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	public int getIsShoving() {
		return isShoving;
	}
	
	public Direction getShovingDirection() {
		return shovingDirection;
	}
	
	public Rectangle getPlayerBounds() {
	    Rectangle r = new Rectangle(
	    		posX,
	    		posY,
	    		sizeX,
	    		sizeY
	    );
	    return r;
	}
	
	public void shove() {
		if (isShoving == 0) {
			if (direction == Direction.UP) {
				posY -= sizeY;
				sizeY *= 2;
			} else if (direction == Direction.DOWN) {
				sizeY *= 2;
			} else if (direction == Direction.LEFT) {
				posX -= sizeX;
				sizeX *= 2;
			} else if (direction == Direction.RIGHT) {
				sizeX *= 2;
			}
			isShoving = 10;
			shovingDirection = direction;
		}
		
	}
	
	public void isHit(){
		currentLives -= 1;
		flip();
	}
}
