package ui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import model.*;

public class GameComponent extends JComponent {
	private static final int TILE_SIZE = 4;

	private Player player;
	private HashMap<Character, ArrayList<Integer>> zombiePositions = new HashMap<>();
	private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	private Timer timer;
	private GameModel model;
	private GameWindow window;

	private ArrayList<Gem> gems = new ArrayList<Gem>();
	private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();

	private HashMap<Character, ArrayList<Integer>> wall_positions = new HashMap<>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();

	private Exit exit;
	private key key;

	private BufferedImage background;
	private static boolean triedLoadBg = false;

	public GameComponent(GameModel model, GameWindow window) {
		this.model = model;
		this.window = window;

		setFocusable(true);
		requestFocus();

		loadBackgroundOnce();
		loadLevel(model.getLevel());

		timer = new Timer(50, e -> {
			player.update();
			requestFocus();

			wallBounce();
			zombieFlip();
			zombieShove();
			gemCollect(model);
			keyCollect(model);
			powerUpCollect(model);

			if (player.getPlayerBounds().intersects(exit.getBounds())) {
				if (player.hasKey()) {
					model.levelWon();
				}
			}

			if (player.hasKey()) {
				exit.makeExitable(player);
			}

			if (model.getLives() <= 0) {
				this.window.showStart(model.getScore());
				timer.stop();
			}

			if (model.drawNewLevel()) {
				gems = new ArrayList<Gem>();
				zombies = new ArrayList<Zombie>();
				walls = new ArrayList<Wall>();
				wall_positions = new HashMap<>();
				powerUps = new ArrayList<PowerUp>();

				loadLevel(model.getLevel());
				model.newLevelDrawn();
			}

			repaint();
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W) {
					player.handleInput(Direction.UP);
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					player.handleInput(Direction.DOWN);
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					player.handleInput(Direction.LEFT);
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					player.handleInput(Direction.RIGHT);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					player.shove();
				}
			}
		});
	}

	private void loadBackgroundOnce() {
		if (triedLoadBg) return;
		triedLoadBg = true;
		try {
			background = ImageIO.read(GameComponent.class.getResource("/ui/background.png"));
		} catch (IOException | IllegalArgumentException e) {
			background = null;
		}
	}

	private void wallBounce() {
		for (int i = 0; i < walls.size(); i++) {
			if (walls.get(i).getX1() == walls.get(i).getX2()) {
				if (player.getPosX() <= walls.get(i).getX1()
						&& walls.get(i).getX1() <= player.getPosX() + player.getSizeX()
						&& ((walls.get(i).getY1() <= player.getPosY() && player.getPosY() <= walls.get(i).getY2())
								|| (walls.get(i).getY1() <= player.getPosY() + player.getSizeY()
										&& player.getPosY() + player.getSizeY() <= walls.get(i).getY2()))) {
					player.flip();
					player.update();
					player.update();
					break;
				}
			} else {
				if (player.getPosY() <= walls.get(i).getY1()
						&& walls.get(i).getY1() <= player.getPosY() + player.getSizeY()
						&& ((walls.get(i).getX1() <= player.getPosX() && player.getPosX() <= walls.get(i).getX2())
								|| (walls.get(i).getX1() <= player.getPosX() + player.getSizeX()
										&& player.getPosX() + player.getSizeX() <= walls.get(i).getX2()))) {
					player.flip();
					player.update();
					player.update();
					break;
				}
			}
		}
	}

	private void zombieShove() {
		for (Zombie zombie : zombies) {
			model.setFreezePowerUp(model.getFreezePowerUp() - 1);
			if (model.getFreezePowerUp() <= 0) {
				zombie.update();
	
				if (player.getPlayerBounds().intersects(zombie.getZombieBounds())) {
					if (player.getIsShoving() >= 5) {
						zombie.getShoved(player.getShovingDirection());
						player.flip();
						player.update();
						player.update();
					} else {
						this.model.livesDecrease();
						player.flip();
						zombie.flip();
						player.update();
						player.update();
					}
				}
			}
		}
	}

	private void zombieFlip() {
		for (int i = 0; i < zombies.size(); i++) {
			for (int j = i + 1; j < zombies.size(); j++) {
				Zombie e1 = zombies.get(i);
				Zombie e2 = zombies.get(j);

				if (e1.getZombieBounds().intersects(e2.getZombieBounds())) {
					e1.flip();
					e2.flip();
				}
			}
		}
	}

	private void gemCollect(GameModel model) {
		for (Gem g : gems) {
			if (player.getPlayerBounds().intersects(g.getBounds())) {
				g.whenInteract(player, model);
			}
		}
	}

	private void keyCollect(GameModel model) {
		if (player.getPlayerBounds().intersects(key.getBounds())) {
			key.whenInteract(player, model);
		}
	}

	private void powerUpCollect(GameModel model) {
		for (PowerUp p : powerUps) {
			if (player.getPlayerBounds().intersects(p.getBounds())) {
				p.whenInteract(player, model);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		Color origColor = g2.getColor();

		if (background != null) {
			g2.drawImage(background, 0, 0, 600, 600, null);
		} else {
			Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, 600, 600);
			g2.setColor(Color.GREEN);
			g2.fill(rect);
		}

		g2.setColor(origColor);

		player.draw(g2);
		for (Zombie zombie : zombies) {
			zombie.draw(g2);
		}

		exit.draw(g2);
		key.draw(g2);

		drawHUD(g2);

		for (int i = 0; i < walls.size(); i++)
			walls.get(i).draw(g2);

		for (Gem gem : gems) {
			gem.draw(g2);
		}
		for (PowerUp powerUp : powerUps) {
			powerUp.draw(g2);
		}
	}

	private void drawHUD(Graphics2D g) {
		Font font = new Font("Arial", Font.PLAIN, 20);
		g.setFont(font);

		Color orig = g.getColor();

		g.setColor(Color.BLACK);
		g.drawString("Score: " + this.model.getScore(), 10, 20);
		g.drawString("Lives: " + this.model.getLives(), 10, 45);

		g.setColor(orig);
	}

	private int getScore() {
		return model.getScore();
	}

	public void startTimer() {
		timer.start();
	}

	private void loadLevel(int level) {
		File file = new File("level" + level + ".txt");
		int row = 0;

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				for (int col = 0; col < line.length(); col++) {
					char c = line.charAt(col);
					int xTile = col * TILE_SIZE;
					int yTile = row * TILE_SIZE;

					if (c == 'P') {
						player = new Player(xTile, yTile, 3, 25, 25);
					} else if (c == 'G') {
						gems.add(new Gem(xTile, yTile));
					} else if (c == 'K') {
						key = new key(xTile, yTile, false);
					} else if (c == 'E') {
						exit = new Exit(xTile, yTile);
					} else if (c == 'p') {
						powerUps.add(new PowerUp(xTile, yTile));
					} else if (isNumeric(Character.toString(c))) {
						int num = Integer.parseInt(Character.toString(c));
						if (num % 2 == 0) {
							if (zombiePositions.containsKey(Integer.toString(num + 1).toCharArray()[0])) {
								ArrayList<Integer> endPos = zombiePositions
										.get(Integer.toString(num + 1).toCharArray()[0]);
								int endX = endPos.get(0);
								int endY = endPos.get(1);
								Direction traverse = Direction.DOWN;
								int traverse_length;
								if (endX != xTile) {
									traverse = endX > xTile ? Direction.RIGHT : Direction.LEFT;
									traverse_length = Math.abs(endX - xTile);
								} else {
									traverse = endY > yTile ? Direction.DOWN : Direction.UP;
									traverse_length = Math.abs(endY - yTile);
								}
								zombies.add(new Zombie(xTile, yTile, traverse, traverse_length));
							} else {
								ArrayList<Integer> startPos = new ArrayList<Integer>();
								startPos.add(xTile);
								startPos.add(yTile);
								zombiePositions.put((Character) (c), startPos);
							}
						} else {
							if (zombiePositions.containsKey(Integer.toString(num - 1).toCharArray()[0])) {
								ArrayList<Integer> startPos = zombiePositions
										.get(Integer.toString(num - 1).toCharArray()[0]);
								int startX = startPos.get(0);
								int startY = startPos.get(1);
								Direction traverse = Direction.DOWN;
								int traverse_length;
								if (startX != xTile) {
									traverse = startX > xTile ? Direction.RIGHT : Direction.LEFT;
									traverse_length = Math.abs(startX - xTile);
								} else {
									traverse = startY > yTile ? Direction.DOWN : Direction.UP;
									traverse_length = Math.abs(startY - yTile);
								}
								zombies.add(new Zombie(xTile, yTile, traverse, traverse_length));
							} else {
								ArrayList<Integer> startPos = new ArrayList<Integer>();
								startPos.add(xTile);
								startPos.add(yTile);
								zombiePositions.put((Character) (c), startPos);
							}
						}
					} else if (c != '.') {
						if (wall_positions.containsKey(Character.toLowerCase(c))) {
							ArrayList<Integer> startPos = wall_positions.get(Character.toLowerCase(c));
							if (Character.isUpperCase(c)) {
								walls.add(new Wall(startPos.get(0), startPos.get(1), xTile, yTile));
							} else {
								walls.add(new Wall(xTile, yTile, startPos.get(0), startPos.get(1)));
							}
						} else {
							ArrayList<Integer> positions = new ArrayList<Integer>();
							positions.add(xTile);
							positions.add(yTile);
							wall_positions.put(Character.toLowerCase(c), positions);
						}
					}
				}
				row++;
			}
			scanner.close();
		} catch (IOException ex) {
			timer.stop();
			this.window.showStart(model.getScore());
		}
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) return false;
		try {
			Double.parseDouble(strNum);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}