package model;

public class GameModel {
	private int score = 0;
	private int lives = 3;
	
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
}
