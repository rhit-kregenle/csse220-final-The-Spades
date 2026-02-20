package ui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.GameModel;

public class GameWindow {
	private JFrame frame = new JFrame("CSSE220 Final Project");
	private JPanel cards = new JPanel(new CardLayout());
	private JPanel startScreen = new StartScreen(this);

	public void show() {
		// Minimal model instance (empty for now, by design)

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cards.add(startScreen, "START");
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, "START");

		frame.setContentPane(cards);

		frame.setSize(615, 640);
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		frame.setVisible(true);
	}

	public void showStart() {
		CardLayout cl = (CardLayout) cards.getLayout();

		cards.removeAll();
		cards.add(startScreen, "START");
		cl.show(cards, "START");
	}

	public void startNewGame() {
		CardLayout cl = (CardLayout) cards.getLayout();
		GameComponent component = new GameComponent(new GameModel(), this);
		cards.add(component, "GAME");
		cl.show(cards, "GAME");
		component.startTimer();
	}
	
	public void showStart(int lastScore) {
		CardLayout cl = (CardLayout) cards.getLayout();

		cards.removeAll();
		cards.add(new StartScreen(this, lastScore), "START");
		cl.show(cards, "START");
	}
}
