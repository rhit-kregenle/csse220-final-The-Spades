package model;

/**
 * Mobile is a super class for Player and Zombie.
 * 
 * @author Manas Paranjape
 */

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

abstract public class Mobile {
	int posX;
	int posY;
	int sizeX;
	int sizeY;
	int delta;
	
	// Sprite fields.
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	private static String spritePath;
	
	abstract void update();
	
	
}
