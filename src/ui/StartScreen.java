package ui;

import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JPanel {
	private JButton start = new JButton("Start");
	private int last_score;
	private GameWindow window;
	
	public StartScreen(GameWindow window) {
		this.window = window;
		
		this.add(start);
		JLabel score = new JLabel(Integer.toString(last_score));
		
		this.add(score);
		
		start.addActionListener(e -> {
		    window.startNewGame();
		});
	}
}
