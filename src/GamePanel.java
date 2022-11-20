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
	private EditPanel storePanel = null;
	private GroundPanel groundPanel=new GroundPanel();
	private JFrame gameFrame;
	private Vector<FwLabel> wordLabels = new Vector<>();
	private WordMaker wordMaker= new WordMaker();
	//게임 생성 상태
	private boolean gameStart = false;
	//게임 진행 여부
	private boolean gaming = false;
	
	
	public GamePanel(WordList wordList,ScorePanel scorePanel,JFrame gameFrame,EditPanel storePanel) {
		this.wordList = wordList;
		
		this.scorePanel = scorePanel;
		
		this.storePanel = storePanel;
		this.gameFrame = gameFrame;
		scorePanel.getGamePanel(this);
		setLayout(new BorderLayout());
		
		
		
		  
		
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
						storePanel.increaseCoin(money);
						
						
						//적의 피를 깎고 적이 맞는 모션을 보여준다.
						groundPanel.damageEnemy();
						
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
	
	class WordMaker extends Thread {
		
		private JLabel timerCount = new JLabel(" ");
		
		private boolean isPause = false;
		
		private int stage;
		
		private int step;
		
		private long delay;
		
		public WordMaker(){
			timerCount.setLocation(600,100);
			timerCount.setSize(300,200);
			timerCount.setForeground(Color.red);
			timerCount.setFont(new Font("Gothic",Font.PLAIN,50));
			groundPanel.add(timerCount);
			
			
			
		}
		
		public void setPause(boolean isPause) {
			this.isPause = isPause;
		}
		
		@Override
		public void run() {
			stage = scorePanel.getStage();
			step = scorePanel.getStep();
			String word = null;
			
			//시작전 3,2,1을 표시한다.
			try {
			
				timerCount.setText("3");
				Thread.sleep(1000);
				timerCount.setText("2");
				Thread.sleep(1000);
				timerCount.setText("1");
				Thread.sleep(1000);
				timerCount.setText("게임시작!");
				Thread.sleep(1000);
				
				timerCount.setVisible(false);
			}catch(InterruptedException e)
			{
				return;
			}
			
			switch(stage)
			{
			case 1:
				delay = 3000;
				
				break;
			case 2:
				delay = 2000;
				
				break;
			case 3:
				delay = 1000;
				
				break;
			}
			
			
			while(true)
			{
			
				
				if(gaming==true) {
					
				//단어를 랜덤하게 받아와 라벨생성
				FwLabel wLabel = new FwLabel(wordList,scorePanel);
				FallingLabel fallinglabel = new FallingLabel(wLabel);
				
				fallinglabel.start();
				}
				try {
					Thread.sleep(delay); //단어 생성 속도
				}catch(InterruptedException e)
				{
					return;
				}
				
				
				
				
			}
		}
	}
	
	class FallingLabel extends Thread{
		FwLabel wLabel;
		private int stage;
		private int step;
		//떨어지는 속도
		private long delay;
		public FallingLabel(FwLabel wLabel)
		{
			this.wLabel = wLabel;
			
		}
	
		@Override
		public void run() {
			/*
			 * // 이미 패널이 화면상에서 사라졌음에도 쓰레드가 살아있는 경우 종료 if (getParent() == null) { return; }
			 */
			//좌표값 설정 ->약간의 수정 필요
			int x = (int)(Math.random()*(1100));
			int y = 10;
			stage = scorePanel.getStage();
			step = scorePanel.getStep();
			switch(stage)
			{
			case 1:
				if(step==1)delay = 100;
				else if(step==2) delay = 90;
				else if(step==3) delay = 80;
				break;
			case 2:
				if(step==1)delay = 70;
				else if(step==2) delay = 60;
				else if(step==3) delay = 50;
				break;
			case 3:
				if(step==1)delay = 40;
				else if(step==2) delay = 30;
				else if(step==3) delay = 20;
				break;
			}
			
			wLabel.setLocation(x,y);
			
			wordLabels.add(wLabel);
			groundPanel.add(wLabel);
			
			while(y < 660&&wLabel.getValid()) {
				if(gaming==true) {
				
				y = wLabel.getY()+3; //3픽셀 씩 아래로 이동
				wLabel.setLocation(x,y);
				}
				try {
					sleep(delay); //떨어지는 속도
				}catch(InterruptedException e)
				{
					return;
				}
				
			}
			if(y>=660 || wLabel.getValid()==false )
			{
				
			//바닥에 닿거나 사용자의 입력에 의해 없어진 단어를 패널상에서 제거
				groundPanel.remove(wLabel); 
				groundPanel.repaint();
			
				
				
			}
			if(y>=660)
			{
				//바닥에 닿은 라벨을 벡터에서 제거
				wordLabels.remove(0);
				//아바타의 피를 하나깎는다.
				scorePanel.damageHeart();
			}
			
			
		}
	}
	

	
	class GroundPanel extends JPanel {
		private HealthBarLabel healthBar = null;
		public GroundPanel() {
			setLayout(null); //절대위치에 컴포넌트를 배치한다.
			
			//적의 피칸이 50개이다.
			healthBar = new HealthBarLabel(10);
			//healthBar.setSize(300,40);
			healthBar.setLocation(880,560);
			
			add(healthBar);
			
		}
		
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.drawImage(forestImg,0,0,getWidth(),getHeight(),this );
			
		}
		
		//적이 데미지를 입었을때의 함수
		public void damageEnemy() {
			healthBar.consume();
		}
		
	}
	
	//게임시작버튼을 누른 이후 실행됨 
	public void start() {
		
		if(!gameStart) {
			
			gameStart = true; 
			gaming = true;
			wordMaker.start();
			inputField.setEnabled(true);
			inputField.requestFocus();
			 
		}
		else if(gameStart==true) {
			
			gaming = true;
			inputField.setEnabled(true);
			inputField.requestFocus();
		}
		 
		
		 
		 
	}
	
	public void pause() {
		//게임중을 멈춘다. 
		gaming = false;
		//텍스트필드 비활성화
		inputField.setEnabled(false);
		
	}
	
	public void stop() {
		gameStart = false;
		gaming = false;
		//화면상의 모든 라벨들 제거
		for(int i=0; i<wordLabels.size(); i++)
		{
			wordLabels.get(i).setVisible(false);
			wordLabels.get(i).setValid(false);
			//wordLabels.remove(i);
		}
		
		wordMaker.interrupt();
		wordMaker = new WordMaker();
	}
		
	
	
	
	
	//적의 피칸을 만든다.
	class HealthBarLabel extends JLabel{
		private int barSize = 0; // 현재 그려져야 할 바의 크기
		private int maxBarSize; // 바의 최대 크기 피가 50칸이면 50
		private int oneSpace; // 피 한칸의 길이
		
		public HealthBarLabel(int maxBarSize) {
			
			setSize(250,20);
			setBackground(Color.gray);
			setOpaque(true);
			this.maxBarSize = maxBarSize;
			
			oneSpace = (int)(this.getWidth() / maxBarSize); 
			barSize = maxBarSize;
		}
		
		@Override
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(new Color(153,0,76));
			int width = barSize*oneSpace;
			if(width==0)return;
			g.fillRect(0,0,width,this.getHeight());
		}
		
		public void consume() {
			
			
			barSize--;
			//피가 0이되면 초기화 
			if(barSize==0) {
				barSize = maxBarSize;
				repaint();
				//다음단계로 진행
				scorePanel.increaseStage();
				
			}
			repaint();
			
		}
		
	}

}

