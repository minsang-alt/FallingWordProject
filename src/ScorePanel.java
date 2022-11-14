import java.awt.Color;

import javax.swing.*;

public class ScorePanel extends JPanel{
	private int score=0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	
	public ScorePanel() {
		setBackground(Color.yellow);
		add(new JLabel("점수"));
		add(scoreLabel);
	}
	
	public void increase() {
		score+=10;
		scoreLabel.setText(Integer.toString(score));
	}
}
