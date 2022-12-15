import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;


public class GameFrame extends JFrame {

	
	  private ImageIcon startIcon = new ImageIcon("start-removebg-preview.png");
	  private ImageIcon pauseIcon = new ImageIcon("pause1-removebg-preview.png");
	  private ImageIcon stopIcon = new ImageIcon("pause-removebg-preview.png");
	  private JButton startBtn = new JButton(startIcon);
	  private JButton pauseBtn = new JButton(pauseIcon);
	  private JButton stopBtn = new JButton(stopIcon);
	 
	
	private WordList wordList = new WordList();
	private ScorePanel scorePanel = new ScorePanel(this);
	
	private EditPanel editPanel  = new EditPanel();
	
	private GamePanel gamePanel = new GamePanel(wordList,scorePanel,this,editPanel);
	
	
	
	public GameFrame() {
		super("단어 게임");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		makeMenu();
		makeToolBar();
		makeSplitPane();
		
		
		
		setVisible(false);
		
	
	}
	
	private void makeMenu() {
		GameMenuBar bar = new GameMenuBar();
		this.setJMenuBar(bar);
		
		
	}
	
	
	  //툴바는 컨텐트팬에 부착 
	private void makeToolBar() { 
		JToolBar tBar = new JToolBar();
	 // 시작버튼을 누르면 게임 시작 
	startBtn.addActionListener(new ActionListener(){ 
		@Override
		public void actionPerformed(ActionEvent e) {
				startBtn.setEnabled(false);
				pauseBtn.setEnabled(true);
				stopBtn.setEnabled(true);
				
				gamePanel.start();
				 
				
				
	  }});
	
	//일시정지 버튼을 누르면 
	pauseBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			startBtn.setEnabled(true);
			pauseBtn.setEnabled(false);
			stopBtn.setEnabled(true);
			
			
			
			gamePanel.pause();
		}
	});
	
	//정지 버튼을 누르면 게임 초기화
	stopBtn.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			startBtn.setEnabled(true);
			pauseBtn.setEnabled(false);
			stopBtn.setEnabled(false);
			
			//스테이지는 그대로 단계만 1단계로 초기화 그리고 하트와 점수도 초기화
			scorePanel.initGame();
			
			gamePanel.stop();
		}
	});
	  
	
	  
	  tBar.add(startBtn);
	  
	  
	  //처음엔 정지버튼 비활성화 상태
	  pauseBtn.setEnabled(false);
	  tBar.add(pauseBtn);
	 
	  stopBtn.setEnabled(false);
	  tBar.add(stopBtn);
	  
	  getContentPane().add(tBar,BorderLayout.NORTH);
	  
	  }
	 
	
	private void makeSplitPane() {
		JSplitPane hPane  = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(1200);
		getContentPane().add(hPane,BorderLayout.CENTER);
		
		JSplitPane vPane  = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(320);
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gamePanel);
		
		vPane.setTopComponent(scorePanel);
		vPane.setBottomComponent(editPanel);
		
		hPane.setEnabled(false);
		vPane.setEnabled(false);
	}
}

