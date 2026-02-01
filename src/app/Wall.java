package app;

public class Wall implements Interactable {
	int x1;
	int y1;
	int x2;
	int y2;

	public Wall(int startX, int startY, int endX, int endY) {
		this.x1 = startX;
		this.y1 = startY;
		this.x2 = endX;
		this.y2 = endY;
	}

	@Override
	public void whenInteract() {
		
	}
}
