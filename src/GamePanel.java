import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

public class GamePanel extends JPanel{
	//private GroundPanel groundPanel = new GroundPanel();
	private JTextField inputField = new JTextField(20);
	
	//떨어질 레이블 50개
	private JLabel wordLabel[] = new JLabel[50];
	private ImageIcon panelBackgroundIcon = new ImageIcon("forest2.jpg");
	private Image forestImg = panelBackgroundIcon.getImage();
	
	
	private WordList wordList = null;
	private ScorePanel scorePanel = null;
	private GroundPanel groundPanel;
	private JFrame gameFrame;
	private Vector<FwLabel> wordLabels = new Vector<>();
	
	
	
	public GamePanel(WordList wordList,ScorePanel scorePanel,JFrame gameFrame) {
		this.wordList = wordList;
		this.scorePanel = scorePanel;
		this.gameFrame = gameFrame;
		setLayout(new BorderLayout());
		groundPanel = new GroundPanel();
		WordMaker wordMaker = new WordMaker();
		wordMaker.start();
		add(groundPanel,BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel();
		
		inputPanel.add(inputField);
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				JTextField t = (JTextField)e.getSource();
				System.out.println(wordLabels.size());
				for(int i=0; i<wordLabels.size();i++)
				{
					String word = wordLabels.get(i).getText();
					//사용자가 입력한값과 같으면
					if(t.getText().equals(word)) {
						wordLabels.get(i).setVisible(false);
						wordLabels.get(i).setValid(false);
						wordLabels.remove(i);
						t.setText("");
						return;
					}
				}
				t.setText("");
			}
		});
		  
		inputPanel.setBackground(Color.yellow);
		inputPanel.add(inputField,BorderLayout.SOUTH);
		add(inputPanel,BorderLayout.SOUTH);
		
		
		
		
		
	}
	
	class WordMaker extends Thread{
		public void run() {
			String word = null;
			while(true)
			{
				word = wordList.getWord();
				
				FallingLabel fallinglabel = new FallingLabel(word);
				fallinglabel.start();
				try {
					sleep(3000);
				}catch(InterruptedException e)
				{
					return;
				}
				
			}
		}
	}
	
	class FallingLabel extends Thread{
		String word;
		public FallingLabel(String word)
		{
			this.word = word;
			
		}
		
		@Override
		public void run() {
			//좌표값 설정 ->약간의 수정 필요
			int x = (int)(Math.random()*(gameFrame.getWidth()-100));
			int y = 10;
			
			//단어를 랜덤하게 받아와 라벨 생성
			FwLabel wLabel = new FwLabel(word);
			
			wLabel.setLocation(x,y);
			wLabel.setForeground(Color.red);
			wordLabels.add(wLabel);
			groundPanel.add(wLabel);
			
			while(y < 500&&wLabel.getValid()) {
				y = wLabel.getY()+5; //5픽셀 씩 아래로 이동
				wLabel.setLocation(x,y);
				try {
					sleep(100); //떨어지는 속도
				}catch(InterruptedException e)
				{
					return;
				}
			}
			if(y>=500 || wLabel.getValid()==false)
			{
				
			//	wordLabels.remove(wLabel);
				groundPanel.remove(wLabel);
				groundPanel.repaint();
			}
			if(y>=500)
			{
				wordLabels.remove(0);
			}
			
			
		}
	}
	

	
	class GroundPanel extends JPanel {
		public GroundPanel() {
			setLayout(null); //절대위치에 컴포넌트를 배치한다.
		
			
			
			
		}
		
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(forestImg,0,0,getWidth(),getHeight(),this );
			
		}
		
	}
}
