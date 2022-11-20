import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;


public class GameFrame extends JFrame {
	/*
	 * private ImageIcon normalIcon = new ImageIcon("nomal.png.jpg"); private
	 * ImageIcon rolloverIcon = new ImageIcon("rollover.jpg"); private ImageIcon
	 * pressedIcon = new ImageIcon("pressed.jpg");
	 */
	
	//private JButton startBtn = new JButton(normalIcon);
	
	
	  private ImageIcon startIcon = new ImageIcon("start-removebg-preview.png");
	  private ImageIcon pauseIcon = new ImageIcon("pause1-removebg-preview.png");
	  private ImageIcon stopIcon = new ImageIcon("pause-removebg-preview.png");
	  private JButton startBtn = new JButton(startIcon);
	  private JButton pauseBtn = new JButton(pauseIcon);
	  private JButton stopBtn = new JButton(stopIcon);
	 
	
	private WordList wordList = new WordList();
	private ScorePanel scorePanel = new ScorePanel();
	
	private EditPanel editPanel  = new EditPanel();
	
	private GamePanel gamePanel = new GamePanel(wordList,scorePanel,this,editPanel);
	
	
	
	public GameFrame() {
		super("단어 게임");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//Image normalImage = normalIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		//Image rolloverImage = rolloverIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		//Image pressedImage = pressedIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
	//	startBtn.setPressedIcon(pressedIcon);
	//	startBtn.setRolloverIcon(rolloverIcon);
		makeMenu();
		makeToolBar();
		makeSplitPane();
		
		
		setVisible(true);
		
	
	}
	
	private void makeMenu() {
		GameMenuBar bar = new GameMenuBar();
		this.setJMenuBar(bar);
		/*
		 * JMenu fileMenu = new JMenu("File"); JMenu editMenu = new JMenu("Edit"); JMenu
		 * sourceMenu = new JMenu("source"); bar.add(fileMenu); bar.add(editMenu);
		 * bar.add(sourceMenu);
		 * 
		 * 
		 * JMenuItem newItem = new JMenuItem("New File"); JMenuItem openItem = new
		 * JMenuItem("Open File"); JMenuItem exitItem = new JMenuItem("Exit File");
		 * fileMenu.add(newItem); fileMenu.add(openItem); fileMenu.addSeparator();
		 * fileMenu.add(exitItem);
		 */
		
		
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

