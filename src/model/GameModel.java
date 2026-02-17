package model;

public class GameModel {
	private int score = 0;
	private int lives = 3;
	private int level = 1;
	private boolean drawNewLevel = false;
	
	public boolean gameOver() {
		if (lives == 0) return true;
		return false;
	}

	public int getScore() {
		return score;
	}

	public int getLives() {
		return lives;
	}
	
	public void scoreIncrease() {
		score += 100;
	}
	
	public void livesDecrease() {
		lives -= 1;
		score -= 10;
	}
	
	public void levelWon() {
		level += 1;
		drawNewLevel = true;
	}
	
	public void newLevelDrawn() {
		drawNewLevel = false;
	}
	
	public boolean drawNewLevel() {
		return drawNewLevel;
	}
}
