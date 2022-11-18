import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;

public class ScorePanel extends JPanel{
	private int score=0;
	private JLabel scoreLabel = new JLabel(Integer.toString(score));
	private int heartCount = 4;
	private JLabel heart[] = new JLabel[4];
	private ImageIcon heartImg = new ImageIcon("heart.png");
	public ScorePanel() {
		setLayout(new BorderLayout());
		
		
		add(new AvatarPanel(),BorderLayout.NORTH);
		add(new StagePanel(),BorderLayout.CENTER);
		
		//점수패널 만들기
		scoreLabel.setFont(new Font("Gothic",Font.BOLD,40));
		scoreLabel.setForeground(Color.RED);
		JPanel sc = new JPanel();
		sc.add(scoreLabel);
		add(sc,BorderLayout.SOUTH);
	}
	
	public class AvatarPanel extends JPanel{
		private ImageIcon basicImg = new ImageIcon("basicKirby.png");
		private JLabel imgLabel = new JLabel(basicImg);
		//닉네임 라벨 (추후에 사용자가 입력한 값을 가져오도록 해야함
		private JLabel nickNameLabel = new JLabel("커비");
		
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
		private JLabel stageLabel = new JLabel("1step [평화]");
		
		
		
		public StagePanel() {
			setLayout(null);
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
