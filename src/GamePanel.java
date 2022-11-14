import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel{
	//private GroundPanel groundPanel = new GroundPanel();
	private JTextField inputField = new JTextField(20);
	
	private JLabel wordLabel = new JLabel("이곳에 단어가 등장합니다.");
	
	//wordLabel 은 groundPanel보다 먼저 생성해야함
	private GroundPanel groundPanel = new GroundPanel();
	
	private WordList wordList = null;
	private ScorePanel scorePanel = null;
	public GamePanel(WordList wordList,ScorePanel scorePanel) {
		this.wordList = wordList;
		this.scorePanel = scorePanel;
		
		setLayout(new BorderLayout());
		add(groundPanel,BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel();
		inputPanel.add(inputField);
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JTextField t = (JTextField)e.getSource();
				if(t.getText().equals(wordLabel.getText())) {
					scorePanel.increase();
					String word = wordList.getWord();
					setWord(word);
					t.setText("");
				}
			}
		});
		inputPanel.setBackground(Color.gray);
		inputPanel.add(inputField,BorderLayout.SOUTH);
		add(inputPanel,BorderLayout.SOUTH);
		
		
		
		
		
	}
	public  void setWord(String word)
	{
		wordLabel.setText(word);
		
	}
	class GroundPanel extends JPanel{
		public GroundPanel() {
			
			add(wordLabel);
		}
	}
}
