import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
//점수 패널과 생명을 보여주며 현 단계와 스테이지의 정보를 담고 있다.
public class ScorePanel extends JPanel{
	
	private int score=0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	
	private int heartCount;
	private JLabel heart[] ;
	private ImageIcon heartImg = new ImageIcon("heart.png");
	
	private int stage; 
	private int step;
	private String stageName[] = {"평화","전쟁","지옥"};
	
	private static String nickName;
	private GamePanel gamePanel;
	
	private StagePanel stagePanel ;
	
	private GameFrame gameFrame;
	public ScorePanel(GameFrame gameFrame) {
		this.gameFrame  = gameFrame; 
		init();
		stagePanel = new StagePanel();
		setLayout(new BorderLayout());
		
		AvatarPanel avatarPanel = new AvatarPanel();
		add(avatarPanel,BorderLayout.NORTH);
		add(stagePanel,BorderLayout.CENTER);
		
		//점수패널 만들기
		scoreLabel.setFont(new Font("Gothic",Font.BOLD,40));
		scoreLabel.setForeground(Color.RED);
		JPanel sc = new JPanel();
		sc.add(scoreLabel);
		add(sc,BorderLayout.SOUTH);
		
		//사용자가 입력한 닉네임을 받아 화면상에 출력하기위해 만든 리스너
		ScorePanel.this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				JLabel nickLabel = avatarPanel.getNickLabel();
				nickLabel.setText(nickName);
			}
		});
	}
	
	public void init() {
		stage = 1; //첫번째 평화 스테이지
		step = 1; //첫 단계
		//nickName = "커비";
		heartCount = 4; //생명의 개수는 4로 설정
		heart = new JLabel[4]; //4개의 하트이미지라벨을 만들기 위한 생성
		
		
	}
	public static void  setNickName(String name) {
		nickName = name;
	}
	public int getStage() {
		return stage;
	}
	public int getStep() {
		return step;
	}
	
	//단계를 올린다. 만약 3단계에서 이 함수가 실행되면 게임클리어 함수가 실행된다.
	public void increaseStage() {
		step+=1;
		if(step>3)
		{
			
			gameEnd("축하합니다!"+nickName+"님은 "+Integer.toString(score)+"점으로 클리어 했습니다.",true);
			
		}
		else {
			//하트의 개수 4개로 다시 초기화
			heartCount=4;
			stagePanel.setStageLabel();
			//다음단계로 넘어가기위해 3초간 쉬고 새롭게 시작된다.
			gamePanel.stop();
			gamePanel.start();
			
		}
	}
	//isWin 게임 이겼는지의 여부
	public void gameEnd(String msg, boolean isWin)
	{
		//게임 멈추기
		gamePanel.stop();
		
		new GameEndDialog(msg,isWin);
		
		//하트와 점수 초기화
		heartCount=4;
		score=0;
		
		//게임에 졌으면 현 스테이지 1단계로 초기화
		if(!isWin) {
			 step=1;
			//단계와 하트를 repaint
			stagePanel.setStageLabel();
			//점수를 repaint
			printScore();
			gamePanel.start();
			return;
		}
		
		//게임에 이겼으면	
			
			stage+=1;
			step=1;
			//3스테이지도 끝난 경우 
			if(stage>3)
			{
				stage=3;
				step=1;
				JOptionPane.showConfirmDialog(null, "모든 스테이지가 클리어되었으므로 다시 지옥스테이지가 실행됩니다.", "현재 지옥 스테이지", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				
			}
				
			//배경 바꾸기
			gamePanel.repaint();
			//단계와 하트 초기화된 것 화면에 보이게하기
			stagePanel.setStageLabel();
			//점수 초기화된것 보이게하기
			printScore();
			
			gamePanel.start();
		
		
		
		
	}
	/* 스테이지는 그대로 단계는 1단계로 초기화 후 repaint
	 * 하트 4개 다시 구성 및 repaint  점수 0으로 수정 및 repaint
	 */
	//게임 새롭게 시작할때 
	public void initGame(){
		heartCount = 4;
		step = 1;
		score=0;
		
		//단계와 하트를 repaint
		stagePanel.setStageLabel();
		
		//점수를 repaint
		printScore();
		
	}
	public void getGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public class AvatarPanel extends JPanel{
		private ImageIcon basicImg = new ImageIcon("basicKirby.png");
		private JLabel imgLabel = new JLabel(basicImg);
		//닉네임 라벨 (추후에 사용자가 입력한 값을 가져오도록 해야함
		private JLabel nickNameLabel = new JLabel(nickName);
		
		public AvatarPanel() {
			setLayout(new FlowLayout(FlowLayout.LEFT));
			nickNameLabel.setFont(new Font("Gothic",Font.BOLD,40));
			nickNameLabel.setForeground(Color.magenta);
			
			
			
			add(imgLabel);
			add(nickNameLabel);
		}
		
		public JLabel getNickLabel() {
			return nickNameLabel;
		}
		
		
		
	}
	public class StagePanel extends JPanel{
		private JLabel stageLabel = new JLabel("");
		
		
		
		public StagePanel() {
			setLayout(null);
			//아바타의 4칸의 피 생성
			makeHeart();
			setStageLabel();
			stageLabel.setFont(new Font("Gothic",Font.BOLD,30));
			stageLabel.setForeground(Color.BLACK);
			stageLabel.setOpaque(true);
			//stageLabel.setBackground(Color.YELLOW);
			stageLabel.setLocation(70,0);
			
			stageLabel.setSize(250,40);
			add(stageLabel);
			
			
			//4개의 피를 패널에 붙임
			for(int i=0; i<heart.length; i++)
			{
				add(heart[i]);
			}
		}
		//단계만 올라갔을때 쓰는 메소드
		public void setStageLabel() {
			String steps = Integer.toString(step);
			
			stageLabel.setText(steps+"step"+" ["+stageName[stage-1]+"]");
			printHeart();
			
			
		}
		
		
	}
	
	
	
	public void damageHeart() {
		if(heartCount==1)
		{
			if(EditPanel.revival>0) {
				EditPanel.revival-=1;
				System.out.println("부활개수는 "+EditPanel.revival);
				return;
			}
			
			// 하트가 1개인데 또 깎이면 게임 종료
			gameEnd(nickName+"님은 "+Integer.toString(score)+"점을 얻고 게임에 실패 했습니다.",false);
			
		}
		else {
			heartCount-=1;
			
			printHeart();
			
		}
	}
	
	
	public void printHeart() {
		for(int i=0; i<heart.length; i++)
		{
			//하트를 지운다.
			if(i>=heartCount)
			{
				heart[i].setVisible(false);
			}
			else {
				heart[i].setVisible(true);
			}
		}
	}
	
	
	
	public void printScore() {
		
		scoreLabel.setText(Integer.toString(this.score));
	
	}
	
	
	
	public void makeHeart() {
		for(int i=0; i<heart.length; i++)
		{
			heart[i] = new JLabel(heartImg);
			heart[i].setOpaque(true);
			heart[i].setSize(50,50);
			heart[i].setLocation(50*i+80,60);
			
		}
	}
	
	
	
	public void increaseScore(int score) {
		
		
		this.score +=score;
		
		scoreLabel.setText(Integer.toString(this.score));
		
		
	}
	
	private class GameEndDialog extends JDialog{
		
		JLabel msgLabel;
		int resScore;
		JButton nextBtn = new JButton("다음단계");
		JButton cancelBtn = new JButton("나가기");
		JButton againBtn = new JButton("다시하기");
		public GameEndDialog(String message , boolean isWin) {
			super(gameFrame,"게임 종료",true);
			setLayout(new FlowLayout());
			setSize(700,300);
			msgLabel = new JLabel(message);
			add(msgLabel);
			makeBtnListener();
			//최종 점수
			resScore = score*heartCount;
			
			if(isWin) {
				add(nextBtn);
				add(cancelBtn);
				Ranking.saveScore(nickName, resScore);
			}
			else {
				add(againBtn);
				add(cancelBtn);
				Ranking.saveScore(nickName, resScore);
			}
			
			
			
			
			setVisible(true);
		}
		
		public void makeBtnListener() {
			nextBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GameEndDialog.this.setVisible(false);
					GameEndDialog.this.dispose();
				}
			});
			
			cancelBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					
					// 프로그램 종료
					System.exit(0);
				}
			});
			
			againBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GameEndDialog.this.setVisible(false);
					GameEndDialog.this.dispose();
				}
			});
		}
		
	}
}
