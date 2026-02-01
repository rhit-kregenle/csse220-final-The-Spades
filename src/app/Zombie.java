package app;

public class Zombie extends Mobile {
	Direction traverseDirection;
	int traverseLength;
	int startingX;
	int startingY;
	
	public Zombie(int startingX, int startingY, Direction traverseDirection, int traverseLength); {
		this.posX = startingX;
		this.posY = startingY;
		this.startingX = startingX;
		this.startingY = startingY;
		this.traverseDirection = traverseDirection;
		this.traverseLength = traverseLength;
	}
	
	@Override
	void update() {
		if (traverseDirection == Direction.DOWN) {
			if (startingY < posY < traverseLength + startingY) {
				posY += delta;
			} else {
				tra
			}
		}
	}

}
