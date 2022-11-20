import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class FwLabel extends JLabel {
	
	private boolean valid  = true; //라벨이 아직도 유효한지 체크
	
	private WordList wordList;
	
	private ScorePanel scorePanel;
	
	private int money=0;
	private int score=0;
	
	private int stage;
	private int step;
	public FwLabel(WordList wordList,ScorePanel scorePanel)
	{
		this.scorePanel = scorePanel;
		this.wordList = wordList;
		
		stage = scorePanel.getStage();
		step = scorePanel.getStep();
		
		//라벨 빨강색,노랑색,파랑색 무작위로 선정(우선순위는 오른쪽부터)
		randomWord();
		setFont(new Font("Gothic",Font.BOLD,20));
		setSize(120,40);
		
		
		
	}
	public void randomWord() {
		//random은 0<=x<1 x가 0.5이하 x가 0.5초과 0.9이하 x가 0.9초과 1미만
		double num = Math.random();
		if(num<=0.5) { //검은색 라벨 생성
			setForeground(Color.black);
			String word = wordList.korGetWord();
			setText(word);
			
			switch(stage) {
			case 1: money=0; score=50; break;
			case 2: money=0; score=75; break;
			case 3: money=0; score=110; break;
			}
			
			
			
			
		}
		else if(0.5<num&&num<=0.8) //노랑색 라벨 생성
		{
			setForeground(Color.yellow);
			String word = wordList.korGetWord();
			setText(word);
			
			switch(stage) {
			case 1: money=100; score=100; break;
			case 2: money=150; score=150; break;
			case 3: money=220; score=220; break;
			}
			
		}
		else {//빨강색 라벨 생성
			setForeground(Color.red);
			String word = wordList.engGetWord();
			setText(word);
			
			switch(stage) {
			case 1: money=200; score=200; break;
			case 2: money=300; score=300; break;
			case 3: money=500; score=500; break;
			}
			
		}
	}
	public int getScore() {
		return score;
	}
	public int getMoney() {
		return money;
	}
	public void setValid(boolean valid)
	{
		this.valid = valid;
	}
	public boolean getValid() {
		return valid;
	}
}
