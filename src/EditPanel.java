import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;



public class EditPanel extends JPanel {
	private int money =100000;
	private JLabel moneyLabel = new JLabel(Integer.toString(money));
	private int enhanceItem = 0;
	protected static int revival = 0;
	public EditPanel() {
		//돈라벨 폰트및 색 설정
		moneyLabel.setForeground(Color.blue);
		moneyLabel.setFont(new Font("Gothic",Font.BOLD,20));
		JLabel coin = new JLabel("coin");
		coin.setFont(new Font("Gothic",Font.BOLD,10));
		
		setLayout(new BorderLayout());
		JLabel store = new JLabel("상점");
		//edit패널에서 상단 패널을 만듬 
		JPanel top = new JPanel();
		//상단 패널에 돈라벨과 상점이름라벨을 붙인다.
		top.add(store);
		top.add(moneyLabel);
		top.add(coin);
		add(top,BorderLayout.NORTH);
		
		//this.getWidth를 사용하기 위해서 만든 컴포넌트리스너 컴포넌트가 생성 혹은 크기가 변경될때 호출된다. 
    	EditPanel.this.addComponentListener(new ComponentAdapter() {
    		@Override
    		public void componentResized(ComponentEvent e)
    		{
    			ItemMenu itemMenu = new ItemMenu(EditPanel.this);
    			add(itemMenu,BorderLayout.CENTER);
    			EditPanel.this.removeComponentListener(this);
    		}
    	});
		
	}
	
	
	public void increaseCoin(int money) {
		this.money +=money;
		moneyLabel.setText(Integer.toString(this.money));
	}
	
	public boolean consumeCoin(int money) {
		//소비하려는 돈보다 가지고 있는 돈이 더 적을때 
		if(this.money <money) {
			JOptionPane.showMessageDialog(this, "코인이 부족합니다!", "Message",JOptionPane.ERROR_MESSAGE );
			return false;
		}
		//구매가 성공적이면
		this.money-=money;
		moneyLabel.setText(Integer.toString(this.money));
		return true;
		
	}
	
	//무기강화아이템을 소유하고있는지의 여부
	public int hasEnhanceItem() {
		return enhanceItem;
	}
	
	//무기강화아이템 소유하게 함
	public void setEnhanceItem() {
		enhanceItem = 1;
	}
}
class ItemMenu extends JPanel{
	ImageIcon enhance = new ImageIcon("itemImg/enhance.png");
	ImageIcon angel = new ImageIcon("itemImg/angel.png");
	JButton enhanceBtn = new JButton(enhance);
	JButton angelBtn = new JButton(angel);
	EditPanel editPanel;
	public ItemMenu(EditPanel editPanel) {
		//아이템들 생성
		makeItem();
		this.editPanel = editPanel;
		setLayout(null);
		this.add(enhanceBtn);
		this.add(angelBtn);
	}
	
	public void makeItem() {
		
		
		enhanceBtn.setBackground(new Color(153,76,0));
		enhanceBtn.setOpaque(true);
		enhanceBtn.setSize(80,80);
		enhanceBtn.setLocation(50,50);
		enhanceBtn.addActionListener(new BuyItemListener(3000,"무기강화","데미지를 증가시킵니다."));
		
		
		
		angelBtn.setBackground(new Color(51,255,255));
		angelBtn.setOpaque(true);
		angelBtn.setSize(80,80);
		angelBtn.setLocation(180,50);
		angelBtn.addActionListener(new BuyItemListener(1000,"부활","체력을 하나더 증가시킵니다."));
	}
	
	//아이템 클릭했을때 구매여부 물어보기
	class BuyItemListener implements ActionListener{
		int money;
		String name;
		String explain;
		BuyItemListener(int money,String name,String explain){
			this.money = money;
			this.name=name;
			
			this.explain = explain + '\n' + "가격은 "+ Integer.toString(money)+"입니다 구입하시겠습니까?";
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(null, explain, name, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)){
				if(editPanel.consumeCoin(money)) {
					//아이템 효과 부여
					if(name.equals("무기강화")) {
						editPanel.setEnhanceItem();
						//버튼 비활성화
						JButton b = (JButton)e.getSource();
						b.setEnabled(false);
					}
					else if(name.equals("부활")) {
						EditPanel.revival +=1;
					}
				}
			}
		}
	}
	
	
	
}
