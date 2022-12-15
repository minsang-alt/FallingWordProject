import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class IntroFrame extends JFrame {
	
	public IntroFrame() {
		setLayout(null);
		setSize(600,500);
		setContentPane(new introPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setUndecorated(true);
		setVisible(false);
	}
	class introPanel extends JPanel{
		
		public introPanel() {
			setResizable(false);
			JButton startBtn = new JButton("게임 시작");
			setLayout(null);
			startBtn.setLocation(180,200);
			startBtn.setSize(150,40);
			startBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//닉네임 입력
					String nickname = JOptionPane.showInputDialog("아이디를 입력하세요");
					ScorePanel.setNickName(nickname);
					if(nickname==null || nickname.equals("")) {
						return;
					}
					
				
					
					
					SingletonClass sc = SingletonClass.getInstance();
					sc.intro.setVisible(false);
					sc.game.setVisible(true);
				}
			});
			
			JButton rankingBtn = new JButton("순위보기");
			rankingBtn.setLocation(180,250);
			rankingBtn.setSize(150,40);
			rankingBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					new Ranking();
				}
			});
			
			add(startBtn);
			add(rankingBtn);
			
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(new Color(0x00ff00ff));
			g.setFont(new Font("Jokerman",Font.ITALIC,50));
			g.drawString("Falling Word GAME", 50, 100);
		}
	}
	
}
