import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;

public class GamePanel extends JPanel {
	// private GroundPanel groundPanel = new GroundPanel();
	private JTextField inputField = new JTextField(20);

	// 떨어질 레이블 50개
	private JLabel wordLabel[] = new JLabel[50];
	// private ImageIcon panelBackgroundIcon = new ImageIcon("forest2.jpg");
	private ImageIcon panelBackgroundIcon[] = { new ImageIcon("forest2.jpg"), new ImageIcon("bluemountain-middle.png"),
			new ImageIcon("bluemountain-bottom.png") };
	private Image forestImg = panelBackgroundIcon[0].getImage();

	private WordList wordList = null;
	private ScorePanel scorePanel = null;
	private EditPanel storePanel = null;
	//적 이미지 라벨
		private ImageIcon enemyIcon = new ImageIcon("enemy.png");
		private JLabel enemy=new JLabel();
	//커비 이미지 라벨
		private ImageIcon kirbyIcon = new ImageIcon("kirbyAttackImg/kirby1Attack1.png");
		private JLabel kirby = new JLabel();
	private GroundPanel groundPanel = new GroundPanel();
	private JFrame gameFrame;
	private Vector<FwLabel> wordLabels = new Vector<>();
	private WordMaker wordMaker = new WordMaker();
	// 게임 생성 상태
	private boolean gameStart = false;
	// 게임 진행 여부
	private boolean gaming = false;
	
			
	public GamePanel(WordList wordList, ScorePanel scorePanel, JFrame gameFrame, EditPanel storePanel) {
		this.wordList = wordList;

		this.scorePanel = scorePanel;

		this.storePanel = storePanel;
		this.gameFrame = gameFrame;
		scorePanel.getGamePanel(this);
		setLayout(new BorderLayout());

		add(groundPanel, BorderLayout.CENTER);

		JPanel inputPanel = new JPanel();

		inputPanel.add(inputField);
		inputField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField t = (JTextField) e.getSource();

				for (int i = 0; i < wordLabels.size(); i++) {
					String word = wordLabels.get(i).getText();
					// 사용자가 입력한값과 같으면
					if (t.getText().equals(word)) {
						int money = wordLabels.get(i).getMoney();
						int score = wordLabels.get(i).getScore();

						scorePanel.increaseScore(score);
						storePanel.increaseCoin(money);

						// 적의 피를 깎고 적이 맞는 모션을 보여준다.
						groundPanel.damageEnemy();
						
						// 라벨 제거
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
		inputPanel.add(inputField, BorderLayout.SOUTH);
		add(inputPanel, BorderLayout.SOUTH);

	}

	class WordMaker extends Thread {

		private JLabel timerCount = new JLabel(" ");

		private boolean isPause = false;

		private int stage;

		private int step;

		private long delay;

		public WordMaker() {
			timerCount.setLocation(600, 100);
			timerCount.setSize(300, 200);
			timerCount.setForeground(Color.red);
			timerCount.setFont(new Font("Gothic", Font.PLAIN, 50));
			groundPanel.add(timerCount);

		}

		public void setPause(boolean isPause) {
			this.isPause = isPause;
		}

		synchronized public void waitCreateWord() {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				return;
			}
		}

		synchronized public void resumeCreateWord() {
			gaming = true;
			this.notify();
		}

		@Override
		public void run() {
			stage = scorePanel.getStage();
			step = scorePanel.getStep();
			String word = null;

			// 시작전 3,2,1을 표시한다.
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
			} catch (InterruptedException e) {
				return;
			}

			switch (stage) {
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

			while (true) {

				if (gaming == false) {
					waitCreateWord();
				}
				if (gaming == true) {

					// 단어를 랜덤하게 받아와 라벨생성
					FwLabel wLabel = new FwLabel(wordList, scorePanel);
					FallingLabel fallinglabel = new FallingLabel(wLabel);
					
					fallinglabel.start();
				}
				try {
					Thread.sleep(delay); // 단어 생성 속도
				} catch (InterruptedException e) {
					return;
				}

			}
		}
	}

	class FallingLabel extends Thread {
		FwLabel wLabel;
		private int stage;
		private int step;
		// 떨어지는 속도
		private long delay;

		public FallingLabel(FwLabel wLabel) {
			this.wLabel = wLabel;

		}

		
		@Override
		public void run() {
			
			int x = (int) (Math.random() * (1100));
			int y = 10;
			stage = scorePanel.getStage();
			step = scorePanel.getStep();
			// 떨어지는 속도 설정
			switch (stage) {
			case 1:
				if (step == 1)
					delay = 90;
				else if (step == 2)
					delay = 80;
				else if (step == 3)
					delay = 70;
				break;
			case 2:
				if (step == 1)
					delay = 60;
				else if (step == 2)
					delay = 50;
				else if (step == 3)
					delay = 40;
				break;
			case 3:
				if (step == 1)
					delay = 35;
				else if (step == 2)
					delay = 30;
				else if (step == 3)
					delay = 20;
				break;
			}

			wLabel.setLocation(x, y);

			wordLabels.add(wLabel);
			
			groundPanel.add(wLabel);
			groundPanel.setComponentZOrder(wLabel,0);

			while (y < 660 && wLabel.getValid()) {

				if (gaming == true) {

					y = wLabel.getY() + 3; // 3픽셀 씩 아래로 이동
					wLabel.setLocation(x, y);
				}
				try {
					sleep(delay); // 떨어지는 속도
				} catch (InterruptedException e) {
					return;
				}

			}
			if (y>=600||wLabel.getValid() == false) {
				if(!wLabel.getValid()) {
					
					new CharactorMotion("kirby1Attack",kirby,100).run();
					
				//단어를 맞췄을때 적 맞는 모션
					new CharactorMotion("enemy1Struct",enemy,300).run();
					kirby.setIcon(kirbyIcon);
					enemy.setIcon(enemyIcon);
					
					
				}
				// 바닥에 닿거나 사용자의 입력에 의해 없어진 단어를 패널상에서 제거
				groundPanel.remove(wLabel);
				groundPanel.repaint();
				

			}
			if (y >= 660) {
				// 바닥에 닿은 라벨을 벡터에서 제거
				wordLabels.remove(0);
				// 아바타의 피를 하나깎는다.
				scorePanel.damageHeart();
				new CharactorMotion("enemy1Attack",enemy,300).run();
				enemy.setIcon(enemyIcon);
			}

		}
	}

	class GroundPanel extends JPanel {
		// 적의 체력바 이용
		private HealthBarLabel healthBar = null;
		
		
		public GroundPanel() {
			setLayout(null); // 절대위치에 컴포넌트를 배치한다.

			// 적의 피칸이 30개이다.
			healthBar = new HealthBarLabel(1);
			// healthBar.setSize(300,40);
			healthBar.setLocation(880, 560);
			
			enemy.setSize(200,200);
			enemy.setOpaque(false);
			enemy.setLocation(700,420);
			enemy.setIcon(enemyIcon);
			
			kirby.setSize(100,100);
			kirby.setOpaque(false);
			kirby.setLocation(500,500);
			kirby.setIcon(kirbyIcon);
			add(healthBar);
			add(enemy);
			add(kirby);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			
			  //스테이지마다 배경을 다르게 표시함 
			if(scorePanel.getStage()==1) { forestImg = panelBackgroundIcon[0].getImage(); } 
			else if(scorePanel.getStage()==2) {forestImg = panelBackgroundIcon[1].getImage(); } 
			else { forestImg = panelBackgroundIcon[2].getImage(); }
			
			  
			 
			g.drawImage(forestImg, 0, 0, getWidth(), getHeight(), this);

		}

		// 적이 데미지를 입었을때의 함수
		public void damageEnemy() {
			healthBar.consume();
			
		
			
		}

		// 적의 체력이 채워진채로 그린다.
		public void repaintBar() {
			healthBar.setHealthBar();
		}

	}

	// 게임시작버튼을 누른 이후 실행됨
	public void start() {
		//게임이 일시정지가 아니라 초기화후 실행 처음실행일때
		if (!gameStart) {
			wordMaker = new WordMaker();

			gameStart = true;
			gaming = true;

			wordMaker.start();

			// 적의 체력바 초기화
			groundPanel.repaintBar();

			inputField.setEnabled(true);
			inputField.requestFocus();

		} else if (gameStart == true) {

			gaming = true;
			wordMaker.resumeCreateWord();
			inputField.setEnabled(true);
			inputField.requestFocus();
		}

	}

	public void pause() {
		// 게임중을 멈춘다.
		gaming = false;
		// 텍스트필드 비활성화
		inputField.setEnabled(false);

	}

	public void stop() {
		gameStart = false;
		gaming = false;
		// 화면상의 모든 라벨들 제거
		for (int i = 0; i < wordLabels.size(); i++) {
			wordLabels.get(i).setVisible(false);
			wordLabels.get(i).setValid(false);
			// wordLabels.remove(i);
		}

		wordMaker.interrupt();

	}

	// 적의 피칸을 만든다.
	private class HealthBarLabel extends JLabel {
		private int barSize = 0; // 현재 그려져야 할 바의 크기
		private int maxBarSize; // 바의 최대 크기 피가 50칸이면 50
		private int oneSpace; // 피 한칸의 길이

		public HealthBarLabel(int maxBarSize) {

			setSize(300, 20);
			setBackground(Color.gray);
			setOpaque(true);
			this.maxBarSize = maxBarSize;

			oneSpace = (int) (Math.floor(this.getWidth() / maxBarSize));
			barSize = maxBarSize;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(153, 0, 76));
			int width = barSize * oneSpace;
			if (width == 0)
				return;
			g.fillRect(0, 0, width, this.getHeight());
		}

		public void consume() {
			//무기강화아이템을 샀다면
			if(storePanel.hasEnhanceItem()==1) {
				barSize-=2;
			}
			else {
			barSize--;
			}
			// 피가 0이되면 초기화
			if (barSize <= 0) {
				barSize = maxBarSize;
				repaint();
				// 다음단계로 진행
				scorePanel.increaseStage();

			}
			repaint();

		}

		public void setHealthBar() {
			// 적의 체력이 채워진채로 그린다.
			barSize = maxBarSize;
			repaint();

		}

	}

}
