import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class GameMenuBar extends JMenuBar{
	private JMenu [] menu= new JMenu[4];
	private String[] menuTitle = {"게임","단어", "설정", "도움말"};
	
	//private AddWordFrame addwordframe = null;
	public GameMenuBar() {
		//this.gameFrame = gameFrame;
		for(int i=0; i<menu.length; i++)
		{
			
			menu[i] = new JMenu(menuTitle[i]);
			add(menu[i]);
			
		}
		
		//단어 추가 아이템 생성
		JMenuItem newWordItem = new JMenuItem("단어 생성");
		newWordItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				/*
				 * if(addwordframe == null){ addwordframe = new AddWordFrame(); }else{
				 * addwordframe.dispose(); addwordframe = new AddWordFrame(); }
				 */
				new AddWordDialog();
			}
		});
		menu[1].add(newWordItem);
	}
	
}
class AddWordDialog extends JDialog {
	private JLabel la = new JLabel("저장하고 싶은 파일을 선택해주세요");
	private String [] fileNames = {"korword.txt","engwords.txt"};
	private JTextField inputWordField = new JTextField(20);
	private JButton checkBtn = new JButton("단어 추가");
	private JComboBox<String> fileCombo;
	public AddWordDialog() {
		setTitle("단어추가");
		setLayout(null);
		setSize(400,300);
		la.setLocation(60,40);
		la.setSize(200,30);
		
		fileCombo = new JComboBox<String>(fileNames); //파일 이름 콤보박스 생성
		fileCombo.setLocation(270,40);
		fileCombo.setSize(100,50);
		
		inputWordField.setLocation(60,200);
		inputWordField.setSize(200,30);
		
		
		checkBtn.setSize(90,40);
		checkBtn.setLocation(270,200);
		BtnActionListener listener = new BtnActionListener();
		checkBtn.addActionListener(listener);
		add(la);
		add(fileCombo);
		add(inputWordField);
		add(checkBtn);
		setVisible(true);
	}
	
	class BtnActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int getSelectedFileName = fileCombo.getSelectedIndex();
			String fileName = fileNames[getSelectedFileName];
			File f = new File(fileName);
			//아무것도 입력한게 없으면 리턴
			if(inputWordField.getText().equals("") || inputWordField.getText().equals("단어를 입력해주세요") || inputWordField.getText().equals("추가 되었습니다!") ) {
				inputWordField.setText("단어를 입력해주세요");
				
				}
			else {
				String word = inputWordField.getText();
				
					// 파일 입력 문자 스트림 생성
					try {
						BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true));
						PrintWriter pw = new PrintWriter(bw,true);
						pw.write("\n"+word);
						pw.flush();
						pw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				inputWordField.setText("추가 되었습니다!");
			}
			
		}
	}
}
