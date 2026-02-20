package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class PowerUp extends JComponent implements Interactable {


	int x;
	int y;
	int diameter = 10;
	Color color = new Color(173, 216, 230);;
	boolean collected = false;

	public PowerUp(int posX, int posY) {

		this.x = posX;

		this.y = posY;
	}

	public void draw(Graphics2D g2) {
		if (!collected) {
			g2.setStroke(new BasicStroke(4));
			g2.setColor(this.color);
			g2.fillOval(x, y, diameter, diameter);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		if (!collected) {
			super.paintComponent(g);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getBounds() {
		Rectangle r = new Rectangle(x, y, diameter, diameter);
		return r;
	}

	@Override
	public void whenInteract(Player _player, GameModel model) {
		if (collected == false) model.setFreezePowerUp(40);
		collected = true;
		// player.scoreIncrease(); need to get this to be able to work, classes
		// unrelated as of right now
		// also talked to jackOwen abt structure and he says its not great
	}
}
