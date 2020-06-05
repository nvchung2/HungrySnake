import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

enum GameStates {
	READY, PLAYING, PAUSED, GAMEOVER
}

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8755708669350878227L;
	private Snake snake;
	private Game game;
	private Apple apple;
	private GameStates state;
	private ImageIcon playIcon;
	private Image gameOverImg, logoImg, grass1, grass2;
	private JButton playBtn;
	private long lastMoveTime, lastKeyPressTime;
	private int gameSpeed = 100;

	public Board(Game g) {
		game = g;
		setFocusable(true);
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				requestFocusInWindow();
			}
		});
		setBorder(new LineBorder(Color.ORANGE, 2));
		setBackground(Color.WHITE);
		setLayout(null);
		new Timer(1000 / Config.FPS, this).start();
		addKeyListener(new GameControl());
		state = GameStates.READY;
		gameOverImg = ImageLoader.load("gameover.png");
		logoImg = ImageLoader.load("logo.png");
		grass1 = ImageLoader.load("grass1.png");
		grass2 = ImageLoader.load("grass2.png");
		playIcon = new ImageIcon(ImageLoader.load("play.png"));
		playBtn = new JButton();
		playBtn.setContentAreaFilled(false);
		playBtn.setBorder(new EmptyBorder(0, 0, 0, 0));
		playBtn.setIcon(playIcon);
		playBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		playBtn.setBounds(Config.BOARD_WIDTH / 2 - playIcon.getIconWidth() / 2, Config.BOARD_HEIGHT / 3,
				playIcon.getIconWidth(), playIcon.getIconHeight());
		playBtn.addActionListener(e -> {
			reset();
		});
		add(playBtn);
	}

	private void reset() {
		state = GameStates.PLAYING;
		snake = new Snake();
		apple = new Apple();
		apple.instantiate(snake);
		remove(playBtn);
		game.getScoreBoard().resetScore();
	}

	private void drawGrass(Graphics g) {
		int rows = Config.BOARD_HEIGHT / Config.BOX_SIZE;
		int columns = Config.BOARD_WIDTH / Config.BOX_SIZE;
		boolean b = false, even = columns % 2 == 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (b = !b) {
					g.drawImage(grass1, j * Config.BOX_SIZE, i * Config.BOX_SIZE, null);
				} else {
					g.drawImage(grass2, j * Config.BOX_SIZE, i * Config.BOX_SIZE, null);
				}
			}
			if (even)
				b = !b;
		}
	}

	public void doDrawing(Graphics g) {
		drawGrass(g);
		if (state == GameStates.READY) {
			g.drawImage(logoImg, (Config.BOARD_WIDTH - logoImg.getWidth(null)) / 2, 0, null);
		} else if (state == GameStates.PLAYING || state == GameStates.PAUSED) {
			snake.draw(g);
			apple.draw(g);
		} else if (state == GameStates.GAMEOVER) {
			g.drawImage(gameOverImg, Config.BOARD_WIDTH / 2 - gameOverImg.getWidth(null) / 2, 0, null);
			add(playBtn);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	private void gameLoop() {
		if (state == GameStates.PAUSED) {
			return;
		}
		if (state == GameStates.PLAYING) {
			if (System.currentTimeMillis() - lastMoveTime > gameSpeed) {
				snake.move();
				lastMoveTime = System.currentTimeMillis();
			}
			if (snake.eat(apple)) {
				game.getScoreBoard().incScore();
				apple.instantiate(snake);
			}
			if (snake.die()) {
				game.getScoreBoard().updateHighScore();
				state = GameStates.GAMEOVER;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameLoop();
		repaint();
	}

	public class GameControl extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (state == GameStates.PLAYING) {
				if (System.currentTimeMillis() - lastKeyPressTime > gameSpeed) {
					Directions dir = snake.getDir();
					Directions newDir = null;
					if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && dir != Directions.DOWN) {
						newDir = Directions.UP;
					} else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && dir != Directions.UP) {
						newDir = Directions.DOWN;
					} else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && dir != Directions.RIGHT) {
						newDir = Directions.LEFT;
					} else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && dir != Directions.LEFT) {
						newDir = Directions.RIGHT;
					} else if (key == KeyEvent.VK_P) {
						state = GameStates.PAUSED;
						game.getScoreBoard().setGameStatus("Press P to resume");
					}
					if (newDir != null) {
						snake.setDir(newDir);
						lastKeyPressTime = System.currentTimeMillis();
					}
				}
			} else if (state == GameStates.PAUSED && key == KeyEvent.VK_P) {
				state = GameStates.PLAYING;
				game.getScoreBoard().setGameStatus("Press P to pause");
			}
		}
	}
}
