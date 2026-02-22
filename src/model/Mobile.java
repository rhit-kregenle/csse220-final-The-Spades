package model;

/**
 * Mobile is a super class for Player and Zombie. It
 * helps avoid redundancy and condenses logic.
 * 
 * @author Manas Paranjape
 */

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

abstract public class Mobile {
	protected int posX;
	protected int posY;
	protected int sizeX;
	protected int sizeY;
	protected int delta;

	// Sprite fields.
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	private static String spritePath;

	abstract void update();

}
