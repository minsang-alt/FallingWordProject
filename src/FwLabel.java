import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class FwLabel extends JLabel {
	private boolean valid  = true; //라벨이 아직도 유효한지 체크
	private WordList wordList;
	
	private int money=0;
	private int score=0;
	public FwLabel(WordList wordList)
	{
		this.wordList = wordList;
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
			money = 0;
			score=50;
			
		}
		else if(0.5<num&&num<=0.8) //노랑색 라벨 생성
		{
			setForeground(Color.yellow);
			String word = wordList.korGetWord();
			setText(word);
			money = 100;
			score=100;
		}
		else {//빨강색 라벨 생성
			setForeground(Color.red);
			String word = wordList.engGetWord();
			setText(word);
			money = 200;
			score=200;
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
