package app;

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
	public Zombie(int startingX, int startingY, Direction traverseDirection, int traverseLength, int speed) {
		this.posX = startingX;
		this.posY = startingY;
		this.startingX = startingX;
		this.startingY = startingY;
		this.traverseDirection = traverseDirection;
		this.traverseLength = traverseLength;
		this.delta = speed;
	}
	
	public Zombie(int startingX, int startingY, Direction traverseDirection, int traverseLength) {
		this.posX = startingX;
		this.posY = startingY;
		this.startingX = startingX;
		this.startingY = startingY;
		this.traverseDirection = traverseDirection;
		this.traverseLength = traverseLength;
		this.delta = 5;
	}
	
	/**
	 * Moves the zombie each tick based on how the zombie was initialized
	 */
	@Override
	public void update() {
		if (traverseDirection == Direction.DOWN) {
			if (startingY < posY & posY < traverseLength + startingY) {
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
			if (startingX < posX & posX < traverseLength + startingX) {
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

}
