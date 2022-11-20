import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

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
	
	private String nickName;
	private GamePanel gamePanel;
	
	private StagePanel stagePanel ;
	public ScorePanel() {
		
		init();
		stagePanel = new StagePanel();
		setLayout(new BorderLayout());
		
		
		add(new AvatarPanel(),BorderLayout.NORTH);
		add(stagePanel,BorderLayout.CENTER);
		
		//점수패널 만들기
		scoreLabel.setFont(new Font("Gothic",Font.BOLD,40));
		scoreLabel.setForeground(Color.RED);
		JPanel sc = new JPanel();
		sc.add(scoreLabel);
		add(sc,BorderLayout.SOUTH);
	}
	
	public void init() {
		stage = 1; //첫번째 평화 스테이지
		step = 1; //첫 단계
		nickName = "커비";
		heartCount = 4; //생명의 개수는 4로 설정
		heart = new JLabel[4]; //4개의 하트이미지라벨을 만들기 위한 생성
		
		
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
			//gameEnd("축하합니다!"+nickName+"님은 "+Integer.toString(score)+"점으로 클리어 했습니다.");
			
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
			
			
			//imgLabel.setSize(basicImg.getIconWidth(),basicImg.getIconHeight());
			add(imgLabel);
			add(nickNameLabel);
		}
		
		
		
		
	}
	public class StagePanel extends JPanel{
		private JLabel stageLabel = new JLabel("");
		
		
		
		public StagePanel() {
			setLayout(null);
			setStageLabel();
			stageLabel.setFont(new Font("Gothic",Font.BOLD,30));
			stageLabel.setForeground(Color.BLACK);
			stageLabel.setOpaque(true);
			//stageLabel.setBackground(Color.YELLOW);
			stageLabel.setLocation(70,0);
			
			stageLabel.setSize(250,40);
			add(stageLabel);
			
			//아바타의 4칸의 피 생성
			makeHeart();
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
			printHeart();
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
		}
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
}
