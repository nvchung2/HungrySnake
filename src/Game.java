import javax.swing.JPanel;

public class Game extends JPanel {

	private static final long serialVersionUID = -3761520017470638453L;
	private Board board;
	private ScoreBoard scoreBoard;
	public ScoreBoard getScoreBoard(){
		return scoreBoard;
	}
	public Game() {
		setLayout(null);
		board=new Board(this);
		scoreBoard=new ScoreBoard();
		board.setBounds(30,30,Config.BOARD_WIDTH,Config.BOARD_HEIGHT);
		scoreBoard.setBounds(50+Config.BOARD_WIDTH,100,120,300);
		add(board);
		add(scoreBoard);
	}
}
