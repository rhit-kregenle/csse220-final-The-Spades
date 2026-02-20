package model;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class key implements Interactable{

	int x;
	int y;
	boolean beenCollected = false;
	public key(int locX, int locY, boolean collected)
	{
		this.x=locX;
		this.y=locY;
		this.beenCollected=collected;
	}

	@Override
	public void whenInteract(Player player, GameModel model) {
		beenCollected=true;
		player.setHasKey(true);
	}

	public void draw(Graphics2D g2) {
		 if (!beenCollected) {
		g2.drawRect(x, y, 5, 10);
		 }
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public boolean getKeyCollected() {
		return beenCollected;
	}
}
