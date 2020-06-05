import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class ScoreBoard extends JPanel {

	private static final long serialVersionUID = 5546553705658700072L;
	private int score = 0, highScore = 0;
	private JLabel scoreLb, highScoreLb, stateLb;
	private JTextPane txtpnWUse;

	public void incScore() {
		score++;
		scoreLb.setText(score + "");
	}

	public void updateHighScore() {
		highScore = Math.max(highScore, score);
		highScoreLb.setText(highScore + "");
	}

	public void setGameStatus(String text) {
		stateLb.setText(text);
	}

	public void resetScore() {
		score = 0;
		scoreLb.setText("0");
	}

	public ScoreBoard() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel lblNewLabel = new JLabel("Your Scores: ");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 16));
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel);

		scoreLb = new JLabel("0");
		scoreLb.setFont(new Font("Consolas", Font.BOLD, 16));
		scoreLb.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(scoreLb);

		JLabel lblNewLabel_1 = new JLabel("High Score:");
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("Consolas", Font.BOLD, 16));
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblNewLabel_1);

		highScoreLb = new JLabel("0");
		highScoreLb.setFont(new Font("Consolas", Font.BOLD, 16));
		highScoreLb.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(highScoreLb);

		stateLb = new JLabel("Press P to pause");
		stateLb.setForeground(Color.ORANGE);
		stateLb.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 12));
		stateLb.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(stateLb);

		txtpnWUse = new JTextPane();
		txtpnWUse.setBackground(new Color(240, 240, 240));
		txtpnWUse.setEditable(false);
		txtpnWUse.setFont(new Font("Consolas", Font.PLAIN, 14));
		txtpnWUse.setText("Tips:\r\nUse arrow keys or A,S,D,W to play.");
		add(txtpnWUse);

	}

}
