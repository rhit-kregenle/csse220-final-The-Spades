package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
		
		this.setLayout(new BorderLayout());
		
		this.add(start, BorderLayout.CENTER);

		JLabel score = new JLabel("Last Score: " + Integer.toString(last_score));
		
		this.add(score, BorderLayout.NORTH);
		
		start.addActionListener(e -> {
		    window.startNewGame();
		});
		
		setFocusable(true);
		requestFocus();
	}
	
	public StartScreen(GameWindow window, int last_score) {
		System.out.println("test");
		this.window = window;
		this.last_score = last_score;
		
		this.setLayout(new BorderLayout());
		
		start.addActionListener(e -> {
		    window.startNewGame();
		});
		
		this.add(start, BorderLayout.CENTER);

		JLabel score = new JLabel("Last Score: " + Integer.toString(last_score));
		
		this.add(score, BorderLayout.NORTH);

		
		setFocusable(true);
		requestFocus();
	}
}
