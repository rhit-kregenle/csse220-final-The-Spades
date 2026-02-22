package model;

/**
 * GameModel
 * 
 * Purpose: GameModel tracks the state of the game, including levels, lives,
 * score, etc. It also is able to track whether to load another level or if
 * the game is over.
 * 
 * @author The Spades
 */

public class GameModel {
	private int score = 0;
	private int lives = 3;
	private int level = 1;
	private boolean drawNewLevel = false;
	private int freezePowerUp = 0;
	
	public int getFreezePowerUp() {
		return freezePowerUp;
	}
	
	public void setFreezePowerUp(int time) {
		freezePowerUp = time;
	}
	
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
	
	public int getLevel() {
		return level;
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
