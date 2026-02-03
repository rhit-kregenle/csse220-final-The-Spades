package model;

/**
 * Wall have the positions of the lines draw to make the maze.
 * 
 * @author Luc Kolczak
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

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

	public void whenInteract(Player player) {
		player.flip();
	}
	
	public void draw(Graphics2D g2) {
		g2.setStroke(new BasicStroke(4));
		g2.drawLine(x1, y1, x2, y2);
	}
	
	public int getX1() {
		return x1;
	}
	public int getX2() {
		return x2;
	}
	public int getY1() {
		return y1;
	}
	public int getY2() {
		return y2;
	}

}
