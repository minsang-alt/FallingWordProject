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
				
				for(int i=0; i<wordLabels.size();i++)
				{
					String word = wordLabels.get(i).getText();
					//사용자가 입력한값과 같으면
					if(t.getText().equals(word)) {
						int money = wordLabels.get(i).getMoney();
						int score = wordLabels.get(i).getScore();
						
						scorePanel.increaseScore(score);
						
						
						//라벨 제거
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
				//단어를 랜덤하게 받아와 라벨생성
				FwLabel wLabel = new FwLabel(wordList);
				
				
				FallingLabel fallinglabel = new FallingLabel(wLabel);
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
		FwLabel wLabel;
		public FallingLabel(FwLabel wLabel)
		{
			this.wLabel = wLabel;
			
		}
		
		@Override
		public void run() {
			//좌표값 설정 ->약간의 수정 필요
			int x = (int)(Math.random()*(1100));
			int y = 10;
	


			wLabel.setLocation(x,y);
			
			wordLabels.add(wLabel);
			groundPanel.add(wLabel);
			
			while(y < 500&&wLabel.getValid()) {
				y = wLabel.getY()+3; //3픽셀 씩 아래로 이동
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
				
			//바닥에 닿거나 사용자의 입력에 의해 없어진 단어를 패널상에서 제거
				groundPanel.remove(wLabel); 
				groundPanel.repaint();
			
				
				
			}
			if(y>=500)
			{
				//바닥에 닿은 라벨을 벡터에서 제거
				wordLabels.remove(0);
				//아바타의 피를 하나깎는다.
				scorePanel.damageHeart();
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
