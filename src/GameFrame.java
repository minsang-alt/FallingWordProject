import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;

public class GameFrame extends JFrame {
	private ImageIcon normalIcon = new ImageIcon("nomal.png.jpg");
	private ImageIcon rolloverIcon = new ImageIcon("rollover.jpg");
	private ImageIcon pressedIcon = new ImageIcon("pressed.jpg");
	
	private JButton startBtn = new JButton(normalIcon);
	
	
	private WordList wordList = new WordList();
	private ScorePanel scorePanel = new ScorePanel();
	
	private EditPanel editPanel  = new EditPanel();
	
	private GamePanel gamePanel = new GamePanel(wordList,scorePanel,this);
	
	
	public GameFrame() {
		super("단어 게임");
		setSize(1200,800);
		//Image normalImage = normalIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		//Image rolloverImage = rolloverIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		//Image pressedImage = pressedIcon.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
		startBtn.setPressedIcon(pressedIcon);
		startBtn.setRolloverIcon(rolloverIcon);
		makeMenu();
		makeToolBar();
		makeSplitPane();
		setVisible(true);
		
	
	}
	
	private void makeMenu() {
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu sourceMenu = new JMenu("source");
		bar.add(fileMenu);
		bar.add(editMenu);
		bar.add(sourceMenu);
		
		
		JMenuItem newItem = new JMenuItem("New File");
		JMenuItem openItem = new JMenuItem("Open File");
		JMenuItem exitItem = new JMenuItem("Exit File");
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		
		
	}
	
	//툴바는 컨텐트팬에 부착
	private void makeToolBar() {
		JToolBar tBar = new JToolBar();
		startBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String word = wordList.getWord();
				//gamePanel.setWord(word);
			}
		}
		);
		tBar.add(startBtn);
		
		getContentPane().add(tBar,BorderLayout.NORTH);
		
	}
	
	private void makeSplitPane() {
		JSplitPane hPane  = new JSplitPane();
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(900);
		getContentPane().add(hPane,BorderLayout.CENTER);
		
		JSplitPane vPane  = new JSplitPane();
		vPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		vPane.setDividerLocation(300);
		hPane.setRightComponent(vPane);
		hPane.setLeftComponent(gamePanel);
		
		vPane.setTopComponent(scorePanel);
		vPane.setBottomComponent(editPanel);
	}
}

